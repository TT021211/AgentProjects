package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.system.api.domain.mes.MesProductBom;
import com.cuixing.mes.mapper.MesProductBomMapper;
import com.cuixing.mes.service.IMesProductBomService;
import java.math.BigInteger;
import java.util.List;

/**
 * 产品BOM表;(mes_product_bom)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-4-12
 */
@Service
public class MesProductBomServiceImpl implements IMesProductBomService{
    @Autowired
    private MesProductBomMapper mesProductBomMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MesProductBom queryById(BigInteger id){
        return mesProductBomMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesProductBom 筛选条件
     * @return 查询结果
     */
    public List<MesProductBom> selectMesProductBomList(MesProductBom mesProductBom)
    {
        return mesProductBomMapper.selectMesProductBomList(mesProductBom);
    }

    /**
     * 新增数据
     *
     * @param mesProductBom 实例对象
     * @return 最后插入的数据的自增主键ID
     */
    public BigInteger insert(MesProductBom mesProductBom){
        mesProductBom.setCreateTime(DateUtils.getNowDate());
        int insertNum = mesProductBomMapper.insert(mesProductBom);
        if(insertNum > 0) {
            return mesProductBom.getId();
        } else {
            return BigInteger.valueOf(0);
        }
    }

    /**
     * 批量新增数据
     *
     * @param mesProductBoms 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MesProductBom> mesProductBoms){
        int rs = 0;
        for (MesProductBom mesProductBom : mesProductBoms) {
            mesProductBom.setCreateTime(DateUtils.getNowDate());
            rs = mesProductBomMapper.insert(mesProductBom) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mesProductBom 实例对象
     * @return 影响行数
     */
    public int update(MesProductBom mesProductBom){
        BigInteger id = mesProductBom.getId();
        MesProductBom theUpdatemesProductBom = mesProductBomMapper.queryById(id);
        Integer version = theUpdatemesProductBom.getVersion();
        if(version != null) {
            mesProductBom.setVersion(version);
        }
        mesProductBom.setUpdateTime(DateUtils.getNowDate());
        return mesProductBomMapper.update(mesProductBom);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mesProductBomMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mesProductBomMapper.deleteByIds(ids);
        return total > 0;
    }
}