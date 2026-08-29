package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.mes.entity.MesWorkstage;
import com.cuixing.mes.mapper.MesWorkstageMapper;
import com.cuixing.mes.service.IMesWorkstageService;
import java.math.BigInteger;
import java.util.List;

/**
 * 工序表;(mes_workstage)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesWorkstageServiceImpl implements IMesWorkstageService{
    @Autowired
    private MesWorkstageMapper mesWorkstageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesWorkstage queryById(BigInteger id){
        return mesWorkstageMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesWorkstage 筛选条件
     * @return 查询结果
     */
    public List<MesWorkstage> selectMesWorkstageList(MesWorkstage mesWorkstage)
    {
        return mesWorkstageMapper.selectMesWorkstageList(mesWorkstage);
    }

    /**
     * 新增数据
     *
     * @param mesWorkstage 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    public BigInteger insert(MesWorkstage mesWorkstage){
        mesWorkstage.setCreateTime(DateUtils.getNowDate());
        int insertNum = mesWorkstageMapper.insert(mesWorkstage);
        if(insertNum > 0) {
            return mesWorkstage.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mesWorkstages 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MesWorkstage> mesWorkstages){
        int rs = 0;
        for (MesWorkstage mesWorkstage : mesWorkstages) {
            mesWorkstage.setCreateTime(DateUtils.getNowDate());
            rs = mesWorkstageMapper.insert(mesWorkstage) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mesWorkstage 实例对象
     * @return 影响行数
     */
    public int update(MesWorkstage mesWorkstage){
        BigInteger id = mesWorkstage.getId();
        MesWorkstage theUpdatemesWorkstage = mesWorkstageMapper.queryById(id);
        Integer version = theUpdatemesWorkstage.getVersion();
        if(version != null) {
            mesWorkstage.setVersion(version);
        }
        mesWorkstage.setUpdateTime(DateUtils.getNowDate());
        return mesWorkstageMapper.update(mesWorkstage);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mesWorkstageMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mesWorkstageMapper.deleteByIds(ids);
        return total > 0;
    }
}