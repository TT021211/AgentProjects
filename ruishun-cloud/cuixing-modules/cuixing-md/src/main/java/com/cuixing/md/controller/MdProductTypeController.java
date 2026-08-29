package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.md.entity.vo.treeSelect;
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
import com.cuixing.md.entity.MdProductType;
import com.cuixing.md.service.IMdProductTypeService;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品分类表;(md_product_type)表控制层
 *
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "产品分类表对象功能接口")
@RestController
@RequestMapping("/mdProductType")
public class MdProductTypeController extends BaseController {

	@Autowired
	private IMdProductTypeService mdProductTypeService;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@ApiOperation("通过ID查询单条数据")
	//@RequiresPermissions("mes:md:product_type:query")
	@GetMapping("/{id}")
	public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id) {
		// mdProductTypeService.checkMdProductTypeDataScope(id);
		return AjaxResult.success(mdProductTypeService.queryById(id));
	}

	/**
	 * 导出数据
	 *
	 * @return 实例对象
	 */
	@ApiOperation("导出数据")
	//@RequiresPermissions("mes:md:product_type:export")
	@Log(title = "产品分类表", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, MdProductType mdProductType) {
		List<MdProductType> list = mdProductTypeService.selectMdProductTypeList(mdProductType);
		ExcelUtil<MdProductType> util = new ExcelUtil<MdProductType>(MdProductType.class);
		util.exportExcel(response, list, "产品分类表数据");
	}

	/**
	 * 分页查询
	 *
	 * @return 查询结果
	 */
	@ApiOperation("分页查询")
	//@RequiresPermissions("mes:md:product_type:list")
	@GetMapping("/list")
	public TableDataInfo list(MdProductType mdProductType) {
		startPage();
		List<MdProductType> list = mdProductTypeService.selectMdProductTypeList(mdProductType);
		return getDataTable(list);
	}

	/**
	 * 查询所有数据
	 *
	 * @return 查询结果
	 */
	@ApiOperation("查询所有数据")
	//@RequiresPermissions("mes:md:product_type:list")
	@GetMapping("/selectall")
	public AjaxResult selectAll(MdProductType mdProductType) {
		List<MdProductType> list = mdProductTypeService.selectMdProductTypeList(mdProductType);
		return AjaxResult.success(list);
	}

	/**
	 * 新增数据
	 *
	 * @param mdProductType 实例对象
	 * @return 实例对象
	 */
	@ApiOperation("新增数据")
	//@RequiresPermissions("mes:md:product_type:add")
	@Log(title = "产品分类表", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody MdProductType mdProductType) {
		return toAjax(mdProductTypeService.insert(mdProductType));
	}

	/**
	 * 批量新增数据
	 *
	 * @param mdProductTypes 实例对象数组
	 * @return 实例对象
	 */
	@ApiOperation("批量新增数据")
	//@RequiresPermissions("mes:md:product_type:add")
	@Log(title = "产品分类表", businessType = BusinessType.INSERT)
	@PostMapping("/insertbatch")
	public AjaxResult addBatch(@RequestBody List<MdProductType> mdProductTypes) {

		return toAjax(mdProductTypeService.insertBatch(mdProductTypes));
	}

	/**
	 * 更新数据
	 *
	 * @param mdProductType 实例对象
	 * @return 实例对象
	 */
	@ApiOperation("更新数据")
	//@RequiresPermissions("mes:md:product_type:edit")
	@Log(title = "产品分类表", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody MdProductType mdProductType) {
		return toAjax(mdProductTypeService.update(mdProductType));
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@ApiOperation("通过主键删除数据")
	//@RequiresPermissions("mes:md:product_type:delete")
	@Log(title = "产品分类表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id) {
		if(mdProductTypeService.checkHasChildren(id))
		{
			return error("该分类下含有子节点，不允许删除");
		}

		return toAjax(mdProductTypeService.deleteById(id));
	}

	/**
	 * 通过主键批量删除数据
	 *
	 * @param ids 主键
	 * @return 是否成功
	 */
	@ApiOperation("通过主键批量删除数据")
	//@RequiresPermissions("mes:md:product_type:delete")
	@Log(title = "产品分类表", businessType = BusinessType.DELETE)
	@DeleteMapping
	public AjaxResult deleteByIds(@RequestBody BigInteger[] ids) {
		if(mdProductTypeService.checkHasChildren(ids))
		{
			return error("该分类下含有子节点，不允许删除");
		}
		return toAjax(mdProductTypeService.deleteByIds(ids));
	}

	@ApiOperation("构造下拉框")
	@GetMapping("/tree")
	public AjaxResult getProductTypeTree() {
		List<treeSelect> productTypeTree = mdProductTypeService.selectMdProductTypeList(null).stream().map(treeSelect::new).collect(Collectors.toList());
		return AjaxResult.success(productTypeTree);
	}

}
