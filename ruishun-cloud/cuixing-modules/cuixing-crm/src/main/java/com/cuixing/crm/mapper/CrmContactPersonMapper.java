package com.cuixing.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.crm.entity.CrmContactPerson;

 /**
 * 客户联系人;(crm_contact_person)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2025-2-8
 */
@Mapper
public interface CrmContactPersonMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmContactPerson queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param crmContactPerson 查询条件
     * @return 对象列表
     */
    List<CrmContactPerson> selectCrmContactPersonList(CrmContactPerson crmContactPerson);
    /** 
     * 新增数据
     *
     * @param crmContactPerson 实例对象
     * @return 影响行数
     */
    int insert(CrmContactPerson crmContactPerson);
    
    /** 
     * 更新数据
     *
     * @param crmContactPerson 实例对象
     * @return 影响行数
     */
    int update(CrmContactPerson crmContactPerson);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}