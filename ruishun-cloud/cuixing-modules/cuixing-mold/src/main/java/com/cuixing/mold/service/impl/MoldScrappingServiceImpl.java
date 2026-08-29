package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.entity.MoldScrapping;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldScrappingMapper;
import com.cuixing.mold.service.MoldLedgerService;
import com.cuixing.mold.service.MoldNestingService;
import com.cuixing.mold.service.MoldScrappingService;
import com.cuixing.mold.utils.MaintainPlanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

/**
 * 模具报废表(MoldScrapping)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:51
 */
@Service("moldScrappingService")
public class MoldScrappingServiceImpl implements MoldScrappingService {
    @Resource
    private MoldScrappingMapper moldScrappingMapper;
    @Resource
    private MoldLedgerService moldLedgerService;
    @Resource
    private MoldNestingMapper moldNestingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param moldScrappingId 主键
     * @return 实例对象
     */
    @Override
    public MoldScrapping queryById(Long moldScrappingId) {
        return this.moldScrappingMapper.queryById(moldScrappingId);
    }

    /**
     * 分页查询
     *
     * @param moldScrapping 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldScrapping> selectMoldScrappingList(MoldScrapping moldScrapping) {
        return  this.moldScrappingMapper.selectMoldScrappingList(moldScrapping);
    }

    @Override
    public MoldScrapping confirm(MoldScrapping moldScrapping) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param moldScrapping 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MoldScrapping insert(MoldScrapping moldScrapping) {
        // 初始化台账列表
        List<MoldLedger> moldLedgerList = moldScrapping.getMoldLedgerList();
        List<MoldLedger> LedgerList = new ArrayList<>();
        List<MoldLedger> nestingLedgerList = new ArrayList<>();
        // 检查台账列表是否为空
        if (!moldLedgerList.isEmpty()) {
            // 分类台账信息
            for (MoldLedger ledgers : moldLedgerList) {
                MoldLedger moldLedger = this.moldLedgerService.queryById(ledgers.getMoldId());
                if (Objects.equals(moldLedger.getStatus(), "UNDER_REPAIR") || Objects.equals(moldLedger.getStatus(), "MAINTENANCE")){
                    throw new BussinessException("该模具正在维修或保养中，无法报废！");
                }
                if (Objects.equals(moldLedger.getStatus(), "CHECKED_OUT")){
                    throw new BussinessException("该模具已出库,请先入库后在进行报废！");
                }
                if (ledgers.getNestingMoldId() == null) {
                    LedgerList.add(ledgers); // 单独模具
                } else {
                    nestingLedgerList.add(ledgers); // 套模
                }
            }
            // 处理套模台账
            if (!nestingLedgerList.isEmpty()) {
                for (MoldLedger ledger : nestingLedgerList) {
                    // 查询套模当前孔径范围
                    MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
                    // 查询模具的孔径范围
                    MoldLedger moldLedger = this.moldLedgerService.queryById(ledger.getMoldId());
                    // 切割套模范围
                    String nestingScope = moldNesting.getNestingScope();
                    String[] split = nestingScope.split("~");
                    String moldAperture = moldLedger.getMoldAperture();

                    // 检查nestingScope是否为单一值且等于moldAperture
                    boolean isSingleValueScope = split.length == 2 && split[0].equals(split[1]) && split[0].equals(moldAperture);

                    String[] filteredArray;
                    if (!isSingleValueScope) {
                        filteredArray = Arrays.stream(split)
                                .filter(s -> !s.equals(moldAperture))
                                .toArray(String[]::new);
                    } else {
                        // 如果是单一值且等于moldAperture，调整起始值或结束值
                        filteredArray = new String[]{split[0]}; // 保留原始起始值
                    }

                    // 更新套模范围
                    if (filteredArray.length > 0) {
                        Arrays.sort(filteredArray);
                        String min = filteredArray[0];
                        String result = String.join("~", filteredArray);
                        this.moldNestingMapper.update(new MoldNesting(ledger.getNestingMoldId(), min, result));
                    } else {
                        this.moldNestingMapper.updateToScopeNull(ledger.getNestingMoldId());
                    }
                    // 更新台账数量
                    if (moldLedger.getMoldNum() > 1) {
                        ledger.setMoldNum(moldLedger.getMoldNum() - 1);
                        ledger.setStatus("SCRAPPED");
                        this.moldLedgerService.update(ledger);
                        // 新建报废列表
                        MoldScrapping moldScrapping1 = getMoldScrapping(moldScrapping, moldLedger,moldNesting);
                        this.moldScrappingMapper.insert(moldScrapping1);
                    } else if (moldLedger.getMoldNum() <= 1) {
                        //如果数量为1，新增台账报废状态信息
                        MoldLedger moldLedger1=new MoldLedger();
                        moldLedger1.setMoldId(ledger.getMoldId());
                        moldLedger1.setStatus("SCRAPPED");
                        this.moldLedgerService.updateStatus(moldLedger1);
                        // 新建报废列表
                        MoldScrapping moldScrapping1 = getMoldScrapping(moldScrapping, moldLedger,moldNesting);
                        this.moldScrappingMapper.insert(moldScrapping1);
                    }
                    // 更新模具套模ID为null
                    this.moldLedgerService.updateNestingIdNull(ledger.getMoldId());
                    //最后查询套模关联信息,如果为空，那么该套模也进行删除操作
                    List<MoldLedger> moldLedgers = this.moldLedgerService.queryByNestingMoldId(ledger.getNestingMoldId());
                    if (moldLedgers.isEmpty()) {
                        //如果空，那么进行操作
                        this.moldNestingMapper.deleteById(ledger.getNestingMoldId());
                    }
                }
            }
        }
        // 处理单独模具台账
        if (!LedgerList.isEmpty()) {
            for (MoldLedger ledger : LedgerList) {
                // 检查是否存在套模关联
                if (ledger.getNestingMoldId() != null) {
                    throw new BussinessException("该台账存在套模关联，请先取消关联再进行报废！");
                }
                // 更新台账数量
                MoldLedger moldLedger = this.moldLedgerService.queryById(ledger.getMoldId());
                if (moldLedger.getMoldNum() > 1) {
                    ledger.setMoldNum(moldLedger.getMoldNum() - 1);
                    ledger.setStatus("SCRAPPED");
                    this.moldLedgerService.update(ledger);
                    // 新建报废列表
                    MoldScrapping moldScrapping1 = getMoldScrapping2(moldScrapping, moldLedger);
                    this.moldScrappingMapper.insert(moldScrapping1);
                } else if (moldLedger.getMoldNum() <= 1) {
                    //如果数量为1，新增台账报废状态信息
                    MoldLedger moldLedger1=new MoldLedger();
                    moldLedger1.setMoldId(ledger.getMoldId());
                    moldLedger1.setStatus("SCRAPPED");
                    this.moldLedgerService.updateStatus(moldLedger1);
                    // 新建报废列表
                    MoldScrapping moldScrapping1 = getMoldScrapping2(moldScrapping, moldLedger);
                    this.moldScrappingMapper.insert(moldScrapping1);
                }
            }
        }
        return moldScrapping;
    }

    private static MoldScrapping getMoldScrapping(MoldScrapping moldScrapping, MoldLedger ledger,MoldNesting nesting) {
        MoldScrapping moldScrapping1 = new MoldScrapping();
        moldScrapping1.setMoldId(ledger.getMoldId());
        moldScrapping1.setArea(ledger.getMoldArea());
        moldScrapping1.setMoldCode(ledger.getMoldCode());
        moldScrapping1.setMoldMaterial(ledger.getMoldMaterial());
        moldScrapping1.setMoldSpec(ledger.getMoldSpec());
        moldScrapping1.setMoldType(ledger.getMoldType());
        moldScrapping1.setRemark(moldScrapping.getRemark());
        moldScrapping1.setScrappingDate(moldScrapping.getScrappingDate());
        moldScrapping1.setNestingName(nesting.getNestingName());
        moldScrapping1.setCreateTime(new Date());
        moldScrapping1.setCreateBy(SecurityUtils.getUsername());
        return moldScrapping1;
    }
    private static MoldScrapping getMoldScrapping2(MoldScrapping moldScrapping, MoldLedger ledger) {
        MoldScrapping moldScrapping1 = new MoldScrapping();
        moldScrapping1.setMoldId(ledger.getMoldId());
        moldScrapping1.setArea(ledger.getMoldArea());
        moldScrapping1.setMoldCode(ledger.getMoldCode());
        moldScrapping1.setMoldMaterial(ledger.getMoldMaterial());
        moldScrapping1.setMoldSpec(ledger.getMoldSpec());
        moldScrapping1.setMoldType(ledger.getMoldType());
        moldScrapping1.setRemark(moldScrapping.getRemark());
        moldScrapping1.setScrappingDate(moldScrapping.getScrappingDate());
        moldScrapping1.setCreateTime(new Date());
        moldScrapping1.setCreateBy(SecurityUtils.getUsername());
        return moldScrapping1;
    }

    /**
     * 修改数据
     *
     * @param moldScrapping 实例对象
     * @return 实例对象
     */
    @Override
    public MoldScrapping update(MoldScrapping moldScrapping) {
        /*if (moldScrapping.getNestingMoldId()!=null) {
            List<MoldLedger> moldLedgerList = moldScrapping.getMoldLedgerList();
            if (moldLedgerList.size()>0) {
                for (MoldLedger ledger:moldLedgerList){
                    MoldScrapping moldScrapping1=new MoldScrapping();
                    moldScrapping1.setNestingMoldId(ledger.getNestingMoldId());
                    moldScrapping1.setMoldId(ledger.getMoldId());
                    moldScrapping1.setMoldScrappingId(moldScrapping.getMoldScrappingId());
                    moldScrapping1.setStatus(moldScrapping.getStatus());
                    moldScrapping1.setRemark(moldScrapping.getRemark());
                    moldScrapping1.setCreateTime(new Date());
                    moldScrapping1.setScrappingDate(moldScrapping.getScrappingDate());
                    moldScrapping1.setCreateBy(SecurityUtils.getUsername());
                    this.moldScrappingMapper.update(moldScrapping1);
                }
            }
        }
        if (moldScrapping.getMoldId()!=null) {
            this.moldScrappingMapper.update(moldScrapping);
        }
        return this.queryById(moldScrapping.getMoldScrappingId());*/
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param moldScrappingId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldScrappingId) {
        return this.moldScrappingMapper.deleteById(moldScrappingId) > 0;
    }
}
