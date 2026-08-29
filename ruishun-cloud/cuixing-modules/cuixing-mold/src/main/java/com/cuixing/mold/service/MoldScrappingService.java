package com.cuixing.mold.service;

import com.cuixing.mold.entity.MoldScrapping;

import java.util.List;


/**
 * 模具报废表(MoldScrapping)表服务接口
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
public interface MoldScrappingService {

    /**
     * 通过ID查询单条数据
     *
     * @param moldScrappingId 主键
     * @return 实例对象
     */
    MoldScrapping queryById(Long moldScrappingId);

    /**
     * 分页查询
     *
     * @param moldScrapping 筛选条件
     * @return 查询结果
     */
    List<MoldScrapping> selectMoldScrappingList(MoldScrapping moldScrapping);

    /**
     * 确认报废
     *
     * @param moldScrapping 实例对象
     * @return 实例对象
     */
    MoldScrapping confirm(MoldScrapping moldScrapping);
    /**
     * 新增数据
     *
     * @param moldScrapping 实例对象
     * @return 实例对象
     */
    MoldScrapping insert(MoldScrapping moldScrapping);

    /**
     * 修改数据
     *
     * @param moldScrapping 实例对象
     * @return 实例对象
     */
    MoldScrapping update(MoldScrapping moldScrapping);

    /**
     * 通过主键删除数据
     *
     * @param moldScrappingId 主键
     * @return 是否成功
     */
    boolean deleteById(Long moldScrappingId);

}
