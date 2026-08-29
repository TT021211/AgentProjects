package com.cuixing.md.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.md.entity.MdAccessory;

 /**
 * 辅料列表;(md_accessory)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Mapper
public interface MdAccessoryMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdAccessory queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mdAccessory 查询条件
     * @return 对象列表
     */
    List<MdAccessory> selectMdAccessoryList(MdAccessory mdAccessory);
    /** 
     * 新增数据
     *
     * @param mdAccessory 实例对象
     * @return 影响行数
     */
    int insert(MdAccessory mdAccessory);
    
    /** 
     * 更新数据
     *
     * @param mdAccessory 实例对象
     * @return 影响行数
     */
    int update(MdAccessory mdAccessory);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}