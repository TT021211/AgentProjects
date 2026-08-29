package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureMaterialReturn;
import java.util.List;


/**
 * 采购退货管理(ProcureMaterialReturn)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureMaterialReturnService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureMaterialReturn queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureMaterialReturn 筛选条件
     * @return 查询结果
     */
    List<ProcureMaterialReturn> selectProcureMaterialReturnList(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 新增数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 实例对象
     */
    ProcureMaterialReturn insert(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 修改数据
     *
     * @param procureMaterialReturn 实例对象
     * @return 实例对象
     */
    ProcureMaterialReturn update(ProcureMaterialReturn procureMaterialReturn);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
