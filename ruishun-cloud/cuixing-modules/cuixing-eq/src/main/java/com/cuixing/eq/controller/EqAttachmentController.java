package com.cuixing.eq.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.eq.entity.EqAttachment;
import com.cuixing.eq.service.IEqAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备配件设置;(ems_attachment)表控制层
 *
 * @author : zhan
 * @date : 2024-7-11
 */
@Api(tags = "设备配件设置对象功能接口")
@RestController
@RequestMapping("/eqAttachment")
public class EqAttachmentController extends BaseController {
    @Autowired
    private IEqAttachmentService iEqAttachmentService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "通过ID查询单条数据", notes = "权限字符:eq:eqAttachment:query")
    //@RequiresPermissions("eq:eqAttachment:query")
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable(value = "id") BigInteger id) {
        return AjaxResult.success(iEqAttachmentService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param emsAttachment 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询", notes = "权限字符:eq:eqAttachment:list")
    //@RequiresPermissions("eq:eqAttachment:list")
    @GetMapping("/list")
    public TableDataInfo list(EqAttachment emsAttachment) {
        startPage();
        List<EqAttachment> list = iEqAttachmentService.selectEmsAttachmentList(emsAttachment);
        list = list.stream().filter(item -> !"已更换".equals(item.getStatus())).collect(Collectors.toList());
        return getDataTable(list);
    }

    /**
     * 分页查询所有的记录
     */
    @ApiOperation(value = "分页查询更换记录", notes = "权限字符:eq:eqAttachment:list")
    //@RequiresPermissions("eq:eqAttachment:list")
    @GetMapping("/listAll")
    public TableDataInfo listReplace(EqAttachment emsAttachment) {
        startPage();
        List<EqAttachment> list = iEqAttachmentService.selectEmsAttachmentList(emsAttachment);
        return getDataTable(list);
    }
    /**
     * 更换配件
     * @param oldEmsAttachmentId 旧配件id
     * @param emsAttachment  要更换的配件信息
     */
    @ApiOperation(value = "更换配件", notes = "权限字符:eq:eqAttachment:update")
    //@RequiresPermissions("eq:eqAttachment:update")
    @Log(title = "设备配件设置", businessType = BusinessType.UPDATE)
    @PutMapping("/replace")
    public AjaxResult replace(@RequestParam BigInteger oldEmsAttachmentId, @RequestBody EqAttachment emsAttachment) {
        return toAjax(iEqAttachmentService.replace(oldEmsAttachmentId, emsAttachment));
    }

    /**
     * 通过关联设备id获取配件列表
     *
     * @param equipmentId
     * @return 查询结果
     */
    @ApiOperation(value = "通过关联设备id获取配件列表", notes = "权限字符:eq:eqAttachment:list")
    //@RequiresPermissions("eq:eqAttachment:list")
    @GetMapping("/selectListByEquipmentId/{equipmentId}")
    public AjaxResult selectListByEquipmentId(@PathVariable(value = "equipmentId") BigInteger equipmentId) {
        List<EqAttachment> list = iEqAttachmentService.selectListByEquipmentId(equipmentId);
        //过滤状态为已更换的配件
        list = list.stream().filter(item -> !"已更换".equals(item.getStatus())).collect(Collectors.toList());
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param emsAttachment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据", notes = "权限字符:eq:eqAttachment:add")
    //@RequiresPermissions("eq:eqAttachment:add")
    @Log(title = "设备配件设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqAttachment emsAttachment) {
        return toAjax(iEqAttachmentService.insert(emsAttachment));
    }


    /**
     * 更新数据
     *
     * @param emsAttachment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据", notes = "权限字符:eq:eqAttachment:update")
    //@RequiresPermissions("eq:eqAttachment:update")
    @Log(title = "设备配件设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqAttachment emsAttachment) {
        return toAjax(iEqAttachmentService.update(emsAttachment));
    }

    /**
     * 通过设备id和生产重量批量修改已生产重量
     */
    @ApiOperation(value = "通过设备id和生产重量批量修改已生产重量", notes = "权限字符:eq:eqAttachment:update")
    //@RequiresPermissions("eq:eqAttachment:update")
    @Log(title = "设备配件设置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateProducedWeight")
    public AjaxResult updateProducedWeight(@RequestParam BigInteger equipmentId, @RequestParam BigDecimal producedWeight) {
        return toAjax(iEqAttachmentService.updateProducedWeight(equipmentId, producedWeight));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation(value = "通过主键批量删除数据", notes = "权限字符:eq:eqAttachment:remove")
    //@RequiresPermissions("eq:eqAttachment:remove")
    @Log(title = "设备配件设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids) {
        return toAjax(iEqAttachmentService.deleteByIds(ids));
    }

}