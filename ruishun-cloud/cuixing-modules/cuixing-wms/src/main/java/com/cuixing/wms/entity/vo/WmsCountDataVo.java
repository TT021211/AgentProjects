package com.cuixing.wms.entity.vo;

import com.cuixing.wms.entity.WmsBox;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class WmsCountDataVo {
    // 所有在库的箱数据
    private Integer countBoxNum;
    // 成品总重量(在库)
    private BigDecimal productTotalWeight;
    // 原料总重量(在库)
    private BigDecimal ingredientsTotalWeight;
    // 铜丝半成品总重量(在库)
    private BigDecimal semiProductTotalWeight;
    // 在库箱数的统计列表
    private List<WmsBoxListKanbanVo> wmsBoxListKanbanVo;
    // 成品库存箱数
    private Integer productBoxNum;
    // 成品今日入库箱数
    private Integer productInBoxNum;
    // 成品今日入口重量
    private BigDecimal productInWeight;
    // 成品今日出库箱数
    private Integer productOutBoxNum;
    // 成品今日出库重量
    private BigDecimal productOutWeight;
    // 成品即时库存
    private List<WmsBox> wmsBoxList;
    // 最近3天入库
    private List<WmsBox> wmsBoxListTowDay;
    // 今年每月出入库重量及数量
    private List<WmsIngredientsLog12Vo> wmsIngredientsLog12Vo;

}
