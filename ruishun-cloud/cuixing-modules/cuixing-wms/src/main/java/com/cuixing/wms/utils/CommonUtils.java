package com.cuixing.wms.utils;

import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.mapper.WmsProductInoutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class CommonUtils {

    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;

    /**
     * 更新删除箱子后的后续数据
     * @param deletedBoxCode 被删除的箱子编号
     * @param productName 产品名称
     * @param productSpec 产品规格
     * @param productBatchNumber 产品批号
     * @param productCode 产品编码
     */
    public void deleteBoxAndUpdateSubsequentRecords(String deletedBoxCode, String productName, String productSpec,
                                                    String productBatchNumber, String productCode) {
        // Step 1: 找到要删除的箱子的所有记录
        List<WmsProductInout> recordsToDelete = wmsProductInoutMapper.findRecordsByBoxCode(
                deletedBoxCode, productName, productSpec, productBatchNumber, productCode);

        if (recordsToDelete.isEmpty()) {
            throw new IllegalArgumentException("未找到指定箱号的数据：" + deletedBoxCode);
        }

        // Step 2: 删除这些记录
        wmsProductInoutMapper.deleteRecordsByBoxCode(deletedBoxCode, productName, productSpec, productBatchNumber, productCode);

       /* // Step 3: 找到所有后续的记录
        WmsProductInout lastRecordOfDeletedBox = recordsToDelete.get(recordsToDelete.size() - 1);
        List<WmsProductInout> subsequentRecords = wmsProductInoutMapper.findRecordsAfter(
                lastRecordOfDeletedBox.getId(), productName, productSpec, productBatchNumber, productCode);

        if (subsequentRecords.isEmpty()) {
            // 如果没有后续记录，则不需要更新数据
            return;
        }*/

        /*// Step 4: 找到删除箱子的前一个箱子的最后一条变化记录
        List<WmsProductInout> previousRecords = wmsProductInoutMapper.findRecordsBefore(
                recordsToDelete.get(0).getId(), productName, productSpec, productBatchNumber, productCode);

        BigDecimal previousFinalQty = BigDecimal.ZERO;  // 初始化为0，用于处理删除第一个箱子的情况
        BigDecimal previousFinalWeight = BigDecimal.ZERO;  // 删除第一个箱子时，重量归0

        if (!previousRecords.isEmpty()) {
            // 如果有前面箱子的记录，取前一个箱子的最后变化后数量和重量
            WmsProductInout lastRecordOfPreviousBox = previousRecords.get(0);
            previousFinalQty = new BigDecimal(lastRecordOfPreviousBox.getArtt8());  // 前一个箱子的最后变化后数量
            previousFinalWeight = new BigDecimal(lastRecordOfPreviousBox.getArtt10());  // 前一个箱子的最后变化后重量
        }

        // Step 5: 更新后续记录
        BigDecimal previousQty = previousFinalQty;  // 继承前一个箱子的变化后数量，或从0开始
        BigDecimal previousWeight = previousFinalWeight;  // 继承前一个箱子的变化后重量，或从0开始

        for (WmsProductInout record : subsequentRecords) {
            // 计算当前记录的数量差值和重量差值
            BigDecimal qtyDiff = new BigDecimal(record.getArtt8()).subtract(new BigDecimal(record.getArtt7()));
            BigDecimal weightDiff = new BigDecimal(record.getArtt10()).subtract(new BigDecimal(record.getArtt9()));

            // 更新数量，保留3位小数
            BigDecimal newArtt7 = previousQty.setScale(0, RoundingMode.HALF_UP); // 变化前数量
            BigDecimal newArtt8 = previousQty.add(qtyDiff).setScale(0, RoundingMode.HALF_UP); // 变化后数量
            record.setArtt7(newArtt7.toString());
            record.setArtt8(newArtt8.toString());

            // 更新重量，保留3位小数
            BigDecimal newArtt9 = previousWeight.setScale(3, RoundingMode.HALF_UP); // 变化前重量
            BigDecimal newArtt10 = previousWeight.add(weightDiff).setScale(3, RoundingMode.HALF_UP); // 变化后重量
            record.setArtt9(newArtt9.toString());
            record.setArtt10(newArtt10.toString());

            // 更新下一条记录的基准值
            previousQty = newArtt8;
            previousWeight = newArtt10;
        }

        // Step 6: 批量更新数据库中的记录
        if (!subsequentRecords.isEmpty()) {
            wmsProductInoutMapper.batchUpdateRecords(subsequentRecords);
        }*/
    }
}
