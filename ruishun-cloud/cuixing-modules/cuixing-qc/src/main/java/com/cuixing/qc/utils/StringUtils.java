package com.cuixing.qc.utils;

import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.service.QcDefectService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils {
    @Resource
    private static QcDefectService defectService;
    /**
     * 将字符串1,2,3格式切割成Long[]
     * @param itemsId
     * @return
     */
    public static Long[] stringToLongArray(String itemsId) {
        String[] idArray = itemsId.split(",");
        Long[] longArray = new Long[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
            longArray[i] = Long.parseLong(idArray[i]);
        }
        return longArray;
    }
    /**
     * 查询返回缺陷项对象
     */
    public static List<QcDefect> queryDefectItem(String itemsId) {
        Long[] longs = stringToLongArray(itemsId);
        List<QcDefect> result = new ArrayList<>();
        for (Long id : longs) {
            QcDefect qcDefect = defectService.queryById(id);
            if (qcDefect!=null) {
                result.add(qcDefect);
            }
        }
        return result;
    }
}
