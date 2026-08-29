package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldMaintainItem;
import com.cuixing.mold.mapper.MoldMaintainItemMapper;
import com.cuixing.mold.service.MoldMaintainItemService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具保养表--保养设置表(MoldMaintainItem)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
@Service("moldMaintainItemService")
public class MoldMaintainItemServiceImpl implements MoldMaintainItemService {
    @Resource
    private MoldMaintainItemMapper moldMaintainItemMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param maintainItemId 主键
     * @return 实例对象
     */
    @Override
    public MoldMaintainItem queryById(Long maintainItemId) {
        return this.moldMaintainItemMapper.queryById(maintainItemId);
    }

    /**
     * 分页查询
     *
     * @param moldMaintainItem 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintainItem> selectMoldMaintainItemList(MoldMaintainItem moldMaintainItem) {
        return this.moldMaintainItemMapper.selectMoldMaintainItemList(moldMaintainItem);
    }
    /**
     * 分页查询
     *
     * @param moldMaintainItem 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintainItem> selectMoldMaintainItemList2(MoldMaintainItem moldMaintainItem) {
        return this.moldMaintainItemMapper.selectMoldMaintainItemList2(moldMaintainItem);
    }

    /**
     * 新增数据
     *
     * @param moldMaintainItem 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainItem insert(MoldMaintainItem moldMaintainItem) {
        moldMaintainItem.setCreateTime(new Date());
        moldMaintainItem.setCreateBy(SecurityUtils.getUsername());
        this.moldMaintainItemMapper.insert(moldMaintainItem);
        return moldMaintainItem;
    }

    /**
     * 修改数据
     *
     * @param moldMaintainItem 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainItem update(MoldMaintainItem moldMaintainItem) {
        moldMaintainItem.setUpdateTime(new Date());
        moldMaintainItem.setUpdateBy(SecurityUtils.getUsername());
        this.moldMaintainItemMapper.update(moldMaintainItem);
        return this.queryById(moldMaintainItem.getMaintainItemId());
    }

    /**
     * 通过主键删除数据
     *
     * @param maintainItemId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long maintainItemId) {
        return this.moldMaintainItemMapper.deleteById(maintainItemId) > 0;
    }
}
