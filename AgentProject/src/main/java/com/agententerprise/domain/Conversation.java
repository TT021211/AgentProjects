package com.agententerprise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * AgentProject MySQL中的会话主表，不存储MES业务事实。
 */
@Entity
@Table(name = "agent_conversation")
public class Conversation {

    /**
     * 会话UUID。
     */
    @Id
    @Column(name = "conversation_id", length = 64)
    private String conversationId;
    /**
     * 当前会话角色。
     */
    @Column(nullable = false, length = 32)
    private String role;
    /**
     * 创建时间。
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;
    /**
     * 最近更新时间。
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Conversation() {
    }

    public Conversation(String conversationId, String role, LocalDateTime now) {
        this.conversationId = conversationId;
        this.role = role;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
