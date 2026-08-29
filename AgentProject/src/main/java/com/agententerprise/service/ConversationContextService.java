package com.agententerprise.service;

import com.agententerprise.domain.Conversation;
import com.agententerprise.domain.ConversationMessage;
import com.agententerprise.repository.ConversationMessageRepository;
import com.agententerprise.repository.ConversationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 管理Agent短期上下文，限制消息窗口避免历史对话无限增长。
 */
@Service
public class ConversationContextService {

    private static final Logger log = LoggerFactory.getLogger(ConversationContextService.class);
    private final ConversationRepository conversationRepository;
    private final ConversationMessageRepository messageRepository;

    public ConversationContextService(ConversationRepository conversationRepository,
                                      ConversationMessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * 创建或确认会话，并返回可供客户端继续使用的会话ID。
     */
    public String resolve(String conversationId, String role) {
        String id = conversationId == null || conversationId.trim().isEmpty()
                ? UUID.randomUUID().toString() : conversationId.trim();
        try {
            if (!conversationRepository.existsById(id)) {
                conversationRepository.save(new Conversation(id, role, LocalDateTime.now()));
            }
        } catch (RuntimeException ex) {
            // 上下文故障不应阻断生产查询和规则降级回答。
            log.warn("保存Agent会话失败: {}", ex.getMessage());
        }
        return id;
    }

    /**
     * 获取最近十条消息并按用户阅读顺序拼接。
     */
    public String recentContext(String conversationId) {
        try {
            List<ConversationMessage> messages = new ArrayList<>(
                    messageRepository.findTop10ByConversationIdOrderByCreatedAtDesc(conversationId));
            Collections.reverse(messages);
            StringBuilder context = new StringBuilder();
            for (ConversationMessage message : messages) {
                context.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
            }
            return context.length() == 0 ? "暂无历史对话。" : context.toString();
        } catch (RuntimeException ex) {
            log.warn("读取Agent会话失败: {}", ex.getMessage());
            return "历史对话暂不可用。";
        }
    }

    /**
     * 保存本轮用户消息和Agent回答。
     */
    public void append(String conversationId, String userMessage, String assistantMessage) {
        try {
            LocalDateTime now = LocalDateTime.now();
            messageRepository.save(new ConversationMessage(conversationId, "user", userMessage, now));
            messageRepository.save(new ConversationMessage(conversationId, "assistant", assistantMessage, now));
            conversationRepository.findById(conversationId).ifPresent(conversation -> {
                conversation.setUpdatedAt(now);
                conversationRepository.save(conversation);
            });
        } catch (RuntimeException ex) {
            log.warn("保存Agent消息失败: {}", ex.getMessage());
        }
    }
}
