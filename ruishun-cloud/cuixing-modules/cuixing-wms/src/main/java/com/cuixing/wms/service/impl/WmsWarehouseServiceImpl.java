package com.cuixing.wms.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.wms.entity.WmsStorageArea;
import com.cuixing.wms.entity.WmsStorageLocation;
import com.cuixing.wms.mapper.WmsWarehouseMapper;
import com.cuixing.wms.service.IWmsWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class WmsWarehouseServiceImpl implements IWmsWarehouseService {

    @Autowired
    private WmsWarehouseMapper wmsWarehouseMapper;
//    @Autowired
//    private WmStorageLocationMapper wmStorageLocationMapper;
//    @Autowired
//    private WmStorageAreaMapper wmStorageAreaMapper;

    public WmsWarehouseServiceImpl() {
    }

    @Override
    public WmsWarehouse selectWmWarehouseByWarehouseId(Long warehouseId) {
        return this.wmsWarehouseMapper.selectWmWarehouseByWarehouseId(warehouseId);
    }
    @Override
    public WmsWarehouse selectWmWarehouseByWarehouseCode(String warehouseCdoe) {
        return this.wmsWarehouseMapper.selectWmWarehouseByWarehouseCode(warehouseCdoe);
    }
    @Override
    public List<WmsWarehouse> selectWmWarehouseList(WmsWarehouse wmsWarehouse) {
        return this.wmsWarehouseMapper.selectWmWarehouseList(wmsWarehouse);
    }
    @Override
    public List<WmsWarehouse> getTreeList() {
        return this.wmsWarehouseMapper.getTreeList();
    }
    @Override
    public String checkWarehouseCodeUnique(WmsWarehouse wmsWarehouse) {
        WmsWarehouse warehouse = this.wmsWarehouseMapper.checkWarehouseCodeUnique(wmsWarehouse);
        Long warehouseId = wmsWarehouse.getWarehouseId() == null ? -1L : wmsWarehouse.getWarehouseId();
        return StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId() != warehouseId ? "1" : "0";
    }
    @Override
    public String checkWarehouseNameUnique(WmsWarehouse wmsWarehouse) {
        WmsWarehouse warehouse = this.wmsWarehouseMapper.checkWarehouseNameUnique(wmsWarehouse);
        Long warehouseId = wmsWarehouse.getWarehouseId() == null ? -1L : wmsWarehouse.getWarehouseId();
        return StringUtils.isNotNull(warehouse) && warehouse.getWarehouseId() != warehouseId ? "1" : "0";
    }
    @Override
    public int insertWmWarehouse(WmsWarehouse wmsWarehouse) {
        wmsWarehouse.setCreateTime(DateUtils.getNowDate());
        return this.wmsWarehouseMapper.insertWmWarehouse(wmsWarehouse);
    }
    @Override
    public int updateWmWarehouse(WmsWarehouse wmsWarehouse) {
        wmsWarehouse.setUpdateTime(DateUtils.getNowDate());
        return this.wmsWarehouseMapper.updateWmWarehouse(wmsWarehouse);
    }
    @Override
    public int deleteWmWarehouseByWarehouseIds(Long[] warehouseIds) {
        return this.wmsWarehouseMapper.deleteWmWarehouseByWarehouseIds(warehouseIds);
    }
    @Override
    public int deleteWmWarehouseByWarehouseId(Long warehouseId) {
        return this.wmsWarehouseMapper.deleteWmWarehouseByWarehouseId(warehouseId);
    }
    @Override
    public WmsWarehouse initVirtualWarehouse() {
        WmsWarehouse warehouse = new WmsWarehouse();
        warehouse.setWarehouseCode("XBK_VIRTUAL");
        warehouse.setWarehouseName("线边库-虚拟");
        this.wmsWarehouseMapper.insertWmWarehouse(warehouse);
        WmsStorageLocation location = new WmsStorageLocation();
        location.setWarehouseId(warehouse.getWarehouseId());
        location.setLocationCode("XBKKQ_VIRTUAL");
        location.setLocationName("线边库库区-虚拟");
        location.setAreaFlag("Y");
//        this.wmStorageLocationMapper.insertWmStorageLocation(location);
        WmsStorageArea area = new WmsStorageArea();
        area.setLocationId(location.getLocationId());
        area.setAreaCode("XBKKW_VIRTUAL");
        area.setAreaName("线边库库位-虚拟");
//        this.wmStorageAreaMapper.insertWmStorageArea(area);
        return warehouse;
    }

    @Override
    public WmsWarehouse getinfoByWorkstageId(BigInteger workstageId) {
        return wmsWarehouseMapper.getinfoByWorkstageId(workstageId);
    }

    @Override
    public WmsWarehouse selectWmWarehouseBytype(String type) {
        return wmsWarehouseMapper.getinfoBytype(type);
    }
}
