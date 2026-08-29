package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
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
import com.cuixing.md.entity.MdWorkgroupMember;
import com.cuixing.md.service.IMdWorkgroupMemberService;
import java.math.BigInteger;
import java.util.List;

/**
 * 班组成员表;(md_workgroup_member)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "班组成员表对象功能接口")
@RestController
@RequestMapping("/mdWorkgroupMember")
public class MdWorkgroupMemberController extends BaseController {

    @Autowired
    private IMdWorkgroupMemberService mdWorkgroupMemberService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    //@RequiresPermissions("mes:md:workgroup_member:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdWorkgroupMemberService.checkMdWorkgroupMemberDataScope(id);
        return AjaxResult.success(mdWorkgroupMemberService.queryById(id));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:workgroup_member:export")
    @Log(title = "班组成员表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkgroupMember mdWorkgroupMember)
    {
        List<MdWorkgroupMember> list = mdWorkgroupMemberService.selectMdWorkgroupMemberList(mdWorkgroupMember);
        ExcelUtil<MdWorkgroupMember> util = new ExcelUtil<MdWorkgroupMember>(MdWorkgroupMember.class);
        util.exportExcel(response, list, "班组成员表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:workgroup_member:list")
    @GetMapping("/list")
    public TableDataInfo list(MdWorkgroupMember mdWorkgroupMember)
    {
        startPage();
        List<MdWorkgroupMember> list = mdWorkgroupMemberService.selectMdWorkgroupMemberList(mdWorkgroupMember);
        return getDataTable(list);
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:workgroup_member:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdWorkgroupMember mdWorkgroupMember){
        List<MdWorkgroupMember> list = mdWorkgroupMemberService.selectMdWorkgroupMemberList(mdWorkgroupMember);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    //@RequiresPermissions("mes:md:workgroup_member:add")
    @Log(title = "班组成员表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkgroupMember mdWorkgroupMember){
        return toAjax(mdWorkgroupMemberService.insert(mdWorkgroupMember));
    }
    /**
     * 批量新增数据
     *
     * @param mdWorkgroupMembers 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:workgroup_member:add")
    @Log(title = "班组成员表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdWorkgroupMember> mdWorkgroupMembers){
        return toAjax(mdWorkgroupMemberService.insertBatch(mdWorkgroupMembers));
    }

    /**
     * 更新数据
     *
     * @param mdWorkgroupMember 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:workgroup_member:edit")
    @Log(title = "班组成员表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkgroupMember mdWorkgroupMember){
        return toAjax(mdWorkgroupMemberService.update(mdWorkgroupMember));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:workgroup_member:delete")
    @Log(title = "班组成员表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdWorkgroupMemberService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:workgroup_member:delete")
    @Log(title = "班组成员表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdWorkgroupMemberService.deleteByIds(ids));
    }




}
