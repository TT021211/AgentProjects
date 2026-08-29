package com.cuixing.qc.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.entity.QcInspectionItems;
import com.cuixing.qc.entity.QcInspectionSummart;
import com.cuixing.qc.entity.vo.QcCountData;
import com.cuixing.qc.mapper.QcDefectMapper;
import com.cuixing.qc.mapper.QcInspectionItemsMapper;
import com.cuixing.qc.mapper.QcInspectionSummartMapper;
import com.cuixing.qc.service.QcDefectService;
import com.cuixing.qc.service.QcInspectionSummartService;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.mes.MesInputLog;
import com.cuixing.system.api.domain.mes.MesOuputLog;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 检验汇总表(QcInspectionSummart)表服务实现类
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
@Service("qcInspectionSummartService")
public class QcInspectionSummartServiceImpl implements QcInspectionSummartService {
    @Resource
    private QcInspectionSummartMapper qcInspectionSummartMapper;

    @Autowired
    private RemoteMesService remoteMesService;
    @Autowired
    private RemoteWmsService remoteWmsService;
    @Autowired
    private QcInspectionItemsMapper qcInspectionItemsMapper;
    @Autowired
    private QcDefectMapper qcDefectMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcInspectionSummart queryById(BigInteger id) {
        return this.qcInspectionSummartMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcInspectionSummart 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcInspectionSummart> selectQcInspectionSummartList(QcInspectionSummart qcInspectionSummart) {
        return this.qcInspectionSummartMapper.selectQcInspectionSummartList(qcInspectionSummart);
    }

    /**
     * 新增数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public QcInspectionSummart insert(QcInspectionSummart qcInspectionSummart) {
        if (qcInspectionSummart.getItemIdentifier() != null) {
            QcInspectionSummart qcInspectionSummart1 = qcInspectionSummartMapper.queryByItemIdentifier(qcInspectionSummart.getItemIdentifier());
            if (qcInspectionSummart1 != null) {
                throw new RuntimeException("已存在该条记录，请勿重复扫码！");
            }
        }
        qcInspectionSummart.setCreateTime(DateUtils.getNowDate());
        qcInspectionSummart.setCreateBy(SecurityUtils.getUsername());
        //首检
        if (Objects.equals(qcInspectionSummart.getInspectionType(), "0")) {
            //查询投料记录
            MesInputLog data = remoteMesService.getInfoByInputSn(qcInspectionSummart.getItemIdentifier(), SecurityConstants.INNER).getData();
            if (data == null) {
                throw new RuntimeException("查询不到投料记录，请检查后再扫码！");
            }
            qcInspectionSummart.setProductId(data.getInputId());
            qcInspectionSummart.setProductCode(data.getInputCode());
            qcInspectionSummart.setProductName(data.getInputName());
            setData(qcInspectionSummart, data.getItemSpecification(), data.getWorkstationCode(), data.getWorkorderId(), data.getWorkorderSn(), data.getBatchCode(), data.getPartNumber(), data.getOrderId(), data.getOrderSn());
            qcInspectionSummart.setWeight(data.getInputWeight());
            qcInspectionSummart.setMeasureName(data.getMeasureName());
            if (data.getAxleNumber() != null) {
                qcInspectionSummart.setAxisNumber(String.valueOf(data.getAxleNumber()));
            }
            if (data.getAlexPositionNumber() != null) {
                qcInspectionSummart.setMachinePosition(String.valueOf(data.getAlexPositionNumber()));
            }
        }
        //下线检
        if (Objects.equals(qcInspectionSummart.getInspectionType(), "1")) {
            //查询报工记录
            MesOuputLog data = remoteMesService.queryByOutputSn(qcInspectionSummart.getItemIdentifier(), SecurityConstants.INNER).getData();
            if (data == null) {
                throw new RuntimeException("查询不到报工记录，请检查后再扫码！");
            }
            if (Objects.equals(data.getOutputType(), "物料")) {
                qcInspectionSummart.setProductId(data.getOutputId());
                qcInspectionSummart.setProductCode(data.getOutputCode());
                qcInspectionSummart.setProductName(data.getOutputName());
            } else {
                qcInspectionSummart.setProductId(data.getProductId());
                qcInspectionSummart.setProductCode(data.getProductCode());
                qcInspectionSummart.setProductName(data.getProductName());
            }
            setData(qcInspectionSummart, data.getItemSpecification(), data.getWorkstationCode(), data.getWorkorderId(), data.getWorkorderSn(), data.getBatchCode(), data.getPartNumber(), data.getOrderId(), data.getOrderSn());
            qcInspectionSummart.setWeight(data.getOutputWeight());
            qcInspectionSummart.setMeasureName(data.getMeasureName());
            if (data.getAlexNumber() != null) {
                qcInspectionSummart.setAxisNumber(data.getAlexNumber());
            }
            if (data.getAlexPositionNumber() != null) {
                qcInspectionSummart.setMachinePosition(String.valueOf(data.getAlexPositionNumber()));
            }
        }
        this.qcInspectionSummartMapper.insert(qcInspectionSummart);
        return qcInspectionSummart;
    }

    private void setData(QcInspectionSummart qcInspectionSummart, String itemSpecification, String workstationCode, BigInteger workorderId, String workorderSn, String batchCode, String partNumber, BigInteger orderId, String orderSn) {
        qcInspectionSummart.setProductSpecification(itemSpecification);
        qcInspectionSummart.setMachineTable(workstationCode);
        qcInspectionSummart.setWorkorderId(workorderId);
        qcInspectionSummart.setWorkorderCode(workorderSn);
        qcInspectionSummart.setBatchNumber(batchCode);
        qcInspectionSummart.setItemNumber(partNumber);
        if (orderId != null) {
            qcInspectionSummart.setSaleOrderId(orderId);
        }
        if (orderSn != null) {
            qcInspectionSummart.setSaleOrderCode(orderSn);
        }
        /*if (qcInspectionSummart.getInspectionItemId() != null) {
            String inspectionItemId = qcInspectionSummart.getInspectionItemId();
            String[] split = inspectionItemId.split(",");
            StringBuilder inspectionItemName = new StringBuilder(); // 使用 StringBuilder

            // 遍历分割后的数组
            for (String number : split) {
                QcInspectionItems qcInspectionItems = qcInspectionItemsMapper.queryById(Long.valueOf(number));
                if (qcInspectionItems != null) { // 确保查询结果不为空
                    inspectionItemName.append(qcInspectionItems.getDetectionName()).append(",");
                }
            }

            // 去除最后一个多余的逗号
            if (inspectionItemName.length() > 0) {
                inspectionItemName.deleteCharAt(inspectionItemName.length() - 1);
            }

            // 获取最终结果
            qcInspectionSummart.setInspectionItemName(inspectionItemName.toString());
        }*/
        //缺陷项
        if (qcInspectionSummart.getDefectClassificationId() != null) {
            String defectClassificationId = qcInspectionSummart.getDefectClassificationId();
            String[] split = defectClassificationId.split(",");
            StringBuilder defectName = new StringBuilder();
            for (String number : split) {
                QcDefect qcDefect = qcDefectMapper.queryById(Long.valueOf(number));
                if (qcDefect != null) { // 确保查询结果不为空
                    defectName.append(qcDefect.getDefectName()).append(",");
                }
            }
            if (defectName.length() > 0) {
                defectName.deleteCharAt(defectName.length() - 1);
            }
            qcInspectionSummart.setDefectName(defectName.toString());
        }
        WmsRealtimeStock data = remoteWmsService.getInfoByItemIdentifier(qcInspectionSummart.getItemIdentifier(), SecurityConstants.INNER).getData();
        if (data == null) {
            throw new RuntimeException("查询不到库存信息，请检查后再扫码！");
        }
        qcInspectionSummart.setWarehouseId(BigInteger.valueOf(data.getWarehouseId()));
        qcInspectionSummart.setWarehouseCode(data.getWarehouseCode());
        qcInspectionSummart.setWarehouseName(data.getWarehouseName());
    }

    /**
     * 修改数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 实例对象
     */
    @Override
    public QcInspectionSummart update(QcInspectionSummart qcInspectionSummart) {
        this.qcInspectionSummartMapper.update(qcInspectionSummart);
        return this.queryById(qcInspectionSummart.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BigInteger id) {
        return this.qcInspectionSummartMapper.deleteById(id) > 0;
    }

    @Override
    public List<QcCountData> getQcCountData() {
        return this.qcInspectionSummartMapper.getQcCountData();
    }
}
