package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.mes.entity.MesProcessRouting;
import com.cuixing.mes.mapper.MesProcessRoutingMapper;
import com.cuixing.mes.service.IMesProcessRoutingService;
import java.math.BigInteger;
import java.util.List;

/**
 * 工艺路线表;(mes_process_routing)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesProcessRoutingServiceImpl implements IMesProcessRoutingService{
    @Autowired
    private MesProcessRoutingMapper mesProcessRoutingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesProcessRouting queryById(BigInteger id){
        return mesProcessRoutingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesProcessRouting 筛选条件
     * @return 查询结果
     */
    public List<MesProcessRouting> selectMesProcessRoutingList(MesProcessRouting mesProcessRouting)
    {
        return mesProcessRoutingMapper.selectMesProcessRoutingList(mesProcessRouting);
    }

    /**
     * 新增数据
     *
     * @param mesProcessRouting 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    public BigInteger insert(MesProcessRouting mesProcessRouting){
        mesProcessRouting.setCreateTime(DateUtils.getNowDate());
        int insertNum = mesProcessRoutingMapper.insert(mesProcessRouting);
        if(insertNum > 0) {
            return mesProcessRouting.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mesProcessRoutings 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MesProcessRouting> mesProcessRoutings){
        int rs = 0;
        for (MesProcessRouting mesProcessRouting : mesProcessRoutings) {
            mesProcessRouting.setCreateTime(DateUtils.getNowDate());
            rs = mesProcessRoutingMapper.insert(mesProcessRouting) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mesProcessRouting 实例对象
     * @return 影响行数
     */
    public int update(MesProcessRouting mesProcessRouting){
        BigInteger id = mesProcessRouting.getId();
        MesProcessRouting theUpdatemesProcessRouting = mesProcessRoutingMapper.queryById(id);
        Integer version = theUpdatemesProcessRouting.getVersion();
        if(version != null) {
            mesProcessRouting.setVersion(version);
        }
        mesProcessRouting.setUpdateTime(DateUtils.getNowDate());
        return mesProcessRoutingMapper.update(mesProcessRouting);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mesProcessRoutingMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mesProcessRoutingMapper.deleteByIds(ids);
        return total > 0;
    }
}
