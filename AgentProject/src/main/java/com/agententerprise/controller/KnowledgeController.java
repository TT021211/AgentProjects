package com.agententerprise.controller;

import com.agententerprise.service.KnowledgeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping("/index")
    public Map<String, Object> index(@RequestBody Map<String, String> request) {
        int indexed = knowledgeService.index(request.get("content"));
        return Collections.<String, Object>singletonMap("indexedChunks", indexed);
    }
}
