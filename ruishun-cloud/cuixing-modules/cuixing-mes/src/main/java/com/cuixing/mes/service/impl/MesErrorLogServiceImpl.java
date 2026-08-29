package com.cuixing.mes.service.impl;

import com.cuixing.mes.entity.MesErrorLog;
import com.cuixing.mes.mapper.MesErrorLogMapper;
import com.cuixing.mes.service.MesErrorLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生产异常表(MesErrorLog)表服务实现类
 *
 * @author 阿江
 * @since 2025-07-14 23:27:49
 */
@Service("mesErrorLogService")
public class MesErrorLogServiceImpl implements MesErrorLogService {
    @Resource
    private MesErrorLogMapper mesErrorLogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesErrorLog queryById(Long id) {
        return this.mesErrorLogMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesErrorLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesErrorLog> selectMesErrorLogList(MesErrorLog mesErrorLog) {
        return this.mesErrorLogMapper.selectMesErrorLogList(mesErrorLog);
    }

    /**
     * 新增数据
     *
     * @param mesErrorLog 实例对象
     * @return 实例对象
     */
    @Override
    public MesErrorLog insert(MesErrorLog mesErrorLog) {
        this.mesErrorLogMapper.insert(mesErrorLog);
        return mesErrorLog;
    }

    /**
     * 修改数据
     *
     * @param mesErrorLog 实例对象
     * @return 实例对象
     */
    @Override
    public MesErrorLog update(MesErrorLog mesErrorLog) {
        this.mesErrorLogMapper.update(mesErrorLog);
        return this.queryById(mesErrorLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.mesErrorLogMapper.deleteById(id) > 0;
    }
}
