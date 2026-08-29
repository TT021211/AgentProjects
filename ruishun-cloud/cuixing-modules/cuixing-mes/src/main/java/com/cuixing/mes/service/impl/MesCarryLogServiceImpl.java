package com.cuixing.mes.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.entity.MesOuputLog;
import com.cuixing.mes.entity.vo.MesCarryLogVo;
import com.cuixing.mes.service.IMesOuputLogService;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesCarryLog;
import com.cuixing.mes.mapper.MesCarryLogMapper;
import com.cuixing.mes.service.IMesCarryLogService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * 搬运日志;(mes_carry_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-10-10
 */
@Service
public class MesCarryLogServiceImpl implements IMesCarryLogService{


    @Autowired
    private MesCarryLogMapper mesCarryLogMapper;

    @Autowired
    private RemoteWmsService remoteWmsService;

    @Autowired
    private IMesOuputLogService iMesOuputLogService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesCarryLog queryById(BigInteger id){
        return mesCarryLogMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param mesCarryLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesCarryLog> selectMesCarryLogList(MesCarryLog mesCarryLog){
       return mesCarryLogMapper.selectMesCarryLogList(mesCarryLog);
    }

    /**
     * 新增数据
     *
     * @param mesCarryLogVo 实例对象
     * @return 实例对象
     */
    @Override
    @GlobalTransactional
    public int insert(MesCarryLogVo mesCarryLogVo){
        List<String> itemIdentifiers = mesCarryLogVo.getItemIdentifiers();
        int result = 0;
        for (String itemIdentifier : itemIdentifiers) {
            //通过二维码查询报工记录
            MesOuputLog mesOuputLog = iMesOuputLogService.getInfoByOutputSn(itemIdentifier);
            if (null == mesOuputLog){
                throw new RuntimeException("二维码:"+ itemIdentifier + "对应报工记录不存在!!!");
            }
            MesCarryLog mesCarryLog = new MesCarryLog();
            mesCarryLog.setSpecification(mesOuputLog.getItemSpecification());
            mesCarryLog.setEquipmentCode(mesOuputLog.getWorkstationCode());
            if (null != mesOuputLog.getWorkstationCode()){
                mesCarryLog.setEnamellingEquipmentCode(mesCarryLogVo.getDeviceCode());
            }
            mesCarryLog.setItemIdentifier(itemIdentifier);
            mesCarryLog.setJobNumber(mesCarryLogVo.getJobNumber());
            mesCarryLog.setCreateTime(DateUtils.getNowDate());
		    mesCarryLog.setCreateBy(SecurityUtils.getUsername());
            result += mesCarryLogMapper.insert(mesCarryLog);

            R<AjaxResult> ajaxResultR = remoteWmsService.updateDetectionStatus(itemIdentifier, SecurityConstants.INNER);
            if (ajaxResultR.getCode() != 200){
                throw new RuntimeException("修改在制品状态失败，请查看系统二维码:" + itemIdentifier +"是否存在");
            }
        }
        return result;
    }
    private static String getString(MesCarryLogVo mesCarryLogVo) {
        String taskType="";
        //拉丝
        if (Objects.equals(mesCarryLogVo.getAttr1(), "0")){
            //添加退火在制品
            taskType="0";
        }
        //退火
        if (Objects.equals(mesCarryLogVo.getAttr1(), "1")){
            //添加退火在制品
            taskType="4";
        }
        //镀锡
        if (Objects.equals(mesCarryLogVo.getAttr1(), "2")){
            //添加镀锡在制品
            taskType="5";
        }
        //并丝
        if (Objects.equals(mesCarryLogVo.getAttr1(), "3")){
            //添加并丝在制品
            taskType="6";
        }
        //绞线
        if (Objects.equals(mesCarryLogVo.getAttr1(), "4")){
            //添加绞线在制品
            taskType="7";
        }
        return taskType;
    }
    /**
     * 更新数据
     *
     * @param mesCarryLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesCarryLog mesCarryLog){
        mesCarryLog.setUpdateTime(DateUtils.getNowDate());
		mesCarryLog.setUpdateBy(SecurityUtils.getUsername());
        return mesCarryLogMapper.update(mesCarryLog);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesCarryLogMapper.deleteByIds(ids);
    }
}
