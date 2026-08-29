package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.*;
import com.cuixing.mold.mapper.MoldLedgerMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldRestructuringMapper;
import com.cuixing.mold.mapper.MoldSupplierMapper;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldRestructuringService;
import com.cuixing.mold.utils.MaintainPlanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

/**
 * 模具改制表(MoldRestructuring)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:50
 */
@Service("moldRestructuringService")
public class MoldRestructuringServiceImpl implements MoldRestructuringService {
    @Resource
    private MoldRestructuringMapper moldRestructuringMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldNestingMapper moldNestingMapper;
    @Resource
    private MoldSupplierMapper moldSupplierMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param moldRestructuringId 主键
     * @return 实例对象
     */
    @Override
    public MoldRestructuring queryById(Long moldRestructuringId) {
        return this.moldRestructuringMapper.queryById(moldRestructuringId);
    }

    /**
     * 分页查询
     *
     * @param moldRestructuring 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldRestructuring> selectMoldRestructuringList(MoldRestructuring moldRestructuring) {
        List<MoldRestructuring> moldRestructurings = this.moldRestructuringMapper.selectMoldRestructuringList(moldRestructuring);
        if (!moldRestructurings.isEmpty()) {
            for (MoldRestructuring curOutbound : moldRestructurings) {
                if (curOutbound.getMoldId()!=null){
                    MoldLedger ledger = this.moldLedgerService.queryById(curOutbound.getMoldId());
                    if(ledger!=null){
                        curOutbound.setMoldLedger(ledger);
                    }
                }
            }
        }
        return moldRestructurings;
    }

    /**
     * 确认报废
     * @param moldRestructuring 实例对象
     * @return
     */
    @Override
    public MoldRestructuring confirm(MoldRestructuring moldRestructuring) {

        return null;
    }

