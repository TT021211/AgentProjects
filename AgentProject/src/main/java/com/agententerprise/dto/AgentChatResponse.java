package com.agententerprise.dto;

import java.util.List;

public class AgentChatResponse {
    private String conversationId;
    private String answer;
    private String role;
    private String model;
    private boolean localModelAvailable;
    private List<String> sources;
    private List<String> suggestedActions;

    public AgentChatResponse(String conversationId, String answer, String role, String model, boolean localModelAvailable,
                             List<String> sources, List<String> suggestedActions) {
        this.conversationId = conversationId;
        this.answer = answer;
        this.role = role;
        this.model = model;
        this.localModelAvailable = localModelAvailable;
        this.sources = sources;
        this.suggestedActions = suggestedActions;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getRole() {
        return role;
    }

    public String getModel() {
        return model;
    }

    public boolean isLocalModelAvailable() {
        return localModelAvailable;
    }

    public List<String> getSources() {
        return sources;
    }

    public List<String> getSuggestedActions() {
        return suggestedActions;
    }
}
