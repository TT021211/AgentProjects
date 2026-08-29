package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.md.mapper.MdMouldItemMapper;
import com.cuixing.md.service.IMdItemTypeService;
import com.cuixing.md.service.IMdUnitMeasureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.md.service.IMdItemService;
import java.math.BigInteger;
import java.util.List;

/**
 * 物料表;(md_item)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "物料表对象功能接口")
@RestController
@RequestMapping("/mdItem")
public class MdItemController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(MdItemController.class);

    @Autowired
    private IMdItemService mdItemService;

    @Autowired
    private IMdItemTypeService mdItemTypeService;



    @Autowired
    IMdUnitMeasureService mdUnitMeasureService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:item:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdItemService.checkMdItemDataScope(id);
        return AjaxResult.success(mdItemService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:item:export")
    @Log(title = "物料表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdItem mdItem)
    {
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        ExcelUtil<MdItem> util = new ExcelUtil<MdItem>(MdItem.class);
        util.exportExcel(response, list, "物料表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:item:list")
    @GetMapping("/list")
    public TableDataInfo list(MdItem mdItem)
    {
        startPage();
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:item:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdItem mdItem){
        List<MdItem> list = mdItemService.selectMdItemList(mdItem);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdItem 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:item:add")
    @Log(title = "物料表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdItem mdItem){
        return toAjax(mdItemService.insert(mdItem));
    }

    /**
     * 批量新增数据
     *
     * @param mdItems 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:item:add")
    @Log(title = "物料表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdItem> mdItems){
        return toAjax(mdItemService.insertBatch(mdItems));
    }

    /**
     * 更新数据
     *
     * @param mdItem 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:item:edit")
    @Log(title = "物料表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdItem mdItem){
        System.out.println(mdItem);
        return toAjax(mdItemService.update(mdItem));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:item:delete")
    @Log(title = "物料表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdItemService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:item:delete")
    @Log(title = "物料表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdItemService.deleteByIds(ids));
    }

    @Autowired
    MdMouldItemMapper mdMouldItemMapper;
    /**
     * 通过模具id取对应的在制品
     *
     * @param mouldId 模具id
     * @return 实例对象
     */
    @ApiOperation("通过模具id取对应的在制品")
//    //@RequiresPermissions("mes:md:item:list")
    @GetMapping("/mouldItems/{mould_id}")
    public AjaxResult getItemListByMouldId(@PathVariable(value = "mould_id") Integer mouldId) {
        List<MdItem> l = mdMouldItemMapper.selectItemListByMouldId(mouldId);
        return AjaxResult.success(l);
    }

    /**
     * 通过规格查询物料
     * @param
     * @return 实例对象
     */
    @ApiOperation("通过物料规格取对应的在制品")
    @GetMapping("/getItemBySpecification/{specification}")
    public AjaxResult getItemBySpecification(@PathVariable("specification") String specification) {
        MdItem mdItem = mdItemService.getItemBySpecification(specification);
        return AjaxResult.success(mdItem);
    }

    /**
     * 通过工序id查询物料列表
     *
     * @param
     * @return 实例对象
     */
    @ApiOperation("通过工序id查询物料列表")
    @GetMapping("/getItemByWorkstageId/{workstageId}")
    public AjaxResult getItemByWorkstageId(@PathVariable("workstageId") BigInteger workstageId) {
        List<MdItem> list = mdItemService.getItemByWorkstageId(workstageId);
        return AjaxResult.success(list);
    }

    /**
     * 通过工序名称查询物料列表
     *
     * @param
     * @return 实例对象
     */
    @ApiOperation("通过工序名称查询物料列表")
    @GetMapping("/getItemByWorkstageName/{workstageName}")
    public AjaxResult getItemByWorkstageName(@PathVariable("workstageName") String workstageName) {
        List<MdItem> list = mdItemService.getItemByWorkstageName(workstageName);
        return AjaxResult.success(list);
    }

}
