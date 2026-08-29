package com.cuixing.sale.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.sale.entity.SaleOrder;
import com.cuixing.sale.service.SaleOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
* 销售订单;(sale_order)表控制层
* @author : http://www.chiner.pro
* @date : 2024-6-6
*/
@Api(tags = "销售订单对象功能接口")
@RestController
@RequestMapping("/sale/SaleOrder")
public class SaleOrderController extends BaseController{
   @Autowired
   private SaleOrderService SaleOrderService;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @ApiOperation("通过ID查询单条数据")
   @GetMapping("{id}")
   public AjaxResult queryById(@PathVariable(value = "id") BigInteger id){
       return AjaxResult.success(SaleOrderService.queryById(id));
   }
    /**
     * 获取列表
     *
     * @return 实例对象
     */
    @ApiOperation("通过ID查询数据")
    @GetMapping("/selectAll")
    public AjaxResult selectAll(){
        return AjaxResult.success(SaleOrderService.selectAll());
    }

   /**
    * 分页查询
    *
    * @param mdSaleOrder 筛选条件
    * @return 查询结果
    */
   @ApiOperation("分页查询")
   @GetMapping("/list")
   public TableDataInfo list(SaleOrder mdSaleOrder){
       startPage();
       List<SaleOrder> list =SaleOrderService.selectMdSaleOrderList(mdSaleOrder);
       return getDataTable(list);
   }

   /**
    * 新增数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   @ApiOperation("新增数据")
//    @RequiresPermissions("md:sale:add")
   @Log(title = "销售订单", businessType = BusinessType.INSERT)
   @PostMapping
   public AjaxResult add(@RequestBody SaleOrder mdSaleOrder){
       return toAjax(SaleOrderService.insert(mdSaleOrder));
   }

   /**
    * 批量新增数据
    *
    * @param mdSaleOrderList 批量新增数据
    * @return 批量新增数据
    */
   @ApiOperation("批量新增数据")
   @PostMapping("/addSaleOrderList")
   public AjaxResult addBatch(@RequestBody List<SaleOrder> mdSaleOrderList){
       return toAjax(SaleOrderService.insertBatch(mdSaleOrderList));
   }

   /**
    * 更新数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   @ApiOperation("更新数据")
   @Log(title = "销售订单", businessType = BusinessType.UPDATE)
   @PutMapping
   public AjaxResult edit(@RequestBody SaleOrder mdSaleOrder){
       return toAjax(SaleOrderService.update(mdSaleOrder));
   }


   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @ApiOperation("通过主键批量删除数据")
   @Log(title = "销售订单", businessType = BusinessType.DELETE)
   @DeleteMapping("/{ids}")
   public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
       return toAjax(SaleOrderService.deleteByIds(ids));
   }
}
