package com.agententerprise.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KnowledgeService {

    private final OllamaClient ollamaClient;
    private final QdrantVectorStore vectorStore;

    public KnowledgeService(OllamaClient ollamaClient, QdrantVectorStore vectorStore) {
        this.ollamaClient = ollamaClient;
        this.vectorStore = vectorStore;
    }

    /** 将文档按简单字符窗口切分，原型阶段先保证流程可运行，后续再增加Markdown/PDF解析。 */
    public int index(String document) {
        if (document == null || document.trim().isEmpty()) return 0;
        int indexed = 0;
        for (int start = 0; start < document.length(); start += 800) {
            String chunk = document.substring(start, Math.min(start + 800, document.length())).trim();
            if (chunk.isEmpty()) continue;
            if (vectorStore.upsert(chunk, ollamaClient.embedding(chunk))) indexed++;
        }
        return indexed;
    }

    public List<String> search(String query) {
        float[] vector = ollamaClient.embedding(query);
        return vector == null ? Collections.<String>emptyList() : vectorStore.search(vector, 4);
    }
}
