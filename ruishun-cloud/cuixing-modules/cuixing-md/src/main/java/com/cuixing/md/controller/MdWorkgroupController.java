package com.cuixing.md.controller;

import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.md.entity.vo.treeSelect;
import com.cuixing.system.api.RemoteUserService;
import com.cuixing.system.api.domain.SysUser;
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
import com.cuixing.md.entity.MdWorkgroup;
import com.cuixing.md.service.IMdWorkgroupService;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班组表;(md_workgroup)表控制层
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "班组表对象功能接口")
@RestController
@RequestMapping("/mdWorkgroup")
public class MdWorkgroupController extends BaseController {

	@Autowired
	private IMdWorkgroupService mdWorkgroupService;


	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@ApiOperation("通过ID查询单条数据")
	//@RequiresPermissions("mes:md:workgroup:query")
	@GetMapping("/{id}")
	public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id) {
		// mdWorkgroupService.checkMdWorkgroupDataScope(id);
		return AjaxResult.success(mdWorkgroupService.queryById(id));
	}

	/**
	 * 导出数据
	 *
	 * @return 实例对象
	 */
	@ApiOperation("导出数据")
	//@RequiresPermissions("mes:md:workgroup:export")
	@Log(title = "班组表", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, MdWorkgroup mdWorkgroup) {
		List<MdWorkgroup> list = mdWorkgroupService.selectMdWorkgroupList(mdWorkgroup);
		ExcelUtil<MdWorkgroup> util = new ExcelUtil<MdWorkgroup>(MdWorkgroup.class);
		util.exportExcel(response, list, "班组表数据");
	}

	/**
	 * 分页查询
	 *
	 * @return 查询结果
	 */
	@ApiOperation("分页查询")
	//@RequiresPermissions("mes:md:workgroup:list")
	@GetMapping("/list")
	public TableDataInfo list(MdWorkgroup mdWorkgroup) {
		startPage();
		List<MdWorkgroup> list = mdWorkgroupService.selectMdWorkgroupList(mdWorkgroup);
		return getDataTable(list);
	}

	/**
	 * 查询所有数据
	 *
	 * @return 查询结果
	 */
	@ApiOperation("查询所有数据")
	//@RequiresPermissions("mes:md:workgroup:list")
	@GetMapping("/selectall")
	public AjaxResult selectAll(MdWorkgroup mdWorkgroup) {
		List<MdWorkgroup> list = mdWorkgroupService.selectMdWorkgroupList(mdWorkgroup);
		return AjaxResult.success(list);
	}

	/**
	 * 新增数据
	 *
	 * @param mdWorkgroup 实例对象
	 * @return 实例对象
	 */
	@ApiOperation("新增数据")
	//@RequiresPermissions("mes:md:workgroup:add")
	@Log(title = "班组表", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody MdWorkgroup mdWorkgroup) {
		return toAjax(mdWorkgroupService.insert(mdWorkgroup));
	}

	/**
	 * 批量新增数据
	 *
	 * @param mdWorkgroups 实例对象数组
	 * @return 实例对象
	 */
	@ApiOperation("批量新增数据")
	//@RequiresPermissions("mes:md:workgroup:add")
	@Log(title = "班组表", businessType = BusinessType.INSERT)
	@PostMapping("/insertbatch")
	public AjaxResult addBatch(@RequestBody List<MdWorkgroup> mdWorkgroups) {
		return toAjax(mdWorkgroupService.insertBatch(mdWorkgroups));
	}

	/**
	 * 更新数据
	 *
	 * @param mdWorkgroup 实例对象
	 * @return 实例对象
	 */
	@ApiOperation("更新数据")
	//@RequiresPermissions("mes:md:workgroup:edit")
	@Log(title = "班组表", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody MdWorkgroup mdWorkgroup) {
		return toAjax(mdWorkgroupService.update(mdWorkgroup));
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@ApiOperation("通过主键删除数据")
	//@RequiresPermissions("mes:md:workgroup:delete")
	@Log(title = "班组表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id) {
		return toAjax(mdWorkgroupService.deleteById(id));
	}

	/**
	 * 通过主键批量删除数据
	 *
	 * @param ids 主键
	 * @return 是否成功
	 */
	@ApiOperation("通过主键批量删除数据")
	//@RequiresPermissions("mes:md:workgroup:delete")
	@Log(title = "班组表", businessType = BusinessType.DELETE)
	@DeleteMapping
	public AjaxResult deleteByIds(@RequestBody BigInteger[] ids) {
		return toAjax(mdWorkgroupService.deleteByIds(ids));
	}

	/**
	 * 构造前端使用的下拉列表
	 *
	 * @return 查询结果
	 */
	@ApiOperation("构造下拉列表")
	@GetMapping("/tree")
	public TableDataInfo getTree() {
		List<treeSelect> workGroupTree = mdWorkgroupService.selectMdWorkgroupList(null).stream().map(treeSelect::new).collect(Collectors.toList());
		return getDataTable(workGroupTree);
	}

	@PostMapping("/allocated/{userIds}")
	public AjaxResult allocated(@PathVariable("userIds") Long[] userIds,@RequestBody MdWorkgroup mdWorkgroup)
	{
			return toAjax(mdWorkgroupService.allocatedUserByuserIds(userIds,mdWorkgroup));
	}



}
