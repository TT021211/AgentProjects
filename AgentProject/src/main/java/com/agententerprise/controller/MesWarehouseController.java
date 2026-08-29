package com.agententerprise.controller;

import com.agententerprise.dto.MesWarehouseView;
import com.agententerprise.service.MesProductionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * WMS仓库只读查询接口。
 */
@RestController
@RequestMapping("/api/mes/warehouses")
public class MesWarehouseController {

    private final MesProductionService mesProductionService;

    public MesWarehouseController(MesProductionService mesProductionService) {
        this.mesProductionService = mesProductionService;
    }

    /**
     * 通过ruishun-wms API查询仓库列表。
     */
    @GetMapping
    public List<MesWarehouseView> warehouses() {
        return mesProductionService.queryWarehouses();
    }
}
