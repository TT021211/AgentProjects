package com.agententerprise.repository;

import com.agententerprise.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Agent会话持久化仓储。
 */
public interface ConversationRepository extends JpaRepository<Conversation, String> {
}
