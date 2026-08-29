package com.cuixing.crm.mapper;

import com.cuixing.crm.entity.CrmClientInterview;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
* 客户访问记录;(crm_client_interview)表数据库访问层
* @author : http://www.chiner.pro
* @date : 2025-1-7
*/
@Mapper
public interface CrmClientInterviewMapper{
   /**
    * 通过ID查询单条数据
    *
    * @param id 主键
    * @return 实例对象
    */
   CrmClientInterview queryById(BigInteger id);
   /**
    * 分页查询指定行数据
    *
    * @param crmClientInterview 查询条件
    * @return 对象列表
    */
   List<CrmClientInterview> selectCrmClientInterviewList(CrmClientInterview crmClientInterview);
   /**
    * 新增数据
    *
    * @param crmClientInterview 实例对象
    * @return 影响行数
    */
   int insert(CrmClientInterview crmClientInterview);

   /**
    * 更新数据
    *
    * @param crmClientInterview 实例对象
    * @return 影响行数
    */
   int update(CrmClientInterview crmClientInterview);
   /**
    * 通过主键批量删除数据
    *
    * @param ids 主键
    * @return 影响行数
    */
   int deleteByIds(BigInteger[] ids);


   @MapKey("type")
   List<Map<String,Object>> typeAnalyze();

   @MapKey("month")
   List<Map<String,Object>> getInterviewClientMonth();
}