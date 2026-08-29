package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdPlantType;
import java.util.List;

 /**
 * 油漆类型;(md_plant_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
public interface IMdPlantTypeService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantType queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdPlantType 筛选条件
     * @return 查询结果
     */
    List<MdPlantType> selectMdPlantTypeList(MdPlantType mdPlantType);
    /** 
     * 新增数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    int insert(MdPlantType mdPlantType);
    /** 
     * 更新数据
     *
     * @param mdPlantType 实例对象
     * @return 实例对象
     */
    int update(MdPlantType mdPlantType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}