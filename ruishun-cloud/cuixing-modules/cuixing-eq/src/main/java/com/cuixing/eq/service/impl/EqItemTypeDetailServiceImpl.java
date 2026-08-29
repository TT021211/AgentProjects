package com.cuixing.eq.service.impl;

import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.eq.entity.EqItem;
import com.cuixing.eq.entity.EqItemType;
import com.cuixing.eq.mapper.EqItemMapper;
import com.cuixing.eq.mapper.EqItemTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqItemTypeDetail;
import com.cuixing.eq.mapper.EqItemTypeDetailMapper;
import com.cuixing.eq.service.IEqItemTypeDetailService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
 /**
 * 项目类型详情;(eq_item_type_detail)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Service
public class EqItemTypeDetailServiceImpl implements IEqItemTypeDetailService{
    @Autowired
    private EqItemTypeDetailMapper eqItemTypeDetailMapper;

    @Autowired
    private EqItemMapper eqItemMapper;

    @Autowired
    private EqItemTypeMapper eqItemTypeMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqItemTypeDetail queryById(BigInteger id){
        return eqItemTypeDetailMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqItemTypeDetail 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqItemTypeDetail> selectEqItemTypeDetailList(EqItemTypeDetail eqItemTypeDetail){
       return eqItemTypeDetailMapper.selectEqItemTypeDetailList(eqItemTypeDetail);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqItemTypeDetail eqItemTypeDetail){
    	eqItemTypeDetail.setCreateTime(DateUtils.getNowDate());
		eqItemTypeDetail.setCreateBy(SecurityUtils.getUsername());
        return eqItemTypeDetailMapper.insert(eqItemTypeDetail);
    }
    
    /** 
     * 更新数据
     *
     * @param eqItemTypeDetail 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqItemTypeDetail eqItemTypeDetail){
        eqItemTypeDetail.setUpdateTime(DateUtils.getNowDate());
		eqItemTypeDetail.setUpdateBy(SecurityUtils.getUsername());
        return eqItemTypeDetailMapper.update(eqItemTypeDetail);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        //删除的数量
        int totalItem = ids.length;
        //通过id查询项目类型
        EqItemTypeDetail eqItemTypeDetail = eqItemTypeDetailMapper.queryById(new BigInteger(ids[0].toString()));
        EqItemType eqItemType = eqItemTypeMapper.queryById(eqItemTypeDetail.getItemTypeId());
        eqItemType.setTotalItem(eqItemType.getTotalItem()-totalItem);
        eqItemTypeMapper.update(eqItemType);
        //更新项目类型数量
        return eqItemTypeDetailMapper.deleteByIds(ids);
    }

     /**
      * 批量新增数据
      */
     @Override
     @Transactional
     public int allocatedUserByIds(Long[] itemIds, EqItemType eqItemType) {
         int result = 0;
         //获取
         for (Long itemId : itemIds) {
             EqItem eqItem = eqItemMapper.queryById(new BigInteger(itemId.toString()));
             EqItemTypeDetail eqItemTypeDetail = new EqItemTypeDetail();
             eqItemTypeDetail.setCreateTime(DateUtils.getNowDate());
             eqItemTypeDetail.setCreateBy(SecurityUtils.getUsername());
             eqItemTypeDetail.setItemTypeId(eqItemType.getId());
             eqItemTypeDetail.setItemTypeName(eqItemType.getItemTypeName());
             eqItemTypeDetail.setItemId(eqItem.getId());
             eqItemTypeDetail.setItemName(eqItem.getItemName());
             eqItemTypeDetail.setItemContent(eqItem.getItemContent());
             eqItemTypeDetail.setCompletionStandard(eqItem.getCompletionStandard());
             eqItemTypeDetail.setRemark(eqItem.getRemark());
             result += eqItemTypeDetailMapper.insert(eqItemTypeDetail);
         }
         if (result != itemIds.length){
             throw new EqException("批量添加失败");
         }
         //新增的数量
         int num = itemIds.length;
         //更新项目类型数量
         EqItemType newEqItemType = eqItemTypeMapper.queryById(eqItemType.getId());
         newEqItemType.setTotalItem(eqItemType.getTotalItem() + num);
         eqItemTypeMapper.update(newEqItemType);
         return result;
     }
 }