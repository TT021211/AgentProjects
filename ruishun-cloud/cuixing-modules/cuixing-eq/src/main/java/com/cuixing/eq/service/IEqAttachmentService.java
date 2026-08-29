package com.cuixing.eq.service;


import com.cuixing.eq.entity.EqAttachment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 设备配件设置;(ems_attachment)表服务接口
 *
 * @author : zhan
 * @date : 2024-7-11
 */
public interface IEqAttachmentService {
    /**
     * 通过ID查询单条数据
     *
     * @pram id 主键
     * @return 实例对象
     */
    EqAttachment queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param emsAttachment 筛选条件
     * @return 查询结果
     */
    List<EqAttachment> selectEmsAttachmentList(EqAttachment emsAttachment);

    /**
     * 新增数据
     *
     * @param emsAttachment 实例对象
     * @return 实例对象
     */
    int insert(EqAttachment emsAttachment);

    /**
     * 更新数据
     *
     * @param emsAttachment 实例对象
     * @return 实例对象
     */
    int update(EqAttachment emsAttachment);

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    List<EqAttachment> selectListByEquipmentId(BigInteger equipmentId);

    /**
     * 通过设备id和生产重量批量修改已生产重量
     * @param equipmentId
     * @param producedWeight
     * @return
     */
    boolean updateProducedWeight(BigInteger equipmentId, BigDecimal producedWeight);

    /**
     * 替换设备配件
     * @param oldEmsAttachmentId 旧设备配件id
     * @param emsAttachment 新设备配件信息
     * @return 是否成功
     */
    boolean replace(BigInteger oldEmsAttachmentId, EqAttachment emsAttachment);
}