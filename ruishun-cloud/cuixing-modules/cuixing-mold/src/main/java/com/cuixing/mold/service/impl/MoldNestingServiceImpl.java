package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.*;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldNestingService;
import com.cuixing.mold.utils.MaintainPlanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 拉丝套模表(MoldNesting)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:48
 */
@Service("moldNestingService")
public class MoldNestingServiceImpl implements MoldNestingService {
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldSupplierMapper moldSupplierMapper;
    @Resource
    private MoldMaintainPlanMapper moldMaintainPlanMapper;
    @Resource
    private MoldClassificationMapper moldClassificationMapper;

    @Override
    public List<MoldLedger> queryByNestingMoldId(Long nestingMoldId) {
        List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(nestingMoldId);
        if (!moldLedgers.isEmpty()) {
            for (MoldLedger moldLedger_tt : moldLedgers) {
                if (moldLedger_tt.getNestingMoldId() != null) {
                    MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                    if (moldNesting != null) {
                        moldLedger_tt.setMoldName(moldNesting.getNestingName());
                    }
                }
                if (moldLedger_tt.getClassificationId() != null) {
                    MoldClassification moldClassification = this.moldClassificationMapper.queryById(moldLedger_tt.getClassificationId());
                    if (moldClassification!=null) {
                        moldLedger_tt.setMoldType(moldClassification.getClassificationName());
                        moldLedger_tt.setMoldSpec(moldClassification.getClassificationType());
                    }
                }
            }
        }
        return moldLedgers;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param nestingMoldId 主键
     * @return 实例对象
     */
    @Override
    public MoldNesting queryById(Long nestingMoldId) {
        return this.moldNestingMapper.queryById(nestingMoldId);
    }

    @Override
    public String checkUniqueMoldNestingCode(MoldNesting moldNesting) {
        MoldNesting moldNesting1 = this.moldNestingMapper.checkUniqueNestingCode(moldNesting);
        Long nestingId = moldNesting.getNestingMoldId() == null ? -1L : moldNesting.getNestingMoldId();

        // 如果查询结果不为空且itemId不同，返回"1"表示不唯一，否则返回"0"表示唯一
        return (moldNesting1 != null && !moldNesting1.getNestingMoldId().equals(nestingId)) ? "1" : "0";
    }

    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldNesting> selectMoldNestingList(MoldNesting moldNesting) {
        List<MoldNesting> moldNestings = this.moldNestingMapper.selectMoldNestingList(moldNesting);
        if (!moldNestings.isEmpty()) {
            for (MoldNesting moldNesting_tt : moldNestings) {
                if (moldNesting_tt.getPlanId()!= null) {
                    MoldMaintainPlan moldMaintainPlan = this.moldMaintainPlanMapper.queryById(moldNesting_tt.getPlanId());
                    moldNesting_tt.setPlanName(moldMaintainPlan.getMaintainPlanName());
                }
            }
        }
        return moldNestings;
    }
    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldNesting> selectMoldNestingList2(MoldNesting moldNesting) {
        List<MoldNesting> moldNestings = this.moldNestingMapper.selectMoldNestingList2(moldNesting);
        if (!moldNestings.isEmpty()) {
            for (MoldNesting moldNesting_tt : moldNestings) {
                if (moldNesting_tt.getPlanId()!= null) {
                    MoldMaintainPlan moldMaintainPlan = this.moldMaintainPlanMapper.queryById(moldNesting_tt.getPlanId());
                    moldNesting_tt.setPlanName(moldMaintainPlan.getMaintainPlanName());
                }
            }
        }
        return moldNestings;
    }

    @Override
    public List<MoldNesting> selectMoldNestingList3(MoldNesting moldNesting) {
        List<MoldNesting> moldNestings = this.selectMoldNestingList2(moldNesting);
        // 检查列表是否为空
        if (!moldNestings.isEmpty()) {
            Iterator<MoldNesting> iterator = moldNestings.iterator();
            while (iterator.hasNext()) {
                MoldNesting moldNesting_tt = iterator.next();
                List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(moldNesting_tt.getNestingMoldId());
                if (!moldLedgers.isEmpty()) {
                    boolean shouldRemove = false;
                    for (MoldLedger moldLedger : moldLedgers) {
                        // 如果找到状态不是 SCRAPPED 的 MoldLedger，标记要删除
                        if (!Objects.equals(moldLedger.getStatus(), "SCRAPPED")) {
                            shouldRemove = true;
                            break;
                        }
                    }
                    // 根据条件删除
                    if (shouldRemove) {
                        iterator.remove();
                    }
                }
            }
        }
        return moldNestings;
    }

    @Override
    public int updateToScopeAndSpec(MoldNesting moldNesting) {
        for (MoldLedger ledger : moldNesting.getMoldLedgers()) {
            MoldNesting moldNesting1 = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
            if (moldNesting1 != null) {
                List<String> scopeList = new ArrayList<>(Arrays.asList(moldNesting1.getNestingScope().split("~")));
                boolean isRemoved = scopeList.remove(ledger.getMoldAperture()); // 尝试移除孔径
                if (isRemoved) {
                    if (scopeList.isEmpty()) {
                        // 如果移除后列表为空，则将nestingScope和nestingSpec置空或设置为默认值
                       this.moldNestingMapper.updateToScopeNull(moldNesting1.getNestingMoldId());
                    } else {
                        // 如果列表不为空，则重新计算nestingSpec
                        if (moldNesting1.getNestingSpec().equals(ledger.getMoldAperture())) {
                            Optional<String> newSpec = scopeList.stream().min(String::compareTo);
                            moldNesting1.setNestingSpec(newSpec.orElse(""));
                        }
                        String newScope = String.join("~", scopeList);
                        moldNesting1.setNestingScope(newScope);
                        this.moldNestingMapper.update(moldNesting1); // 更新数据库
                    }
                }
            }
        }
        return 1;
    }

    /**
     * 新增数据
     *
     * @param moldNesting 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldNesting insert(MoldNesting moldNesting) {
        moldNesting.setCreateTime(new Date());
        moldNesting.setCreateBy(SecurityUtils.getUsername());
        List<String> aperture = getStrings(moldNesting);
        if (!aperture.isEmpty()) {
            // 将字符串列表转换为BigDecimal对象列表
            List<BigDecimal> decimals = convertToBigDecimals(aperture);
            // 对列表进行排序
            Collections.sort(decimals);
            // 转换回字符串列表
            List<String> sortedDecimalStrings = convertToStringList(decimals);
            // 获取最小值和最大值
            String minValue = sortedDecimalStrings.get(0);
            // 将排序后的字符串列表格式化为所需的形式
            String result = String.join("~", sortedDecimalStrings);
            moldNesting.setNestingScope(result);
            moldNesting.setNestingSpec(minValue);
            this.moldNestingMapper.insert(moldNesting);
        }
        return moldNesting;
    }

    private static List<String> getStrings(MoldNesting moldNesting) {
        List<MoldLedger> moldLedgers = moldNesting.getMoldLedgers();
        List<String> aperture=new ArrayList<>();
        if (!moldLedgers.isEmpty()) {
            for (MoldLedger ledger:moldLedgers){
                if (ledger.getMoldAperture()!=null){
                    aperture.add(ledger.getMoldAperture());
                }
               /* if (ledger.getInOutStatus()!=null){
                    if(ledger.getInOutStatus().equals("CHECKED_OUT")){
                        throw new BussinessException("需要关联的模具已出库！");
                    }
                }*/
                if (ledger.getMaintenanceStatus()!=null){
                    if (ledger.getMaintenanceStatus().equals("UNDER_REPAIR")||ledger.getMaintenanceStatus().equals("MAINTENANCE")){
                        throw new BussinessException("需要关联的模具正在进行保养或者维修！");
                    }
                }
                if (ledger.getRestructutingStatus()!=null){
                    if (ledger.getRestructutingStatus().equals("SCRAPPED")){
                        throw new BussinessException("需要关联的模具已经报废！");
                    }
                }
            }
        }
        return aperture;
    }

    /**
     * 修改数据
     *
     * @param moldNesting 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldNesting update(MoldNesting moldNesting) {
        moldNesting.setUpdateTime(new Date());
        moldNesting.setUpdateBy(SecurityUtils.getUsername());
        List<MoldLedger> moldLedger_tt = moldNesting.getMoldLedgers();
        List<MoldLedger> moldLedgerList = this.moldLedgerService.queryByNestingMoldId(moldNesting.getNestingMoldId());
        List<String> aperture=new ArrayList<>();
        if (!moldLedger_tt.isEmpty()) {
            for (MoldLedger ledger : moldLedger_tt) {
                if (ledger.getMoldAperture() != null) {
                    aperture.add(ledger.getMoldAperture());
                }
               /* if (ledger.getInOutStatus() != null) {
                    if (ledger.getInOutStatus().equals("CHECKED_OUT")) {
                        throw new BussinessException("需要关联的模具已出库！");
                    }
                }*/
                if (ledger.getMaintenanceStatus() != null) {
                    if (ledger.getMaintenanceStatus().equals("UNDER_REPAIR") || ledger.getMaintenanceStatus().equals("MAINTENANCE")) {
                        throw new BussinessException("需要关联的模具正在进行保养或者维修！");
                    }
                }
                if (ledger.getRestructutingStatus() != null) {
                    if (ledger.getRestructutingStatus().equals("SCRAPPED")) {
                        throw new BussinessException("需要关联的模具已经报废！");
                    }
                }

                MoldLedger moldLedger = new MoldLedger();
                moldLedger.setMoldId(ledger.getMoldId());
                moldLedger.setNestingMoldId(moldNesting.getNestingMoldId());
                this.moldLedgerService.update(moldLedger);
            }
            // 将数据库中的数据转为集合
            Set<Long> moldLedgerSet = moldLedgerList.stream()
                    .map(MoldLedger::getMoldId)
                    .collect(Collectors.toSet());

            // 将传入的数据转为集合
            Set<Long> moldLedger_ttSet = moldLedger_tt.stream()
                    .map(MoldLedger::getMoldId)
                    .collect(Collectors.toSet());

            // 找出多余的记录
            Set<Long> redundantRecords = new HashSet<>(moldLedgerSet);
            redundantRecords.removeAll(moldLedger_ttSet);

            // 打印多余的记录
            for (Long redundantRecordId : redundantRecords) {
                this.moldLedgerService.updateNestingIdNull(redundantRecordId);
            }

        }
        if (!aperture.isEmpty()) {
            // 将字符串列表转换为BigDecimal对象列表
            List<BigDecimal> decimals = convertToBigDecimals(aperture);
            // 对列表进行排序
            Collections.sort(decimals);
            // 转换回字符串列表
            List<String> sortedDecimalStrings = convertToStringList(decimals);
            // 获取最小值和最大值
            String minValue = sortedDecimalStrings.get(0);
            // 将排序后的字符串列表格式化为所需的形式
            String result = String.join("~", sortedDecimalStrings);
            moldNesting.setNestingScope(result);
            moldNesting.setNestingSpec(minValue);
        }
        this.moldNestingMapper.update(moldNesting);
        return this.queryById(moldNesting.getNestingMoldId());
    }

    /**
     * 通过主键删除数据
     *
     * @param nestingMoldId 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(Long nestingMoldId) {
        List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(nestingMoldId);
        if (!moldLedgers.isEmpty()) {
            for (MoldLedger ledger:moldLedgers){
                this.moldLedgerService.updateNestingIdNull(ledger.getMoldId());
            }
        }
        return this.moldNestingMapper.deleteById(nestingMoldId);
    }

    // 将字符串列表转换为BigDecimal对象列表
    public static List<BigDecimal> convertToBigDecimals(List<String> decimalStrings) {
        return MaintainPlanUtils.convertToBigDecimals(decimalStrings);
    }

    // 将BigDecimal列表转换回字符串列表
    public static List<String> convertToStringList(List<BigDecimal> decimalList) {
        return MaintainPlanUtils.convertToStringList(decimalList);
    }

}

