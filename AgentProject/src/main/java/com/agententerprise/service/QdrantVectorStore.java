package com.agententerprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Qdrant的最小REST客户端。使用REST而不是引入额外SDK，方便本机部署和后续替换向量库。
 */
@Service
public class QdrantVectorStore {

    private static final Logger log = LoggerFactory.getLogger(QdrantVectorStore.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String collection;
    private final int dimension;

    public QdrantVectorStore(RestTemplate restTemplate, ObjectMapper objectMapper,
                             @Value("${agent.vector-store.base-url}") String baseUrl,
                             @Value("${agent.vector-store.collection}") String collection,
                             @Value("${agent.vector-store.embedding-dimension}") int dimension) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
        this.collection = collection;
        this.dimension = dimension;
    }

    public boolean upsert(String content, float[] vector) {
        if (vector == null || vector.length != dimension) {
            log.warn("向量维度不匹配，期望{}，实际{}", dimension, vector == null ? 0 : vector.length);
            return false;
        }
        try {
            ensureCollection();
            ObjectNode point = objectMapper.createObjectNode();
            point.put("id", UUID.randomUUID().toString());
            ArrayNode vectorNode = point.putArray("vector");
            for (float value : vector) vectorNode.add(value);
            point.putObject("payload").put("content", content);

            ObjectNode body = objectMapper.createObjectNode();
            body.putArray("points").add(point);
            post("/collections/" + collection + "/points", body);
            return true;
        } catch (Exception ex) {
            log.warn("写入Qdrant失败: {}", ex.getMessage());
            return false;
        }
    }

    public List<String> search(float[] vector, int limit) {
        if (vector == null || vector.length != dimension) return Collections.emptyList();
        try {
            // 空知识库首次查询时先创建集合，避免因集合不存在返回404。
            ensureCollection();
            ObjectNode body = objectMapper.createObjectNode();
            ArrayNode vectorNode = body.putArray("vector");
            for (float value : vector) vectorNode.add(value);
            body.put("limit", limit);
            body.put("with_payload", true);
            JsonNode response = post("/collections/" + collection + "/points/search", body);
            List<String> result = new ArrayList<>();
            if (response != null && response.path("result").isArray()) {
                for (JsonNode item : response.path("result")) {
                    JsonNode content = item.path("payload").path("content");
                    if (!content.isMissingNode()) result.add(content.asText());
                }
            }
            return result;
        } catch (Exception ex) {
            log.warn("查询Qdrant失败: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    private void ensureCollection() throws Exception {
        ObjectNode vectors = objectMapper.createObjectNode();
        vectors.put("size", dimension);
        vectors.put("distance", "Cosine");
        ObjectNode body = objectMapper.createObjectNode().set("vectors", vectors);
        try {
            put("/collections/" + collection, body);
        } catch (HttpClientErrorException ex) {
            // Qdrant已存在该集合时返回409，重复初始化属于正常情况。
            if (ex.getStatusCode() != HttpStatus.CONFLICT) throw ex;
        }
    }

    private JsonNode post(String path, ObjectNode body) throws Exception {
        return request("POST", path, body);
    }

    private void put(String path, ObjectNode body) throws Exception {
        request("PUT", path, body);
    }

    private JsonNode request(String method, String path, ObjectNode body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);
        String response;
        if ("PUT".equals(method)) {
            response = restTemplate.exchange(baseUrl + path, org.springframework.http.HttpMethod.PUT, entity, String.class).getBody();
        } else {
            response = restTemplate.postForObject(baseUrl + path, entity, String.class);
        }
        return response == null ? null : objectMapper.readTree(response);
    }
}
