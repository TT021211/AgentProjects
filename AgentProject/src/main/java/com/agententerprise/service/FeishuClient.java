package com.agententerprise.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 飞书自定义机器人Webhook适配器。正式应用接入时，在此处扩展tenant_access_token和消息卡片能力。
 */
@Service
public class FeishuClient {
    private static final Logger log = LoggerFactory.getLogger(FeishuClient.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String webhookUrl;

    public FeishuClient(RestTemplate restTemplate, ObjectMapper objectMapper,
                        @Value("${agent.feishu.webhook-url:}") String webhookUrl) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.webhookUrl = webhookUrl;
    }

    public boolean sendText(String text) {
        if (webhookUrl == null || webhookUrl.trim().isEmpty()) return false;
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("msg_type", "text");
            Map<String, String> content = new HashMap<>();
            content.put("text", text);
            body.put("content", content);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.postForEntity(webhookUrl,
                    new HttpEntity<>(objectMapper.writeValueAsString(body), headers), String.class);
            return true;
        } catch (Exception ex) {
            log.warn("发送飞书消息失败: {}", ex.getMessage());
            return false;
        }
    }
}
