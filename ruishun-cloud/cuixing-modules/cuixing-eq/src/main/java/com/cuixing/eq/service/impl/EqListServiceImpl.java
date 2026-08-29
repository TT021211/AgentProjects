package com.cuixing.eq.service.impl;

import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.eq.entity.EqListLog;
import com.cuixing.eq.mapper.EqListLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqList;
import com.cuixing.eq.mapper.EqListMapper;
import com.cuixing.eq.service.IEqListService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
 /**
 * 待点检(保养)列表;(eq_list)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Service
public class EqListServiceImpl implements IEqListService{
    @Autowired
    private EqListMapper eqListMapper;

    @Autowired
    private EqListLogMapper eqListLogMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqList queryById(BigInteger id){
        return eqListMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqList 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqList> selectEqListList(EqList eqList){
       return eqListMapper.selectEqListList(eqList);
    }
    
    /** 
     * 新增数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqList eqList){
    	eqList.setCreateTime(DateUtils.getNowDate());
		eqList.setCreateBy(SecurityUtils.getUsername());
        return eqListMapper.insert(eqList);
    }
    
    /** 
     * 更新数据
     *
     * @param eqList 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqList eqList){
        eqList.setUpdateTime(DateUtils.getNowDate());
		eqList.setUpdateBy(SecurityUtils.getUsername());
        return eqListMapper.update(eqList);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqListMapper.deleteByIds(ids);
    }

     /**
      * 根据计划id删除待点检(保养)列表
      *
      * @param planId 主键
      * @return 影响行数
      */
     @Override
     public int deleteByPlanId(BigInteger planId) {
         //根据计划id删除待点检(保养)列表
         EqList selectConditions = new EqList();
         selectConditions.setPlanId(planId);
         List<EqList> eqLists = eqListMapper.selectEqListList(selectConditions);
         if (eqLists.size() == 0){
             return 1;
         }
         BigInteger[] ids = new BigInteger[eqLists.size()];
         for (int i = 0; i < eqLists.size(); i++) {
             EqList eqList = eqLists.get(i);
             //生成点检(保养)日志
             EqListLog eqListLog = new EqListLog();
             BeanUtils.copyProperties(eqList, eqListLog);
             eqListLog.setLogTime(DateUtils.getNowDate());
             eqListLog.setId(null);
             if (eqList.getPlanType().equals("0")){
                 eqListLog.setStatus("1");
             }else if (eqList.getPlanType().equals("1")) {
                 eqListLog.setStatus("2");
             }
             System.out.println(eqListLog);
             eqListLogMapper.insert(eqListLog);
             //删除当前待点检(保养)数据
             ids[i] = eqList.getId();
         }
         return eqListMapper.deleteByIds(ids);
     }


     @Override
     public int getListByPlanType(String planType) {

         return eqListMapper.getListByPlanType(planType);
     }
 }