package com.cuixing.crm.service.impl;

import com.cuixing.crm.entity.CrmClientInterview;
import com.cuixing.crm.mapper.CrmClientInterviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.crm.service.ICrmClientInterviewService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;

/**
* 客户访问记录;(crm_client_interview)表服务实现类
* @author : http://www.chiner.pro
* @date : 2025-1-7
*/
@Service
public class CrmClientInterviewServiceImpl implements ICrmClientInterviewService {
   @Autowired
   private CrmClientInterviewMapper crmClientInterviewMapper;

   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   @Override
   public CrmClientInterview queryById(BigInteger id){
       return crmClientInterviewMapper.queryById(id);
   }

   /**
    * 分页查询
    *
    * @param crmClientInterview 筛选条件
    * @return 查询结果
    */
   @Override
   public List<CrmClientInterview> selectCrmClientInterviewList(CrmClientInterview crmClientInterview){
      return crmClientInterviewMapper.selectCrmClientInterviewList(crmClientInterview);
   }

   /**
    * 新增数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   @Override
   public int insert(CrmClientInterview crmClientInterview){
       crmClientInterview.setCreateTime(DateUtils.getNowDate());
       crmClientInterview.setCreateBy(SecurityUtils.getUsername());
       return crmClientInterviewMapper.insert(crmClientInterview);
   }

   /**
    * 更新数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   @Override
   public int update(CrmClientInterview crmClientInterview){
       crmClientInterview.setUpdateTime(DateUtils.getNowDate());
       crmClientInterview.setUpdateBy(SecurityUtils.getUsername());
       return crmClientInterviewMapper.update(crmClientInterview);
   }
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   @Override
   public int deleteByIds(BigInteger[] ids){
       return crmClientInterviewMapper.deleteByIds(ids);
   }
}