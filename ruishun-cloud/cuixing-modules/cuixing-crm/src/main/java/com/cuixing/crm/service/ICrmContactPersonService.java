package com.cuixing.crm.service;

import java.math.BigInteger;
import com.cuixing.crm.entity.CrmContactPerson;
import java.util.List;

 /**
 * 客户联系人;(crm_contact_person)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2025-2-8
 */
public interface ICrmContactPersonService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmContactPerson queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param crmContactPerson 筛选条件
     * @return 查询结果
     */
    List<CrmContactPerson> selectCrmContactPersonList(CrmContactPerson crmContactPerson);
    /** 
     * 新增数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    int insert(CrmContactPerson crmContactPerson);
    /** 
     * 更新数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    int update(CrmContactPerson crmContactPerson);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}