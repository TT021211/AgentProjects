package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdAccessory;
import java.util.List;

 /**
 * 辅料列表;(md_accessory)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
public interface IMdAccessoryService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdAccessory queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdAccessory 筛选条件
     * @return 查询结果
     */
    List<MdAccessory> selectMdAccessoryList(MdAccessory mdAccessory);
    /** 
     * 新增数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    int insert(MdAccessory mdAccessory);
    /** 
     * 更新数据
     *
     * @param mdAccessory 实例对象
     * @return 实例对象
     */
    int update(MdAccessory mdAccessory);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}