    /**
     * 新增数据
     *
     * @param moldRestructuring 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldRestructuring insert(MoldRestructuring moldRestructuring) {
        List<MoldLedger> moldLedgerList = moldRestructuring.getMoldLedgerList();
        List<MoldLedger> LedgerList = new ArrayList<>();
        List<MoldLedger> nestingLedgerList = new ArrayList<>();
        MoldNesting moldNesting = new MoldNesting();
        List<String> aperture = new ArrayList<>();
        if (!moldLedgerList.isEmpty()) {
            //是否是台账信息
            for (MoldLedger ledgers : moldLedgerList) {
                if (ledgers.getNestingMoldId() == null) {
                    LedgerList.add(ledgers);
                } else {
                    nestingLedgerList.add(ledgers);
                }
            }
            //台账
            if (!LedgerList.isEmpty()) {
                for (MoldLedger ledgers : LedgerList) {

                    //更新孔径
                    MoldLedger ledger1 = this.moldLedgerService.queryById(ledgers.getMoldId());
                    if(Objects.equals(ledger1.getStatus(), "UNDER_REPAIR") || Objects.equals(ledger1.getStatus(), "MAINTENANCE") || Objects.equals(ledger1.getStatus(), "CHECKED_OUT")){
                        throw new BussinessException("模具已处于维修或者保养、出库状态，不能进行改制");
                    }
                    //新建改制列表
                    MoldRestructuring moldRestructuring1 = getMoldRestructuring(moldRestructuring, ledgers, ledger1);
                    this.moldRestructuringMapper.insert(moldRestructuring1);

                    ledgers.setStatus("OPERATIONAL");

                    this.moldLedgerService.update(ledgers);
                }
            }
            //套模
            if (!nestingLedgerList.isEmpty()) {
                List<MoldLedger> allNestingLedgers=new ArrayList<>();
                for (MoldLedger ledger : nestingLedgerList) {
                    //更新孔径
                    MoldLedger ledger1 = this.moldLedgerService.queryById(ledger.getMoldId());
                    if(Objects.equals(ledger1.getStatus(), "UNDER_REPAIR") || Objects.equals(ledger1.getStatus(), "MAINTENANCE") || Objects.equals(ledger1.getStatus(), "CHECKED_OUT")){
                        throw new BussinessException("模具已处于维修或者保养、出库状态，不能进行改制");
                    }
                    //新建改制列表
                    MoldRestructuring moldRestructuring1 = getMoldRestructuring(moldRestructuring, ledger, ledger1);
                    this.moldRestructuringMapper.insert(moldRestructuring1);
                    ledger.setStatus("OPERATIONAL");
                    this.moldLedgerService.update(ledger);

                    allNestingLedgers = this.moldLedgerService.queryByNestingMoldId(moldRestructuring.getNestingMoldId());
                    for (MoldLedger moldLedger:allNestingLedgers){
                        if (Objects.equals(moldLedger.getMoldId(), ledger.getMoldId())){
                            moldLedger.setMoldAperture(ledger.getMoldAperture());
                        }
                    }
                }
                for (MoldLedger moldLedger:allNestingLedgers){
                    aperture.add(moldLedger.getMoldAperture());
                }
                if (!aperture.isEmpty()) {
                    // 将字符串列表转换为BigDecimal对象列表
                    List<BigDecimal> decimals = MaintainPlanUtils.convertToBigDecimals(aperture);
                    // 对列表进行排序
                    Collections.sort(decimals);
                    // 转换回字符串列表
                    List<String> sortedDecimalStrings = MaintainPlanUtils.convertToStringList(decimals);
                    // 获取最小值和最大值
                    String minValue = sortedDecimalStrings.get(0);
                    // 将排序后的字符串列表格式化为所需的形式
                    String result = String.join("~", sortedDecimalStrings);
                    moldNesting.setNestingScope(result);
                    moldNesting.setNestingSpec(minValue);

                    moldNesting.setNestingMoldId(moldRestructuring.getNestingMoldId());
                    this.moldNestingMapper.update(moldNesting);
                }
            }
        }
        return moldRestructuring;
    }

    private static MoldRestructuring getMoldRestructuring(MoldRestructuring moldRestructuring, MoldLedger ledgers, MoldLedger ledger1) {
        MoldRestructuring moldRestructuring1 = new MoldRestructuring();
        moldRestructuring1.setNestingMoldId(ledger1.getNestingMoldId());
        moldRestructuring1.setMoldId(ledgers.getMoldId());
        moldRestructuring1.setRestructuringDate(moldRestructuring.getRestructuringDate());
        moldRestructuring1.setCreateTime(new Date());
        moldRestructuring1.setRemark(moldRestructuring.getRemark());
        moldRestructuring1.setCreateBy(SecurityUtils.getUsername());
        moldRestructuring1.setNowAperture(ledgers.getMoldAperture());
        moldRestructuring1.setBeforeAperture(ledger1.getMoldAperture());
        moldRestructuring1.setBeforeEngineeringTolerance(ledger1.getEngineeringTolerance());
        moldRestructuring1.setEngineeringTolerance(ledgers.getEngineeringTolerance());
        moldRestructuring1.setMoldCode(ledger1.getMoldCode());
        moldRestructuring1.setMoldMaterial(ledger1.getMoldMaterial());
        moldRestructuring1.setMoldSpec(ledger1.getMoldSpec());
        moldRestructuring1.setMoldType(ledger1.getMoldType());
        return moldRestructuring1;
    }

    /**
     * 修改数据
     *
     * @param moldRestructuring 实例对象
     * @return 实例对象
     */
    @Override
    public MoldRestructuring update(MoldRestructuring moldRestructuring) {
        List<MoldLedger> moldLedgerList = moldRestructuring.getMoldLedgerList();
        List<MoldLedger> LedgerList = new ArrayList<>();
        List<MoldLedger> nestingLedgerList = new ArrayList<>();
        MoldNesting moldNesting = new MoldNesting();
        List<String> aperture = new ArrayList<>();
        if (!moldLedgerList.isEmpty()) {
            //是否是台账信息
            for (MoldLedger ledgers : moldLedgerList) {
                if (ledgers.getNestingMoldId() == null) {
                    LedgerList.add(ledgers);
                } else {
                    nestingLedgerList.add(ledgers);
                }
            }
            //台账
            if (!LedgerList.isEmpty()) {
                for (MoldLedger ledgers : LedgerList) {
                    //更新孔径
                    MoldLedger ledger1 = this.moldLedgerService.queryById(ledgers.getMoldId());
                    //新建改制列表
                    MoldRestructuring moldRestructuring1 = getRestructuring(moldRestructuring, ledgers, ledger1);
                    this.moldRestructuringMapper.update(moldRestructuring1);


                    ledger1.setMoldAperture(ledgers.getMoldAperture());
                    this.moldLedgerService.update(ledger1);
                }
            }
            //套模
            if (!nestingLedgerList.isEmpty()) {
                for (MoldLedger ledger : nestingLedgerList) {
                    //更新孔径
                    MoldLedger ledger1 = this.moldLedgerService.queryById(ledger.getMoldId());
                    //新建改制列表
                    MoldRestructuring moldRestructuring1 = getMoldRestructuring1(moldRestructuring, ledger, ledger1);
                    this.moldRestructuringMapper.update(moldRestructuring1);


                    ledger1.setMoldAperture(ledger.getMoldAperture());
                    ledger1.setMoldId(ledger.getMoldId());
                    this.moldLedgerService.update(ledger1);

                    //更新套模孔径范围
                    if (ledger.getMoldAperture() != null) {
                        aperture.add(ledger.getMoldAperture());
                    } else {
                        MoldLedger moldLedger = this.moldLedgerService.queryById(ledger.getMoldId());
                        if (moldLedger != null) {
                            aperture.add(moldLedger.getMoldAperture());
                        }
                    }
                }
                if (!aperture.isEmpty()) {
                    // 将字符串列表转换为BigDecimal对象列表
                    List<BigDecimal> decimals = MaintainPlanUtils.convertToBigDecimals(aperture);
                    // 对列表进行排序
                    Collections.sort(decimals);
                    // 转换回字符串列表
                    List<String> sortedDecimalStrings = MaintainPlanUtils.convertToStringList(decimals);
                    // 获取最小值和最大值
                    String minValue = sortedDecimalStrings.get(0);
                    // 将排序后的字符串列表格式化为所需的形式
                    String result = String.join("~", sortedDecimalStrings);
                    moldNesting.setNestingScope(result);
                    moldNesting.setNestingSpec(minValue);

                    moldNesting.setNestingMoldId(moldRestructuring.getNestingMoldId());
                    this.moldNestingMapper.update(moldNesting);
                }
            }
        }
        return this.queryById(moldRestructuring.getMoldRestructuringId());
    }

