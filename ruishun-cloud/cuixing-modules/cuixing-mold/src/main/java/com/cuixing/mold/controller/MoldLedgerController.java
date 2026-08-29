package com.cuixing.mold.controller;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.core.utils.poi.ExcelUtil;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldClassification;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.service.MoldClassificationService;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具台账表(MoldLedger)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:45
 */
@Api(tags = "模具台账表接口")
@RestController
@RequestMapping("/mold/moldLedger")
public class MoldLedgerController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldClassificationService moldClassificationService;

    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldLedger moldLedger) {
        startPage();
        List<MoldLedger> list = this.moldLedgerService.selectMoldLedgerList(moldLedger);
        return getDataTable(list);
    }
    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list2"})
    public TableDataInfo list2(MoldLedger moldLedger) {
        this.startPage();
        List<MoldLedger> list = this.moldLedgerService.selectMoldLedgerList2(moldLedger);
        return this.getDataTable(list);
    }
    /**
     * 查询拉丝模类型的台账信息
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/queryByClassificationId"})
    public TableDataInfo queryByClassificationId(MoldLedger moldLedger) {
        this.startPage();
        List<MoldLedger> moldLedgers = this.moldLedgerService.queryByClassificationId(moldLedger);
        return this.getDataTable(moldLedgers);
    }
    /**
     * 查询拉丝模类型的台账信息
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/queryByClassificationId2"})
    public TableDataInfo queryByClassificationId2(MoldLedger moldLedger) {
        this.startPage();
        List<MoldLedger> moldLedgers = this.moldLedgerService.queryByClassificationId2(moldLedger);
        return this.getDataTable(moldLedgers);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.moldLedgerService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldLedger 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody MoldLedger moldLedger) {
        if ("1".equals(this.moldLedgerService.checkUniqueMoldLedgerCode(moldLedger))) {
            return AjaxResult.error("检测项编号已存在！");
        } else {
            return AjaxResult.success(this.moldLedgerService.insert(moldLedger));
        }
    }

    /**
     * 编辑数据
     *
     * @param moldLedger 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody MoldLedger moldLedger) {
        if ("1".equals(this.moldLedgerService.checkUniqueMoldLedgerCode(moldLedger))) {
            return AjaxResult.error("检测项编号已存在！");
        } else {
            return AjaxResult.success(this.moldLedgerService.update(moldLedger));
        }
    }

//    /**
//     * 删除数据
//     *
//     * @param id 主键
//     * @return 删除是否成功
//     */
//    @DeleteMapping("/{id}")
//    public AjaxResult deleteById(@PathVariable Long id) {
//        return this.toAjax(this.moldLedgerService.deleteById(id));
//    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键批量删除数据")
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable BigInteger[] ids){
        return toAjax(moldLedgerService.deleteByIds(ids));
    }

