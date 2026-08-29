package com.cuixing.sale.mapper;

import com.cuixing.sale.entity.SaleOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
* 销售订单;(md_sale_order)表数据库访问层
* @author : http://www.chiner.pro
* @date : 2024-6-6
*/
@Mapper
public interface SaleOrderMapper {
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   SaleOrder queryById(BigInteger id);
   /**
    * 分页查询指定行数据
    *
    * @param mdSaleOrder 查询条件
    * @return 对象列表
    */
   List<SaleOrder> selectMdSaleOrderList(SaleOrder mdSaleOrder);
   /**
    * 新增数据
    *
    * @param mdSaleOrder 实例对象
    * @return 影响行数
    */
   int insert(SaleOrder mdSaleOrder);

   /**
    * 更新数据
    *
    * @param mdSaleOrder 实例对象
    * @return 影响行数
    */
   int update(SaleOrder mdSaleOrder);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 影响行数
    */
   int deleteByIds(BigInteger[] ids);

    List<SaleOrder> selectAll();

    int insertBatch(@Param("list") List<SaleOrder> mdSaleOrderList);
}
