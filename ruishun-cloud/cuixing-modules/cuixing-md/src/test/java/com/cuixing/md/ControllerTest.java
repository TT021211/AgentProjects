package com.cuixing.md;


import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.md.controller.*;
import com.cuixing.md.service.impl.MdProductServiceImpl;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdClient;
import com.cuixing.md.entity.*;
import com.cuixing.system.api.domain.md.MdItem;

import com.cuixing.system.api.domain.mes.MesProductRouting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class ControllerTest {

    private final Logger log = LoggerFactory.getLogger(ControllerTest.class);

    @Autowired
    MdUnitMeasureController mdUnitMeasureController;

    @Autowired
    MdProductTypeController mdProductTypeController;

    @Autowired
    MdProductController mdProductController;


    @Autowired
    MdItemController mdItemController;

    @Autowired
    MdItemTypeController mdItemTypeController;

    @Autowired
    MdWorkshopController mdWorkshopController;

    @Autowired
    MdWorkstationController mdWorkstationController;

    @Autowired
    MdWorkgroupController mdWorkgroupController;

    @Autowired
    MdWorkgroupMemberController mdWorkgroupMemberController;

    @Autowired
    MdClientController mdClientController;

    @Autowired
    MdVendorController mdVendorController;

    @Autowired
    private RemoteSystemService remoteSystemService;

//    @Autowired
//    RabbitTemplate rabbitTemplate;
    @Test
    @DisplayName("产品分类列表")
    void testGetProductTypeList(){
        MdProductType mdProductType = new MdProductType();
//        mdProductType.setTypeCode("test");
        mdProductType.setIfEnable(1);
        AjaxResult rs = mdProductTypeController.selectAll(mdProductType);
        System.out.println(rs);
    }

    @Test
    @DisplayName("产品分类下拉框")
    void testGetProductTypeTree() {
        AjaxResult rs = mdProductTypeController.getProductTypeTree();
        System.out.println(rs);
    }
    @Test
    @DisplayName("测试编码规则")
    public void testCode(){
        //物料唯一码
        String autoCode = remoteSystemService.getAutoCode("ITEM_SOLE_CODE", "1");
        System.out.println(autoCode);
    }

    @Test
    @DisplayName("添加产品分类")
    void testAddProductType(){
        MdProductType mdProductType = new MdProductType();
        mdProductType.setTypeCode("888");
        mdProductType.setTypeName("888");
        mdProductType.setIfEnable(1);
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductTypeController.add(mdProductType);
        System.out.println(rs);
    }

    @Test
    @DisplayName("取产品分类详情")
    void testGetProductType(){
//        MdProductType mdProductType = new MdProductType();
//        mdProductType.setId();
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductTypeController.getInfo(BigInteger.valueOf(6));
        System.out.println(rs.get("data"));
    }

    @Test
    @DisplayName("修改产品分类")
    void testEditProductType(){
        MdProductType mdProductType = new MdProductType();
        mdProductType.setId(BigInteger.valueOf(6));
        mdProductType.setTypeName("999");
        mdProductType.setTypeCode("999");
        mdProductType.setIfEnable(0);
        mdProductType.setRemark("999");
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductTypeController.edit(mdProductType);
        System.out.println(rs);
    }

    @Test
    @DisplayName("删除产品分类")
    void testDelProductType(){
//        MdProductType mdProductType = new MdProductType();
//        mdProductType.setId(BigInteger.valueOf(1));
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductTypeController.deleteById(BigInteger.valueOf(6));
        System.out.println(rs);
    }



    @Test
    @DisplayName("产品列表")
    void testGetProductList(){
        MdProduct mdProduct = new MdProduct();
        mdProduct.setProductCode("test2222");
//        mdProduct.setProductName("test");
        AjaxResult rs = mdProductController.selectAll(mdProduct);
        System.out.println(rs);
    }

//    @Test
//    @DisplayName("添加产品")
//    void testAddProduct(){
//        MdProduct mdProduct = new MdProduct();
//        mdProduct.setProductName("555");
//        mdProduct.setProductCode("555");
//        mdProduct.setProductTypeId(BigInteger.valueOf(2));
////        PageRequest pageRequest = PageRequest.of(1,20);
//        AjaxResult rs = mdProductController.add(mdProduct);
//        System.out.println(rs);
//    }

    @Test
    @DisplayName("取产品详情")
    void testGetProduct(){
//        MdProductType mdProductType = new MdProductType();
//        mdProductType.setId();
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductController.getInfo(BigInteger.valueOf(4));
        System.out.println(rs.get("data"));
    }

    @Test
    @DisplayName("修改产品")
    void testEditProduct(){
        MdProduct mdProduct = new MdProduct();
        mdProduct.setId(BigInteger.valueOf(4));
        mdProduct.setProductName("666");
        mdProduct.setProductCode("666");
        mdProduct.setRemark("666");
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductController.edit(mdProduct);
        System.out.println(rs);
    }

    @Test
    @DisplayName("删除产品")
    void testDelProduct(){
//        MdProductType mdProductType = new MdProductType();
//        mdProductType.setId(BigInteger.valueOf(1));
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdProductController.deleteById(BigInteger.valueOf(4));
        System.out.println(rs);
    }

    @Test
    @DisplayName("添加物料")
    void testAddItem(){
        MdItem m = new MdItem();
        m.setItemName("辅料999");
        m.setItemCode("999");
//        m.setProductTypeId(BigInteger.valueOf(2));
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdItemController.add(m);
        System.out.println(rs);
    }

    @Test
    @DisplayName("物料列表")
    void testListItem(){
//        MdItem m = new MdItem();
////        m.setProductTypeId(BigInteger.valueOf(2));
////        PageRequest pageRequest = PageRequest.of(1,20);
//        AjaxResult rs = mdItemController.selectAll(m);
//        System.out.println(rs);
        Integer mouldId = 1;
        AjaxResult rs = mdItemController.getItemListByMouldId(mouldId);
        System.out.println(rs);
    }

//    @Test
//    @DisplayName("模具创建对应在制品物料")
//    void testMouldItem(){
////        MdItem m = new MdItem();
//////        m.setProductTypeId(BigInteger.valueOf(2));
//////        PageRequest pageRequest = PageRequest.of(1,20);
////        AjaxResult rs = mdItemController.selectAll(m);
////        System.out.println(rs);
//        Integer mouldId = 1;
//        AjaxResult rs = mdItemController.createMouldItem(mouldId);
//        System.out.println(rs);
//    }

    @Test
    @DisplayName("编辑物料")
    void testEditItem() throws Exception {
        int n = 5; // 等待5个线程完成任务
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(() -> {
                try {
                    MdItem m = new MdItem();
                    m.setId(BigInteger.valueOf(1));
                    m.setItemName("辅料"+Thread.currentThread().getName());
                    AjaxResult rs = mdItemController.edit(m);
                    System.out.println(rs);

                    System.out.println(Thread.currentThread().getName() + " is working");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " done");
                    countDownLatch.countDown(); // 计数器减1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        countDownLatch.await(); // 等待其他线程完成任务
        System.out.println("All tasks done");

//        for(int i=1;i<5;i++) {
//            new Thread( () -> {
//
//            }
//            MdItem m = new MdItem();
//            m.setId(BigInteger.valueOf(1));
//            String str = String.valueOf(i);
//            m.setItemName("辅料"+str+str+str);
//            AjaxResult rs = mdItemController.edit(m);
//            System.out.println(rs);
//
//            Thread.sleep(1000);
//        }
    }

    @Test
    @DisplayName("删除物料")
    void testDelItem(){
        MdItem m = new MdItem();
//        m.setCopperbarName("666");
//        PageRequest pageRequest = PageRequest.of(1,20);
        AjaxResult rs = mdItemController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs);
    }





    @Test
    @DisplayName("测试物料分类")
    void testItemType(){

        MdItemType m = new MdItemType();
        m.setItemTypeName("辅料");
        m.setItemTypeCode("fuliao");
        AjaxResult rs = mdItemTypeController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdItemType m2 = new MdItemType();
        AjaxResult rs2 = mdItemTypeController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdItemType m3 = new MdItemType();
        m3.setId(BigInteger.valueOf(1));
        m3.setItemTypeName("上引辅料");
        m3.setItemTypeCode("syfuliao");
        AjaxResult rs3 = mdItemTypeController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdItemTypeController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdItemTypeController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试产品分类")
    void testProductType(){

        MdProductType m = new MdProductType();
        m.setTypeName("铜排");
        m.setTypeCode("tongpai");
        AjaxResult rs = mdProductTypeController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdProductType m2 = new MdProductType();
        AjaxResult rs2 = mdProductTypeController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdProductType m3 = new MdProductType();
        m3.setId(BigInteger.valueOf(1));
        m3.setTypeName("铜棒");
        m3.setTypeCode("tongbang");
        AjaxResult rs3 = mdProductTypeController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdProductTypeController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdProductTypeController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

//    @Test
//    @DisplayName("测试产品")
//    void testProduct(){
//        MdProduct m = new MdProduct();
//        m.setProductName("铜排50");
//        m.setProductCode("tp50");
//        m.setProductTypeId(BigInteger.valueOf(1));
//        m.setProductTypeName("铜排");
//        m.setProductTypeCode("tongpai");
//        m.setProcessRouteId(BigInteger.valueOf(1));
//        m.setProcessRouteName("挤制");
//        AjaxResult rs = mdProductController.add(m);
//        System.out.println(rs);
//        assert rs.get("code").equals(200):"测试新增失败";
////        MdProduct m2 = new MdProduct();
////        AjaxResult rs2 = mdProductController.selectAll(m2);
////        System.out.println(rs2);
////        assert rs2.get("code").equals(200):"测试列表失败";
////        MdProduct m3 = new MdProduct();
////        m3.setId(BigInteger.valueOf(1));
////        m3.setProductName("铜排50");
////        m3.setProductCode("tp50");
////        AjaxResult rs3 = mdProductController.edit(m3);
////        System.out.println(rs3);
////        assert rs3.get("code").equals(200):"测试修改失败";
////        AjaxResult rs4 = mdProductController.selectAll(m2);
////        System.out.println(rs4);
////        AjaxResult rs5 = mdProductController.deleteById(BigInteger.valueOf(1));
////        System.out.println(rs5);
////        assert rs5.get("code").equals(200):"测试删除失败";
//    }


    @Test
    @DisplayName("测试单位")
    void testUnitMeasure(){
        MdUnitMeasure m = new MdUnitMeasure();
        m.setMeasureName("千克");
        m.setMeasureCode("kg");
        m.setIfEnable(1);
        m.setIfPrimary(1);
        AjaxResult rs = mdUnitMeasureController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdUnitMeasure m2 = new MdUnitMeasure();
        AjaxResult rs2 = mdUnitMeasureController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdUnitMeasure m3 = new MdUnitMeasure();
        m3.setId(BigInteger.valueOf(1));
        m3.setMeasureName("克");
        AjaxResult rs3 = mdUnitMeasureController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdUnitMeasureController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdUnitMeasureController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试车间")
    void testWorkShop(){

        MdWorkshop m = new MdWorkshop();
        m.setWorkshopName("1车间");
        m.setWorkshopCode("workshop_1");
        m.setIfEnable(1);
        AjaxResult rs = mdWorkshopController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdWorkshop m2 = new MdWorkshop();
        AjaxResult rs2 = mdWorkshopController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdWorkshop m3 = new MdWorkshop();
        m3.setId(BigInteger.valueOf(1));
        m3.setWorkshopName("2车间");
        AjaxResult rs3 = mdWorkshopController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdWorkshopController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdWorkshopController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试机台")
    void testWorkStation(){

        MdWorkstation m = new MdWorkstation();
        m.setWorkstationName("上引机台");
        m.setWorkstationCode("sy");
        m.setIfEnable(1);
        AjaxResult rs = mdWorkstationController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdWorkstation m2 = new MdWorkstation();
        AjaxResult rs2 = mdWorkstationController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdWorkstation m3 = new MdWorkstation();
        m3.setId(BigInteger.valueOf(1));
        m3.setWorkstationName("上引机台222");
        AjaxResult rs3 = mdWorkstationController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdWorkstationController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdWorkstationController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试班组")
    void testWorkGroup(){

        MdWorkgroup m = new MdWorkgroup();
        m.setWorkgroupName("上引白班");
        m.setWorkgroupCode("sybb");
        m.setIfEnable(1);
        AjaxResult rs = mdWorkgroupController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdWorkgroup m2 = new MdWorkgroup();
        AjaxResult rs2 = mdWorkgroupController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdWorkgroup m3 = new MdWorkgroup();
        m3.setId(BigInteger.valueOf(1));
        m3.setWorkgroupName("上引夜班");
        m3.setWorkgroupCode("syyb");
        AjaxResult rs3 = mdWorkgroupController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdWorkgroupController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdWorkgroupController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试班组成员")
    void testWorkGroupMember(){

        MdWorkgroupMember m = new MdWorkgroupMember();
        m.setWorkgroupName("上引白班");
        m.setWorkgroupCode("sybb");
        m.setMemberId(BigInteger.valueOf(1));
        m.setMemberName("admin");
        AjaxResult rs = mdWorkgroupMemberController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdWorkgroupMember m2 = new MdWorkgroupMember();
        AjaxResult rs2 = mdWorkgroupMemberController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdWorkgroupMember m3 = new MdWorkgroupMember();
        m3.setId(BigInteger.valueOf(1));
        m3.setWorkgroupName("上引夜班");
        m3.setWorkgroupCode("syyb");
        m3.setMemberId(BigInteger.valueOf(2));
        m3.setMemberName("kongqiang");
        AjaxResult rs3 = mdWorkgroupMemberController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdWorkgroupMemberController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdWorkgroupMemberController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试客户管理")
    void testClient(){

        MdClient m = new MdClient();
        m.setClientName("xxx有限公司");
        m.setClientCode("xxx");
        AjaxResult rs = mdClientController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdClient m2 = new MdClient();
        AjaxResult rs2 = mdClientController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdClient m3 = new MdClient();
        m3.setId(BigInteger.valueOf(1));
        m3.setClientName("xxx有限公司123");
        m3.setClientCode("xxx123");
        AjaxResult rs3 = mdClientController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdClientController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdClientController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Test
    @DisplayName("测试供应商管理")
    void testVendor(){

        MdVendor m = new MdVendor();
        m.setVendorName("江西铜业");
        m.setVendorCode("jxty");
        AjaxResult rs = mdVendorController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";
        MdVendor m2 = new MdVendor();
        AjaxResult rs2 = mdVendorController.selectAll(m2);
        System.out.println(rs2);
        assert rs2.get("code").equals(200):"测试列表失败";
        MdVendor m3 = new MdVendor();
        m3.setId(BigInteger.valueOf(1));
        m3.setVendorName("江铜123");
        m3.setVendorCode("jxty123");
        AjaxResult rs3 = mdVendorController.edit(m3);
        System.out.println(rs3);
        assert rs3.get("code").equals(200):"测试修改失败";
        AjaxResult rs4 = mdVendorController.selectAll(m2);
        System.out.println(rs4);
        AjaxResult rs5 = mdVendorController.deleteById(BigInteger.valueOf(1));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

//    @Test
//    @DisplayName("测试产品与工艺路线绑定的消息队列")
//    void testProductRouting() throws InterruptedException {
//        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
//        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error("spring amqp 处理确认结果异常{}",ex);
//            }
//
//            @Override
//            public void onSuccess(CorrelationData.Confirm result) {
//                if(result.isAck()) {
//                    log.info("收到ACK");
//                } else {
//                    log.error("收到NACK");
//                }
//            }
//        });
//        MesProductRouting m = new MesProductRouting();
//        m.setProductId(BigInteger.valueOf(1));
//        m.setRoutingId(BigInteger.valueOf(1));
//        rabbitTemplate.convertAndSend("cuixing.exchange","mes",m);
//
//        Thread.sleep(5000);
//    }
//
//    @Test
//    @DisplayName("测试生产排产与模具绑定的消息队列")
//    public void sender() throws AmqpException {
//        HashMap<String,String> map = new HashMap<>();
//        map.put("mouldId","88");
//        map.put("machineId","25");
//        map.put("machineName","拉拔008");
//        map.put("teamId","25");
//        map.put("teamName","丙班");
//        rabbitTemplate.convertAndSend("cuixing.exchange","mould",map);
//    }
}