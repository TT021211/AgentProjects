package com.agententerprise.controller;

import com.agententerprise.dto.AgentChatRequest;
import com.agententerprise.dto.AgentChatResponse;
import com.agententerprise.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/agent")
@Validated
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AgentChatResponse> chat(@Valid @RequestBody AgentChatRequest request) {
        return ResponseEntity.ok(agentService.chat(request));
    }
}
