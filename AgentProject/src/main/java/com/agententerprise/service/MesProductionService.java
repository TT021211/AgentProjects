package com.agententerprise.service;

import com.agententerprise.config.MesDataSourceProperties;
import com.agententerprise.config.MesApiProperties;
import com.agententerprise.dto.MesProductionOverview;
import com.agententerprise.dto.MesWarehouseView;
import com.agententerprise.dto.MesWorkOrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MES生产事实查询服务。
 * 只读取固定SQL和最小字段集，不接受Agent传入SQL，也不提供MES写操作。
 */
@Service
public class MesProductionService {

    private static final Logger log = LoggerFactory.getLogger(MesProductionService.class);
    private final JdbcTemplate jdbcTemplate;
    private final MesDataSourceProperties properties;
    private final KnowledgeService knowledgeService;
    private final MesApiProperties apiProperties;
    private final MesApiClient mesApiClient;
    private final boolean enabled;

    public MesProductionService(@Qualifier("mesJdbcTemplate") JdbcTemplate mesJdbcTemplate,
                                MesDataSourceProperties properties,
                                KnowledgeService knowledgeService,
                                MesApiProperties apiProperties,
                                MesApiClient mesApiClient,
                                @Value("${agent.mes.enabled:false}") boolean enabled) {
        this.jdbcTemplate = mesJdbcTemplate;
        this.properties = properties;
        this.knowledgeService = knowledgeService;
        this.apiProperties = apiProperties;
        this.mesApiClient = mesApiClient;
        this.enabled = enabled;
    }

    /**
     * 查询生产工单状态概览及最近工单。
     */
    public MesProductionOverview overview() {
        LocalDateTime queriedAt = LocalDateTime.now();
        if (apiProperties.isEnabled()) {
            MesProductionOverview apiOverview = mesApiClient.productionOverview();
            if (apiOverview != null) return apiOverview;
        }
        if (!isEnabled()) {
            String message = apiProperties.isEnabled()
                    ? "ruishun-cloud MES API不可用，请检查MES_API_BASE_URL和服务状态"
                    : "MES数据源未启用，请设置MES_DB_ENABLED=true或MES_API_ENABLED=true";
            return unavailable(queriedAt, message);
        }
        try {
            String summarySql = "SELECT COUNT(*) AS total_work_orders, "
                    + "SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) AS scheduling_work_orders, "
                    + "SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END) AS producing_work_orders, "
                    + "SUM(CASE WHEN status = 3 THEN 1 ELSE 0 END) AS completed_work_orders "
                    + "FROM mes_product_workorder WHERE is_deleted = 0";
            OverviewRow row = jdbcTemplate.queryForObject(summarySql, (resultSet, index) -> new OverviewRow(
                    resultSet.getLong("total_work_orders"),
                    resultSet.getLong("scheduling_work_orders"),
                    resultSet.getLong("producing_work_orders"),
                    resultSet.getLong("completed_work_orders")));
            List<MesWorkOrderView> recent = queryWorkOrders(null, null, 10);
            return new MesProductionOverview(true, "rsmes_cloud", queriedAt, null,
                    row == null ? 0 : row.total, row == null ? 0 : row.scheduling,
                    row == null ? 0 : row.producing, row == null ? 0 : row.completed, recent);
        } catch (DataAccessException ex) {
            log.warn("读取MES生产概览失败: {}", ex.getMessage());
            return unavailable(queriedAt, "MES生产数据暂不可用，请检查数据库连接和表结构");
        }
    }

