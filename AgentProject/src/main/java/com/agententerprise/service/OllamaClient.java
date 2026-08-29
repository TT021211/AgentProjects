package com.agententerprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ollama适配器。业务代码不直接依赖某个模型，后续可以替换为其他本地推理服务。
 */
@Service
public class OllamaClient {

    private static final Logger log = LoggerFactory.getLogger(OllamaClient.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String chatModel;
    private final String embeddingModel;

    public OllamaClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                        @Value("${agent.model.base-url}") String baseUrl,
                        @Value("${agent.model.chat-model}") String chatModel,
                        @Value("${agent.model.embedding-model}") String embeddingModel) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
    }

    public String chat(String systemPrompt, String userPrompt) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", chatModel);
            body.put("stream", false);
            body.put("messages", Arrays.asList(
                    message("system", systemPrompt),
                    message("user", userPrompt)));
            JsonNode response = post("/api/chat", body);
            return response == null || response.path("message").path("content").isMissingNode()
                    ? null : response.path("message").path("content").asText();
        } catch (Exception ex) {
            log.warn("调用本地模型失败，将使用规则降级: {}", ex.getMessage());
            return null;
        }
    }

    public float[] embedding(String text) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", embeddingModel);
            body.put("prompt", text);
            JsonNode response = post("/api/embeddings", body);
            JsonNode vector = response == null ? null : response.path("embedding");
            if (vector == null || !vector.isArray()) return null;
            float[] result = new float[vector.size()];
            for (int i = 0; i < vector.size(); i++) result[i] = (float) vector.get(i).asDouble();
            return result;
        } catch (Exception ex) {
            log.warn("生成向量失败: {}", ex.getMessage());
            return null;
        }
    }

    public String getChatModel() { return chatModel; }

    private JsonNode post(String path, Map<String, Object> body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = objectMapper.writeValueAsString(body);
        String response = restTemplate.postForObject(baseUrl + path, new HttpEntity<>(json, headers), String.class);
        return response == null ? null : objectMapper.readTree(response);
    }

    private Map<String, String> message(String role, String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }
}
