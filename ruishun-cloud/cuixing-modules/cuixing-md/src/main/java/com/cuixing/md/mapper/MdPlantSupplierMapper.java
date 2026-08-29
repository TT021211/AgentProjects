package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdPlantSupplier;

 /**
 * 油漆供应商;(md_plant_supplier)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Mapper
public interface MdPlantSupplierMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantSupplier queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdPlantSupplier 查询条件
     * @return 对象列表
     */
    List<MdPlantSupplier> selectMdPlantSupplierList(MdPlantSupplier mdPlantSupplier);
    /** 
     * 新增数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 影响行数
     */
    int insert(MdPlantSupplier mdPlantSupplier);
    
    /** 
     * 更新数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 影响行数
     */
    int update(MdPlantSupplier mdPlantSupplier);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}