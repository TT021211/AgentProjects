package com.cuixing.mes.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.MesProcessRouting;
import com.cuixing.mes.entity.MesWorkstage;
import com.cuixing.mes.service.IMesProcessRoutingService;
import com.cuixing.mes.service.IMesWorkstageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.service.IMesRoutingWorkstageService;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * 工艺路线与工序关联表;(mes_routing_workstage)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "工艺路线与工序关联表对象功能接口")
@RestController
@RequestMapping("/mesRoutingWorkstage")
public class MesRoutingWorkstageController extends BaseController {

    @Autowired
    private IMesRoutingWorkstageService mesRoutingWorkstageService;

    @Autowired
    private IMesProcessRoutingService mesProcessRoutingService;

    @Autowired
    private IMesWorkstageService mesWorkstageService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:routing_workstage:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesRoutingWorkstageService.checkMesRoutingWorkstageDataScope(id);
        return AjaxResult.success(mesRoutingWorkstageService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:routing_workstage:export")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesRoutingWorkstage mesRoutingWorkstage)
    {
        List<MesRoutingWorkstage> list = mesRoutingWorkstageService.selectMesRoutingWorkstageList(mesRoutingWorkstage);
        ExcelUtil<MesRoutingWorkstage> util = new ExcelUtil<MesRoutingWorkstage>(MesRoutingWorkstage.class);
        util.exportExcel(response, list, "工艺路线与工序关联表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:routing_workstage:list")
    @GetMapping("/list")
    public TableDataInfo list(MesRoutingWorkstage mesRoutingWorkstage)
    {
        startPage();
        List<MesRoutingWorkstage> list = mesRoutingWorkstageService.selectMesRoutingWorkstageList(mesRoutingWorkstage);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:routing_workstage:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesRoutingWorkstage mesRoutingWorkstage){
        List<MesRoutingWorkstage> list = mesRoutingWorkstageService.selectMesRoutingWorkstageList(mesRoutingWorkstage);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:routing_workstage:add")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesRoutingWorkstage mesRoutingWorkstage){
        if(mesRoutingWorkstage.getRoutingId() == null || mesRoutingWorkstage.getWorkstageId() == null || mesRoutingWorkstage.getOrderNum() == null) {
            return AjaxResult.error("缺少参数");
        }
        BigInteger routingId = mesRoutingWorkstage.getRoutingId();
        BigInteger workStageId = mesRoutingWorkstage.getWorkstageId();
        Integer orderNum = mesRoutingWorkstage.getOrderNum();

        MesRoutingWorkstage m = new MesRoutingWorkstage();

        try {
            // 取工艺路线信息
            MesProcessRouting mesProcessRouting = mesProcessRoutingService.queryById(routingId);

            m.setRoutingId(routingId);
            m.setRoutingName(mesProcessRouting.getRoutingName());
            m.setRoutingCode(mesProcessRouting.getRoutingCode());

            // 取工序信息
            MesWorkstage mesWorkstage = mesWorkstageService.queryById(workStageId);
            m.setWorkstageId(mesWorkstage.getId());
            m.setWorkstageName(mesWorkstage.getWorkstageName());
            m.setWorkstageCode(mesWorkstage.getWorkstageCode());
            // 设置排序
            m.setOrderNum(orderNum);
            // 设置创建人
            m.setCreateBy(SecurityUtils.getUsername());
            // 判断是否最后一道工序
            MesRoutingWorkstage search = new MesRoutingWorkstage();
            search.setRoutingId(routingId);
            List<MesRoutingWorkstage> workstages = mesRoutingWorkstageService.selectMesRoutingWorkstageList(search);
            for (MesRoutingWorkstage workstage : workstages) {
                if (Objects.equals(workstage.getOrderMark(), "last")) {
                    workstage.setOrderMark("");
                    mesRoutingWorkstageService.update(workstage);
                }
            }
            if(orderNum > workstages.size() ) {
                m.setOrderMark("last");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return AjaxResult.error("内部错误");
        }

        BigInteger i = mesRoutingWorkstageService.insert(m);
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
     * @param mesRoutingWorkstages 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:routing_workstage:add")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesRoutingWorkstage> mesRoutingWorkstages){
        return toAjax(mesRoutingWorkstageService.insertBatch(mesRoutingWorkstages));
    }

    /**
     * 更新数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:routing_workstage:edit")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesRoutingWorkstage mesRoutingWorkstage){
        return toAjax(mesRoutingWorkstageService.update(mesRoutingWorkstage));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
   //@RequiresPermissions("mes:routing_workstage:delete")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mesRoutingWorkstageService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
   //@RequiresPermissions("mes:routing_workstage:delete")
    @Log(title = "工艺路线与工序关联表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesRoutingWorkstageService.deleteByIds(ids));
    }

}