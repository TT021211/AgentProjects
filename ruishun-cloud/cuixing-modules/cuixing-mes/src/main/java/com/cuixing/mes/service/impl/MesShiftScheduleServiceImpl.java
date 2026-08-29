package com.cuixing.mes.service.impl;

import com.cuixing.common.core.exception.mes.ProductException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mes.entity.MesShiftSchedule;
import com.cuixing.mes.mapper.MesShiftScheduleMapper;
import com.cuixing.mes.service.MesShiftScheduleService;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.annotation.Resource;

/**
 * 生产交班表(MesShiftSchedule)表服务实现类
 *
 * @author 阿江
 * @since 2025-03-24 11:18:54
 */
@Service("mesShiftScheduleService")
public class MesShiftScheduleServiceImpl implements MesShiftScheduleService {
    @Resource
    private MesShiftScheduleMapper mesShiftScheduleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesShiftSchedule queryById(Long id) {
        return this.mesShiftScheduleMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesShiftSchedule 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesShiftSchedule> selectMesShiftScheduleList(MesShiftSchedule mesShiftSchedule) {
        return this.mesShiftScheduleMapper.selectMesShiftScheduleList(mesShiftSchedule);
    }

    /**
     * 新增数据
     *
     * @param mesShiftScheduleList 实例对象
     * @return 实例对象
     */
    @Override
    public List<MesShiftSchedule> insert(List<MesShiftSchedule> mesShiftScheduleList) {
        // 首先检查上一次插入的时间
        if (!mesShiftScheduleList.isEmpty()) {
            for (MesShiftSchedule mesShiftSchedule : mesShiftScheduleList) {
                //拉丝
                if (mesShiftSchedule.getAttr2() != null && mesShiftSchedule.getAttr2() == 0) {
                    if (mesShiftSchedule.getWorkstationCode() != null) {
                        MesShiftSchedule lastSchedule = this.mesShiftScheduleMapper.selectLastInsert(mesShiftSchedule.getWorkstationCode()); // 假设此方法已存在
                        checkTime(lastSchedule);
                    }
                    //工艺
                    if (mesShiftSchedule.getAttr2() != null && mesShiftSchedule.getAttr2() == 1) {
                        if (mesShiftSchedule.getWorkstationCode() != null) {
                            MesShiftSchedule lastSchedule = this.mesShiftScheduleMapper.selectLastInsertToGy(mesShiftSchedule.getWorkstationCode(),mesShiftSchedule.getAlexPositionNumber(),mesShiftSchedule.getAlexNumber()); // 假设此方法已存在
                            checkTime(lastSchedule);
                        }
                    }
                }
                // 如果未提供创建时间和创建者，则设置默认值
                if (null == mesShiftSchedule.getCreateTime() && null == mesShiftSchedule.getCreateBy()) {
                    mesShiftSchedule.setCreateTime(DateUtils.getNowDate());
                    mesShiftSchedule.setCreateBy(SecurityUtils.getUsername());
                }
                this.mesShiftScheduleMapper.insert(mesShiftSchedule);
            }
        }
        return mesShiftScheduleList;
    }

    private void checkTime(MesShiftSchedule lastSchedule) {
        if (lastSchedule != null && lastSchedule.getCreateTime() != null) {
            Date lastInsertTime = lastSchedule.getCreateTime();
            Date now = DateUtils.getNowDate();

            // 计算时间差（毫秒）
            long timeDiff = now.getTime() - lastInsertTime.getTime();
            // 将1小时转换为毫秒 (1小时 = 3600000毫秒)
            long oneHourInMillis = 60 * 60 * 1000;

            if (timeDiff < oneHourInMillis) {
                // 计算剩余时间（分钟）
                long remainingMillis = oneHourInMillis - timeDiff;
                long remainingMinutes = (remainingMillis / 60000); // 转换为分钟
                throw new RuntimeException("操作过于频繁，请在 " + remainingMinutes + " 分钟后再进行新增。");
            }
        }
    }

    /**
     * 修改数据
     *
     * @param mesShiftSchedule 实例对象
     * @return 实例对象
     */
    @Override
    public MesShiftSchedule update(MesShiftSchedule mesShiftSchedule) {
        this.mesShiftScheduleMapper.update(mesShiftSchedule);
        return this.queryById(mesShiftSchedule.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.mesShiftScheduleMapper.deleteById(id) > 0;
    }

    @Override
    public MesShiftSchedule getInfoByWorkstationCode(String workstationCode, String workorderSn) {
        return this.mesShiftScheduleMapper.getInfoByWorkstationCode(workstationCode, workorderSn);
    }

    @Override
    public List<MesShiftSchedule> getInfoByWorkstationCodeToGy(String workstationCode, String workorderSn, List<String> alexPositionNumber, String alexNumber) {
        List<MesShiftSchedule> mesShiftScheduleList = new ArrayList<>();
        if (!alexPositionNumber.isEmpty()) {
            for (String alexPosition : alexPositionNumber) {
                if (Objects.equals(alexPosition, "A")){
                    alexPosition = null;
                }
                MesShiftSchedule infoByWorkstationCodeToGy = this.mesShiftScheduleMapper.getInfoByWorkstationCodeToGy(workstationCode, workorderSn, alexPosition, alexNumber);
                if (infoByWorkstationCodeToGy != null) {
                    mesShiftScheduleList.add(infoByWorkstationCodeToGy);
                }
            }
        }
        return mesShiftScheduleList;
    }
}
