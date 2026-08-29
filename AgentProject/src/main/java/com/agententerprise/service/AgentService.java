package com.agententerprise.service;

import com.agententerprise.domain.Inventory;
import com.agententerprise.domain.RoleType;
import com.agententerprise.domain.SalesOrder;
import com.agententerprise.dto.AgentChatRequest;
import com.agententerprise.dto.AgentChatResponse;
import com.agententerprise.repository.InventoryRepository;
import com.agententerprise.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业运营智能体的核心服务，负责组织上下文、调用本地模型并生成可执行建议。
 *
 * <p>当前原型只允许查询业务数据和生成建议，不会直接写入订单、库存等业务数据。</p>
 */
@Service
public class AgentService {

    /**
     * 本地 Ollama 模型客户端，用于生成自然语言回答。
     */
    private final OllamaClient ollamaClient;
    /**
     * 知识库服务，用于检索与用户问题相关的参考资料。
     */
    private final KnowledgeService knowledgeService;
    /**
     * 库存数据仓库，用于构建库存业务上下文。
     */
    private final InventoryRepository inventoryRepository;
    /**
     * 销售订单数据仓库，用于构建订单业务上下文。
     */
    private final SalesOrderRepository orderRepository;
    /**
     * MES生产事实查询服务，实时数据不依赖向量库。
     */
    private final MesProductionService mesProductionService;
    /**
     * 对话上下文服务，保存有限窗口的历史消息。
     */
    private final ConversationContextService conversationContextService;

    /**
     * 注入模型、知识库和业务数据访问依赖。
     */
    public AgentService(OllamaClient ollamaClient, KnowledgeService knowledgeService,
                        InventoryRepository inventoryRepository, SalesOrderRepository orderRepository,
                        MesProductionService mesProductionService,
                        ConversationContextService conversationContextService) {
        this.ollamaClient = ollamaClient;
        this.knowledgeService = knowledgeService;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.mesProductionService = mesProductionService;
        this.conversationContextService = conversationContextService;
    }

    /**
     * 处理一次智能体对话请求。
     *
     * <p>处理顺序为：解析角色、读取业务数据、检索知识库、调用模型、生成建议。
     * 如果本地模型不可用，则使用规则模式返回结果。</p>
     */
    public AgentChatResponse chat(AgentChatRequest request) {
        // 无法识别的角色会在 parseRole 中统一降级为销售角色。
        RoleType role = parseRole(request.getRole());
        String conversationId = conversationContextService.resolve(request.getConversationId(), role.name());
        String conversationContext = conversationContextService.recentContext(conversationId);
        // 只查询与当前问题相关的数据，避免把全部业务数据发送给模型。
        String businessContext = buildBusinessContext(request.getMessage());
        // 检索知识库，为模型补充制度、流程等非结构化信息。
        List<String> sources = knowledgeService.search(request.getMessage());

        // 系统提示词约束模型只能使用已提供的上下文，并禁止直接执行写操作。
        String systemPrompt = "你是企业运营协作平台中的" + roleLabel(role) + "智能体。"
                + "你只能基于提供的业务上下文回答，不要编造数据。"
                + "涉及创建、修改、删除、付款、出库等操作时，只能生成建议并提示人工确认。"
                + "用简洁的中文回答，明确区分事实、风险和建议。"
                + "如果上下文没有数据，必须明确说数据不可用，不要猜测。";

        // 将用户问题、结构化业务数据和知识库内容组合成模型输入。
        String userPrompt = "用户请求：\n" + request.getMessage()
                + "\n\n业务上下文：\n" + businessContext
                + "\n\n历史对话：\n" + conversationContext
                + "\n\n知识库参考：\n" + joinSources(sources);
        String answer = ollamaClient.chat(systemPrompt, userPrompt);
        boolean localModelAvailable = answer != null && !answer.trim().isEmpty();
        if (!localModelAvailable) {
            // 模型调用失败时仍返回可用结果，保证对话接口具备降级能力。
            answer = fallbackAnswer(role, request.getMessage(), businessContext);
        }

        // 根据问题中的关键词生成后续协作建议，这些建议不会自动执行。
        List<String> actions = new ArrayList<>();
        if (containsAny(request.getMessage(), "采购", "缺料", "供应商")) actions.add("生成采购申请草稿（需人工确认）");
        if (containsAny(request.getMessage(), "订单", "交付", "库存")) actions.add("执行订单交付评估");
        if (containsAny(request.getMessage(), "排产", "生产", "产能")) actions.add("查询生产计划和产能风险");
        if (containsAny(request.getMessage(), "出库", "入库", "仓库")) actions.add("生成仓储协作任务（需人工确认）");
        // 没有命中业务关键词时，引导用户补充结构化信息。
        if (actions.isEmpty()) actions.add("继续补充业务对象、数量和日期，以便执行结构化分析");
        conversationContextService.append(conversationId, request.getMessage(), answer);
        return new AgentChatResponse(conversationId, answer, role.name(), localModelAvailable ? ollamaClient.getChatModel() : "rule-fallback",
                localModelAvailable, sources, actions);
    }

