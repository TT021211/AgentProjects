package com.cuixing.system.api;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.ServiceNameConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.system.api.domain.md.*;
import com.cuixing.system.api.factory.RemoteMdFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主数据服务
 *
 * @author cuixing
 */
@FeignClient(contextId = "remoteMdService", value = ServiceNameConstants.MD_SERVICE, fallbackFactory = RemoteMdFallbackFactory.class)
public interface RemoteMdService {
    /**
     * 通过id查询产品信息
     *
     * @param id 产品id
     * @return 结果
     */
    @GetMapping("/mdProduct/{id}")
    public R<MdProduct> getProduct(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping("/mdProduct/queryByproductCode")
    public R<List<MdProduct>> getProductsByCodes(@RequestParam(value = "productCode") Set<String> productCode, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过id查询物料信息
     *
     * @param id 物料id
     * @return 结果
     */
    @GetMapping("/mdItem/{id}")
    public R<MdItem> getItem(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过id查询辅料信息
     *
     * @param id 辅料id
     * @return 结果
     */
    @GetMapping("/mdAccessory/{id}")
    public R<MdAccessory> getAccessory(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过id查询客户信息
     *
     * @param id 客户id
     * @return 结果
     */
    @GetMapping("/mdClient/{id}")
    public R<MdClient> getInfo(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过id查询单位信息
     *
     * @param id 客户id
     * @return 结果
     */
    @GetMapping("/mdUnitMeasure/{id}")
    public R<MdUnitMeasure> getMeasure(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过物料规格取对应的在制品
     *
     * @return 结果
     */
    @GetMapping("/mdItem/getItemBySpecification/{specification}")
    public R<MdItem> getItemBySpecification(@PathVariable("specification") String specification, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 通过拉丝机台id查询信息
     *
     * @return 结果
     */
    @GetMapping("/mdWorkbenchWiredrawing/{id}")
    public R<MdWorkbenchWiredrawing> getWorkbenchWiredrawingById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过漆包机台id查询信息
     *
     * @return 结果
     */
    @GetMapping("/mdWorkbenchEnamelling/{id}")
    public R<MdWorkbenchEnamelling> getWorkbenchEnamellingById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过退火机台id查询信息
     */
    @GetMapping("/mdWorkbenchAnnealing/{id}")
    public R<MdWorkbenchAnnealing> getWorkbenchAnnealingById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过退火机台id查询信息
     */
    @GetMapping("/mdWorkbenchDoublewire/{id}")
    public R<MdWorkbenchDoublewire> getWorkbenchDoublewireById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过退火机台id查询信息
     */
    @GetMapping("/mdWorkbenchStrandedwire/{id}")
    public R<MdWorkbenchStrandedwire> getWorkbenchStrandedwireById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
 /**
     * 通过退火机台id查询信息
     */
    @GetMapping("/mdWorkbenchTinning/{id}")
    public R<MdWorkbenchTinning> getWorkbenchTinningById(@PathVariable("id") BigInteger id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 添加退货记录接口
     *
     * @return 结果*
     */
    @PostMapping("/mdReturnGoods")
    public R<AjaxResult> insertReturnGoods(@RequestBody MdReturnGoods mdReturnGoods, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/mdClient/queryByClientNick")
    public R<List<MdClient>> queryByClientNicks(@RequestParam(value = "clientNick") Set<String> clientNick, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