//    /**
//     * 文件上传
//     * @param file 对象
//     * @return
//     */
//    @PostMapping("/upload")
//    public AjaxResult uploadMoldLedger(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return AjaxResult.error("上传文件不能为空");
//        }
//        Workbook workbook = null;
//        try {
//            workbook = new XSSFWorkbook(file.getInputStream());
//            Sheet sheet = workbook.getSheetAt(0);
//            List<MoldLedger> moldLedgers = new ArrayList<>();
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) { // 跳过标题行
//                    continue;
//                }
//                //组装台账信息
//                MoldLedger moldLedger = new MoldLedger();
//                //获取单元格
//                Cell cell = row.getCell(0);
//                if (cell != null) {
//                    cell.setCellType(CellType.STRING);
//                    String cellValue = cell.getStringCellValue();
//                    moldLedger.setMoldCode(cellValue);
//                }
//                Cell moldApertureCell = row.getCell(1);
//                if (moldApertureCell != null){
//                    if (moldApertureCell.getCellType() == CellType.NUMERIC) {
//                        moldLedger.setMoldAperture(String.valueOf(moldApertureCell.getNumericCellValue()));
//                    } else if (moldApertureCell.getCellType() == CellType.STRING) {
//                        moldLedger.setMoldAperture((moldApertureCell.getStringCellValue()));
//                    }
//                }
//                Cell name = row.getCell(2);
//                if (name != null){
//                    moldLedger.setMoldType(name.getStringCellValue());
//                }
//                Cell spec = row.getCell(3);
//                if (spec != null){
//                    moldLedger.setMoldSpec(spec.getStringCellValue());
//                }
//                Cell cell2 = row.getCell(4);
//                if (cell2 != null){
//                    moldLedger.setMoldMaterial(cell2.getStringCellValue());
//                }
//                Cell moldNumCell = row.getCell(5);
//                if (moldNumCell != null){
//                    if (moldNumCell.getCellType() == CellType.NUMERIC) {
//                        moldLedger.setMoldNum((long) moldNumCell.getNumericCellValue());
//                    } else if (moldNumCell.getCellType() == CellType.STRING) {
//                        moldLedger.setMoldNum(Long.parseLong(moldNumCell.getStringCellValue()));
//                    }
//                }
//                if (spec!= null && name !=null){
//                    MoldClassification moldClassification = this.moldClassificationService.queryclassificationType(spec.getStringCellValue(),name.getStringCellValue());
//                    if (moldClassification!= null){
//                        moldLedger.setClassificationId(moldClassification.getClassificationId());
//                    }else{
//                        return AjaxResult.error("规格不存在，请先添加规格");
//                    }
//                }
//                Cell moldengineeringToleranceCell = row.getCell(6);
//                cell.setCellType(CellType.NUMERIC);
//                if (moldengineeringToleranceCell != null){
//                    if (moldApertureCell.getCellType() == CellType.NUMERIC) {
//                        double numericCellValue = moldengineeringToleranceCell.getNumericCellValue();
//                        moldLedger.setEngineeringTolerance(BigDecimal.valueOf((numericCellValue)));
//                    } else if (moldengineeringToleranceCell.getCellType() == CellType.STRING) {
//                        String EngineeringToleranceCellValue = moldengineeringToleranceCell.getStringCellValue();
//                        BigDecimal bigDecimal = new BigDecimal(EngineeringToleranceCellValue);
//                        moldLedger.setEngineeringTolerance(bigDecimal);
//                    }
//                }
//                Cell cell3 = row.getCell(7);
//                if (cell3 != null){
//                    moldLedger.setMoldArea(cell3.getStringCellValue());
//                }
//                Cell cell1 = row.getCell(8);
//                if (cell1 != null){
//                    moldLedger.setMoldSupplierName(row.getCell(8).getStringCellValue());
//                }
//                Cell cell4 = row.getCell(9);
//                if (cell4 != null && cell4.getLocalDateTimeCellValue() != null){
//                    LocalDateTime localDateTimeCellValue = cell4.getLocalDateTimeCellValue();
//                    Date date = Date.from(localDateTimeCellValue.atZone(ZoneId.systemDefault()).toInstant());
//                    moldLedger.setBuyTime(date);
//                }
//                Cell attr2Cell = row.getCell(10);
//                if (null != attr2Cell){
//                    attr2Cell.setCellType(CellType.STRING);
//                    String cellValue2 = attr2Cell.getStringCellValue();
//                    moldLedger.setAttr2(cellValue2);
//                }
//                moldLedger.setCreateTime(new Date());
//                moldLedger.setCreateBy(SecurityUtils.getUsername());
//                moldLedger.setStatus("OPERATIONAL");
//                // 检查编码唯一
//                if ("1".equals(this.moldLedgerService.checkUniqueMoldLedgerCode(moldLedger))) {
//                    return AjaxResult.error("检测项编号已存在！");
//                } else {
//                    moldLedgers.add(moldLedger);
//                }
//            }
//            // 调用服务层保存到数据库
//            moldLedgerService.saveMoldLedgers(moldLedgers);
//            return AjaxResult.success("上传成功");
//        } catch (IOException e) {
//            return AjaxResult.error("上传失败，原因: " + e.getMessage());
//        }finally {
//            if (workbook != null) {
//                try {
//                    workbook.close();
//                } catch (IOException e) {
//                    // 日志记录或其他处理
//                }
//            }
//        }
//    }

    /**
     * 文件上传
     * @param file 对象
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadMoldLedger(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return AjaxResult.error("上传文件不能为空");
        }
        ExcelUtil<MoldLedger> util = new ExcelUtil<>(MoldLedger.class);
        List<MoldLedger> moldLedgers = util.importExcel(file.getInputStream());
        int resutl = 0;
        for (MoldLedger moldLedger : moldLedgers) {
            if ("1".equals(this.moldLedgerService.checkUniqueMoldLedgerCode(moldLedger))) {
                return AjaxResult.error("检测项编号" + moldLedger.getMoldCode() + "已存在！");
            }
            MoldClassification moldClassification = this.moldClassificationService.queryclassificationType(moldLedger.getMoldSpec(),moldLedger.getMoldType());
            if (moldClassification!= null){
                moldLedger.setClassificationId(moldClassification.getClassificationId());
            }else{
                return AjaxResult.error("规格不存在，请先添加规格");
            }
            MoldLedger insert = moldLedgerService.insert(moldLedger);
            if (insert != null){
                resutl++;
            }
        }
        if (resutl > 0){
            return AjaxResult.success(resutl);
        }else{
            return AjaxResult.error("未导入数据");
        }
    }
}

