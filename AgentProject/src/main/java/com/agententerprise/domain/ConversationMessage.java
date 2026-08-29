package com.agententerprise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Agent会话消息，按会话保存最近上下文。
 */
@Entity
@Table(name = "agent_conversation_message")
public class ConversationMessage {

    /**
     * 消息主键。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 所属会话ID。
     */
    @Column(name = "conversation_id", nullable = false, length = 64)
    private String conversationId;
    /**
     * 消息角色：user或assistant。
     */
    @Column(nullable = false, length = 16)
    private String role;
    /**
     * 消息正文。
     */
    @Column(nullable = false, length = 12000)
    private String content;
    /**
     * 消息时间。
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected ConversationMessage() {
    }

    public ConversationMessage(String conversationId, String role, String content, LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
