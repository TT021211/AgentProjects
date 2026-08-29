package com.cuixing.wms.controller;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.service.WmsBoxService;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.core.web.controller.BaseController;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import com.cuixing.common.core.web.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;

/**
 * (WmsBox)表控制层
 *
 * @author 阿江
 * @since 2024-10-21 11:44:28
 */
@Api(tags = "接口")
@RestController
@RequestMapping("/wmsBox")
public class WmsBoxController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WmsBoxController.class);
    /**
     * /**
     * 服务对象
     */
    @Autowired
    private WmsBoxService wmsBoxService;
    @Resource
    private ExecutorService executorService; // 注入线程池

    /**
     * 分页查询
     *
     * @param wmsBox 筛选条件
     * @return 查询结果
     */
    @ApiOperation("")
    @GetMapping({"/list"})
    public TableDataInfo list(WmsBox wmsBox) {
        this.startPage();
        List<WmsBox> list = this.wmsBoxService.selectWmsBoxList(wmsBox);
        return this.getDataTable(list);
    }

    /**
     * 查询多个托下面的箱记录
     *
     * @param InstallationCodes
     * @return
     */
    @ApiOperation("")
    @PostMapping({"/selectInstallationCodeToBox"})
    public AjaxResult selectInstallationCodeToBox(@RequestBody List<String> InstallationCodes) {
        return AjaxResult.success(this.wmsBoxService.selectInstallation(InstallationCodes));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Long id) {
        return AjaxResult.success(this.wmsBoxService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param wmsBox 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody WmsBox wmsBox) {
        long startTime = System.currentTimeMillis();
        WmsBox insert;
        try {
            insert = this.wmsBoxService.insert(wmsBox);
            if (insert == null) {
                logger.error("插入箱记录失败，箱码：{}" + wmsBox.getBoxCode());
                return AjaxResult.error("插入箱记录失败");
            }
        } catch (Exception e) {
            logger.error("插入箱记录时发生异常，箱码：{}" + wmsBox.getBoxCode(), e);
            throw new BussinessException("插入箱记录失败", e);
        }
        logger.debug("插入插入箱记录耗时：" + (System.currentTimeMillis() - startTime) + "ms，箱码：{}" + wmsBox.getBoxCode());

//        // 2. 异步上传二级节点
//        CompletableFuture.runAsync(() -> {
//            try {
//                wmsBoxService.uploadSecondaryNode(wmsBox);
//                logger.info("异步上传二级节点成功，箱码：{}" + wmsBox.getBoxCode());
//            } catch (Exception e) {
//                logger.error("异步上传二级节点失败，箱码：{}" + wmsBox.getBoxCode(), e);
//                // 可选：记录失败任务以便重试
//            }
//        }, executorService);

        return AjaxResult.success(insert);
    }

    /**
     * 编辑数据
     *
     * @param wmsBox 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WmsBox wmsBox) {
        return AjaxResult.success(this.wmsBoxService.update(wmsBox));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        return toAjax(this.wmsBoxService.deleteById(id));
    }

    @DeleteMapping("/deleteByBoxCode/{boxCode}")
    public AjaxResult deleteByBoxCode(@PathVariable("boxCode") String boxCode) {
        return toAjax(this.wmsBoxService.deleteByBoxCode(boxCode));
    }

}

