package com.agententerprise.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * MES生产概览及数据新鲜度信息。
 */
public class MesProductionOverview {

    /**
     * 数据是否成功从MES读取。
     */
    private boolean dataAvailable;
    /**
     * 数据来源数据库。
     */
    private String source;
    /**
     * 查询时间。
     */
    private LocalDateTime queriedAt;
    /**
     * 查询失败或未启用时的说明。
     */
    private String message;
    /**
     * 工单总数。
     */
    private long totalWorkOrders;
    /**
     * 排产中工单数。
     */
    private long schedulingWorkOrders;
    /**
     * 生产中工单数。
     */
    private long producingWorkOrders;
    /**
     * 完工工单数。
     */
    private long completedWorkOrders;
    /**
     * 最近工单。
     */
    private List<MesWorkOrderView> recentWorkOrders;

    public MesProductionOverview() {
    }

    public MesProductionOverview(boolean dataAvailable, String source, LocalDateTime queriedAt,
                                 String message, long totalWorkOrders, long schedulingWorkOrders,
                                 long producingWorkOrders, long completedWorkOrders,
                                 List<MesWorkOrderView> recentWorkOrders) {
        this.dataAvailable = dataAvailable;
        this.source = source;
        this.queriedAt = queriedAt;
        this.message = message;
        this.totalWorkOrders = totalWorkOrders;
        this.schedulingWorkOrders = schedulingWorkOrders;
        this.producingWorkOrders = producingWorkOrders;
        this.completedWorkOrders = completedWorkOrders;
        this.recentWorkOrders = recentWorkOrders == null ? Collections.<MesWorkOrderView>emptyList() : recentWorkOrders;
    }

    public boolean isDataAvailable() {
        return dataAvailable;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getQueriedAt() {
        return queriedAt;
    }

    public String getMessage() {
        return message;
    }

    public long getTotalWorkOrders() {
        return totalWorkOrders;
    }

    public long getSchedulingWorkOrders() {
        return schedulingWorkOrders;
    }

    public long getProducingWorkOrders() {
        return producingWorkOrders;
    }

    public long getCompletedWorkOrders() {
        return completedWorkOrders;
    }

    public List<MesWorkOrderView> getRecentWorkOrders() {
        return recentWorkOrders;
    }
}
