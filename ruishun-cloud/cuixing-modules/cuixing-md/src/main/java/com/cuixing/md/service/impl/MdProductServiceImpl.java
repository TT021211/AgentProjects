package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
//import com.cuixing.common.rabbitmq.config.RabbitMqConfig;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.md.entity.MdProductType;
import com.cuixing.md.entity.vo.MdProductVo;
import com.cuixing.md.mapper.MdProductTypeMapper;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.mes.MesProductRouting;
//import io.seata.core.context.RootContext;
//import io.seata.spring.annotation.GlobalTransactional;
//import io.seata.core.context.RootContext;
//import io.seata.spring.annotation.GlobalTransactional;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.md.entity.MdProduct;
import com.cuixing.md.mapper.MdProductMapper;
import com.cuixing.md.service.IMdProductService;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;

/**
 * 产品表;(md_product)表服务实现类
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Service
public class MdProductServiceImpl implements IMdProductService{
    private final Logger log = LoggerFactory.getLogger(MdProductServiceImpl.class);
    @Autowired
    private MdProductMapper mdProductMapper;

    @Autowired
    private RemoteSystemService remoteSystemService;

    @Autowired
    private MdProductTypeMapper mdProductTypeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MdProduct queryById(BigInteger id){
        return mdProductMapper.queryById(id);
    }

    @Override
    public MdProductVo queryByName(String name) {
        Set<String> specSet = new HashSet<>();
        Map<String, String> axialTypeMap = new HashMap<>(); // 修正变量名拼写错误
        Map<String, Map<String, List<String>>> batchNumberMap = new HashMap<>(); // 改为嵌套Map
        Map<String, String> materialCodeMap = new HashMap<>();
        Map<String, String> coefficientMap = new HashMap<>();

        MdProductVo vo = new MdProductVo();
        MdProductType mdProductType = this.mdProductTypeMapper.queryByTypeName(name);

        if (mdProductType != null) {
            List<MdProduct> mdProducts = this.mdProductMapper.queryBypTypeId(mdProductType.getId());
            if (!mdProducts.isEmpty()) {
                for (MdProduct mdProduct : mdProducts) {
                    String spec = mdProduct.getSpecification();
                    specSet.add(spec);

                    if (mdProduct.getAxialType() != null) {
                        axialTypeMap.put(spec, mdProduct.getAxialType());
                    }

                    if (mdProduct.getBatchNumber() != null) {
                        // 创建一个嵌套的Map来存储批次号，外层key为规格，内层key为型号
                        batchNumberMap.computeIfAbsent(spec, k -> new HashMap<>())
                                .computeIfAbsent(mdProduct.getProductCode(), k -> new ArrayList<>())
                                .add(mdProduct.getBatchNumber());
                    }

                    if (mdProduct.getMaterialCode() != null) {
                        materialCodeMap.put(mdProduct.getProductCode(), mdProduct.getMaterialCode());
                    }

                    if (mdProduct.getCoefficient() != null) {
                        coefficientMap.put(spec, mdProduct.getCoefficient());
                    }
                }
            }
        }

        // 将Set转换为List并排序
        List<String> specList = new ArrayList<>(specSet);
        Collections.sort(specList);
        vo.setSpecList(specList);
        vo.setCoefficient(coefficientMap);
        vo.setAxialType(axialTypeMap);
        vo.setMaterialCode(materialCodeMap);
        vo.setBatchNumberMap(batchNumberMap); // 更新方法名以反映新的数据结构
        return vo;
    }

    /**
     * 分页查询
     *
     * @param mdProduct 筛选条件
     * @return 查询结果
     */
    public List<MdProduct> selectMdProductList(MdProduct mdProduct)
    {
        return mdProductMapper.selectMdProductList(mdProduct);
    }

    /**
     * 新增数据
     *
     * @param mdProduct 实例对象
     * @return 影响行数
     */
    public int insert(MdProduct mdProduct){
        mdProduct.setCreateTime(DateUtils.getNowDate());
        mdProduct.setCreateBy(SecurityUtils.getUsername());
        String code = remoteSystemService.getAutoCode("PRODUCT_LIST_CODE", SecurityConstants.INNER);
        mdProduct.setProductCode(code);
        return mdProductMapper.insert(mdProduct);
    }

    /**
     * 批量新增数据
     *
     * @param mdProducts 实例对象数组
     * @return 影响行数
     */
    public int insertBatch(List<MdProduct> mdProducts){
        int rs = 0;
        for (MdProduct mdProduct : mdProducts) {
            mdProduct.setCreateTime(DateUtils.getNowDate());
            mdProduct.setCreateBy(SecurityUtils.getUsername());
            rs = mdProductMapper.insert(mdProduct) + 1;
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param mdProduct 实例对象
     * @return 影响行数
     */
    public int update(MdProduct mdProduct){
        BigInteger id = mdProduct.getId();
        MdProduct theUpdatemdProduct = mdProductMapper.queryById(id);
        Integer version = theUpdatemdProduct.getVersion();
        if(version != null) {
            mdProduct.setVersion(version);
        }
        mdProduct.setUpdateTime(DateUtils.getNowDate());
        mdProduct.setUpdateBy(SecurityUtils.getUsername());
        return mdProductMapper.update(mdProduct);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(BigInteger id){
        int total = mdProductMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键数组
     * @return 是否成功
     */
    public boolean deleteByIds(BigInteger[] ids){
        int total = mdProductMapper.deleteByIds(ids);
        return total > 0;
    }

    /**
     * 获取产品列表，按型号分类
     */
    public List<List<MdProduct>> getProductList(MdProduct mdProduct) {
        List<List<MdProduct>> productList = mdProductMapper.getProductList(mdProduct);
        return productList;
    }

    @Override
    public List<MdProduct> selectByParam(MdProduct mdProduct) {
        return mdProductMapper.selectByParam(mdProduct);
    }

    @Override
    public boolean isProductDuplicate(String productTypeName, String specification, String batchNumber) {
        MdProduct mdProduct = new MdProduct();
        mdProduct.setBatchNumber(batchNumber);
        mdProduct.setProductTypeName(productTypeName);
        mdProduct.setSpecification(specification);
        List<MdProduct> mdProducts = this.mdProductMapper.selectByParam(mdProduct);
        return !mdProducts.isEmpty();
    }

    @Override
    public List<MdProduct> getProductListBySpec(List<String> productSize) {
        return this.mdProductMapper.getProductListBySpec(productSize);
    }

    @Override
    public List<MdProduct> queryByproductCode(Set<String> productCode) {
        return this.mdProductMapper.queryByproductCode(productCode);
    }
}
