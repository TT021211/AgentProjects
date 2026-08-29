package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesOperationMethod;
import com.cuixing.mes.mapper.MesOperationMethodMapper;
import com.cuixing.mes.service.IMesOperationMethodService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 操作规范/作业指导书;(mes_operation_method)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-27
 */
@Service
public class MesOperationMethodServiceImpl implements IMesOperationMethodService{
    @Autowired
    private MesOperationMethodMapper mesOperationMethodMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesOperationMethod queryById(BigInteger id){
        return mesOperationMethodMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param mesOperationMethod 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesOperationMethod> selectMesOperationMethodList(MesOperationMethod mesOperationMethod){
       return mesOperationMethodMapper.selectMesOperationMethodList(mesOperationMethod);
    }
    
    /** 
     * 新增数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MesOperationMethod mesOperationMethod){
    	mesOperationMethod.setCreateTime(DateUtils.getNowDate());
		mesOperationMethod.setCreateBy(SecurityUtils.getUsername());
        return mesOperationMethodMapper.insert(mesOperationMethod);
    }
    
    /** 
     * 更新数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesOperationMethod mesOperationMethod){
        mesOperationMethod.setUpdateTime(DateUtils.getNowDate());
		mesOperationMethod.setUpdateBy(SecurityUtils.getUsername());
        return mesOperationMethodMapper.update(mesOperationMethod);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesOperationMethodMapper.deleteByIds(ids);
    }

     @Override
     public List<MesOperationMethod> getByEnanellingWorkbenchId(BigInteger enanellingWorkbenchId,String operationType) {
         return mesOperationMethodMapper.getByEnanellingWorkbenchId(enanellingWorkbenchId,operationType);
     }
 }