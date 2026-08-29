package com.agententerprise.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 飞书事件订阅入口。正式接入前还需要在这里补充请求签名校验和事件去重。
 */
@RestController
@RequestMapping("/api/feishu")
public class FeishuController {

    @PostMapping("/events")
    public Map<String, String> events(@RequestBody JsonNode body) {
        if ("url_verification".equals(body.path("type").asText())) {
            return Collections.singletonMap("challenge", body.path("challenge").asText());
        }
        Map<String, String> result = new HashMap<>();
        result.put("status", "accepted");
        return result;
    }
}
