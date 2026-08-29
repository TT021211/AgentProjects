package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdPlantType;

 /**
 * 油漆类型;(md_plant_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Mapper
public interface MdPlantTypeMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantType queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdPlantType 查询条件
     * @return 对象列表
     */
    List<MdPlantType> selectMdPlantTypeList(MdPlantType mdPlantType);
    /** 
     * 新增数据
     *
     * @param mdPlantType 实例对象
     * @return 影响行数
     */
    int insert(MdPlantType mdPlantType);
    
    /** 
     * 更新数据
     *
     * @param mdPlantType 实例对象
     * @return 影响行数
     */
    int update(MdPlantType mdPlantType);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}