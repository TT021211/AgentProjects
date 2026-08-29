package com.cuixing.ecm.mapper;

import java.time.LocalDate;
import java.util.List;

import com.cuixing.ecm.domain.EcmElectricityConsumption;
import com.cuixing.ecm.domain.VO.ElectricQuantity;
import com.cuixing.ecm.domain.VO.ElectricityConsumptionDate;
import com.cuixing.ecm.domain.VO.KanbanPage;
import com.cuixing.ecm.domain.VO.WeeksElectricQuantity;
import org.apache.ibatis.annotations.Param;

/**
 * 电能耗数据Mapper接口
 *
 * @author zhan
 * @date 2024-07-24
 */
public interface EcmElectricityConsumptionMapper {
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
     * 删除电能耗数据
     *
     * @param consumptionId 电能耗数据主键
     * @return 结果
     */
    public int deleteEcmElectricityConsumptionByConsumptionId(String consumptionId);

    /**
     * 批量删除电能耗数据
     *
     * @param consumptionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEcmElectricityConsumptionByConsumptionIds(String[] consumptionIds);

    List<EcmElectricityConsumption> getConsumptionByDeviceAndDate(
            @Param("electricDeviceCode") String electricDeviceCode,
            @Param("date") LocalDate date
    );

    Integer getDayFirstValue(@Param("electricDeviceCode") String electricDeviceCode, @Param("date") LocalDate date);

    /**
     * 获取每个月的数据
     * @param electricDeviceCode
     * @param startDate
     * @param endDate
     * @return
     */
    List<EcmElectricityConsumption> getConsumptionByDeviceAndMonth(@Param("electricDeviceCode") String electricDeviceCode, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 获取前一天的最后一条数据
     *
     * @param electricDeviceCode
     * @param date
     * @return
     */
    Integer getDayLastValue(@Param("electricDeviceCode") String electricDeviceCode, @Param("date") LocalDate date);

    /**
     * 获取每个月的第一条数据
     * @param year
     * @param electricDeviceCode
     * @return
     */
    List<EcmElectricityConsumption> getFirstRecordOfEachMonth(@Param("year") int year, @Param("electricDeviceCode") String electricDeviceCode);
    /**
     * 获取指定一个月的最后一条数据
     */
    Integer getLastRecordOfMonth(@Param("year") int year, @Param("month") int month, @Param("electricDeviceCode") String electricDeviceCode);

    /**
     * 获取指定设备最后一条数据
     * @param deviceCode
     * @return
     */
    EcmElectricityConsumption selectLastByCode(String deviceCode);

    EcmElectricityConsumption getDayFirstConsumption(@Param("electricDeviceCode") String electricDeviceCode, @Param("date") LocalDate date);

    EcmElectricityConsumption getZeroPointData(@Param("electricDeviceCode")  String electricDeviceCode,@Param("date") LocalDate date);

    //查询总电量
    int selectTotalYearConsumption(@Param("electricDeviceCode")String electricDeviceCode,@Param("year") int year);

    //查询时间范围内用电量
    List<ElectricityConsumptionDate> selectTotalConsumptionDate(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<ElectricityConsumptionDate> selectTotalConsumptionDateByLocation(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //漆包车间年用电量
    List<ElectricQuantity> getWorkshopConsumptionEnamelledYear();

    //漆包车间月用电量
    List<ElectricQuantity> getWorkshopConsumptionEnamelledMonth();

    //漆包车间周用电量
    List<WeeksElectricQuantity> getWorkshopConsumptionEnamelledMonthWeek();
}
