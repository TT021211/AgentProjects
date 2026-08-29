package com.cuixing.md.service;

import java.math.BigInteger;
import com.cuixing.md.entity.MdPlantList;
import java.util.List;

 /**
 * 油漆清单;(md_plant_list)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-8-8
 */
public interface IMdPlantListService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MdPlantList queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mdPlantList 筛选条件
     * @return 查询结果
     */
    List<MdPlantList> selectMdPlantListList(MdPlantList mdPlantList);
    /** 
     * 新增数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    int insert(MdPlantList mdPlantList);
    /** 
     * 更新数据
     *
     * @param mdPlantList 实例对象
     * @return 实例对象
     */
    int update(MdPlantList mdPlantList);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);
}