    /**
     * 根据问题关键词拼装业务上下文。
     * 库存和订单数据只在可能相关时查询，减少无关数据库访问和模型输入长度。
     */
    private String buildBusinessContext(String message) {
        StringBuilder context = new StringBuilder();
        if (containsAny(message, "库存", "订单", "交付", "缺料", "采购")) {
            context.append("库存：\n");
            for (Inventory item : inventoryRepository.findAll()) {
                // 提供库存总量和已预留量，便于评估实际可用库存。
                context.append("- ").append(item.getProductCode()).append(" / ")
                        .append(item.getWarehouseCode()).append("：总库存")
                        .append(item.getQuantity()).append("，已预留").append(item.getReservedQuantity()).append("\n");
            }
        }
        if (containsAny(message, "订单", "交付", "销售")) {
            context.append("最近订单：\n");
            for (SalesOrder order : orderRepository.findTop20ByOrderByCreatedAtDesc()) {
                // 最多使用最近 20 条订单，避免上下文过大。
                context.append("- ").append(order.getOrderNo()).append("：")
                        .append(order.getCustomerName()).append("，产品 ").append(order.getProductCode())
                        .append("，数量 ").append(order.getQuantity()).append("，要求日期 ")
                        .append(order.getRequiredDate()).append("，状态 ").append(order.getStatus()).append("\n");
            }
        }
        if (containsAny(message, "生产", "工单", "排产", "产能", "车间", "设备", "报工", "MES", "mes")) {
            context.append(mesProductionService.buildAgentContext()).append("\n");
        }
        if (containsAny(message, "仓库", "仓储", "库位")) {
            context.append(mesProductionService.buildWarehouseContext()).append("\n");
        }
        // 没有匹配数据时明确告知模型，避免模型误以为存在可用业务数据。
        return context.length() == 0 ? "当前请求没有匹配的业务查询数据。" : context.toString();
    }

    /**
     * 生成不依赖本地模型的规则降级回答。
     */
    private String fallbackAnswer(RoleType role, String message, String context) {
        return "本地模型当前不可用，已切换为规则模式。\n"
                + "角色：" + roleLabel(role) + "\n"
                + "请求：" + message + "\n\n"
                + context + "\n"
                + "提示：当前仅完成信息查询和建议生成，写入业务数据前需要人工确认。";
    }

    /**
     * 解析请求中的角色名称，非法或缺失时默认使用销售角色。
     */
    private RoleType parseRole(String value) {
        if (value == null) return RoleType.SALES;
        try {
            return RoleType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return RoleType.SALES;
        }
    }

    /**
     * 将枚举角色转换为面向用户展示的中文名称。
     */
    private String roleLabel(RoleType role) {
        switch (role) {
            case PROCUREMENT:
                return "采购";
            case PRODUCTION:
                return "生产";
            case WAREHOUSE:
                return "仓储";
            case ADMIN:
                return "管理员";
            default:
                return "销售";
        }
    }

    /**
     * 判断文本是否至少包含一个指定关键词。
     */
    private boolean containsAny(String text, String... words) {
        for (String word : words) if (text.contains(word)) return true;
        return false;
    }

    /**
     * 将知识库检索结果拼接为模型可读的参考文本。
     */
    private String joinSources(List<String> sources) {
        return sources == null || sources.isEmpty() ? "暂无可用知识库内容" : String.join("\n---\n", sources);
    }
}
