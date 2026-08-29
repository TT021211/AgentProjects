package com.cuixing.mes.controller;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.MesProcessRouting;
import com.cuixing.mes.service.IMesProcessRoutingService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdProduct;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.system.api.domain.mes.MesProductRouting;
import com.cuixing.mes.service.IMesProductRoutingService;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品工艺路线关联表;(mes_product_routing)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "产品工艺路线关联表对象功能接口")
@RestController
@RequestMapping("/mesProductRouting")
public class MesProductRoutingController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(MesProductRoutingController.class);

    @Autowired
    private IMesProductRoutingService mesProductRoutingService;

    @Autowired
    RemoteMdService remoteMdService;

    @Autowired
    private IMesProcessRoutingService mesProcessRoutingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:product_routing:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesProductRoutingService.checkMesProductRoutingDataScope(id);
        return AjaxResult.success(mesProductRoutingService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
//    //@RequiresPermissions("mes:product_routing:export")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesProductRouting mesProductRouting)
    {
        List<MesProductRouting> list = mesProductRoutingService.selectMesProductRoutingList(mesProductRouting);
        ExcelUtil<MesProductRouting> util = new ExcelUtil<MesProductRouting>(MesProductRouting.class);
        util.exportExcel(response, list, "产品工艺路线关联表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
//    //@RequiresPermissions("mes:product_routing:list")
    @GetMapping("/list")
    public TableDataInfo list(MesProductRouting mesProductRouting)
    {
        startPage();
        List<MesProductRouting> list = mesProductRoutingService.selectMesProductRoutingList(mesProductRouting);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
//    //@RequiresPermissions("mes:product_routing:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesProductRouting mesProductRouting){
        List<MesProductRouting> list = mesProductRoutingService.selectMesProductRoutingList(mesProductRouting);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询所有数据")
//    //@RequiresPermissions("mes:product_routing:list")
    @GetMapping("/getProductsByRoutingId")
    public AjaxResult getProductsByRoutingId(@RequestParam("routing_id") BigInteger routingId){
        System.out.println(routingId);
        MesProductRouting mesProductRouting = new MesProductRouting();
        mesProductRouting.setRoutingId(routingId);
        List<MesProductRouting> list = mesProductRoutingService.selectMesProductRoutingList(mesProductRouting);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mesProductRouting 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
//    //@RequiresPermissions("mes:product_routing:add")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.INSERT)
    @PostMapping
    @GlobalTransactional
    public AjaxResult add(@RequestBody MesProductRouting mesProductRouting){
        if(mesProductRouting.getRoutingId() == null || mesProductRouting.getProductId() == null) {
            return AjaxResult.error("缺少参数");
        }
        MesProductRouting m = new MesProductRouting();

        BigInteger routingId = mesProductRouting.getRoutingId();
        BigInteger productId = mesProductRouting.getProductId();

        MesProductRouting search = new MesProductRouting();
        search.setRoutingId(routingId);
        search.setProductId(productId);
        List<MesProductRouting> l = mesProductRoutingService.selectMesProductRoutingList(search);
        System.out.println(l);
        if(l.size() > 0) {
            return AjaxResult.error("请勿重复绑定");
        }

        MesProcessRouting mesProcessRouting = mesProcessRoutingService.queryById(routingId);
        if(mesProcessRouting == null) {
            return AjaxResult.error("找不到该工艺路线");
        }
        m.setRoutingId(routingId);
        m.setRoutingName(mesProcessRouting.getRoutingName());

        R<MdProduct> productInfoRs = remoteMdService.getProduct(productId, SecurityConstants.INNER);
        if(productInfoRs == null) {
            return AjaxResult.error("找不到该产品");
        }
        MdProduct productInfo = productInfoRs.getData();

        m.setProductId(productId);
        m.setProductName(productInfo.getProductName());
        m.setProductCode(productInfo.getProductCode());
        if(mesProductRouting.getIsDefault() != null) {
            m.setIsDefault(mesProductRouting.getIsDefault());
        }
        m.setCreateBy(SecurityUtils.getUsername());
        BigInteger i = mesProductRoutingService.insert(m);
        /** insert返回为last_insert_id，判断是否为正，
         * signum方法返回一个表示BigInteger值符号的int值：
         * 如果这个值为零返回0，如果这个值为正返回1，如果这个值为负返回-1。
         */
        int signum = i.signum();
        return toAjax(signum);

    }

    @ApiOperation("feign新增数据")
//    //@RequiresPermissions("mes:product_routing:add")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.INSERT)
    @PostMapping("/addByFeign")
    @GlobalTransactional
    public AjaxResult addByFeign(@RequestBody MesProductRouting mesProductRouting){
        log.info("开始addByFeign {}", mesProductRouting.toString());
        log.info("当前 XID: {}", RootContext.getXID());
        if(mesProductRouting.getRoutingId() == null || mesProductRouting.getProductId() == null) {
            log.info("缺少参数");
//            throw new RuntimeException("缺少参数");
            return AjaxResult.error("缺少参数");
        }

        MesProductRouting m = new MesProductRouting();

        BigInteger routingId = mesProductRouting.getRoutingId();
        BigInteger productId = mesProductRouting.getProductId();

        MesProductRouting search = new MesProductRouting();
        search.setRoutingId(routingId);
        search.setProductId(productId);
        List<MesProductRouting> l = mesProductRoutingService.selectMesProductRoutingList(search);
        if(l.size() > 0) {
            log.info("请勿重复绑定");
            throw new RuntimeException("请勿重复绑定");
        }

        MesProcessRouting mesProcessRouting = mesProcessRoutingService.queryById(routingId);
        if(mesProcessRouting == null) {
            log.info("找不到该工艺路线");
            throw new RuntimeException("找不到该工艺路线");
        }

        m.setRoutingId(routingId);
        m.setRoutingName(mesProcessRouting.getRoutingName());

        m.setProductId(productId);
        m.setProductName(mesProductRouting.getProductName());
        m.setProductCode(mesProductRouting.getProductCode());

        if(mesProductRouting.getIsDefault() != null) {
            m.setIsDefault(mesProductRouting.getIsDefault());
        }
        m.setCreateBy(SecurityUtils.getUsername());
        BigInteger i = mesProductRoutingService.insert(m);
        /** insert返回为last_insert_id，判断是否为正，
         * signum方法返回一个表示BigInteger值符号的int值：
         * 如果这个值为零返回0，如果这个值为正返回1，如果这个值为负返回-1。
         */
        int signum = i.signum();
        if (signum != 1) {
            throw new RuntimeException("添加产品工艺路线失败");
        }
        log.info("新增产品工艺路线id: {}", i);
        return toAjax(signum);

    }

    /**
     * 批量新增数据
     *
     * @param mesProductRoutings 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
//    //@RequiresPermissions("mes:product_routing:add")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesProductRouting> mesProductRoutings){
        return toAjax(mesProductRoutingService.insertBatch(mesProductRoutings));
    }

    /**
     * 更新数据
     *
     * @param mesProductRouting 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
//    //@RequiresPermissions("mes:product_routing:edit")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductRouting mesProductRouting){
        if(mesProductRouting.getId() == null) {
            return AjaxResult.error("id不能为空");
        }
        try {
            BigInteger id = mesProductRouting.getId();
            MesProductRouting search = mesProductRoutingService.queryById(id);
            // 获取产品id
            BigInteger productId = search.getProductId();

            // 假如修改是否默认路线，先根据产品id查询出默认路线，把默认路线设置为非默认
            if (mesProductRouting.getIsDefault() != null && mesProductRouting.getIsDefault() == 1) {
                MesProductRouting m = new MesProductRouting();
                m.setIsDefault(1);
                m.setProductId(productId);
                System.out.println(m);
                List<MesProductRouting> routingList = mesProductRoutingService.selectMesProductRoutingList(m);
                // 循环设置路线为非默认
                for (MesProductRouting r : routingList) {
                    r.setIsDefault(0);
                    mesProductRoutingService.update(r);
                }
            }
            return toAjax(mesProductRoutingService.update(mesProductRouting));
        } catch (Exception e) {
            return AjaxResult.error("内部错误");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
//    //@RequiresPermissions("mes:product_routing:delete")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesProductRoutingService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
//    //@RequiresPermissions("mes:product_routing:delete")
    @Log(title = "产品工艺路线关联表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesProductRoutingService.deleteByIds(ids));
    }



}