    private static MoldRestructuring getMoldRestructuring1(MoldRestructuring moldRestructuring, MoldLedger ledger, MoldLedger ledger1) {
        MoldRestructuring moldRestructuring1 = new MoldRestructuring();
        moldRestructuring1.setMoldRestructuringId(moldRestructuring.getMoldRestructuringId());
        moldRestructuring1.setNestingMoldId(ledger.getNestingMoldId());
        moldRestructuring1.setMoldId(ledger.getMoldId());
        moldRestructuring1.setRestructuringDate(moldRestructuring.getRestructuringDate());
        moldRestructuring1.setCreateTime(new Date());
        moldRestructuring1.setRemark(moldRestructuring.getRemark());
        moldRestructuring1.setCreateBy(SecurityUtils.getUsername());
        moldRestructuring1.setNowAperture(ledger.getMoldAperture());
        moldRestructuring1.setBeforeAperture(ledger1.getMoldAperture());
        moldRestructuring1.setMoldCode(ledger1.getMoldCode());
        moldRestructuring1.setMoldMaterial(ledger1.getMoldMaterial());
        moldRestructuring1.setMoldSpec(ledger1.getMoldSpec());
        moldRestructuring1.setMoldType(ledger1.getMoldType());
        return moldRestructuring1;
    }

    private static MoldRestructuring getRestructuring(MoldRestructuring moldRestructuring, MoldLedger ledgers, MoldLedger ledger1) {
        MoldRestructuring moldRestructuring1 = new MoldRestructuring();
        moldRestructuring1.setMoldRestructuringId(moldRestructuring.getMoldRestructuringId());
        moldRestructuring1.setNestingMoldId(ledgers.getNestingMoldId());
        moldRestructuring1.setMoldId(ledgers.getMoldId());
        moldRestructuring1.setRestructuringDate(moldRestructuring.getRestructuringDate());
        moldRestructuring1.setUpdateTime(new Date());
        moldRestructuring1.setRemark(moldRestructuring.getRemark());
        moldRestructuring1.setUpdateBy(SecurityUtils.getUsername());
        moldRestructuring1.setNowAperture(ledgers.getMoldAperture());
        moldRestructuring1.setBeforeAperture(ledger1.getMoldAperture());
        moldRestructuring1.setMoldCode(ledger1.getMoldCode());
        moldRestructuring1.setMoldMaterial(ledger1.getMoldMaterial());
        moldRestructuring1.setMoldSpec(ledger1.getMoldSpec());
        moldRestructuring1.setMoldType(ledger1.getMoldType());
        return moldRestructuring1;
    }

    /**
     * 通过主键删除数据
     *
     * @param moldRestructuringId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldRestructuringId) {
        return this.moldRestructuringMapper.deleteById(moldRestructuringId) > 0;
    }

}
