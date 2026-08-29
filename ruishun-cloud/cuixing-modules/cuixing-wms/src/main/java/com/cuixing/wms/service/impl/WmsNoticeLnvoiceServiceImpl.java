package com.cuixing.wms.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSaleService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdClient;
import com.cuixing.system.api.domain.md.MdProduct;
import com.cuixing.system.api.domain.sale.SaleOrder;
import com.cuixing.wms.entity.*;
import com.cuixing.wms.mapper.*;
import com.cuixing.wms.service.WmsNoticeLnvoiceService;
import com.cuixing.wms.utils.NumberGenerator;
import com.cuixing.wms.utils.OrderCodeGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 发货通知单(WmsNoticeLnvoice)表服务实现类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
@Service("wmsNoticeLnvoiceService")
public class WmsNoticeLnvoiceServiceImpl implements WmsNoticeLnvoiceService {
    private static final Logger log = LoggerFactory.getLogger(WmsNoticeLnvoiceServiceImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private WmsNoticeLnvoiceMapper wmsNoticeLnvoiceMapper;
    @Autowired
    private WmsOutboundOrderMapper wmsOutboundOrderMapper;
    @Autowired
    private WmsBoxMapper wmsBoxMapper;
    @Autowired
    private WmsInstallationMapper wmsInstallationMapper;
    @Autowired
    private WmsNoticeInvoiceRecordMapper wmsNoticeInvoiceRecordMapper;
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;

    @Autowired
    private RemoteMdService remoteMdService;
    @Autowired
    private RemoteSaleService remoteSaleService;
    @Autowired
    private RemoteSystemService remoteSystemService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsNoticeLnvoice queryById(Long id) {
        return this.wmsNoticeLnvoiceMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsNoticeLnvoice 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsNoticeLnvoice> selectWmsNoticeLnvoiceList(WmsNoticeLnvoice wmsNoticeLnvoice) {
        return this.wmsNoticeLnvoiceMapper.selectWmsNoticeLnvoiceList(wmsNoticeLnvoice);
    }

    /**
     * 新增数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsNoticeLnvoice insert(WmsNoticeLnvoice wmsNoticeLnvoice) {
        wmsNoticeLnvoice.setCreateBy(SecurityUtils.getUsername());
        wmsNoticeLnvoice.setCreateTime(new Date());
        wmsNoticeLnvoice.setDocumentStatus("DRAFT");
        String code = NumberGenerator.generateNumber("SD", new Date());
        wmsNoticeLnvoice.setDocumentNumber(code);

        //存放装托编号
        Set<String> installtionSet = new HashSet<>();

        //箱管理关联发货通知单号
        List<WmsBox> wmsBoxList = wmsNoticeLnvoice.getWmsBoxList();
        if (!wmsBoxList.isEmpty()) {
            for (WmsBox wmsBox : wmsBoxList) {
                wmsBox.setNoticeNumberCode(code);
                this.wmsBoxMapper.update(wmsBox);
                //修改装托状态
                if (wmsBox.getInstallationCode() != null) {
                    installtionSet.add(wmsBox.getInstallationCode());
                }
            }
        }
        // 将Set转换为List并排序
        if (!installtionSet.isEmpty()) {
            List<String> installtionList = new ArrayList<>(installtionSet);
            String InstallationCode = String.join(",", installtionList);
            Collections.sort(installtionList);
            if (!installtionList.isEmpty()) {
                for (String installtion : installtionList) {
                    WmsInstallation wmsInstallation1 = this.wmsInstallationMapper.queryByCode(installtion);
                    if (wmsInstallation1 != null) {
                        wmsInstallation1.setShipStatus("SHIP_OUT");
                        this.wmsInstallationMapper.update(wmsInstallation1);
                    }
                }
            }
            if (wmsNoticeLnvoice.getArtt1() == null) {
                wmsNoticeLnvoice.setArtt1(InstallationCode);
            }
        }
        this.wmsNoticeLnvoiceMapper.insert(wmsNoticeLnvoice);
        return wmsNoticeLnvoice;
    }

    /**
     * 修改数据
     *
     * @param wmsNoticeLnvoice 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsNoticeLnvoice update(WmsNoticeLnvoice wmsNoticeLnvoice) {
        wmsNoticeLnvoice.setUpdateBy(SecurityUtils.getUsername());
        wmsNoticeLnvoice.setUpdateTime(new Date());
        List<WmsBox> wmsBoxList = wmsNoticeLnvoice.getWmsBoxList();
        if (!wmsBoxList.isEmpty()) {
            for (WmsBox wmsBox : wmsBoxList) {
                wmsBox.setNoticeNumberCode(wmsNoticeLnvoice.getDocumentNumber());
                this.wmsBoxMapper.update(wmsBox);
            }
        }
        this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
        return this.queryById(wmsNoticeLnvoice.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(List<Long> ids) {
        if (ids.isEmpty()) {
            throw new RuntimeException("请选择要删除的数据");
        }
        for (Long id : ids) {
            WmsNoticeLnvoice wmsNoticeLnvoice = this.wmsNoticeLnvoiceMapper.queryById(id);
            if (wmsNoticeLnvoice != null) {
                if (!Objects.equals(wmsNoticeLnvoice.getDocumentStatus(), "DRAFT")) {
                    throw new RuntimeException("发货通知单状态不是草稿状态，不能删除");
                }
                List<WmsBox> wmsBoxes = new ArrayList<>();
                List<String> list = new ArrayList<>();
                if (wmsNoticeLnvoice.getArtt2() == null || wmsNoticeLnvoice.getArtt2().isEmpty()) {
                    wmsBoxes = this.wmsBoxMapper.queryByDocumentNumber(wmsNoticeLnvoice.getDocumentNumber());
                } else {
                    String artt2 = wmsNoticeLnvoice.getArtt2();
                    String[] split = artt2.split(",");
                    //将拿到托编号集合
                    list = Arrays.asList(split);
                    if (!list.isEmpty()) {
                        for (String s : list) {
                            //根据托编号修改箱状态、已经盘状态、以及成品状态
                            WmsInstallation wmsInstallation1 = this.wmsInstallationMapper.queryByCode(s);
                            wmsInstallation1.setShipStatus("IN");
                            this.wmsInstallationMapper.update(wmsInstallation1);
                        }
                    }
                    wmsBoxes = this.wmsBoxMapper.selectInstallation(list);

                }
                if (!wmsBoxes.isEmpty()) {
                    for (WmsBox wmsBox : wmsBoxes) {
                        //修改出品信息
                        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCode(wmsBox.getBoxCode());
                        if (!wmsProductInouts.isEmpty()) {
                            for(WmsProductInout wmsProductInout : wmsProductInouts){
                                this.wmsProductInoutMapper.updateToNull(wmsProductInout.getId());
                            }
                        }
                        this.wmsBoxMapper.updateToNotice(wmsBox.getId());
                    }
                }
            }
            this.wmsNoticeLnvoiceMapper.deleteById(id);
    }
        return true;
}

@Override
@Transactional
public WmsNoticeLnvoice updateStatus(List<WmsNoticeLnvoice> wmsNoticeLnvoices) {
    if (wmsNoticeLnvoices.isEmpty()) {
        throw new RuntimeException("请选择要操作的数据");
    }
    for (WmsNoticeLnvoice wmsNoticeLnvoice : wmsNoticeLnvoices) {
        String documentStatus = wmsNoticeLnvoice.getDocumentStatus();
        if (documentStatus != null) {
            WmsNoticeLnvoice wmsNoticeLnvoice1 = this.wmsNoticeLnvoiceMapper.queryById(wmsNoticeLnvoice.getId());
            WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord = new WmsNoticeInvoiceRecord();
            //如果发货通知单状态为待确认，则修改发货通知单状态为已确认
            if ("CONFIRM".equals(documentStatus) && "DRAFT".equals(wmsNoticeLnvoice1.getDocumentStatus())) {
                wmsNoticeInvoiceRecord.setAction("CONFIRM");
                wmsNoticeLnvoice.setUpdateBy(SecurityUtils.getUsername());
                wmsNoticeLnvoice.setUpdateTime(new Date());
                this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
                //如果发货通知单状态为已确认，则修改发货通知单状态为已取消
            } else if ("CANCEL".equals(documentStatus)) {
                //修改回草稿状态
                wmsNoticeInvoiceRecord.setAction("CANCEL");
                wmsNoticeInvoiceRecord.setAfterStatus("DRAFT");
                wmsNoticeLnvoice.setDocumentStatus("DRAFT");
                this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
                //如果发货通知单状态为已审核，则修改发货通知单状态为已确认
            } else if ("CHECK".equals(documentStatus) && "CONFIRM".equals(wmsNoticeLnvoice1.getDocumentStatus())) {
                wmsNoticeInvoiceRecord.setAction("CHECK");
                //新增出库单
                WmsOutboundOrder wmsOutboundOrder = new WmsOutboundOrder();
                String outCode = NumberGenerator.generateNumber("ISSUE", new Date());
                wmsOutboundOrder.setOutboundCode(outCode);
                wmsOutboundOrder.setOutboundType(wmsNoticeLnvoice.getDocumentType());
                wmsOutboundOrder.setOutboundStatus("SHIP_OUT");
                wmsOutboundOrder.setDocumentNumber(wmsNoticeLnvoice.getDocumentNumber());
                wmsOutboundOrder.setDetailedWeight(wmsNoticeLnvoice.getDetailedWeight());
                wmsOutboundOrder.setDetailedQuantity(wmsNoticeLnvoice.getDetailedQuantity());
                wmsOutboundOrder.setDetailedInformation(wmsNoticeLnvoice.getDetailedInformation());
                wmsOutboundOrder.setCreateTime(wmsNoticeLnvoice.getCreateTime());
                wmsOutboundOrder.setCreateBy(wmsNoticeLnvoice.getCreateBy());
                wmsOutboundOrder.setDetailCategory("成品");
                this.wmsOutboundOrderMapper.insert(wmsOutboundOrder);
                //修改发货通知单状态为已审核（待出库）
                this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
                //关联产品信息
                List<WmsBox> wmsBoxList = wmsNoticeLnvoice.getWmsBoxList();
                if (!wmsBoxList.isEmpty()){
                    for (WmsBox wmsBox : wmsBoxList) {
                        List<WmsProductInout> wmsProductInouts = this.wmsProductInoutMapper.selectToBoxCode(wmsBox.getBoxCode());
                        if (!wmsProductInouts.isEmpty()){
                            for (WmsProductInout wmsProductInout : wmsProductInouts){
                                if ((wmsProductInout.getArtt1()==null|| Objects.equals(wmsProductInout.getArtt1(), ""))&&(wmsProductInout.getDocumentNumber()==null|| Objects.equals(wmsProductInout.getDocumentNumber(), ""))){
                                    wmsProductInout.setArtt1(wmsNoticeLnvoice.getDocumentNumber());
                                    this.wmsProductInoutMapper.update(wmsProductInout);
                                }
                            }
                        }
                    }
                }
            }
            //记录发货通知单审核状态
            wmsNoticeInvoiceRecord.setNoticeInvoiceId(wmsNoticeLnvoice.getId());
            wmsNoticeInvoiceRecord.setNoticeInvoiceCode(wmsNoticeLnvoice.getDocumentNumber());
            wmsNoticeInvoiceRecord.setBeforeStatus(wmsNoticeLnvoice1.getDocumentStatus());
            if (wmsNoticeInvoiceRecord.getAfterStatus()==null){
                wmsNoticeInvoiceRecord.setAfterStatus(documentStatus);
            }
            wmsNoticeInvoiceRecord.setCreateBy(SecurityUtils.getUsername());
            wmsNoticeInvoiceRecord.setCreateTime(new Date());
            wmsNoticeInvoiceRecord.setRemake(wmsNoticeLnvoice.getArtt5());
            this.wmsNoticeInvoiceRecordMapper.insert(wmsNoticeInvoiceRecord);
        }
        this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
    }
    return null;
}

/**
 * 取消箱关联
 *
 * @param wmsBoxs
 * @return
 */
@Override
@Transactional
public boolean removeBox(List<WmsBox> wmsBoxs) {
    if (!wmsBoxs.isEmpty()) {
        for (WmsBox wmsBox : wmsBoxs) {
            if (wmsBox.getNoticeNumberCode() != null) {
                this.wmsBoxMapper.updateToNotice(wmsBox.getId());
            }
        }
        return true;
    }
    return false;
    }

    @Override
    @Transactional
    public int recall(List<WmsNoticeLnvoice> wmsNoticeLnvoices) {
        if (!wmsNoticeLnvoices.isEmpty()) {
            for (WmsNoticeLnvoice wmsNoticeLnvoice : wmsNoticeLnvoices) {
                if (Objects.equals(wmsNoticeLnvoice.getDocumentStatus(), "CHECK")){
                    //拿到对象根据单据号查询对应的出库单
                    WmsOutboundOrder wmsOutboundOrder = this.wmsOutboundOrderMapper.queryByDocumentNumber(wmsNoticeLnvoice.getDocumentNumber());
                    if (wmsOutboundOrder != null) {
                        if (Objects.equals(wmsOutboundOrder.getOutboundStatus(), "SHIP_OUT")) {
                            this.wmsOutboundOrderMapper.deleteById(wmsOutboundOrder.getId());
                        }
                    }
                    //记录发货通知单审核状态
                    WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord = getWmsNoticeInvoiceRecord(wmsNoticeLnvoice);
                    this.wmsNoticeInvoiceRecordMapper.insert(wmsNoticeInvoiceRecord);

                    wmsNoticeLnvoice.setDocumentStatus("DRAFT");
                }
                return this.wmsNoticeLnvoiceMapper.update(wmsNoticeLnvoice);
            }
        }
        return 0;
    }

    @Override
    public boolean test() {
        List<WmsNoticeLnvoice> wmsNoticeLnvoices = this.wmsNoticeLnvoiceMapper.selectWmsNoticeLnvoiceList(null);
        if (wmsNoticeLnvoices == null || wmsNoticeLnvoices.isEmpty()) {
            log.warn("No WmsNoticeLnvoice records found");
            return false;
        }

        List<SaleOrder> saleOrderList = new ArrayList<>();
        Set<String> itemCodes = new HashSet<>();
        Set<String> traderInitials = new HashSet<>();
        // 存储 WmsNoticeLnvoice 和其对应的 orderCode 列表
        Map<String, List<String>> noticeToOrderCodes = new HashMap<>();

        // 收集 itemCode 和 trader 拼音
        for (WmsNoticeLnvoice lnvoice : wmsNoticeLnvoices) {
            WmsBox wmsBox = new WmsBox();
            wmsBox.setNoticeNumberCode(lnvoice.getDocumentNumber());
            List<WmsBox> wmsBoxes = this.wmsBoxMapper.selectWmsBoxList(wmsBox);
            List<WmsBox> wmsBoxes1 = deduplicateAndSumWeight(wmsBoxes);
            for (WmsBox box : wmsBoxes1) {
                itemCodes.add(box.getItemCode());
            }
            if (lnvoice.getTrader() != null) {
                traderInitials.add(toPinyinInitials(lnvoice.getTrader()));
            }
        }

        // 批量查询 MdProduct 和 MdClient
        R<List<MdProduct>> productListR = remoteMdService.getProductsByCodes(itemCodes, SecurityConstants.INNER);
        Map<String, MdProduct> productMap = productListR.getData() != null
                ? productListR.getData().stream()
                .collect(Collectors.toMap(MdProduct::getProductCode, product -> product, (p1, p2) -> p1))
                : new HashMap<>();
        R<List<MdClient>> clientListR = remoteMdService.queryByClientNicks(traderInitials, SecurityConstants.INNER);
        Map<String, MdClient> clientMap = clientListR.getData() != null
                ? clientListR.getData().stream()
                .collect(Collectors.toMap(MdClient::getClientNick, client -> client, (c1, c2) -> c1))
                : new HashMap<>();

        // 构造 SaleOrder 并收集 orderCode
        for (WmsNoticeLnvoice wmsNoticeLnvoice : wmsNoticeLnvoices) {
            WmsBox wmsBox = new WmsBox();
            wmsBox.setNoticeNumberCode(wmsNoticeLnvoice.getDocumentNumber());
            List<WmsBox> wmsBoxes = this.wmsBoxMapper.selectWmsBoxList(wmsBox);
            List<WmsBox> wmsBoxes1 = deduplicateAndSumWeight(wmsBoxes);
            List<String> orderCodes = new ArrayList<>();

            for (WmsBox distinctWmsBox : wmsBoxes1) {
                MdProduct mdProduct = productMap.get(distinctWmsBox.getItemCode());
                if (mdProduct == null) {
                    log.warn("Product not found for itemCode: {}", distinctWmsBox.getItemCode());
                    continue;
                }
                /*if (!Objects.equals(distinctWmsBox.getProductName(), "裸铜线") || !Objects.equals(distinctWmsBox.getProductName(), "镀锡线")){
                    continue;
                }*/
                SaleOrder saleOrder = new SaleOrder();
                String autoCode = OrderCodeGenerator.generateOrderCode(adjustCreateTime(wmsNoticeLnvoice.getShipmentDate()));
                saleOrder.setOrderCode(sanitizeString(autoCode));
                saleOrder.setArtt4(sanitizeString(mdProduct.getProductName()));
                saleOrder.setSpecification(sanitizeString(mdProduct.getSpecification()));
                saleOrder.setProductId(mdProduct.getId());
                saleOrder.setModel(sanitizeString(mdProduct.getProductSize()));
                saleOrder.setWeight(distinctWmsBox.getNetWeight());
                saleOrder.setCreateTime(adjustCreateTime2(wmsNoticeLnvoice.getCreateTime()));
                saleOrder.setCreateBy(sanitizeString("hy"));
                saleOrder.setMeasureId(BigInteger.valueOf(28L));
                saleOrder.setMeasureName(sanitizeString("kg"));
                saleOrder.setAttr1(sanitizeString(mdProduct.getBatchNumber()));
                saleOrder.setPayTime(adjustCreateTime(wmsNoticeLnvoice.getShipmentDate()));
                saleOrder.setTechnicalRequirements(sanitizeString("/"));
                saleOrder.setRemark(sanitizeString("/"));
                saleOrder.setStandardId(BigInteger.valueOf(3L));
                saleOrder.setStandardName(sanitizeString("客户标准"));
                saleOrder.setStatus(sanitizeString("0"));
                saleOrder.setIsDeleted(0);
                saleOrder.setVersion(0);

                String trader = wmsNoticeLnvoice.getTrader();
                if (trader != null) {
                    String initials = toPinyinInitials(trader);
                    MdClient mdClient = clientMap.get(initials);
                    if (mdClient != null) {
                        saleOrder.setCustomerId(mdClient.getId());
                        saleOrder.setCustomerName(sanitizeString(mdClient.getClientNick()));
                    } else {
                        saleOrder.setCustomerName(sanitizeString(initials));
                    }
                    saleOrder.setOrderType("0");
                } else {
                    saleOrder.setOrderType("1");
                }

                saleOrderList.add(saleOrder);
                orderCodes.add(saleOrder.getOrderCode()); // 收集 orderCode
            }

            // 将 documentNumber 和 orderCodes 关联
            if (!orderCodes.isEmpty()) {
                noticeToOrderCodes.put(wmsNoticeLnvoice.getDocumentNumber(), orderCodes);
            }
        }

        if (saleOrderList.isEmpty()) {
            log.warn("No SaleOrder generated");
            return false;
        }

        try {
            log.info("SaleOrderList JSON: {}", mapper.writeValueAsString(saleOrderList));
            int i = this.wmsNoticeLnvoiceMapper.insertBatch2(saleOrderList);
            if (i < 1) {
                throw new RuntimeException("主数据服务调用失败");
            }

            // 回写 orderCode 到 WmsNoticeLnvoice 的 artt1 字段
            for (WmsNoticeLnvoice lnvoice : wmsNoticeLnvoices) {
                List<String> orderCodes = noticeToOrderCodes.get(lnvoice.getDocumentNumber());
                if (orderCodes != null && !orderCodes.isEmpty()) {
                    String artt1 = String.join(",", orderCodes); // 以逗号分隔 orderCode
                    lnvoice.setArtt1(sanitizeString(artt1));
                    this.wmsNoticeLnvoiceMapper.update(lnvoice);
                }
            }
        } catch (Exception e) {
            log.error("Failed to call remoteSaleService or update WmsNoticeLnvoice", e);
            throw new RuntimeException("主数据服务调用失败或更新失败", e);
        }

        return true;
    }

    private String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        // 清理所有控制字符（ASCII 0-31, 127-159）和其他不可打印字符
        return input.replaceAll("[\\p{Cntrl}\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "").trim();
    }

    public Date adjustCreateTime(Date createTime) {
        if (createTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.DAY_OF_MONTH, -3); // 提前 3 天
        return calendar.getTime();
    }
    public Date adjustCreateTime2(Date createTime) {
        if (createTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.DAY_OF_MONTH, -10); // 提前 3 天
        return calendar.getTime();
    }
    public String toPinyinInitials(String trader) {
        if (trader == null || trader.isEmpty()) {
            return trader;
        }

        try {
            // 设置拼音格式
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE); // 首字母大写
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 无音调

            StringBuilder initials = new StringBuilder();
            // 按字符处理
            for (char c : trader.toCharArray()) {
                // 仅处理中文字符
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]")) {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (pinyinArray != null && pinyinArray.length > 0) {
                        String pinyin = pinyinArray[0]; // 取第一个拼音
                        if (pinyin != null && !pinyin.isEmpty()) {
                            initials.append(pinyin.charAt(0)); // 提取首字母
                        }
                    }
                } else {
                    initials.append(c); // 非中文字符直接保留
                }
            }
            return initials.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return trader; // 转换失败返回原字符串
        }
    }
    public List<WmsBox> deduplicateAndSumWeight(List<WmsBox> wmsBoxes) {
        return wmsBoxes.stream()
                .collect(Collectors.groupingBy(
                        box -> box.getItemCode() + "|" + box.getProductName() + "|" + box.getWmsSpec(),
                        Collectors.reducing((box1, box2) -> {
                            WmsBox result = new WmsBox();
                            result.setProductName(box1.getProductName());
                            result.setItemCode(box1.getItemCode());
                            result.setWmsSpec(box1.getWmsSpec());
                            result.setGrossWeight(box1.getGrossWeight() + box2.getGrossWeight());
                            result.setNetWeight(box1.getNetWeight() + box2.getNetWeight());
                            return result;
                        })
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(box -> box.getGrossWeight() >= 100.0) // 过滤掉毛重小于100公斤的箱子
                .collect(Collectors.toList());
    }
    private static WmsNoticeInvoiceRecord getWmsNoticeInvoiceRecord(WmsNoticeLnvoice wmsNoticeLnvoice) {
        WmsNoticeInvoiceRecord wmsNoticeInvoiceRecord=new WmsNoticeInvoiceRecord();
        wmsNoticeInvoiceRecord.setNoticeInvoiceId(wmsNoticeLnvoice.getId());
        wmsNoticeInvoiceRecord.setNoticeInvoiceCode(wmsNoticeLnvoice.getDocumentNumber());
        wmsNoticeInvoiceRecord.setBeforeStatus(wmsNoticeLnvoice.getDocumentStatus());
        wmsNoticeInvoiceRecord.setAfterStatus("DRAFT");
        wmsNoticeInvoiceRecord.setCreateBy(SecurityUtils.getUsername());
        wmsNoticeInvoiceRecord.setCreateTime(new Date());
        wmsNoticeInvoiceRecord.setRemake(wmsNoticeLnvoice.getArtt5());
        wmsNoticeInvoiceRecord.setAction("RECALL");
        return wmsNoticeInvoiceRecord;
    }
}
