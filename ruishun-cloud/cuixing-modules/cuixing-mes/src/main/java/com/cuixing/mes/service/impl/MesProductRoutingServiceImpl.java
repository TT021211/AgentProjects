package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.system.api.domain.mes.MesProductRouting;
import com.cuixing.mes.mapper.MesProductRoutingMapper;
import com.cuixing.mes.service.IMesProductRoutingService;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品工艺路线关联表;(mes_product_routing)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesProductRoutingServiceImpl implements IMesProductRoutingService{
    @Autowired
    private MesProductRoutingMapper mesProductRoutingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesProductRouting queryById(BigInteger id){
        return mesProductRoutingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesProductRouting 筛选条件
     * @return 查询结果
     */
    public List<MesProductRouting> selectMesProductRoutingList(MesProductRouting mesProductRouting)
    {
        return mesProductRoutingMapper.selectMesProductRoutingList(mesProductRouting);
    }

    /**
     * 新增数据
     *
     * @param mesProductRouting 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    public BigInteger insert(MesProductRouting mesProductRouting){
        mesProductRouting.setCreateTime(DateUtils.getNowDate());
        int insertNum = mesProductRoutingMapper.insert(mesProductRouting);
        if(insertNum > 0) {
            return mesProductRouting.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mesProductRoutings 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MesProductRouting> mesProductRoutings){
        int rs = 0;
        for (MesProductRouting mesProductRouting : mesProductRoutings) {
            mesProductRouting.setCreateTime(DateUtils.getNowDate());
            rs = mesProductRoutingMapper.insert(mesProductRouting) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mesProductRouting 实例对象
     * @return 影响行数
     */
    public int update(MesProductRouting mesProductRouting){
        BigInteger id = mesProductRouting.getId();
        MesProductRouting theUpdatemesProductRouting = mesProductRoutingMapper.queryById(id);
        Integer version = theUpdatemesProductRouting.getVersion();
        if(version != null) {
            mesProductRouting.setVersion(version);
        }

        mesProductRouting.setUpdateTime(DateUtils.getNowDate());
        return mesProductRoutingMapper.update(mesProductRouting);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mesProductRoutingMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mesProductRoutingMapper.deleteByIds(ids);
        return total > 0;
    }
}