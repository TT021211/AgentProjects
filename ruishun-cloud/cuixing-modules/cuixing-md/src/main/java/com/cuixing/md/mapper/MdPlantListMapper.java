package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdPlantList;

 /**
 * 油漆清单;(md_plant_list)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-8-8
 */
@Mapper
public interface MdPlantListMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantList queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdPlantList 查询条件
     * @return 对象列表
     */
    List<MdPlantList> selectMdPlantListList(MdPlantList mdPlantList);
    /** 
     * 新增数据
     *
     * @param mdPlantList 实例对象
     * @return 影响行数
     */
    int insert(MdPlantList mdPlantList);
    
    /** 
     * 更新数据
     *
     * @param mdPlantList 实例对象
     * @return 影响行数
     */
    int update(MdPlantList mdPlantList);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}