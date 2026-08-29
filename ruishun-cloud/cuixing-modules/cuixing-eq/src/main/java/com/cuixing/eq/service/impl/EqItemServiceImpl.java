package com.cuixing.eq.service.impl;

import com.cuixing.eq.entity.EqList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqItem;
import com.cuixing.eq.mapper.EqItemMapper;
import com.cuixing.eq.service.IEqItemService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 设备项目维护;(eq_item)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Service
public class EqItemServiceImpl implements IEqItemService{
    @Autowired
    private EqItemMapper eqItemMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqItem queryById(BigInteger id){
        return eqItemMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqItem 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqItem> selectEqItemList(EqItem eqItem){
       return eqItemMapper.selectEqItemList(eqItem);
    }
    
    /** 
     * 新增数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqItem eqItem){
    	eqItem.setCreateTime(DateUtils.getNowDate());
		eqItem.setCreateBy(SecurityUtils.getUsername());
        return eqItemMapper.insert(eqItem);
    }
    
    /** 
     * 更新数据
     *
     * @param eqItem 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqItem eqItem){
        eqItem.setUpdateTime(DateUtils.getNowDate());
		eqItem.setUpdateBy(SecurityUtils.getUsername());
        return eqItemMapper.update(eqItem);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqItemMapper.deleteByIds(ids);
    }

 }