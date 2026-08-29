package com.cuixing.eq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqListLog;
import com.cuixing.eq.mapper.EqListLogMapper;
import com.cuixing.eq.service.IEqListLogService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 点检(保养)记录;(eq_list_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
@Service
public class EqListLogServiceImpl implements IEqListLogService{
    @Autowired
    private EqListLogMapper eqListLogMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqListLog queryById(BigInteger id){
        return eqListLogMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqListLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqListLog> selectEqListLogList(EqListLog eqListLog){
       return eqListLogMapper.selectEqListLogList(eqListLog);
    }
    
    /** 
     * 新增数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqListLog eqListLog){
    	eqListLog.setCreateTime(DateUtils.getNowDate());
		eqListLog.setCreateBy(SecurityUtils.getUsername());
        eqListLog.setLogTime(DateUtils.getNowDate());
        return eqListLogMapper.insert(eqListLog);
    }
    
    /** 
     * 更新数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqListLog eqListLog){
        eqListLog.setUpdateTime(DateUtils.getNowDate());
		eqListLog.setUpdateBy(SecurityUtils.getUsername());
        return eqListLogMapper.update(eqListLog);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqListLogMapper.deleteByIds(ids);
    }
}