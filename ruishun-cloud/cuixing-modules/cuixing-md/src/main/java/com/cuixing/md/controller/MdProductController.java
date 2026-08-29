package com.cuixing.md.controller;

import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdProductType;
import com.cuixing.md.entity.vo.MdProductVo;
import com.cuixing.md.entity.vo.treeSelect;
import com.cuixing.md.service.IMdProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.page.TableDataInfo;
import com.cuixing.common.security.annotation.RequiresPermissions;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.log.annotation.Log;
import javax.servlet.http.HttpServletResponse;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.md.entity.MdProduct;
import com.cuixing.md.service.IMdProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 产品表;(md_product)表控制层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Api(tags = "产品表对象功能接口")
@RestController
@RequestMapping("/mdProduct")
public class MdProductController extends BaseController {

    @Autowired
    private IMdProductService mdProductService;
    @Autowired
    private IMdProductTypeService mdProductTypeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
//    @RequiresPermissions("mes:md:product:query")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") BigInteger id){
        // mdProductService.checkMdProductDataScope(id);
        return AjaxResult.success(mdProductService.queryById(id));
    }

    @ApiOperation("查询产品列表")
    @GetMapping("/queryByproductCode")
    public AjaxResult queryByproductCode(@RequestParam Set<String> productCode) {
        return AjaxResult.success(mdProductService.queryByproductCode(productCode));
    }

    /**
     * 导出数据
     *
     * @return 实例对象
     */
    @ApiOperation("导出数据")
    //@RequiresPermissions("mes:md:product:export")
    @Log(title = "产品表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdProduct mdProduct)
    {
        List<MdProduct> list = mdProductService.selectMdProductList(mdProduct);
        ExcelUtil<MdProduct> util = new ExcelUtil<MdProduct>(MdProduct.class);
        util.exportExcel(response, list, "产品表数据");
    }
    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    //@RequiresPermissions("mes:md:product:list")
    @GetMapping("/list")
    public TableDataInfo list(MdProduct mdProduct)
    {
        startPage();
        List<MdProduct> list = mdProductService.selectMdProductList(mdProduct);
        return getDataTable(list);
    }

    /**
     * 根据批号,型号，规格查询
     * @param mdProduct
     * @return
     */
    @ApiOperation("根据批号,型号，规格查询")
    //@RequiresPermissions("mes:md:product:list")
    @GetMapping("/selectByParam")
    public AjaxResult selectByParam(MdProduct mdProduct)
    {
        return AjaxResult.success(mdProductService.selectByParam(mdProduct));
    }

    /**
     * 查询品名下对应的规格
     *
     * @return 查询结果
     */
    @ApiOperation("查询品名下对应的规格")
    //@RequiresPermissions("mes:md:product:list")
    @GetMapping("/getSpecList")
    public AjaxResult getSpecList(String specName)
    {
        return AjaxResult.success(mdProductService.queryByName(specName));
    }

    /**
     * 查询所有数据
     *
     * @return 查询结果
     */
    @ApiOperation("查询所有数据")
    //@RequiresPermissions("mes:md:product:list")
    @GetMapping("/selectall")
    public AjaxResult selectAll(MdProduct mdProduct){
        List<MdProduct> list = mdProductService.selectMdProductList(mdProduct);
        return AjaxResult.success(list);
    }

    /**
     * 新增数据
     *
     * @param mdProduct 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
//    //@RequiresPermissions("mes:md:product:add")
    @Log(title = "产品表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdProduct mdProduct){
        return toAjax(mdProductService.insert(mdProduct));
    }
    /**
     * 批量新增数据
     *
     * @param mdProducts 实例对象数组
     * @return 实例对象
     */
    @ApiOperation("批量新增数据")
    //@RequiresPermissions("mes:md:product:add")
    @Log(title = "产品表", businessType = BusinessType.INSERT)
    @PostMapping("/insertbatch")
    public AjaxResult addBatch(@RequestBody List<MdProduct> mdProducts){
        return toAjax(mdProductService.insertBatch(mdProducts));
    }

    /**
     * 更新数据
     *
     * @param mdProduct 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    //@RequiresPermissions("mes:md:product:edit")
    @Log(title = "产品表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdProduct mdProduct){
        return toAjax(mdProductService.update(mdProduct));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    //@RequiresPermissions("mes:md:product:delete")
    @Log(title = "产品表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable(value = "id") BigInteger id){
        return toAjax(mdProductService.deleteById(id));
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    //@RequiresPermissions("mes:md:product:delete")
    @Log(title = "产品表", businessType = BusinessType.DELETE)
    @DeleteMapping
    public AjaxResult deleteByIds(@RequestBody BigInteger[] ids){
        return toAjax(mdProductService.deleteByIds(ids));
    }


    /**
     * 获取产品列表，按型号分类
     *
     * @return 查询结果
     */
    @ApiOperation("获取产品列表")
    //@RequiresPermissions("mes:md:product:list")
    @GetMapping("/getProductList")
    public AjaxResult getProductList(MdProduct mdProduct)
    {
        List<List<MdProduct>> list = mdProductService.getProductList(mdProduct);
        return AjaxResult.success(list);
    }


    /**
     * 根据多个型号查询
     */
    @ApiOperation("根据多个型号查询")
    @GetMapping("/getProductListByProductSize")
    public AjaxResult getProductListBySpec(@RequestParam List<String> productSize)
    {
        List<MdProduct> list = mdProductService.getProductListBySpec(productSize);
        return AjaxResult.success(list);
    }

    /**
     * 文件上传
     * @param file 对象
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadMoldLedger(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.error("上传文件不能为空");
        }
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<MdProduct> mdProducts = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // 跳过标题行
                    continue;
                }
                //组装信息
                MdProduct mdProduct = new MdProduct();
                String productTypeName = getCellValue(row, 0);
                if (productTypeName == null || productTypeName.isEmpty()) {
                    continue; // 如果产品分类为空，则跳过此行
                }
                mdProduct.setProductTypeName(productTypeName);
                MdProductType mdProductType = this.mdProductTypeService.queryByTypeName(productTypeName);
                if (mdProductType == null) {
                    return AjaxResult.error("产品分类不存在！");
                }
                mdProduct.setProductTypeId(mdProductType.getId());

                mdProduct.setProductName(getCellValue(row, 1));
                mdProduct.setProductSize(getCellValue(row, 2));
                mdProduct.setProductSizeInternational(getCellValue(row, 3));
                mdProduct.setSpecification(getCellValue(row, 4));
                mdProduct.setBatchNumber(getCellValue(row, 5));
                mdProduct.setAxialType(getCellValue(row, 6));
                mdProduct.setCoefficient(getCellValue(row, 7));
                mdProduct.setMaterialCode(getCellValue(row, 8));
                mdProduct.setWeightMeasureName(getCellValue(row, 9));
                mdProduct.setRemark(getCellValue(row, 10));
                mdProduct.setProductCode(getCellValue(row, 11));
                // 读取数据
                /*String specification = getCellValue(row, 5);
                String batchNumber = getCellValue(row, 6);*/

                // 判断是否重复
                /*if (mdProductService.isProductDuplicate(productTypeName, specification, batchNumber)) {
                    return AjaxResult.error("产品数据重复：产品分类、产品编码、规格和批次号的组合已存在");
                }*/
                mdProducts.add(mdProduct);
            }
            // 调用服务层保存到数据库
            mdProductService.insertBatch(mdProducts);
            return AjaxResult.success("上传成功");
        } catch (IOException e) {
            return AjaxResult.error("上传失败，原因: " + e.getMessage());
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    // 日志记录或其他处理
                }
            }
        }
    }

    // 辅助方法：获取单元格的值，避免空指针异常
    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return null;
        }
        // 处理不同单元格类型
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().replaceAll("\\s+", "");
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).replaceAll("\\s+", "");
        } else if (cell.getCellType() == CellType.BLANK) {
            return ""; // 如果是空白单元格返回空字符串
        }
        return null; // 其他类型返回null
    }

}
