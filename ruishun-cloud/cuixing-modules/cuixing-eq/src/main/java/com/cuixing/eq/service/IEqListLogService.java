package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqListLog;
import java.util.List;

 /**
 * 点检(保养)记录;(eq_list_log)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-9
 */
public interface IEqListLogService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqListLog queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqListLog 筛选条件
     * @return 查询结果
     */
    List<EqListLog> selectEqListLogList(EqListLog eqListLog);
    /** 
     * 新增数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    int insert(EqListLog eqListLog);
    /** 
     * 更新数据
     *
     * @param eqListLog 实例对象
     * @return 实例对象
     */
    int update(EqListLog eqListLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}