    /**
     * 按工单号和状态查询生产工单，参数只作为预编译查询值。
     */
    public List<MesWorkOrderView> queryWorkOrders(String workorderCode, Integer status, int limit) {
        if (apiProperties.isEnabled()) {
            List<MesWorkOrderView> result = mesApiClient.queryWorkOrders(workorderCode, status, limit);
            if (result != null) return result;
        }
        if (!isEnabled()) return Collections.emptyList();
        StringBuilder sql = new StringBuilder("SELECT id, workorder_code, order_code, customer_name, "
                + "product_model, product_specification, status, weight, create_time, update_time "
                + "FROM mes_product_workorder WHERE is_deleted = 0");
        List<Object> arguments = new ArrayList<>();
        if (workorderCode != null && !workorderCode.trim().isEmpty()) {
            sql.append(" AND workorder_code LIKE ?");
            arguments.add("%" + workorderCode.trim() + "%");
        }
        if (status != null) {
            sql.append(" AND status = ?");
            arguments.add(status);
        }
        sql.append(" ORDER BY COALESCE(update_time, create_time) DESC LIMIT ?");
        arguments.add(Math.max(1, Math.min(limit, 200)));
        try {
            return jdbcTemplate.query(sql.toString(), this::mapWorkOrder, arguments.toArray());
        } catch (DataAccessException ex) {
            log.warn("查询MES生产工单失败: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 将当前生产快照转换为可检索文档，供人工触发索引，不替代实时查询。
     */
    public int indexKnowledgeSnapshot() {
        MesProductionOverview overview = overview();
        if (!overview.isDataAvailable()) return 0;
        StringBuilder document = new StringBuilder("MES生产快照\n")
                .append("数据来源：").append(overview.getSource()).append("\n")
                .append("查询时间：").append(overview.getQueriedAt()).append("\n")
                .append("工单总数：").append(overview.getTotalWorkOrders()).append("\n")
                .append("排产中：").append(overview.getSchedulingWorkOrders()).append("\n")
                .append("生产中：").append(overview.getProducingWorkOrders()).append("\n")
                .append("完工：").append(overview.getCompletedWorkOrders()).append("\n\n");
        for (MesWorkOrderView workOrder : overview.getRecentWorkOrders()) {
            document.append("工单 ").append(workOrder.getWorkorderCode())
                    .append("，订单 ").append(workOrder.getOrderCode())
                    .append("，客户 ").append(workOrder.getCustomerName())
                    .append("，产品型号 ").append(workOrder.getProductModel())
                    .append("，产品规格 ").append(workOrder.getProductSpecification())
                    .append("，状态 ").append(statusLabel(workOrder.getStatus()))
                    .append("，计划重量 ").append(workOrder.getWeight()).append("。\n");
        }
        return knowledgeService.index(document.toString());
    }

    /**
     * 生成Agent使用的生产上下文，明确标注数据更新时间。
     */
    public String buildAgentContext() {
        MesProductionOverview overview = overview();
        if (!overview.isDataAvailable()) return "MES生产数据：" + overview.getMessage();
        StringBuilder context = new StringBuilder("MES生产事实（查询时间：")
                .append(overview.getQueriedAt()).append("）：\n")
                .append("工单总数：").append(overview.getTotalWorkOrders())
                .append("，排产中：").append(overview.getSchedulingWorkOrders())
                .append("，生产中：").append(overview.getProducingWorkOrders())
                .append("，完工：").append(overview.getCompletedWorkOrders()).append("\n");
        for (MesWorkOrderView workOrder : overview.getRecentWorkOrders()) {
            context.append("- ").append(workOrder.getWorkorderCode()).append("：")
                    .append(workOrder.getProductModel()).append(" / ")
                    .append(workOrder.getProductSpecification()).append("，")
                    .append(statusLabel(workOrder.getStatus())).append("，客户 ")
                    .append(workOrder.getCustomerName()).append("\n");
        }
        return context.toString();
    }

    private boolean isEnabled() {
        return enabled
                || Boolean.parseBoolean(System.getProperty("agent.mes.enabled", "false"))
                || Boolean.parseBoolean(System.getenv().getOrDefault("MES_DB_ENABLED", "false"));
    }

    /**
     * 通过ruishun-wms API查询启用中的仓库。
     */
    public List<MesWarehouseView> queryWarehouses() {
        if (!apiProperties.isEnabled()) return Collections.emptyList();
        List<MesWarehouseView> result = mesApiClient.queryWarehouses();
        return result == null ? Collections.<MesWarehouseView>emptyList() : result;
    }

    /**
     * 生成Agent使用的仓库上下文，数据来源保持为WMS API。
     */
    public String buildWarehouseContext() {
        List<MesWarehouseView> warehouses = queryWarehouses();
        if (warehouses.isEmpty()) return "WMS仓库数据：暂无可用数据或仓储API未连接。";
        StringBuilder context = new StringBuilder("WMS仓库数据（API实时查询）：\n");
        for (MesWarehouseView warehouse : warehouses) {
            context.append("- ").append(warehouse.getWarehouseCode()).append(" / ")
                    .append(warehouse.getWarehouseName()).append("，位置 ")
                    .append(warehouse.getLocation()).append("，负责人 ")
                    .append(warehouse.getCharge()).append("\n");
        }
        return context.toString();
    }

    private MesProductionOverview unavailable(LocalDateTime queriedAt, String message) {
        return new MesProductionOverview(false, "rsmes_cloud", queriedAt, message,
                0, 0, 0, 0, Collections.<MesWorkOrderView>emptyList());
    }

    private MesWorkOrderView mapWorkOrder(ResultSet resultSet, int index) throws SQLException {
        int status = resultSet.getInt("status");
        Integer nullableStatus = resultSet.wasNull() ? null : status;
        return new MesWorkOrderView(resultSet.getLong("id"), resultSet.getString("workorder_code"),
                resultSet.getString("order_code"), resultSet.getString("customer_name"),
                resultSet.getString("product_model"), resultSet.getString("product_specification"),
                nullableStatus, resultSet.getBigDecimal("weight"),
                toLocalDateTime(resultSet.getTimestamp("create_time")),
                toLocalDateTime(resultSet.getTimestamp("update_time")));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    private String statusLabel(Integer status) {
        if (status == null) return "未知状态";
        switch (status) {
            case 1:
                return "排产中";
            case 2:
                return "生产中";
            case 3:
                return "完工";
            default:
                return "状态" + status;
        }
    }

    private static class OverviewRow {
        private final long total;
        private final long scheduling;
        private final long producing;
        private final long completed;

        private OverviewRow(long total, long scheduling, long producing, long completed) {
            this.total = total;
            this.scheduling = scheduling;
            this.producing = producing;
            this.completed = completed;
        }
    }
}
