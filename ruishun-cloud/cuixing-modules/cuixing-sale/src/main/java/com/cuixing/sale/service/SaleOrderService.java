package com.cuixing.sale.service;



import com.cuixing.sale.entity.SaleOrder;

import java.math.BigInteger;
import java.util.List;

/**
* 销售订单;(md_sale_order)表服务接口
* @author : http://www.chiner.pro
* @date : 2024-6-6
*/
public interface SaleOrderService {
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   SaleOrder queryById(BigInteger id);
   /**
    * 分页查询
    *
    * @param mdSaleOrder 筛选条件
    * @return 查询结果
    */
   List<SaleOrder> selectMdSaleOrderList(SaleOrder mdSaleOrder);
   /**
    * 新增数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   int insert(SaleOrder mdSaleOrder);
   /**
    * 更新数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   int update(SaleOrder mdSaleOrder);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   int deleteByIds(BigInteger[] ids);

    List<SaleOrder> selectAll();

    int insertBatch(List<SaleOrder> mdSaleOrderList);
}
