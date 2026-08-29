package com.agententerprise.repository;

import com.agententerprise.domain.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Agent会话消息持久化仓储。
 */
public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {

    /**
     * 按时间倒序读取最近消息，服务层会再恢复为对话顺序。
     */
    List<ConversationMessage> findTop10ByConversationIdOrderByCreatedAtDesc(String conversationId);
}
