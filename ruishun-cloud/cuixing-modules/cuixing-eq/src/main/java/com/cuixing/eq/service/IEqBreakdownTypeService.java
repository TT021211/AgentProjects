package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqBreakdownType;
import java.util.List;

 /**
 * 设备故障类型;(eq_breakdown_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-3
 */
public interface IEqBreakdownTypeService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqBreakdownType queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqBreakdownType 筛选条件
     * @return 查询结果
     */
    List<EqBreakdownType> selectEqBreakdownTypeList(EqBreakdownType eqBreakdownType);
    /** 
     * 新增数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    int insert(EqBreakdownType eqBreakdownType);
    /** 
     * 更新数据
     *
     * @param eqBreakdownType 实例对象
     * @return 实例对象
     */
    int update(EqBreakdownType eqBreakdownType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}