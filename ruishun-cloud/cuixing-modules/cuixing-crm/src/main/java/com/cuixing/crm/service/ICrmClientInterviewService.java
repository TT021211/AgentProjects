package com.cuixing.crm.service;

import com.cuixing.crm.entity.CrmClientInterview;

import java.math.BigInteger;


import java.util.List;

/**
* 客户访问记录;(crm_client_interview)表服务接口
* @author : http://www.chiner.pro
* @date : 2025-1-7
*/
public interface ICrmClientInterviewService {
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   CrmClientInterview queryById(BigInteger id);
   /**
    * 分页查询
    *
    * @param crmClientInterview 筛选条件
    * @return 查询结果
    */
   List<CrmClientInterview> selectCrmClientInterviewList(CrmClientInterview crmClientInterview);
   /**
    * 新增数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   int insert(CrmClientInterview crmClientInterview);
   /**
    * 更新数据
    *
    * @param crmClientInterview 实例对象
    * @return 实例对象
    */
   int update(CrmClientInterview crmClientInterview);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 是否成功
    */
   int deleteByIds(BigInteger[] ids);
}