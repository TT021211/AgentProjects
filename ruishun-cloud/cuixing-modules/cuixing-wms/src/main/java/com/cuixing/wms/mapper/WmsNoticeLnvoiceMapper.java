package com.cuixing.wms.mapper;

import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.wms.entity.WmsNoticeLnvoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 发货通知单(WmsNoticeLnvoice)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
@Mapper
public interface WmsNoticeLnvoiceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsNoticeLnvoice queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wmsNoticeLnvoice 查询条件
     * @return 对象列表
     */
    List<WmsNoticeLnvoice> selectWmsNoticeLnvoiceList(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 统计总行数
     *
     * @param wmsNoticeLnvoice 查询条件
     * @return 总行数
     */
    long count(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 新增数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 影响行数
     */
    int insert(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsNoticeLnvoice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsNoticeLnvoice> entities);

    int insertBatch2(@Param("list") List<SaleOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsNoticeLnvoice> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsNoticeLnvoice> entities);

    /**
     * 修改数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 影响行数
     */
    int update(WmsNoticeLnvoice wmsNoticeLnvoice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    WmsNoticeLnvoice queryByCode(@Param("documentNumber") String documentNumber);
}

