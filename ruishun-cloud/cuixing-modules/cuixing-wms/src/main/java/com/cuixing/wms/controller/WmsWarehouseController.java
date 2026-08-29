package com.cuixing.wms.controller;

import com.cuixing.common.core.constant.UserConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.utils.MyCollectionUtils;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.wms.service.IWmsWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "仓库管理")
@RestController
@RequestMapping("/wms/warehouse")
public class WmsWarehouseController extends BaseController {


    @Autowired
    private IWmsWarehouseService wmWarehouseService;


    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping("/list")
    public TableDataInfo list(WmsWarehouse wmsWarehouse)
    {
        startPage();
        List<WmsWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmsWarehouse);
        return getDataTable(list);
    }

    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping("/getlist")
    public AjaxResult getlist(WmsWarehouse wmsWarehouse)
    {
        WmsWarehouse wmsRealtimeStock = new WmsWarehouse();
        wmsRealtimeStock.setEnableFlag("1");
        List<WmsWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmsWarehouse);
        return AjaxResult.success(list);
    }

    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping("/getWorksstationIds")
    public AjaxResult getWorksstationIds() {
        WmsWarehouse wmsRealtimeStock = new WmsWarehouse();
        wmsRealtimeStock.setEnableFlag("1");
        List<WmsWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmsRealtimeStock);
        List<Long> collect = list.stream().filter(item -> item.getWorkstationId() != null).map(item -> item.getWorkstationId()).distinct().collect(Collectors.toList());
        return AjaxResult.success(collect);
    }

    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping(value = "/{warehouseId}")
    public AjaxResult getInfo(@PathVariable("warehouseId") Long warehouseId)
    {
        return AjaxResult.success(wmWarehouseService.selectWmWarehouseByWarehouseId(warehouseId));
    }

    /**
     * 通过字典键值查询仓库
     * @param type
     * @return
     */
    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping(value = "/getByType/{type}")
    public AjaxResult getByType(@PathVariable("type") String type)
    {
        return AjaxResult.success(wmWarehouseService.selectWmWarehouseBytype(type));
    }

    //@RequiresPermissions("wms:warehouse:list")
    @GetMapping(value = "/getinfoByWorkstationId/{workstationId}")
    public R<WmsWarehouse> getinfoByWorkstationId(@PathVariable("workstationId") Long workstationId){
        WmsWarehouse wmsWarehouse = new WmsWarehouse();
        wmsWarehouse.setWorkstationId(workstationId);
        List<WmsWarehouse> list = wmWarehouseService.selectWmWarehouseList(wmsWarehouse);
        if(MyCollectionUtils.isNotEmpty(list)){
            WmsWarehouse wmsWarehouse1 = list.stream().filter(item -> item.getWorkstationId().equals(workstationId)).findFirst().orElse(null);
            return R.ok(wmsWarehouse1);
        }
        return R.fail(String.format("根据工作站ID:%s未找到该工作站对应的仓库信息！", workstationId));
    }
    @ApiOperation("通过工序id获取仓库")
    @GetMapping(value = "/getinfoByWorkstageId/{workstageId}")
    public R<WmsWarehouse> getinfoByWorkstageId(@PathVariable("workstageId") BigInteger workstageId){

        WmsWarehouse wmsWarehouse =  wmWarehouseService.getinfoByWorkstageId(workstageId);

        return R.ok(wmsWarehouse);
    }

    //@RequiresPermissions("wms:warehouse:add")
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouse wmsWarehouse)
    {
        if(UserConstants.NOT_UNIQUE_STR.equals(wmWarehouseService.checkWarehouseCodeUnique(wmsWarehouse))){
            return AjaxResult.error("仓库编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE_STR.equals(wmWarehouseService.checkWarehouseNameUnique(wmsWarehouse))){
            return AjaxResult.error("仓库名称已存在！");
        }
        // 判断是否有机台ID
        if(wmsWarehouse.getWorkstationId() != null){
            R<WmsWarehouse> wmWarehouseR = getinfoByWorkstationId(wmsWarehouse.getWorkstationId());
            if(wmWarehouseR.getData() != null){
                return AjaxResult.error("该工作站已存在仓库信息！");
            }
        }

        wmWarehouseService.insertWmWarehouse(wmsWarehouse);
        // 生成条码 要上传附件 先注释，后面实现
//        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_WAREHOUSE,wmWarehouse.getWarehouseId(),wmWarehouse.getWarehouseCode(),wmWarehouse.getWarehouseName());

        return AjaxResult.success(wmsWarehouse.getWarehouseId());
    }

    //@RequiresPermissions("wms:warehouse:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouse wmsWarehouse)
    {
        // 判断是否有机台ID
        if(wmsWarehouse.getWorkstationId() != null){
            R<WmsWarehouse> wmWarehouseR = getinfoByWorkstationId(wmsWarehouse.getWorkstationId());
            if(wmWarehouseR.getData() != null && !wmWarehouseR.getData().getWarehouseId().equals(wmsWarehouse.getWarehouseId())){
                return AjaxResult.error("该工作站已存在仓库信息！");
            }
        }
        return toAjax(wmWarehouseService.updateWmWarehouse(wmsWarehouse));
    }

    @Transactional
    @DeleteMapping("/{warehouseIds}")
    public AjaxResult remove(@PathVariable Long[] warehouseIds)
    {
        for (Long wahouseId: warehouseIds
        ) {
//            wmStorageLocationService.deleteByWarehouseId(wahouseId);
//            wmStorageAreaService.deleteByWarehouseId(wahouseId);
        }

        return toAjax(wmWarehouseService.deleteWmWarehouseByWarehouseIds(warehouseIds));
    }

}
