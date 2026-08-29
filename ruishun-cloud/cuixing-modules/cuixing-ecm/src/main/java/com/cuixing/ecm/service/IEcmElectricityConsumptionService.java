package com.cuixing.ecm.service;

import java.time.LocalDate;
import java.util.List;
import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.domain.VO.ConsumptionValueAndCharge;
import com.cuixing.ecm.domain.VO.ConsumptionValueAndCharge24;
import com.cuixing.ecm.domain.VO.ElectricityConsumptionDate;
import com.cuixing.ecm.domain.VO.KanbanPage;

/**
 * 电能耗数据Service接口
 * 
 * @author zhan
 * @date 2024-07-24
 */
public interface IEcmElectricityConsumptionService 
{
    /**
     * 查询电能耗数据
     * 
     * @param consumptionId 电能耗数据主键
     * @return 电能耗数据
     */
    public EcmElectricityConsumption selectEcmElectricityConsumptionByConsumptionId(Long consumptionId);

    /**
     * 查询电能耗数据列表
     * 
     * @param ecmElectricityConsumption 电能耗数据
     * @return 电能耗数据集合
     */
    public List<EcmElectricityConsumption> selectEcmElectricityConsumptionList(EcmElectricityConsumption ecmElectricityConsumption);

    /**
     * 新增电能耗数据
     * 
     * @param ecmElectricityConsumption 电能耗数据
     * @return 结果
     */
    public int insertEcmElectricityConsumption(EcmElectricityConsumption ecmElectricityConsumption);

    /**
     * 修改电能耗数据
     * 
     * @param ecmElectricityConsumption 电能耗数据
     * @return 结果
     */
    public int updateEcmElectricityConsumption(EcmElectricityConsumption ecmElectricityConsumption);

    /**
     * 批量删除电能耗数据
     * 
     * @param consumptionIds 需要删除的电能耗数据主键集合
     * @return 结果
     */
    public int deleteEcmElectricityConsumptionByConsumptionIds(String[] consumptionIds);

    /**
     * 删除电能耗数据信息
     * 
     * @param consumptionId 电能耗数据主键
     * @return 结果
     */
    public int deleteEcmElectricityConsumptionByConsumptionId(String consumptionId);

    /**
     * 计算每小时的消耗量
     *
     * @param electricDeviceCode 电表编码
     * @param date       日期
     * @return 每小时消耗量和日总消耗量
     */
    ConsumptionValueAndCharge calculateHourlyConsumption(String electricDeviceCode, LocalDate date);

    /**
     * 计算每日的消耗量
     */
    public ConsumptionValueAndCharge calculateDailyConsumption(String electricDeviceCode, LocalDate month);

    /**
     * 根据年份，计算每月的消耗量
     */
    public ConsumptionValueAndCharge calculateMonthlyConsumption(String electricDeviceCode, int year);

    List<ConsumptionValueAndCharge24> getDeviceConsumptionValue24(String Location,LocalDate date);

    List<ConsumptionValueAndCharge24> getDeviceConsumptionValue24ByLine(Integer parentId,LocalDate date);

    List<ElectricityConsumptionDate> calculateTotalElectricityConsumption(String location, String type, String date);

    List<ConsumptionValueAndCharge> calculateTotalSummaryTable(String type, String date);

    List<ElectricityConsumptionDate> calculateTotalSummaryTableDate(String beginTime, String endTime);

    List<ElectricityConsumptionDate> calculateTotalSummaryTableDateByLocation(String beginTime, String endTime);

    KanbanPage getKanban();

    ConsumptionValueAndCharge24 calculateHourlyConsumption24(String electricDeviceCode, LocalDate date);
}
