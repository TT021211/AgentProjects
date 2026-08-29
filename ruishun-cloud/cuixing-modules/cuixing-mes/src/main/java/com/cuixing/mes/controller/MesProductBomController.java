package com.cuixing.mes.controller;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.service.IMesProductRoutingService;
import com.cuixing.mes.service.IMesRoutingWorkstageService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdProduct;
import com.cuixing.system.api.domain.mes.MesProductRouting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.system.api.domain.mes.MesProductBom;
import com.cuixing.mes.service.IMesProductBomService;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * 产品BOM表;(mes_product_bom)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "产品BOM表对象功能接口")
@RestController
@RequestMapping("/mesProductBom")
public class MesProductBomController extends BaseController {

    @Autowired
    private IMesProductBomService mesProductBomService;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private IMesProductRoutingService mesProductRoutingService;

    @Autowired
    private IMesRoutingWorkstageService mesRoutingWorkstageService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:product_bom:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesProductBomService.checkMesProductBomDataScope(id);
        return AjaxResult.success(mesProductBomService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:product_bom:export")
    @Log(title = "产品BOM表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesProductBom mesProductBom)
    {
        List<MesProductBom> list = mesProductBomService.selectMesProductBomList(mesProductBom);
        ExcelUtil<MesProductBom> util = new ExcelUtil<MesProductBom>(MesProductBom.class);
        util.exportExcel(response, list, "产品BOM表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:product_bom:list")
    @GetMapping("/list")
    public TableDataInfo list(MesProductBom mesProductBom)
    {
        startPage();
        List<MesProductBom> list = mesProductBomService.selectMesProductBomList(mesProductBom);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
//    //@RequiresPermissions("mes:product_bom:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesProductBom mesProductBom){
        List<MesProductBom> list = mesProductBomService.selectMesProductBomList(mesProductBom);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mesProductBom 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:product_bom:add")
    @Log(title = "产品BOM表", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody MesProductBom mesProductBom){

        System.out.println(mesProductBom);
        BigInteger productId = mesProductBom.getProductId();
        BigInteger routingId = mesProductBom.getRoutingId();
        BigInteger workstageId = mesProductBom.getWorkstageId();
        // 工艺路线与工序关联表的自增id，因为工序可能重复出现，需要通过id确认唯一性
        BigInteger routingWorkstageId = mesProductBom.getRoutingWorkstageId();

        if(productId == null || routingId == null || workstageId == null || routingWorkstageId == null) {
            System.out.println("参数不正确");
            return AjaxResult.error("参数不正确");
        }

        MesRoutingWorkstage routingWorkstage = mesRoutingWorkstageService.queryById(routingWorkstageId);
        System.out.println(routingWorkstage);
        if(routingWorkstage == null || !Objects.equals(routingWorkstage.getWorkstageId(), workstageId) || !Objects.equals(routingWorkstage.getRoutingId(), routingId)) {
            System.out.println("routingWorkstage参数不正确");
            return AjaxResult.error("routingWorkstage参数不正确");
        }

        R<MdProduct> productInfoRs = remoteMdService.getProduct(productId, SecurityConstants.INNER);
        if (productInfoRs == null) {
            System.out.println("找不到该产品");
            return AjaxResult.error("找不到该产品");
        }
        MdProduct productInfo = productInfoRs.getData();
        if (productInfo == null) {
            System.out.println(productInfoRs.getMsg());
            return AjaxResult.error("找不到该产品");
        }
        mesProductBom.setProductCode(productInfo.getProductCode());
        mesProductBom.setProductName(productInfo.getProductName());

        // 产品与工艺路线是否已关联
        MesProductRouting m = new MesProductRouting();
        m.setProductId(productId);
        m.setRoutingId(routingId);
        List<MesProductRouting> productRoutings = mesProductRoutingService.selectMesProductRoutingList(m);
        if( productRoutings == null || productRoutings.size() == 0) {
            System.out.println("找不到该产品对应的工艺路线");
            return AjaxResult.error("找不到该产品对应的工艺路线");
        }
        mesProductBom.setRoutingName(productRoutings.get(0).getRoutingName());


        mesProductBom.setWorkstageCode(routingWorkstage.getWorkstageCode());
        mesProductBom.setWorkstageName(routingWorkstage.getWorkstageName());
        mesProductBom.setOrderNum(routingWorkstage.getOrderNum());
        mesProductBom.setOrderMark(routingWorkstage.getOrderMark());

        // 最后一道工序，产出的为产品，其他工序产出为物料半成品
        if(Objects.equals(routingWorkstage.getOrderMark(), "last")) {
            mesProductBom.setOutputBomId(productId);
            mesProductBom.setOutputBomCode(productInfo.getProductCode());
            mesProductBom.setOutputBomName(productInfo.getProductName());
        }
        System.out.println(mesProductBom);
        BigInteger i = mesProductBomService.insert(mesProductBom);
        /** insert返回为last_insert_id，判断是否为正，
         * signum方法返回一个表示BigInteger值符号的int值：
         * 如果这个值为零返回0，如果这个值为正返回1，如果这个值为负返回-1。
         */
        int signum = i.signum();
        return toAjax(signum);
    }

    /**
     * 批量新增数据
     *
     * @param mesProductBoms 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:product_bom:add")
    @Log(title = "产品BOM表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesProductBom> mesProductBoms){
        return toAjax(mesProductBomService.insertBatch(mesProductBoms));
    }

    /**
     * 更新数据
     *
     * @param mesProductBom 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:product_bom:edit")
    @Log(title = "产品BOM表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductBom mesProductBom){
        return toAjax(mesProductBomService.update(mesProductBom));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:product_bom:delete")
    @Log(title = "产品BOM表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesProductBomService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:product_bom:delete")
    @Log(title = "产品BOM表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesProductBomService.deleteByIds(ids));
    }

}