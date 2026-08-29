package com.agententerprise.controller;

import com.agententerprise.dto.MesProductionOverview;
import com.agententerprise.dto.MesWorkOrderView;
import com.agententerprise.service.MesProductionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * MES生产只读查询和快照索引接口。
 */
@RestController
@RequestMapping("/api/mes/production")
public class MesProductionController {

    private final MesProductionService mesProductionService;

    public MesProductionController(MesProductionService mesProductionService) {
        this.mesProductionService = mesProductionService;
    }

    /**
     * 查询MES工单状态概览和最近工单。
     */
    @GetMapping("/overview")
    public MesProductionOverview overview() {
        return mesProductionService.overview();
    }

    /**
     * 查询MES生产工单，首期限制最多返回200条。
     */
    @GetMapping("/workorders")
    public List<MesWorkOrderView> workOrders(@RequestParam(required = false) String workorderCode,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(defaultValue = "20") int limit) {
        return mesProductionService.queryWorkOrders(workorderCode, status, limit);
    }

    /**
     * 手动将当前MES生产快照转为知识库文档，避免定时任务未经确认产生大量重复向量。
     */
    @PostMapping("/index-knowledge")
    public Map<String, Object> indexKnowledge() {
        return Collections.<String, Object>singletonMap("indexedChunks",
                mesProductionService.indexKnowledgeSnapshot());
    }
}
