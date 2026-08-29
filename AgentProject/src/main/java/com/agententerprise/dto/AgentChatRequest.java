package com.agententerprise.dto;

import javax.validation.constraints.NotBlank;

public class AgentChatRequest {

    @NotBlank(message = "message不能为空")
    private String message;

    private String role = "SALES";

    /**
     * 可选会话ID，不传时由服务端创建新会话。
     */
    private String conversationId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
