package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdAccessorySupplier;
import java.util.List;

 /**
 * 辅料供应商;(md_accessory_supplier)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-10-29
 */
public interface IMdAccessorySupplierService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MdAccessorySupplier queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mdAccessorySupplier 筛选条件
     * @return 查询结果
     */
    List<MdAccessorySupplier> selectMdAccessorySupplierList(MdAccessorySupplier mdAccessorySupplier);
    /**
     * 新增数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    int insert(MdAccessorySupplier mdAccessorySupplier);
    /**
     * 更新数据
     *
     * @param mdAccessorySupplier 实例对象
     * @return 实例对象
     */
    int update(MdAccessorySupplier mdAccessorySupplier);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}
