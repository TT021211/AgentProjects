package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.mes.entity.MesRoutingWorkstage;
import com.cuixing.mes.mapper.MesRoutingWorkstageMapper;
import com.cuixing.mes.service.IMesRoutingWorkstageService;
import java.math.BigInteger;
import java.util.List;

/**
 * 工艺路线与工序关联表;(mes_routing_workstage)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesRoutingWorkstageServiceImpl implements IMesRoutingWorkstageService{
    @Autowired
    private MesRoutingWorkstageMapper mesRoutingWorkstageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesRoutingWorkstage queryById(BigInteger id){
        return mesRoutingWorkstageMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesRoutingWorkstage 筛选条件
     * @return 查询结果
     */
    public List<MesRoutingWorkstage> selectMesRoutingWorkstageList(MesRoutingWorkstage mesRoutingWorkstage)
    {
        return mesRoutingWorkstageMapper.selectMesRoutingWorkstageList(mesRoutingWorkstage);
    }

    @Cacheable(cacheNames="RoutingWorkstageCache",key="#routingId")
    public List<MesRoutingWorkstage> selectMesRoutingWorkstageListByRoutingId(BigInteger routingId)
    {
        MesRoutingWorkstage mesRoutingWorkstage = new MesRoutingWorkstage();
        mesRoutingWorkstage.setRoutingId(routingId);
        return mesRoutingWorkstageMapper.selectMesRoutingWorkstageList(mesRoutingWorkstage);
    }

    /**
     * 新增数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    @CacheEvict(value = "RoutingWorkstageCache",allEntries = true)
    public BigInteger insert(MesRoutingWorkstage mesRoutingWorkstage){
        mesRoutingWorkstage.setCreateTime(DateUtils.getNowDate());
        int insertNum = mesRoutingWorkstageMapper.insert(mesRoutingWorkstage);
        if(insertNum > 0) {
            return mesRoutingWorkstage.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mesRoutingWorkstages 实例对象数组
     * @return 影响行数
     */
    @CacheEvict(value = "RoutingWorkstageCache",allEntries = true)
    public int insertBatch(List<MesRoutingWorkstage> mesRoutingWorkstages){
        int rs = 0;
        for (MesRoutingWorkstage mesRoutingWorkstage : mesRoutingWorkstages) {
            mesRoutingWorkstage.setCreateTime(DateUtils.getNowDate());
            rs = mesRoutingWorkstageMapper.insert(mesRoutingWorkstage) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mesRoutingWorkstage 实例对象
     * @return 影响行数
     */
    @CacheEvict(value = "RoutingWorkstageCache",allEntries = true)
    public int update(MesRoutingWorkstage mesRoutingWorkstage){
        BigInteger id = mesRoutingWorkstage.getId();
        MesRoutingWorkstage theUpdatemesRoutingWorkstage = mesRoutingWorkstageMapper.queryById(id);
        Integer version = theUpdatemesRoutingWorkstage.getVersion();
        if(version != null) {
            mesRoutingWorkstage.setVersion(version);
        }
        mesRoutingWorkstage.setUpdateTime(DateUtils.getNowDate());
        return mesRoutingWorkstageMapper.update(mesRoutingWorkstage);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @CacheEvict(value = "RoutingWorkstageCache",allEntries = true)
    public boolean deleteById(BigInteger id){
        int total = mesRoutingWorkstageMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    @CacheEvict(value = "RoutingWorkstageCache",allEntries = true)
    public boolean deleteByIds(BigInteger[] ids){
        int total = mesRoutingWorkstageMapper.deleteByIds(ids);
        return total > 0;
    }
}