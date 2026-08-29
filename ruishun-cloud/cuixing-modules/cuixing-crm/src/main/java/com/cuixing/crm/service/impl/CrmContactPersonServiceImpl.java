package com.cuixing.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.crm.entity.CrmContactPerson;
import com.cuixing.crm.mapper.CrmContactPersonMapper;
import com.cuixing.crm.service.ICrmContactPersonService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 客户联系人;(crm_contact_person)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2025-2-8
 */
@Service
public class CrmContactPersonServiceImpl implements ICrmContactPersonService{
    @Autowired
    private CrmContactPersonMapper crmContactPersonMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public CrmContactPerson queryById(BigInteger id){
        return crmContactPersonMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param crmContactPerson 筛选条件
     * @return 查询结果
     */
    @Override
    public List<CrmContactPerson> selectCrmContactPersonList(CrmContactPerson crmContactPerson){
       return crmContactPersonMapper.selectCrmContactPersonList(crmContactPerson);
    }
    
    /** 
     * 新增数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(CrmContactPerson crmContactPerson){
    	crmContactPerson.setCreateTime(DateUtils.getNowDate());
		crmContactPerson.setCreateBy(SecurityUtils.getUsername());
        return crmContactPersonMapper.insert(crmContactPerson);
    }
    
    /** 
     * 更新数据
     *
     * @param crmContactPerson 实例对象
     * @return 实例对象
     */
    @Override
    public int update(CrmContactPerson crmContactPerson){
        crmContactPerson.setUpdateTime(DateUtils.getNowDate());
		crmContactPerson.setUpdateBy(SecurityUtils.getUsername());
        return crmContactPersonMapper.update(crmContactPerson);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return crmContactPersonMapper.deleteByIds(ids);
    }
}