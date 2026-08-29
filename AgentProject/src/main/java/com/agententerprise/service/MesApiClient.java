package com.agententerprise.service;

import com.agententerprise.config.MesApiProperties;
import com.agententerprise.dto.MesProductionOverview;
import com.agententerprise.dto.MesWarehouseView;
import com.agententerprise.dto.MesWorkOrderView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ruishun-cloud API适配器。
 * AgentProject不直接依赖ruishun-cloud的OpenFeign接口，而是调用其注册服务暴露的HTTP API。
 */
@Service
public class MesApiClient {

    private static final Logger log = LoggerFactory.getLogger(MesApiClient.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MesApiProperties properties;

    public MesApiClient(RestTemplate restTemplate, ObjectMapper objectMapper, MesApiProperties properties) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    /**
     * 调用MES看板接口和分页接口组装生产概览。失败返回null，由上层决定是否数据库降级。
     */
    public MesProductionOverview productionOverview() {
        JsonNode countData = data(get("/mesProductWorkorder/getMesWorkOrdersData", properties.getMesBaseUrl()));
        if (countData == null || countData.isMissingNode() || countData.isNull()) return null;
        Integer scheduling = firstInt(countData, "schedulingNum", "schedulingWorkorder");
        Integer producing = firstInt(countData, "productionNum", "productionWorkorder");
        Integer completed = firstInt(countData, "completedNum", "completeWorkorder");
        List<MesWorkOrderView> recent = queryWorkOrders(null, null, 10);
        if (recent == null) return null;
        LocalDateTime queriedAt = LocalDateTime.now();
        long total = value(scheduling) + value(producing) + value(completed);
        return new MesProductionOverview(true, "ruishun-mes-api", queriedAt, null,
                total, value(scheduling), value(producing), value(completed), recent);
    }

    /**
     * 调用MES分页查询接口，支持工单号和状态筛选。失败返回null，空结果表示API正常但没有数据。
     */
    public List<MesWorkOrderView> queryWorkOrders(String workorderCode, Integer status, int limit) {
        String url = UriComponentsBuilder.fromHttpUrl(properties.getMesBaseUrl() + "/mesProductWorkorder/list")
                .queryParam("pageNum", 1)
                .queryParam("pageSize", Math.max(1, Math.min(limit, 200)))
                .queryParamIfPresent("workorderCode", java.util.Optional.ofNullable(blankToNull(workorderCode)))
                .queryParamIfPresent("status", java.util.Optional.ofNullable(status))
                .toUriString();
        JsonNode response = getUrl(url);
        if (response == null) return null;
        JsonNode rows = response.path("rows");
        if (!rows.isArray()) rows = data(response);
        if (rows == null || !rows.isArray()) return Collections.emptyList();
        List<MesWorkOrderView> result = new ArrayList<>();
        for (JsonNode row : rows) result.add(toWorkOrder(row));
        return result;
    }

    /**
     * 调用WMS仓库列表接口，失败返回null。
     */
    public List<MesWarehouseView> queryWarehouses() {
        String url = properties.getWmsBaseUrl() + "/wms/warehouse/getlist";
        JsonNode response = getUrl(url);
        if (response == null) return null;
        JsonNode data = data(response);
        if (data == null || !data.isArray()) return Collections.emptyList();
        List<MesWarehouseView> result = new ArrayList<>();
        for (JsonNode row : data) {
            result.add(new MesWarehouseView(longValue(row, "warehouseId"), text(row, "warehouseCode"),
                    text(row, "warehouseName"), text(row, "location"), text(row, "charge"),
                    text(row, "status"), text(row, "workstageName")));
        }
        return result;
    }

    private JsonNode get(String path, String baseUrl) {
        return getUrl(baseUrl + path);
    }

    private JsonNode getUrl(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            if (properties.getFromSource() != null && !properties.getFromSource().trim().isEmpty()) {
                headers.set("from-source", properties.getFromSource());
            }
            String body = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Void>(headers), String.class).getBody();
            return body == null ? null : objectMapper.readTree(body);
        } catch (RestClientException ex) {
            log.warn("调用ruishun-cloud API失败: {}", ex.getMessage());
            return null;
        } catch (Exception ex) {
            log.warn("解析ruishun-cloud API响应失败: {}", ex.getMessage());
            return null;
        }
    }

    private JsonNode data(JsonNode response) {
        return response == null ? null : response.path("data");
    }

    private MesWorkOrderView toWorkOrder(JsonNode row) {
        return new MesWorkOrderView(longValue(row, "id"), text(row, "workorderCode"), text(row, "orderCode"),
                text(row, "customerName"), text(row, "productModel"), text(row, "productSpecification"),
                integerValue(row, "status"), decimalValue(row, "weight"), dateValue(row, "createTime"),
                dateValue(row, "updateTime"));
    }

    private Integer firstInt(JsonNode node, String first, String second) {
        Integer value = integerValue(node, first);
        return value == null ? integerValue(node, second) : value;
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? null : value.asText();
    }

    private Long longValue(JsonNode node, String field) {
        String value = text(node, field);
        try {
            return value == null ? null : Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Integer integerValue(JsonNode node, String field) {
        String value = text(node, field);
        try {
            return value == null ? null : Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private BigDecimal decimalValue(JsonNode node, String field) {
        String value = text(node, field);
        try {
            return value == null ? null : new BigDecimal(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private LocalDateTime dateValue(JsonNode node, String field) {
        String value = text(node, field);
        if (value == null) return null;
        for (DateTimeFormatter formatter : new DateTimeFormatter[]{
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")}) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    private String blankToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    private long value(Integer number) {
        return number == null ? 0 : number;
    }
}
