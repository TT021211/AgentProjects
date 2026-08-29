package com.cuixing.eq.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.eq.EqException;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdWorkbenchEnamelling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.eq.entity.EqRepairReport;
import com.cuixing.eq.mapper.EqRepairReportMapper;
import com.cuixing.eq.service.IEqRepairReportService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 设备报修单;(eq_repair_report)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-7-1
 */
@Service
public class EqRepairReportServiceImpl implements IEqRepairReportService{
    @Autowired
    private EqRepairReportMapper eqRepairReportMapper;

    @Autowired
    private RemoteMdService remoteMdService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EqRepairReport queryById(BigInteger id){
        return eqRepairReportMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param eqRepairReport 筛选条件
     * @return 查询结果
     */
    @Override
    public List<EqRepairReport> selectEqRepairReportList(EqRepairReport eqRepairReport){
       return eqRepairReportMapper.selectEqRepairReportList(eqRepairReport);
    }
    
    /** 
     * 新增数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(EqRepairReport eqRepairReport){
    	eqRepairReport.setCreateTime(DateUtils.getNowDate());
		eqRepairReport.setCreateBy(SecurityUtils.getUsername());
        eqRepairReport.setApplyBy(SecurityUtils.getUsername());
        eqRepairReport.setApplyTime(DateUtils.getNowDate());
        return eqRepairReportMapper.insert(eqRepairReport);
    }
    
    /** 
     * 更新数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    @Override
    public int update(EqRepairReport eqRepairReport){
        eqRepairReport.setUpdateTime(DateUtils.getNowDate());
		eqRepairReport.setUpdateBy(SecurityUtils.getUsername());
        if (eqRepairReport.getStatus().equals("1")){
            eqRepairReport.setRepairBy(SecurityUtils.getUsername());
            eqRepairReport.setRepairTime(DateUtils.getNowDate());
        }else if (eqRepairReport.getStatus().equals("2")){
            eqRepairReport.setApproveBy(SecurityUtils.getUsername());
            eqRepairReport.setApproveTime(DateUtils.getNowDate());
        }
        return eqRepairReportMapper.update(eqRepairReport);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return eqRepairReportMapper.deleteByIds(ids);
    }

     /**
      * * 写入设备知识库
      * @param id
      * @return
      */
     @Override
     public int addKnowledge(BigInteger id) {
         return eqRepairReportMapper.addKnowledge(id);
     }

     /**
      * 分页查询设备知识库
      */
     @Override
     public List<EqRepairReport> knowledgeList(EqRepairReport eqRepairReport) {
         return eqRepairReportMapper.knowledgeList(eqRepairReport);
     }
     /**
      * 删除设备知识库数据
      */
     @Override
     public int deleteKnowledgeByIds(BigInteger[] ids) {
         return eqRepairReportMapper.deleteKnowledgeByIds(ids);
     }

     @Override
     public List<EqRepairReport> getByWorkbenchEnamellingId(BigInteger workbenchEnamellingId) {
         //通过机台id查询设备id
         MdWorkbenchEnamelling data = remoteMdService.getWorkbenchEnamellingById(workbenchEnamellingId, SecurityConstants.INNER).getData();
         if (null == data){
             throw new EqException("根据漆包机台id获取设备失败");
         }
         BigInteger equipmentId = data.getEquipmentId();
         return eqRepairReportMapper.getByEquipmentId(equipmentId);
     }
 }