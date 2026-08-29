package com.cuixing.sale.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.sale.entity.SaleOrder;
import com.cuixing.sale.mapper.SaleOrderMapper;
import com.cuixing.sale.service.SaleOrderService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdProduct;
import com.cuixing.system.api.domain.md.MdUnitMeasure;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
* 销售订单;(md_sale_order)表服务实现类
* @author : http://www.chiner.pro
* @date : 2024-6-6
*/
@Service
public class SaleOrderServiceImpl implements SaleOrderService {
   @Autowired
   private SaleOrderMapper mdSaleOrderMapper;

   @Autowired
   private RemoteMdService remoteMdService;

   @Autowired
   private RemoteSystemService remoteSystemService;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @Override
   public SaleOrder queryById(BigInteger id){
       return mdSaleOrderMapper.queryById(id);
   }

   /**
    * 分页查询
    *
    * @param mdSaleOrder 筛选条件
    * @return 查询结果
    */
   @Override
   public List<SaleOrder> selectMdSaleOrderList(SaleOrder mdSaleOrder){
      return mdSaleOrderMapper.selectMdSaleOrderList(mdSaleOrder);
   }

   /**
    * 获取列表
    *
    * @return 列表
    */
    @Override
    public List<SaleOrder> selectAll() {
        return mdSaleOrderMapper.selectAll();
    }

    @Override
    public int insertBatch(List<SaleOrder> mdSaleOrderList) {
        return this.mdSaleOrderMapper.insertBatch(mdSaleOrderList);
    }

    /**
    * 新增数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   @Override
   @GlobalTransactional
   public int insert(SaleOrder mdSaleOrder){
       if (mdSaleOrder.getMeasureId() != null){
           MdUnitMeasure mdUnitMeasure = remoteMdService.getMeasure(mdSaleOrder.getMeasureId(), SecurityConstants.INNER).getData();
           if (mdUnitMeasure == null){
               throw new RuntimeException("主数据服务调用失败");
           }
           mdSaleOrder.setMeasureName(mdUnitMeasure.getMeasureName());
       }
       //生成订单编码
       String autoCode = remoteSystemService.getAutoCode("SALE_ORDER_CODE", "1");
       mdSaleOrder.setOrderCode(autoCode);
       //通过产品id获取数据
       MdProduct mdProduct = remoteMdService.getProduct(mdSaleOrder.getProductId(), SecurityConstants.INNER).getData();
       if (mdProduct == null){
           throw new RuntimeException("主数据服务调用失败");
       }
       mdSaleOrder.setSpecification(mdProduct.getSpecification());
       mdSaleOrder.setModel(mdProduct.getProductSize());
       if (mdSaleOrder.getCreateTime() == null){
           mdSaleOrder.setCreateTime(DateUtils.getNowDate());
       }else{
           mdSaleOrder.setCreateTime(mdSaleOrder.getCreateTime());
       }
       mdSaleOrder.setCreateBy(SecurityUtils.getUsername());
       return mdSaleOrderMapper.insert(mdSaleOrder);
   }

   /**
    * 更新数据
    *
    * @param mdSaleOrder 实例对象
    * @return 实例对象
    */
   @Override
   @GlobalTransactional
   public int update(SaleOrder mdSaleOrder){
       if (mdSaleOrder.getMeasureId() != null){
           MdUnitMeasure mdUnitMeasure = remoteMdService.getMeasure(mdSaleOrder.getMeasureId(), SecurityConstants.INNER).getData();
           if (mdUnitMeasure == null){
               throw new RuntimeException("主数据服务调用失败");
           }
           mdSaleOrder.setMeasureName(mdUnitMeasure.getMeasureName());
       }
       mdSaleOrder.setUpdateTime(DateUtils.getNowDate());
       mdSaleOrder.setUpdateBy(SecurityUtils.getUsername());
       return mdSaleOrderMapper.update(mdSaleOrder);
   }
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @Override
   public int deleteByIds(BigInteger[] ids){
       return mdSaleOrderMapper.deleteByIds(ids);
   }
}
