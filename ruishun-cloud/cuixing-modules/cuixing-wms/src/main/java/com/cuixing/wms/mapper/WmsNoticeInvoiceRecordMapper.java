package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsNoticeInvoiceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * 发货通知单审核记录表(WmsNoticeInvoiceRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-30 14:09:47
 */
@Mapper
public interface WmsNoticeInvoiceRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsNoticeInvoiceRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param wmsNoticeInvoiceRecord 查询条件
     * @return 对象列表
     */
    List<WmsNoticeInvoiceRecord> selectWmsNoticeInvoiceRecordList(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 统计总行数
     *
     * @param wmsNoticeInvoiceRecord 查询条件
     * @return 总行数
     */
    long count(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 新增数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 影响行数
     */
    int insert(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsNoticeInvoiceRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsNoticeInvoiceRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsNoticeInvoiceRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsNoticeInvoiceRecord> entities);

    /**
     * 修改数据
     *
     * @param wmsNoticeInvoiceRecord 实例对象
     * @return 影响行数
     */
    int update(WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    List<WmsNoticeInvoiceRecord> selectWmsNoticeInvoiceRecordList2(@Param("noticeInvoiceId") Long noticeInvoiceId);

}

