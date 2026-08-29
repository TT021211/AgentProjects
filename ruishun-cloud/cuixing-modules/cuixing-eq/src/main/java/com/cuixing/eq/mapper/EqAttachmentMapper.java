package com.cuixing.eq.mapper;

import com.cuixing.eq.entity.EqAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * 设备配件设置;(eq_attachment)表数据库访问层
 *
 * @author : zhan
 * @date : 2024-7-11
 */
@Mapper
public interface EqAttachmentMapper {
    /**
     * 通过ID查询单条数据
     *
     * @paqam id 主键
     * @return 实例对象
     */
    EqAttachment queryById(BigInteger id);

    /**
     * 分页查询指定行数据
     *
     * @param eqAttachment 查询条件
     * @return 对象列表
     */
    List<EqAttachment> selectEmsAttachmentList(EqAttachment eqAttachment);

    /**
     * 新增数据
     *
     * @param emsAttachment 实例对象
     * @return 影响行数
     */
    int insert(EqAttachment emsAttachment);

    /**
     * 更新数据
     *
     * @param emsAttachment 实例对象
     * @return 影响行数
     */
    int update(EqAttachment emsAttachment);

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    /**
     * 通过设备id获取配件列表
     *
     * @param equipmentId 主键
     * @return 影响行数
     */
    List<EqAttachment> selectListByEquipmentId(@Param("equipmentId") BigInteger equipmentId);
}