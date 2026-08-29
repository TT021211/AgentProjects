# 当前项目流程图

本文档按当前源码绘制 FlowPilot 原型的运行流程，包含工作台加载、Agent 对话、知识库索引和飞书事件四条链路。

## 总体流程

```mermaid
    graph TD
    START([用户打开工作台]) --> APP["Vue App.vue"]
    APP --> LOAD["onMounted loadDashboard"]
    LOAD -->|并行请求| SUMMARY["GET /api/dashboard/summary"]
    LOAD -->|并行请求| INVENTORY["GET /api/inventory"]
    LOAD -->|并行请求| ORDERS["GET /api/orders"]

    SUMMARY --> DC[DashboardController]
    DC --> DS[DashboardService]
    DS --> PRODUCT_REPO[ProductRepository]
    DS --> INVENTORY_REPO_1[InventoryRepository]
    DS --> ORDER_REPO_1[SalesOrderRepository]
    PRODUCT_REPO --> MYSQL[(MySQL)]
    INVENTORY_REPO_1 --> MYSQL
    ORDER_REPO_1 --> MYSQL
    DS --> LOW_STOCK{"可用库存低于安全库存?"}
    LOW_STOCK --> SUMMARY_RESPONSE[返回 DashboardSummary]
    SUMMARY_RESPONSE --> DASHBOARD[更新工作台指标]

    INVENTORY --> IC[InventoryController]
    IC --> IS[InventoryService]
    IS --> KEYWORD{是否有搜索关键词?}
    KEYWORD -->|是| INVENTORY_SEARCH[按产品编码/仓库编码查询]
    KEYWORD -->|否| INVENTORY_ALL[查询全部库存]
    INVENTORY_SEARCH --> INVENTORY_REPO_2[InventoryRepository]
    INVENTORY_ALL --> INVENTORY_REPO_2
    INVENTORY_REPO_2 --> MYSQL
    IS --> PRODUCT_REPO_2[ProductRepository 查询产品名称]
    PRODUCT_REPO_2 --> MYSQL
    IS --> INVENTORY_VIEW[组装 InventoryView 并计算可用库存]
    INVENTORY_VIEW --> DASHBOARD

    ORDERS --> OC[OrderController]
    OC --> OS[OrderService]
    OS --> ORDER_REPO_2[SalesOrderRepository]
    ORDER_REPO_2 --> MYSQL
    OS --> ORDER_VIEW[取最近20条并组装 OrderView]
    ORDER_VIEW --> DASHBOARD

    APP -->|输入问题/快捷场景| CHAT_REQUEST[POST /api/agent/chat]
    CHAT_REQUEST --> AC[AgentController]
    AC --> VALIDATE{"@Valid：message 非空?"}
    VALIDATE -->|否| HTTP_400[返回 HTTP 400]
    VALIDATE -->|是| AS[AgentService.chat]
    AS --> ROLE[解析角色 role]
    ROLE --> ROLE_DEFAULT{角色缺失或非法?}
    ROLE_DEFAULT -->|是| SALES[降级为 SALES]
    ROLE_DEFAULT -->|否| ROLE_OK[使用指定角色]
    SALES --> CONTEXT
    ROLE_OK --> CONTEXT

    CONTEXT["按问题关键词构建业务上下文"]
    CONTEXT --> STOCK_WORDS{"命中库存/交付/采购关键词?"}
    STOCK_WORDS -->|是| INVENTORY_REPO_3["InventoryRepository.findAll"]
    STOCK_WORDS -->|否| SKIP_STOCK[跳过库存查询]
    INVENTORY_REPO_3 --> MYSQL
    CONTEXT --> ORDER_WORDS{"命中订单/交付/销售关键词?"}
    ORDER_WORDS -->|是| ORDER_REPO_3["SalesOrderRepository.findTop20..."]
    ORDER_WORDS -->|否| SKIP_ORDER[跳过订单查询]
    ORDER_REPO_3 --> MYSQL
    SKIP_STOCK --> KNOWLEDGE
    SKIP_ORDER --> KNOWLEDGE
    MYSQL --> KNOWLEDGE

    CONTEXT --> MES_WORDS{"命中生产/MES关键词?"}
    MES_WORDS -->|是| MES_SERVICE[MesProductionService固定只读查询]
    MES_WORDS -->|否| SKIP_MES[跳过MES查询]
    MES_SERVICE --> MES_DB[(rsmes_cloud)]
    MES_DB --> MES_CONTEXT[生产工单概览和查询时间]
    SKIP_MES --> KNOWLEDGE
    MES_CONTEXT --> KNOWLEDGE

    KNOWLEDGE["KnowledgeService.search"]
    KNOWLEDGE --> EMBED_QUERY["OllamaClient.embedding"]
    EMBED_QUERY --> OLLAMA_EMBED["Ollama Embedding API"]
    OLLAMA_EMBED --> EMBED_OK{"向量有效且维度为1024?"}
    EMBED_OK -->|否/异常| NO_SOURCE[空知识库结果]
    EMBED_OK -->|是| QDRANT_SEARCH[QdrantVectorStore.search]
    QDRANT_SEARCH --> QDRANT["Qdrant enterprise_knowledge"]
    QDRANT --> SEARCH_OK{"查询成功?"}
    SEARCH_OK -->|否/异常| NO_SOURCE
    SEARCH_OK -->|是| SOURCES[返回最多4条知识片段]

    KNOWLEDGE --> PROMPT[拼接系统提示词、用户问题、业务上下文和知识库参考]
    NO_SOURCE --> PROMPT
    SOURCES --> PROMPT
    PROMPT --> CHAT_MODEL["OllamaClient.chat"]
    CHAT_MODEL --> OLLAMA_CHAT["Ollama Chat API (qwen3:8b)"]
    OLLAMA_CHAT --> ANSWER_OK{"回答非空?"}
    ANSWER_OK -->|是| MODEL_ANSWER[使用模型回答]
    ANSWER_OK -->|否/异常| FALLBACK[规则降级回答]
    MODEL_ANSWER --> ACTIONS[按关键词生成建议动作]
    FALLBACK --> ACTIONS
    ACTIONS --> ACTION_MATCH{"是否命中业务关键词?"}
    ACTION_MATCH -->|是| SUGGESTIONS[返回采购/交付/产能/仓储建议]
    ACTION_MATCH -->|否| MORE_INFO[提示补充业务对象、数量和日期]
    SUGGESTIONS --> CHAT_RESPONSE[返回 AgentChatResponse]
    MORE_INFO --> CHAT_RESPONSE
    CHAT_RESPONSE --> APP_REPLY[Vue 展示回答和建议]

    APP -->|管理知识库| INDEX_REQUEST[POST /api/knowledge/index]
    INDEX_REQUEST --> KC[KnowledgeController]
    KC --> KS[KnowledgeService.index]
    KS --> CONTENT{"内容为空?"}
    CONTENT -->|是| INDEX_ZERO[返回 indexedChunks=0]
    CONTENT -->|否| CHUNK[按每800字符切分]
    CHUNK --> EMBED_CHUNK["为每个片段生成Embedding"]
    EMBED_CHUNK --> OLLAMA_EMBED
    OLLAMA_EMBED --> EMBED_CHUNK_OK{"片段向量有效且维度为1024?"}
    EMBED_CHUNK_OK -->|否/异常| SKIP[跳过该片段]
    EMBED_CHUNK_OK -->|是| UPSERT["QdrantVectorStore.upsert"]
    UPSERT --> COLLECTION{"集合是否存在?"}
    COLLECTION -->|否| CREATE_COLLECTION["创建1024维 Cosine 集合"]
    COLLECTION -->|是/409| WRITE_VECTOR[写入向量和 content payload]
    CREATE_COLLECTION --> WRITE_VECTOR
    WRITE_VECTOR --> INDEX_COUNT[累计成功片段数]
    INDEX_COUNT --> INDEX_RESPONSE[返回 indexedChunks]
    INDEX_ZERO --> INDEX_RESPONSE

    FEISHU["飞书平台"] -->|POST /api/feishu/events| FC[FeishuController]
    FC --> FEISHU_TYPE{"type 为 url_verification?"}
    FEISHU_TYPE -->|是| CHALLENGE[返回 challenge]
    FEISHU_TYPE -->|否| ACCEPTED[返回 accepted]
    CHALLENGE --> FEISHU
    ACCEPTED --> FEISHU

    FEISHU_WEBHOOK["FeishuClient.sendText"]
    FEISHU_WEBHOOK --> WEBHOOK_URL{"已配置 FEISHU_WEBHOOK_URL?"}
    WEBHOOK_URL -->|否| SEND_FALSE[返回 false]
    WEBHOOK_URL -->|是| SEND_TEXT[POST 飞书机器人 Webhook]
    SEND_TEXT --> SEND_RESULT[成功 true / 异常 false]

    classDef client fill:#e8f1fb,stroke:#599ce7,color:#141414
    classDef controller fill:#f1ebfb,stroke:#8d72c7,color:#141414
    classDef service fill:#eaf5ef,stroke:#3f9a66,color:#141414
    classDef external fill:#fff4e5,stroke:#d9943a,color:#141414
    classDef decision fill:#f8f0e8,stroke:#d9943a,color:#141414
    classDef response fill:#edf5f4,stroke:#4c9a8a,color:#141414

    class APP,START,DASHBOARD,APP_REPLY client
    class AC,DC,IC,OC,KC,FC controller
    class AS,DS,IS,OS,KS,KNOWLEDGE,ACTIONS service
    class MYSQL,QDRANT,OLLAMA_EMBED,OLLAMA_CHAT,FEISHU external
    class VALIDATE,ROLE_DEFAULT,STOCK_WORDS,ORDER_WORDS,MES_WORDS,EMBED_OK,EMBED_CHUNK_OK,SEARCH_OK,ANSWER_OK,ACTION_MATCH,CONTENT,COLLECTION,FEISHU_TYPE,WEBHOOK_URL,LOW_STOCK decision
    class SUMMARY_RESPONSE,CHAT_RESPONSE,INDEX_RESPONSE,CHALLENGE,ACCEPTED,SUGGESTIONS,MORE_INFO,FALLBACK response
```

## 关键边界

- 当前 Agent 读取 AgentProject MySQL、按需读取 rsmes_cloud 和 Qdrant 数据，并生成建议，不会自动创建采购单、修改订单或执行出入库。
- Ollama Chat 不可用时，`AgentService` 使用 `rule-fallback` 返回规则模式结果。
- 知识库检索失败时使用空参考内容，不会阻断 Agent 对话。
- 角色目前影响提示词和响应字段，还没有实现真正的数据权限过滤；MES只读接入也未做用户级数据范围控制。
- 飞书事件接口当前只处理 URL 验证并接受其他事件，不会调用 `AgentService`。
- `FeishuClient.sendText` 是独立 Webhook 适配器，目前没有接入 Agent 主流程。
