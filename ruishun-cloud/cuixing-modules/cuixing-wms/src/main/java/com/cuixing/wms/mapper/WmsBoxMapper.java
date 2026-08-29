package com.cuixing.wms.mapper;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.vo.WmsBoxListKanbanVo;
import com.cuixing.wms.entity.vo.WmsIngredientsLog12Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * (WmsBox)表数据库访问层
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
@Mapper
public interface WmsBoxMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsBox queryById(Long id);

    WmsBox queryByCode(@Param("boxCode") String boxCode);

    /**
     * 查询指定行数据
     *
     * @param wmsBox 查询条件
     * @return 对象列表
     */
    List<WmsBox> selectWmsBoxList(WmsBox wmsBox);

    /**
     * 统计总行数
     *
     * @param wmsBox 查询条件
     * @return 总行数
     */
    long count(WmsBox wmsBox);

    /**
     * 新增数据
     *
     * @param wmsBox 实例对象
     * @return 影响行数
     */
    int insert(WmsBox wmsBox);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsBox> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WmsBox> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WmsBox> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WmsBox> entities);

    /**
     * 修改数据
     *
     * @param wmsBox 实例对象
     * @return 影响行数
     */
    int update(WmsBox wmsBox);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     */
    int disassociation(WmsBox wmsBox1);

    List<WmsBox> selectInstallation(@Param("installationCodes") List<String> installationCodes);

    int updateToNotice(Long id);

    List<WmsBox> queryByDocumentNumber(@Param("documentNumber")String documentNumber);

    int deleteByBoxCode(@Param("boxCode")String boxCode);

    int getCountBoxNumber(@Param("inboundStatus")String inboundStatus);

    BigDecimal getCountWeight(@Param("inboundStatus")String inboundStatus);

    List<WmsBoxListKanbanVo> getCountBoxData(@Param("inboundStatus")String inboundStatus);

    int getCountBoxNumbertoDay(@Param("inboundStatus")String inboundStatus);

    BigDecimal getCountWeighttoDay(@Param("inboundStatus")String inboundStatus);

    List<WmsBox> selectWmsBoxListTowDay(@Param("inboundStatus")String inboundStatus);

    void updateWeight(@Param("id") Long id, @Param("countNetWeight") Double countNetWeight,@Param("countGrossWeight") Double countGrossWeight);

    //WmsBox queryByUserName(String username);
}

