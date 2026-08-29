package com.cuixing.wms.utils;

import com.cuixing.wms.entity.WmsBox;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil<T> {
    private final Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = getStrings();

        CellStyle headerStyle = createHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 创建日期格式
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        // 填充数据
        int rowNum = 1;
        for (T item : list) {
            Row row = sheet.createRow(rowNum++);
            if (item instanceof WmsBox) {
                WmsBox box = (WmsBox) item;
                // 标识信息
                row.createCell(0).setCellValue(safeString(box.getBoxCode()));
                row.createCell(1).setCellValue(safeString(box.getBoxSerialNumber()));
                // 批次信息
                row.createCell(2).setCellValue(safeString(box.getBatchNumber2()));
                // 数量信息
                row.createCell(3).setCellValue(safeString(box.getBoxPerNumber()));

                row.createCell(4).setCellValue(safeString(box.getProductName()));
                row.createCell(5).setCellValue(safeString(box.getWmsSpec()));
                // 重量信息
                row.createCell(6).setCellValue(box.getNetWeight());
                row.createCell(7).setCellValue(box.getGrossWeight());
                row.createCell(8).setCellValue(box.getAxleLoad());
                row.createCell(9).setCellValue(safeString(box.getWmsUnit()));

            }
        }

        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 设置响应头
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(sheetName + ".xlsx", "UTF-8").replace("+", "%20"));
            response.setCharacterEncoding("UTF-8");
            try (OutputStream os = response.getOutputStream()) {
                workbook.write(os);
                os.flush();
                System.out.println("Excel written to response stream successfully.");
            }
        } catch (Exception e) {
            System.err.println("Error writing Excel to stream: " + e.getMessage());
            throw new RuntimeException("导出Excel失败: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getStrings() {
        String[] headers;
        if (clazz == WmsBox.class) {
            headers = new String[]{
                    "箱编号", "箱流水号", "批号",
                    "每箱盘数", "品名", "型号",
                    "净重", "毛重", "轴重", "单位"
            };
        }
        else {
            throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
        }
        return headers;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private String safeString(Object value) {
        return value != null ? value.toString() : "";
    }
}