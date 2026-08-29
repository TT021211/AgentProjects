package com.cuixing.md.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.system.api.domain.md.MdClient;
import com.cuixing.md.mapper.MdClientMapper;
import com.cuixing.md.service.IMdClientService;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * 客户表;(md_client)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdClientServiceImpl implements IMdClientService{
    @Autowired
    private MdClientMapper mdClientMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdClient queryById(BigInteger id){
        return mdClientMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mdClient 筛选条件
     * @return 查询结果
     */
    public List<MdClient> selectMdClientList(MdClient mdClient)
    {
        return mdClientMapper.selectMdClientList(mdClient);
    }

    /**
     * 新增数据
     *
     * @param mdClient 实例对象
     * @return 影响行数
     */
    public int insert(MdClient mdClient){
        mdClient.setCreateTime(DateUtils.getNowDate());
        mdClient.setCreateBy(SecurityUtils.getUsername());
        return mdClientMapper.insert(mdClient);
    }

    /**
     * 批量新增数据
     *
     * @param mdClients 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdClient> mdClients){
        int rs = 0;
        for (MdClient mdClient : mdClients) {
            mdClient.setCreateTime(DateUtils.getNowDate());
            mdClient.setCreateBy(SecurityUtils.getUsername());
            rs = mdClientMapper.insert(mdClient) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdClient 实例对象
     * @return 影响行数
     */
    public int update(MdClient mdClient){
        BigInteger id = mdClient.getId();
        MdClient theUpdatemdClient = mdClientMapper.queryById(id);
        Integer version = theUpdatemdClient.getVersion();
        if(version != null) {
            mdClient.setVersion(version);
        }
        mdClient.setUpdateTime(DateUtils.getNowDate());
        mdClient.setUpdateBy(SecurityUtils.getUsername());
        return mdClientMapper.update(mdClient);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdClientMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdClientMapper.deleteByIds(ids);
        return total > 0;
    }

    @Override
    public List<MdClient> getInfoByClientNick(Set<String> clientNick) {
        return this.mdClientMapper.getInfoByClientNick(clientNick);
    }
}