package com.cuixing.purchase.service;

import com.cuixing.purchase.entity.ProcureContract;
import java.util.List;


/**
 * 采购合同管理(ProcureContract)表服务接口
 *
 * @author 阿江
 * @since 2025-03-04 14:57:35
 */
public interface ProcureContractService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProcureContract queryById(Long id);

    /**
     * 分页查询
     *
     * @param procureContract 筛选条件
     * @return 查询结果
     */
    List<ProcureContract> selectProcureContractList(ProcureContract procureContract);

    /**
     * 新增数据
     *
     * @param procureContract 实例对象
     * @return 实例对象
     */
    ProcureContract insert(ProcureContract procureContract);

    /**
     * 修改数据
     *
     * @param procureContract 实例对象
     * @return 实例对象
     */
    ProcureContract update(ProcureContract procureContract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
