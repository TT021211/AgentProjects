package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesProcessEnter;
import java.util.List;

 /**
 * 工艺录入;(mes_process_enter)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
public interface IMesProcessEnterService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProcessEnter queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesProcessEnter 筛选条件
     * @return 查询结果
     */
    List<MesProcessEnter> selectMesProcessEnterList(MesProcessEnter mesProcessEnter);
    /** 
     * 新增数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    int insert(MesProcessEnter mesProcessEnter);
    /** 
     * 更新数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    int update(MesProcessEnter mesProcessEnter);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    MesProcessEnter getInfoByEquipmentIdAndSpecification(BigInteger equipmentId,String specification);

    MesProcessEnter getInfoByEquipmentCodeAndOther(String equipmentCode, String productSpecification, String productModel,String attr1);
 }