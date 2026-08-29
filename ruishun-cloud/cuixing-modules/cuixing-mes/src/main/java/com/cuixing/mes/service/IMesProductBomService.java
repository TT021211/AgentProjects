package com.cuixing.mes.service;

import com.cuixing.system.api.domain.mes.MesProductBom;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品BOM表;(mes_product_bom)表服务接口
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
public interface IMesProductBomService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductBom queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesProductBom 筛选条件
     * @return 查询结果
     */
    List<MesProductBom> selectMesProductBomList(MesProductBom mesProductBom);
    /**
     * 新增数据
     *
     * @param mesProductBom 实例对象
     * @return 影响行数
     */
    BigInteger insert(MesProductBom mesProductBom);

    /**
     * 批量新增数据
     *
     * @param mesProductBoms 实例对象数组
     * @return 影响行数
     */
    int insertBatch(List<MesProductBom> mesProductBoms);
    /**
     * 更新数据
     *
     * @param mesProductBom 实例对象
     * @return 影响行数
     */
    int update(MesProductBom mesProductBom);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean deleteByIds(BigInteger[] ids);
}