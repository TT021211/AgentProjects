package com.cuixing.ecm.service.impl;

import java.util.List;
import com.cuixing.common.core.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.ecm.mapper.EcmElectricDeviceMapper;
import com.cuixing.ecm.domain.EcmElectricDevice;
import com.cuixing.ecm.service.IEcmElectricDeviceService;

/**
 * 能耗设备Service业务层处理
 * 
 * @author zhan
 * @date 2024-07-24
 */
@Service
public class EcmElectricDeviceServiceImpl implements IEcmElectricDeviceService 
{
    @Autowired
    private EcmElectricDeviceMapper ecmElectricDeviceMapper;

    /**
     * 查询能耗设备
     * 
     * @param electricDeviceId 能耗设备主键
     * @return 能耗设备
     */
    @Override
    public EcmElectricDevice selectEcmElectricDeviceByElectricDeviceId(Long electricDeviceId)
    {
        return ecmElectricDeviceMapper.selectEcmElectricDeviceByElectricDeviceId(electricDeviceId);
    }



    /**
     * 查询能耗设备列表
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 能耗设备
     */
    @Override
    public List<EcmElectricDevice> selectEcmElectricDeviceList(EcmElectricDevice ecmElectricDevice)
    {
        return ecmElectricDeviceMapper.selectEcmElectricDeviceList(ecmElectricDevice);
    }

    /**
     * 新增能耗设备
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 结果
     */
    @Override
    public int insertEcmElectricDevice(EcmElectricDevice ecmElectricDevice)
    {
        ecmElectricDevice.setCreateTime(DateUtils.getNowDate());
        return ecmElectricDeviceMapper.insertEcmElectricDevice(ecmElectricDevice);
    }

    /**
     * 修改能耗设备
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 结果
     */
    @Override
    public int updateEcmElectricDevice(EcmElectricDevice ecmElectricDevice)
    {
        ecmElectricDevice.setUpdateTime(DateUtils.getNowDate());
        return ecmElectricDeviceMapper.updateEcmElectricDevice(ecmElectricDevice);
    }

    /**
     * 批量删除能耗设备
     * 
     * @param electricDeviceIds 需要删除的能耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEcmElectricDeviceByElectricDeviceIds(Long[] electricDeviceIds)
    {
        return ecmElectricDeviceMapper.deleteEcmElectricDeviceByElectricDeviceIds(electricDeviceIds);
    }

    /**
     * 删除能耗设备信息
     * 
     * @param electricDeviceId 能耗设备主键
     * @return 结果
     */
    @Override
    public int deleteEcmElectricDeviceByElectricDeviceId(Long electricDeviceId)
    {
        return ecmElectricDeviceMapper.deleteEcmElectricDeviceByElectricDeviceId(electricDeviceId);
    }

    @Override
    public List<EcmElectricDevice> getDeviceListByLocation(String location) {
        return ecmElectricDeviceMapper.getDeviceListByLocation(location);
    }

    @Override
    public List<EcmElectricDevice> getDeviceListByParentId(Integer parentId) {
        return ecmElectricDeviceMapper.getDeviceListByParentId(parentId);
    }

    @Override
    public EcmElectricDevice getDeviceListByElectricDeviceCode(String electricDeviceCode) {
        return ecmElectricDeviceMapper.getDeviceListByElectricDeviceCode(electricDeviceCode);
    }
}
