package com.cuixing.mold.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldClassification;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.entity.MoldSupplier;
import com.cuixing.mold.mapper.MoldClassificationMapper;
import com.cuixing.mold.mapper.MoldLedgerMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.mapper.MoldSupplierMapper;
import com.cuixing.mold.service.MoldLedgerService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Resource;

/**
 * 模具台账表(MoldLedger)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 14:06:46
 */
@Service("moldLedgerService")
public class MoldLedgerServiceImpl implements MoldLedgerService {
    @Resource
    private MoldLedgerMapper moldLedgerMapper;
    @Resource
    private MoldSupplierMapper moldSupplierMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param moldId 主键
     * @return 实例对象
     */
    @Override
    public MoldLedger queryById(Long moldId) {
        MoldLedger ledger = this.moldLedgerMapper.queryById(moldId);
        if (ledger != null) {
            if (ledger.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(ledger.getNestingMoldId());
                if (moldNesting != null) {
                    ledger.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return ledger;
    }

    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldLedger> selectMoldLedgerList(MoldLedger moldLedger) {
        List<MoldLedger> moldLedgers = this.moldLedgerMapper.selectMoldLedgerList(moldLedger);
        for (MoldLedger moldLedger_tt : moldLedgers) {
            if (moldLedger_tt.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                if (moldNesting != null) {
                    moldLedger_tt.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return moldLedgers;
    }

    /**
     * 分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    public List<MoldLedger> selectMoldLedgerListNew2(MoldLedger moldLedger) {
        List<MoldLedger> moldLedgers = this.moldLedgerMapper.selectMoldLedgerListNew2(moldLedger);
        for (MoldLedger moldLedger_tt : moldLedgers) {
            if (moldLedger_tt.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                if (moldNesting != null) {
                    moldLedger_tt.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return moldLedgers;
    }

    public List<MoldLedger> selectMoldLedgerListNew1(MoldLedger moldLedger) {
        List<MoldLedger> moldLedgers = this.moldLedgerMapper.selectMoldLedgerListNew1(moldLedger);

        for (MoldLedger moldLedger_tt : moldLedgers) {
            if (moldLedger_tt.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                if (moldNesting != null) {
                    moldLedger_tt.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return moldLedgers;
    }
    /**
     * 过滤分页查询
     *
     * @param moldLedger 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldLedger> selectMoldLedgerList2(MoldLedger moldLedger) {
        moldLedger.setMoldSpec("出口模");
        List<MoldLedger> moldLedgers = this.moldLedgerMapper.selectMoldLedgerTypeList(moldLedger);
        for (MoldLedger moldLedger_tt : moldLedgers) {
            if (moldLedger_tt.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                if (moldNesting != null) {
                    moldLedger_tt.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return moldLedgers;
    }

    // 示例方法：根据条件删除 MoldLedger 对象
    public void removeMoldLedgerByCondition(List<MoldLedger> moldLedgers, Predicate<MoldLedger> condition) {
        moldLedgers.removeIf(condition);
    }

    @Override
    public List<MoldLedger> queryByClassificationId(MoldLedger moldLedger) {
        List<MoldLedger> moldLedgers = this.selectMoldLedgerListNew1(moldLedger);

//        if (!moldLedgers.isEmpty()) {
//            Predicate<MoldLedger> condition1 = moldLedger1 -> moldLedger1.getNestingMoldId() != null;
//            moldLedgers = moldLedgers.stream()
//                    .filter(condition1.negate())
//                    .collect(Collectors.toList());
//        }
        return moldLedgers;
    }

    @Override
    public List<MoldLedger> queryByClassificationId2(MoldLedger moldLedger) {
        List<MoldLedger> moldLedgers = this.selectMoldLedgerListNew2(moldLedger);

//        if (!moldLedgers.isEmpty()) {
//            Predicate<MoldLedger> condition1 = moldLedger1 -> moldLedger1.getNestingMoldId() != null;
//            Predicate<MoldLedger> condition2 = moldLedger1 -> "出口模".equals(moldLedger1.getMoldSpec());
//            Predicate<MoldLedger> combinedCondition = condition1.or(condition2); // Combine conditions using 'or' or 'and'
//
//            moldLedgers = moldLedgers.stream()
//                    .filter(combinedCondition.negate())
//                    .collect(Collectors.toList());
//        }

        return moldLedgers;
    }

    @Override
    public int updateStatus(MoldLedger moldLedger) {
        moldLedger.setUpdateTime(new Date());
        moldLedger.setUpdateBy(SecurityUtils.getUsername());
        return this.moldLedgerMapper.updateStatus(moldLedger);
    }

    @Override
    public int updateStatusAndNum(MoldLedger moldLedger) {
        moldLedger.setUpdateTime(new Date());
        moldLedger.setUpdateBy(SecurityUtils.getUsername());
        return this.moldLedgerMapper.updateStatusAndNum(moldLedger);
    }

    @Override
    public int updateNestingIdNull(Long moldId) {
        return this.moldLedgerMapper.updateNestingIdNull(moldId);
    }

    @Override
    public List<MoldLedger> queryByNestingMoldId(Long nestingMoldId) {
        List<MoldLedger> moldLedgerList = this.moldLedgerMapper.queryByNestingMoldId(nestingMoldId);
        for (MoldLedger moldLedger_tt : moldLedgerList) {
            if (moldLedger_tt.getNestingMoldId() != null) {
                MoldNesting moldNesting = this.moldNestingMapper.queryById(moldLedger_tt.getNestingMoldId());
                if (moldNesting != null) {
                    moldLedger_tt.setMoldName(moldNesting.getNestingName());
                }
            }
        }
        return moldLedgerList;
    }

    /**
     * 新增数据
     *
     * @param moldLedger 实例对象
     * @return 实例对象
     */
    @Override
    public MoldLedger insert(MoldLedger moldLedger) {
        moldLedger.setCreateTime(new Date());
        moldLedger.setCreateBy(SecurityUtils.getUsername());
        moldLedger.setStatus("OPERATIONAL");
        if (moldLedger.getMoldSupplierId()!=null){
            MoldSupplier moldSupplier = this.moldSupplierMapper.queryById(moldLedger.getMoldSupplierId());
            moldLedger.setMoldSupplierName(moldSupplier.getSupplierName());
        }
        this.moldLedgerMapper.insert(moldLedger);
        return moldLedger;
    }

    /**
     * 修改数据
     *
     * @param moldLedger 实例对象
     * @return 实例对象
     */
    @Override
    public MoldLedger update(MoldLedger moldLedger) {
        if (moldLedger.getMoldId() == null) {
            throw new BussinessException("id为空，修改失败！");
        } else {
            if (moldLedger.getMoldSupplierId()!=null){
                MoldSupplier moldSupplier = this.moldSupplierMapper.queryById(moldLedger.getMoldSupplierId());
                moldLedger.setMoldSupplierName(moldSupplier.getSupplierName());
            }
            moldLedger.setUpdateTime(new Date());
            moldLedger.setUpdateBy(SecurityUtils.getUsername());
            this.moldLedgerMapper.update(moldLedger);
            return this.queryById(moldLedger.getMoldId());
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param moldId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long moldId) {
        return this.moldLedgerMapper.deleteById(moldId) > 0;
    }

    @Override
    public String checkUniqueMoldLedgerCode(MoldLedger moldLedger) {
        MoldLedger moldLedgers = this.moldLedgerMapper.checkUniqueMoldLedgerCode(moldLedger);
        Long moldId = moldLedger.getMoldId() == null ? -1L : moldLedger.getMoldId();

        // 如果查询结果不为空且itemId不同，返回"1"表示不唯一，否则返回"0"表示唯一
        return (moldLedgers != null && !moldLedgers.getMoldId().equals(moldId)) ? "1" : "0";
    }

    @Override
    public void saveMoldLedgers(List<MoldLedger> moldLedgers) {
        this.moldLedgerMapper.insertBatch(moldLedgers);
    }

    @Override
    public boolean deleteByIds(BigInteger[] ids) {
        int result = moldLedgerMapper.deleteByIds(ids);
        return result > 0;
    }
}
