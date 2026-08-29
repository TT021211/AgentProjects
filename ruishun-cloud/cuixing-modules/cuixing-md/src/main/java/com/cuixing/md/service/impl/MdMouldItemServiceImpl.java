package com.cuixing.md.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.system.api.domain.md.MdMouldItem;
import com.cuixing.md.mapper.MdMouldItemMapper;
import com.cuixing.md.service.IMdMouldItemService;
import java.math.BigInteger;
import java.util.List;

/**
 * 模具产出物料关联表;(md_mould_item)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-5-21
 */
@Service
public class MdMouldItemServiceImpl implements IMdMouldItemService{
    @Autowired
    private MdMouldItemMapper mdMouldItemMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdMouldItem queryById(BigInteger id){
        return mdMouldItemMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdMouldItem 筛选条件
     * @return 查询结果
     */
    public List<MdMouldItem> selectMdMouldItemList(MdMouldItem mdMouldItem)
    {
        return mdMouldItemMapper.selectMdMouldItemList(mdMouldItem);
    }

    /**
     * 新增数据
     *
     * @param mdMouldItem 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    public BigInteger insert(MdMouldItem mdMouldItem){
        mdMouldItem.setCreateTime(DateUtils.getNowDate());
        int insertNum = mdMouldItemMapper.insert(mdMouldItem);
        if(insertNum > 0) {
            return mdMouldItem.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mdMouldItems 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdMouldItem> mdMouldItems){
        int rs = 0;
        for (MdMouldItem mdMouldItem : mdMouldItems) {
            mdMouldItem.setCreateTime(DateUtils.getNowDate());
            rs = mdMouldItemMapper.insert(mdMouldItem) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdMouldItem 实例对象
     * @return 影响行数
     */
    public int update(MdMouldItem mdMouldItem){
        BigInteger id = mdMouldItem.getId();
        MdMouldItem theUpdatemdMouldItem = mdMouldItemMapper.queryById(id);
        Integer version = theUpdatemdMouldItem.getVersion();
        if(version != null) {
            mdMouldItem.setVersion(version);
        }
        mdMouldItem.setUpdateTime(DateUtils.getNowDate());
        return mdMouldItemMapper.update(mdMouldItem);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdMouldItemMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdMouldItemMapper.deleteByIds(ids);
        return total > 0;
    }
}