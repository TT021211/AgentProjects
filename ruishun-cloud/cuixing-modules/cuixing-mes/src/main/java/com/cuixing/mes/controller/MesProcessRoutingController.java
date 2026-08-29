package com.cuixing.mes.controller;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.entity.dto.RoutingDto;
import com.cuixing.mes.service.IMesProductRoutingService;
import com.cuixing.mes.service.IMesRoutingWorkstageService;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.domain.mes.MesProductRouting;
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
import com.cuixing.mes.entity.MesProcessRouting;
import com.cuixing.mes.entity.vo.treeSelect;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.cuixing.mes.service.IMesProcessRoutingService;
import java.math.BigInteger;
import java.util.List;

/**
 * 工艺路线表;(mes_process_routing)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Api(tags = "工艺路线表对象功能接口")
@RestController
@RequestMapping("/mesProcessRouting")
public class MesProcessRoutingController extends BaseController {

    @Autowired
    private IMesProcessRoutingService mesProcessRoutingService;

    @Autowired
    private IMesRoutingWorkstageService mesRoutingWorkstageService;

    @Autowired
    private IMesProductRoutingService mesProductRoutingService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:routing:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mesProcessRoutingService.checkMesProcessRoutingDataScope(id);
        MesProcessRouting mesProcessRouting = mesProcessRoutingService.queryById(id);
        RoutingDto routingDto = getRoutingDto(mesProcessRouting);
        return AjaxResult.success(routingDto);
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:routing:export")
    @Log(title = "工艺路线表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesProcessRouting mesProcessRouting)
    {
        List<MesProcessRouting> list = mesProcessRoutingService.selectMesProcessRoutingList(mesProcessRouting);
        ExcelUtil<MesProcessRouting> util = new ExcelUtil<MesProcessRouting>(MesProcessRouting.class);
        util.exportExcel(response, list, "工艺路线表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:routing:list")
    @GetMapping("/list")
    public TableDataInfo list(MesProcessRouting mesProcessRouting)
    {
        startPage();
        List<MesProcessRouting> mesProcessRoutingList = mesProcessRoutingService.selectMesProcessRoutingList(mesProcessRouting);
        TableDataInfo dataTable1 = getDataTable(mesProcessRoutingList);
        List<RoutingDto> routingDtoList =  getRoutingDtoList(mesProcessRoutingList);
        TableDataInfo dataTable = getDataTable(routingDtoList);
        dataTable.setTotal(dataTable1.getTotal());
        return dataTable;
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:routing:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MesProcessRouting mesProcessRouting){
        List<MesProcessRouting> mesProcessRoutingList = mesProcessRoutingService.selectMesProcessRoutingList(mesProcessRouting);
        List<RoutingDto> routingDtoList =  getRoutingDtoList(mesProcessRoutingList);
        return AjaxResult.success(routingDtoList);
    }

    /**
     * 新增数据
     *
     * @param mesProcessRouting 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:routing:add")
    @Log(title = "工艺路线表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProcessRouting mesProcessRouting){
        mesProcessRouting.setCreateBy(SecurityUtils.getUsername());
        BigInteger i = mesProcessRoutingService.insert(mesProcessRouting);
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
     * @param mesProcessRoutings 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:routing:add")
    @Log(title = "工艺路线表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MesProcessRouting> mesProcessRoutings){
        return toAjax(mesProcessRoutingService.insertBatch(mesProcessRoutings));
    }

    /**
     * 更新数据
     *
     * @param mesProcessRouting 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:routing:edit")
    @Log(title = "工艺路线表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProcessRouting mesProcessRouting){
        return toAjax(mesProcessRoutingService.update(mesProcessRouting));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:routing:delete")
    @Log(title = "工艺路线表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){

        // 先查询工艺路线是否被产品关联，否则不能删除工艺路线
        MesProductRouting mesProductRouting = new MesProductRouting();
        mesProductRouting.setRoutingId(id);
        List<MesProductRouting> list = mesProductRoutingService.selectMesProductRoutingList(mesProductRouting);
        if(list.size() > 0) {
            return AjaxResult.error("该工艺路线已被产品关联，不能删除");
        }
        return toAjax(mesProcessRoutingService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:routing:delete")
    @Log(title = "工艺路线表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mesProcessRoutingService.deleteByIds(ids));
    }

    /**
     * 构造前端使用的下拉列表
     *
     * @return 查询结果
     */
    @ApiOperation("构造下拉列表")
    @GetMapping("/tree")
    public TableDataInfo getTree() {
        List<treeSelect> mesProcessRoutingTree = mesProcessRoutingService.selectMesProcessRoutingList(null).stream().map(treeSelect::new).collect(Collectors.toList());
        return getDataTable(mesProcessRoutingTree);
    }

    /**
     * 工艺路线关联的工序列表转dto
     * @param mesProcessRoutingList
     * @return
     */
    private List<RoutingDto> getRoutingDtoList(List<MesProcessRouting> mesProcessRoutingList) {
        List<RoutingDto> routingDtoList = new ArrayList<>();
        for (MesProcessRouting m : mesProcessRoutingList) {
            RoutingDto routingDto = getRoutingDto(m);
            routingDtoList.add(routingDto);
        }
        return routingDtoList;
    }

    /**
     * 工艺路线关联的工序列表转dto
     * @param mesProcessRouting
     * @return
     */
    private RoutingDto getRoutingDto(MesProcessRouting mesProcessRouting) {
        RoutingDto routingDto = new RoutingDto();
        routingDto.setId(mesProcessRouting.getId());
        routingDto.setRoutingName(mesProcessRouting.getRoutingName());
        routingDto.setRoutingCode(mesProcessRouting.getRoutingCode());
        routingDto.setRemark(mesProcessRouting.getRemark());
        routingDto.setCreateBy(mesProcessRouting.getCreateBy());
        routingDto.setCreateTime(mesProcessRouting.getCreateTime());

        List<treeSelect> mesWorkstageTree = mesRoutingWorkstageService.selectMesRoutingWorkstageListByRoutingId(mesProcessRouting.getId()).stream().map(treeSelect::new).collect(Collectors.toList());
        routingDto.setRoutingWorkstages(mesWorkstageTree);
        return routingDto;
    }
}
