package com.cuixing.wms.controller;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.entity.vo.ShowVo;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.wms.service.WmsWeighRecordService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import com.cuixing.wms.service.impl.WmsWeighRecordServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


/**
 * 仓储管理---称重记录(WmsWeighRecord)表控制层
 *
 * @author 阿江
 * @since 2024-10-21 11:44:27
 */
@Api(tags = "仓储管理---称重记录接口")
@RestController
@RequestMapping("/wmsWeighRecord")
public class WmsWeighRecordController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WmsWeighRecordController.class);    /**
     * 服务对象
     */
    @Autowired
    private WmsWeighRecordService wmsWeighRecordService;
    @Autowired
    private WmsBoxService wmsBoxService;
    @Resource
    private ExecutorService executorService; // 注入线程池
    /**
     * 分页查询
     *
     * @param wmsWeighRecord 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsWeighRecord wmsWeighRecord) {
        this.startPage();
        List<WmsWeighRecord> list = this.wmsWeighRecordService.selectWmsWeighRecordList(wmsWeighRecord);
        return this.getDataTable(list);
    }

    /**
     * 查询昨天未完成的数据
     */
    @ApiOperation("查询昨天未完成的数据")
    @GetMapping("/queryYesterday")
    public AjaxResult queryYesterday() {
        return AjaxResult.success(this.wmsWeighRecordService.queryYesterday());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.wmsWeighRecordService.queryById(id));
    }

    /**
     * 查询当天三个最大值
     *
     *
     * @return 单条数据
     */
    @GetMapping("/selectMaxValues")
    public AjaxResult selectMaxValues() {
        return AjaxResult.success(this.wmsWeighRecordService.selectMaxValues());
    }

    /**
     * 新增数据
     *
     * @param wmsWeighRecord 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsWeighRecord wmsWeighRecord) {
        // 1. 插入称重记录
        long startTime = System.currentTimeMillis();
        WmsWeighRecord insert;
        try {
            insert = wmsWeighRecordService.insert(wmsWeighRecord);
            if (insert == null) {
                logger.error("插入称重记录失败，箱码：{}"+wmsWeighRecord.getBoxCode());
                return AjaxResult.error("插入称重记录失败");
            }
        } catch (Exception e) {
            logger.error("插入称重记录时发生异常，箱码：{}"+ wmsWeighRecord.getBoxCode(), e);
            throw new BussinessException("插入称重记录失败", e);
        }
        logger.debug("插入称重记录耗时："+(System.currentTimeMillis() - startTime)+"ms，箱码：{}"+wmsWeighRecord.getBoxCode());

//        // 2. 异步上传二级节点
//        CompletableFuture.runAsync(() -> {
//            try {
//                wmsWeighRecordService.uploadSecondaryNode(wmsWeighRecord);
//                logger.info("异步上传二级节点成功，箱码：{}"+wmsWeighRecord.getBoxCode());
//            } catch (Exception e) {
//                logger.error("异步上传二级节点失败，箱码：{}"+ wmsWeighRecord.getBoxCode(), e);
//                // 可选：记录失败任务以便重试
//            }
//        }, executorService);

        // 3. 返回结果
        return AjaxResult.success(insert);
    }

    @GetMapping({"/updateNodeData"})
    public AjaxResult updateNodeData() {
        WmsWeighRecord query = new WmsWeighRecord();
        query.setMaterialCode("_system_all_");
        List<WmsWeighRecord> wmWeighList = this.wmsWeighRecordService.selectWmsWeighRecordList(query);
        System.out.println("size=" + wmWeighList.size());
        Iterator var3 = wmWeighList.iterator();

        while(var3.hasNext()) {
            WmsWeighRecord wmWeigh = (WmsWeighRecord)var3.next();
            WmsWeighRecord entity = null;

            try {
                entity = this.wmsWeighRecordService.queryById(wmWeigh.getId());
                WmsBox wmsBox = this.wmsBoxService.queryByBoxCode(wmWeigh.getBoxCode());
                wmWeigh.setBoxCode(wmWeigh.getBoxCode());
                wmWeigh.setGrossWeight(entity.getGrossWeight());//毛重
                wmWeigh.setTotalNetWeight(wmsBox.getNetWeight());//总净重
                wmWeigh.setTotalGrossWeight(wmsBox.getGrossWeight());//总毛重
                wmWeigh.setBoxPerNumber(entity.getBoxPerNumber());//每箱盘数
            } catch (Exception var8) {
                throw new BussinessException(var8);
            }

            this.wmsWeighRecordService.updateNodeData(wmWeigh);

            try {
                Thread.sleep(200L);
            } catch (InterruptedException var7) {
                var7.printStackTrace();
            }
        }

        return AjaxResult.success();
    }

    /**
     * 编辑数据
     *
     * @param wmsWeighRecord 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWeighRecord wmsWeighRecord) {
        return AjaxResult.success(this.wmsWeighRecordService.update(wmsWeighRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.wmsWeighRecordService.deleteById(id));
    }

    @DeleteMapping("/deleteByBoxCode/{boxCode}")
    public AjaxResult deleteByBoxCode(@PathVariable("boxCode") String boxCode) {
        return toAjax(this.wmsWeighRecordService.deleteByBoxCode(boxCode));
    }

    /**
     * 未状态的最大值（通过品名、规格、产品编码、批号）查询
     */
    @ApiOperation("未状态的最大值（通过品名、规格、产品编码、批号）查询")
    @PostMapping("/selectWmsWeighRecordToParams")
    public AjaxResult selectWmsWeighRecordToParams(@RequestBody WmsWeighRecord wmsWeighRecord) {
        return AjaxResult.success(this.wmsWeighRecordService.selectWmsWeighRecordToParams(wmsWeighRecord));
    }
    /**
     * 未状态的最大值（通过品名、规格、产品编码、批号）查询
     */
    @ApiOperation("未状态的总净重，根据用户名（通过品名、规格、产品编码、批号）查询")
    @PostMapping("/selectWmsWeighRecordToParams2")
    public AjaxResult selectWmsWeighRecordToParams2(@RequestBody WmsWeighRecord wmsWeighRecord) {
        WmsWeighRecord data = this.wmsWeighRecordService.selectWmsWeighRecordToParams2(wmsWeighRecord);
        System.out.println(data);
        return AjaxResult.success(data);
    }

    /**
     * 统计仓储看板数据
     */
    @ApiOperation("统计仓储看板数据")
    @GetMapping("/getWmsCountData")
    public AjaxResult getWmsCountData() {
        return AjaxResult.success(this.wmsWeighRecordService.getWmsCountData());
    }

    /**
     * 统计称重记录数据
     */
    @ApiOperation("统计称重记录数据")
    @PostMapping("/getWmsCountDataForDay")
    public AjaxResult getWmsCountDataForTime(@RequestBody ShowVo showVo) {
        return AjaxResult.success(this.wmsWeighRecordService.getWmsCountDataForTime(showVo.getWmsSpecs(),showVo.getDate(),showVo.getType()));
    }
}

