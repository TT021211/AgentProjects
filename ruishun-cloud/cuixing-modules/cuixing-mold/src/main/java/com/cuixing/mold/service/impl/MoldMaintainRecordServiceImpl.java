package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldMaintainItem;
import com.cuixing.mold.entity.MoldMaintainPlan;
import com.cuixing.mold.entity.MoldMaintainRecord;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.mapper.MoldMaintainItemMapper;
import com.cuixing.mold.mapper.MoldMaintainPlanMapper;
import com.cuixing.mold.mapper.MoldMaintainRecordMapper;
import com.cuixing.mold.mapper.MoldNestingMapper;
import com.cuixing.mold.service.MoldMaintainRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具保养表--保养记录表(MoldMaintainRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-06-25 16:52:05
 */
@Service("moldMaintainRecordService")
public class MoldMaintainRecordServiceImpl implements MoldMaintainRecordService {
    @Resource
    private MoldMaintainRecordMapper moldMaintainRecordMapper;
    @Resource
    private MoldMaintainItemMapper moldMaintainItemMapper;
    @Resource
    private MoldMaintainPlanMapper moldMaintainPlanMapper;
    @Resource
    private MoldNestingMapper moldNestingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param maintainRecordId 主键
     * @return 实例对象
     */
    @Override
    public MoldMaintainRecord queryById(Long maintainRecordId) {
        return this.moldMaintainRecordMapper.queryById(maintainRecordId);
    }

    /**
     * 分页查询
     *
     * @param moldMaintainRecord 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldMaintainRecord> selectMoldMaintainRecordList(MoldMaintainRecord moldMaintainRecord) {
        List<MoldMaintainRecord> moldMaintainRecords = this.moldMaintainRecordMapper.selectMoldMaintainRecordList(moldMaintainRecord);
        if (!moldMaintainRecords.isEmpty()) {
            for(MoldMaintainRecord maintainRecord : moldMaintainRecords){

                List<MoldMaintainItem> items = new ArrayList<>();

                if (maintainRecord.getNestingMoldId() != null) {
                    MoldNesting nesting = this.moldNestingMapper.queryById(maintainRecord.getNestingMoldId());
                    if(nesting!=null){
                        maintainRecord.setNestingName(nesting.getNestingName());
                        maintainRecord.setNestingScope(nesting.getNestingScope());
                        maintainRecord.setNestingArea(nesting.getNestingArea());
                    }
                }
                if (maintainRecord.getMaintainItemId()!=null){
                    String itemIds = maintainRecord.getMaintainItemId();
                    String[] idArray = itemIds.split(",");
                    Long[] longArray = new Long[idArray.length];
                    for (int i = 0; i < idArray.length; i++) {
                        longArray[i] = Long.parseLong(idArray[i]);
                    }
                    // 输出结果以验证转换成功
                    for (Long id : longArray) {
                        MoldMaintainItem moldMaintainItem = this.moldMaintainItemMapper.queryById(id);
                        if (moldMaintainItem!=null){
                            items.add(moldMaintainItem);
                        }
                    }
                    maintainRecord.setMaintainItems(items);
                }
            }
        }
        return moldMaintainRecords;
    }

    /**
     * 新增数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainRecord insert(MoldMaintainRecord moldMaintainRecord) {
        moldMaintainRecord.setCreateTime(new Date());
        moldMaintainRecord.setCreateBy(SecurityUtils.getUsername());
        this.moldMaintainRecordMapper.insert(moldMaintainRecord);
        return moldMaintainRecord;
    }

    /**
     * 修改数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 实例对象
     */
    @Override
    public MoldMaintainRecord update(MoldMaintainRecord moldMaintainRecord) {
        moldMaintainRecord.setUpdateTime(new Date());
        moldMaintainRecord.setUpdateBy(SecurityUtils.getUsername());
        this.moldMaintainRecordMapper.update(moldMaintainRecord);
        return this.queryById(moldMaintainRecord.getMaintainRecordId());
    }

    /**
     * 通过主键删除数据
     *
     * @param maintainRecordId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long maintainRecordId) {
        return this.moldMaintainRecordMapper.deleteById(maintainRecordId) > 0;
    }
}
