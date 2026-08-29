package com.cuixing.mold.controller;

import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.log.annotation.Log;
import com.cuixing.common.log.enums.BusinessType;
import com.cuixing.mold.entity.MoldLedger;
import com.cuixing.mold.entity.MoldNesting;
import com.cuixing.mold.mapper.MoldLedgerMapper;
import com.cuixing.mold.service.MoldNestingService;
import com.cuixing.common.core.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

/**
 * 拉丝套模表(MoldNesting)表控制层
 *
 * @author makejava
 * @since 2024-06-25 14:06:48
 */
@Api(tags = "拉丝套模表接口")
@RestController
@RequestMapping("/mold/moldNesting")
public class MoldNestingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private MoldNestingService moldNestingService;
    @Resource
    private MoldLedgerMapper moldLedgerMapper;

    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(MoldNesting moldNesting) {
        this.startPage();
        List<MoldNesting> list = this.moldNestingService.selectMoldNestingList(moldNesting);
        return this.getDataTable(list);
    }
    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list2"})
    public TableDataInfo list2(MoldNesting moldNesting) {
        this.startPage();
        List<MoldNesting> list = this.moldNestingService.selectMoldNestingList2(moldNesting);
        return this.getDataTable(list);
    }
    /**
     * 分页查询
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list3"})
    public TableDataInfo list3(MoldNesting moldNesting) {
        this.startPage();
        List<MoldNesting> list = this.moldNestingService.selectMoldNestingList3(moldNesting);
        return this.getDataTable(list);
    }

    /**
     * 套模详情
     *
     * @param moldNesting 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/view"})
    public TableDataInfo view(MoldNesting moldNesting) {
        List<MoldLedger> list=new ArrayList<>();
        if (moldNesting.getNestingMoldId()!=null) {
            list= this.moldNestingService.queryByNestingMoldId(moldNesting.getNestingMoldId());
        }
        return this.getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.moldNestingService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param moldNesting 实体
     * @return 新增结果
     */
    @Log(
            title = "拉丝套模",
            businessType = BusinessType.INSERT
    )
    @PostMapping
    public AjaxResult add(@RequestBody MoldNesting moldNesting) {
        if ("1".equals(this.moldNestingService.checkUniqueMoldNestingCode(moldNesting))) {
            return AjaxResult.error("套模编号已存在！");
        } else {
            MoldNesting nesting = this.moldNestingService.insert(moldNesting);
            if (!nesting.getMoldLedgers().isEmpty()) {
                for (MoldLedger moldLedger1: nesting.getMoldLedgers()){
                    MoldLedger moldLedger = new MoldLedger();
                    moldLedger.setNestingMoldId(nesting.getNestingMoldId());
                    moldLedger.setMoldId(moldLedger1.getMoldId());
                    this.moldLedgerMapper.update(moldLedger);
                }
            }
            return AjaxResult.success(nesting);
        }
    }

    /**
     * 编辑数据
     *
     * @param moldNesting 实体
     * @return 编辑结果
     */
    @Log(
            title = "拉丝套模",
            businessType = BusinessType.UPDATE
    )
    @PutMapping
    public AjaxResult edit(@RequestBody MoldNesting moldNesting) {
        if ("1".equals(this.moldNestingService.checkUniqueMoldNestingCode(moldNesting))) {
            return AjaxResult.error("套模编号已存在！");
        } else {
            MoldNesting nesting = this.moldNestingService.update(moldNesting);
            return AjaxResult.success(nesting);
        }
    }

    /**
     * 删除关联数据
     *
     * @param moldNesting 实体
     * @return 新增结果
     */
    @Log(
            title = "拉丝套模",
            businessType = BusinessType.INSERT
    )
    @PostMapping("/deleteByMoldLedger")
    public AjaxResult deleteByMoldLedger(@RequestBody MoldNesting moldNesting) {
        if (!moldNesting.getMoldLedgers().isEmpty()){
            for (MoldLedger moldLedger:moldNesting.getMoldLedgers()){
                //更新套模范围
                this.moldNestingService.updateToScopeAndSpec(moldNesting);
                //删除套模关联的台账
                this.moldLedgerMapper.updateNestingIdNull(moldLedger.getMoldId());
            }
        }
        return AjaxResult.success("删除成功！");
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @Log(
            title = "检测项",
            businessType = BusinessType.DELETE
    )
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        return this.toAjax(this.moldNestingService.deleteByIds(id));
    }

}

