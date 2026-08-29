package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdAccessorySupplier;

 /**
 * 辅料供应商;(md_accessory_supplier)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-10-29
 */
@Mapper
public interface MdAccessorySupplierMapper{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdAccessorySupplier queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param mdAccessorySupplier 查询条件
     * @return 对象列表
     */
    List<MdAccessorySupplier> selectMdAccessorySupplierList(MdAccessorySupplier mdAccessorySupplier);
    /**
     * 新增数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 影响行数
     */
    int insert(MdAccessorySupplier mdAccessorySupplier);

    /**
     * 更新数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 影响行数
     */
    int update(MdAccessorySupplier mdAccessorySupplier);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}
