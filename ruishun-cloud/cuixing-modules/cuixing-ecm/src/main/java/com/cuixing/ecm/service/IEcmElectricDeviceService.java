package com.cuixing.ecm.service;

import java.util.List;
import com.cuixing.ecm.domain.EcmElectricDevice;
import org.apache.ibatis.annotations.Param;

/**
 * 能耗设备Service接口
 * 
 * @author zhan
 * @date 2024-07-24
 */
public interface IEcmElectricDeviceService 
{
    /**
     * 查询能耗设备
     * 
     * @param electricDeviceId 能耗设备主键
     * @return 能耗设备
     */
    public EcmElectricDevice selectEcmElectricDeviceByElectricDeviceId(Long electricDeviceId);

    /**
     * 查询能耗设备列表
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 能耗设备集合
     */
    public List<EcmElectricDevice> selectEcmElectricDeviceList(EcmElectricDevice ecmElectricDevice);

    /**
     * 新增能耗设备
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 结果
     */
    public int insertEcmElectricDevice(EcmElectricDevice ecmElectricDevice);

    /**
     * 修改能耗设备
     * 
     * @param ecmElectricDevice 能耗设备
     * @return 结果
     */
    public int updateEcmElectricDevice(EcmElectricDevice ecmElectricDevice);

    /**
     * 批量删除能耗设备
     * 
     * @param electricDeviceIds 需要删除的能耗设备主键集合
     * @return 结果
     */
    public int deleteEcmElectricDeviceByElectricDeviceIds(Long[] electricDeviceIds);

    /**
     * 删除能耗设备信息
     * 
     * @param electricDeviceId 能耗设备主键
     * @return 结果
     */
    public int deleteEcmElectricDeviceByElectricDeviceId(Long electricDeviceId);

    List<EcmElectricDevice> getDeviceListByLocation(String location);

    List<EcmElectricDevice> getDeviceListByParentId(Integer parentId);

    EcmElectricDevice getDeviceListByElectricDeviceCode(String electricDeviceCode);
}
