package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdPlantSupplier;
import java.util.List;

 /**
 * 油漆供应商;(md_plant_supplier)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
public interface IMdPlantSupplierService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantSupplier queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdPlantSupplier 筛选条件
     * @return 查询结果
     */
    List<MdPlantSupplier> selectMdPlantSupplierList(MdPlantSupplier mdPlantSupplier);
    /** 
     * 新增数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    int insert(MdPlantSupplier mdPlantSupplier);
    /** 
     * 更新数据
     *
     * @param mdPlantSupplier 实例对象
     * @return 实例对象
     */
    int update(MdPlantSupplier mdPlantSupplier);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}