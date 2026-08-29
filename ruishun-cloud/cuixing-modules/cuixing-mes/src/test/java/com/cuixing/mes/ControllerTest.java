package com.cuixing.mes;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.mes.controller.*;
import com.cuixing.mes.entity.*;
import com.cuixing.mes.service.*;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.domain.mes.MesProductBom;
import com.cuixing.system.api.domain.mes.MesProductRouting;
import com.cuixing.system.api.domain.mes.MesProductWorkorder;
import com.cuixing.system.api.domain.mes.dto.MesProcessTaskDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdProduct;

import java.math.BigDecimal;
import java.util.List;
import java.math.BigInteger;

@SpringBootTest
public class ControllerTest {
    @Autowired
    MesWorkstageController mesWorkstageController;

    @Autowired
    IMesWorkstageService iMesWorkstageService;

    @Autowired
    IMesProcessRoutingService iMesProcessRoutingService;

    @Autowired
    IMesProductRoutingService iMesProductRoutingService;

    @Autowired
    IMesRoutingWorkstageService iMesRoutingWorkstageService;

    @Autowired
    RemoteMdService remoteMdService;

    @Autowired
    MesProductRoutingController mesProductRoutingController;

    @Autowired
    MesProductBomController mesProductBomController;

    @Autowired
    IMesProductWorkorderService mesProductWorkorderService;

    @Test
    @DisplayName("批量报工")
    void testBatchProcess() {
        // 第一步，获取所有报工工单信息，比如获取的是拉丝的所有工单，则跑拉丝的投料与报工，完整解释:拉丝工单的工单主要信息有，工单重量、工单完成量，机台编号(存在多台设备，RS-LS-012,RS-LS-022,RS-LS-013)，已完成重量、产品规格、产品id，然后主要插入一下表
        /**
         * id	output_id	output_name	output_code	output_type	output_sn	workstage_id	workstage_code	workstage_name	workstation_id	workstation_name	workstation_code	order_id	order_sn	input_id	input_sn	order_detail_id	client_id	client_name	workorder_id	workorder_sn	workorder_entry_id	task_id	task_sn	product_id	product_name	product_code	product_specification	output_num	output_weight	workgroup_id	workgroup_name	workgroup_code	remark	version	is_deleted	create_by	create_time	update_by	update_time	attr1	attr2	attr3	task_type	batch_code	part_number	measure_name	item_specification	product_model	job_number	input_wire_type	order_type	alex_position_number	alex_lot	alex_lot_number	reel_wight	qb_workstation_code	wire_disc	qb_print_value	workgroup_value	standard_name	standard_value	product_size_international	output_gross_weight	output_tare_weight	ingredients_name	manufacturer_name	weight_time	alex_number	wms_status	wire_disc_id	shift_schedule_id	shift_schedule_name	shift_schedule_weight
         * 27	272	镀锡线-0.127	PROD_LIST_0272	产品	ITEM_202412080616029339	14		小拉	233		RS-JX-020			27	ITEM_202412080616029339													0	50.000					0	0	zlg01	2024-12-08 06:16:13						1	20250220ph	20250220lh	kg	0.127			1																			0
         */
        //细节：1.报工需要先进行投料，获取投料信息，然后进行报工（投料也是根据工单安排的机台进行投料在合计）
        //获取投料信息：根据工单的时间，来获取报工记录表内的报工记录信息，还需要一些条件进行查询，获取到多条记录后，随机选择离工单最近时间的记录，然后进行投料，用redis存储已经投料的数据，等再去投料的时候判断是否已经投料了，如果已经投料了，则不再进行投料，投料还需要注意投料的重量不能超过工单的重量，不够就继续投料，直到投料重量达到工单重量就停止投料，投料量可以超过工单的量
        //细节：2.每一条投料的时间间隔需到达30小时，这个我们修改createTime就可以了
        //细节：3.现在投料投好了，我们开始报工，根据工单安排的机台号，去进行报工，报工的时间间隔6小时，报工现在需要先去查询称重记录表，根据工单里面的产品id查询产品信息，在拿到产品信息结合工单时间范围查询这个范围的称重记录的数据，报工的重量去这个称重记录的重量，然后取过的存redis，再次获取的时候判断是否取过，取过则下一条，没有的话就默认报工40-50kg，随机重量报工。
        //细节：4.报工完成后，需要去更新工单的完成量
        //细节：5.报工完成之后，还需要把这个报工记录插入到仓储的库存管理表内（这个我自己手动来加）
        //所有的操作都基于批量获取到了工单信息之后，批量进行操作
    }


    @Test
    @DisplayName("测试工序")
    void testWorkstage(){
//        MesWorkstage m = new MesWorkstage();
//        m.setWorkstageName("上引");
//        m.setWorkstageCode("SY");
//        BigInteger insertId = iMesWorkstageService.insert(m);
//        System.out.println(insertId);
//        assert insertId.signum()==1:"测试新增失败";
        System.out.println(mesWorkstageController.getTree().getRows());
//        MesWorkstage m2 = new MesWorkstage();
//        AjaxResult rs2 = mesWorkstageController.selectAll(m2);
//        System.out.println(rs2);
//        assert rs2.get("code").equals(200):"测试列表失败";
//        MesWorkstage m3 = new MesWorkstage();
//        m3.setId(insertId);
//        m3.setWorkstageName("拉拔");
//        m3.setWorkstageCode("LB");
//        AjaxResult rs3 = mesWorkstageController.edit(m3);
//        System.out.println(rs3);
//        assert rs3.get("code").equals(200):"测试修改失败";
//        AjaxResult rs4 = mesWorkstageController.selectAll(m2);
//        System.out.println(rs4);
//        AjaxResult rs5 = mesWorkstageController.deleteById(insertId);
//        System.out.println(rs5);
//        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Autowired
    MesProcessRoutingController mesProcessRoutingController;

    @Test
    @DisplayName("测试工艺路线")
    void testProcessRouting(){
//        MesProcessRouting m = new MesProcessRouting();
//        m.setRoutingName("拉制");
//        m.setRoutingCode("lazhi");
//        BigInteger insertId = iMesProcessRoutingService.insert(m);
//        System.out.println(insertId);
//        assert insertId.signum()==1:"测试新增失败";
//        MesProcessRouting m2 = new MesProcessRouting();
//        AjaxResult rs2 = mesProcessRoutingController.selectAll(m2);
//        System.out.println(rs2);
//        assert rs2.get("code").equals(200):"测试列表失败";
//        MesProcessRouting m3 = new MesProcessRouting();
//        m3.setId(insertId);
//        m3.setRoutingName("挤制");
//        m3.setRoutingCode("jizhi");
//        AjaxResult rs3 = mesProcessRoutingController.edit(m3);
//        System.out.println(rs3);
//        assert rs3.get("code").equals(200):"测试修改失败";
//        AjaxResult rs4 = mesProcessRoutingController.selectAll(m2);
//        System.out.println(rs4);
        AjaxResult rs5 = mesProcessRoutingController.deleteById(BigInteger.valueOf(2));
        System.out.println(rs5);
        assert rs5.get("code").equals(200):"测试删除失败";
    }

    @Autowired
    MesRoutingWorkstageController mesRoutingWorkstageController;

    @Test
    @DisplayName("测试工艺路线工序关联表")
    void testRoutingWorkstage(){
        MesRoutingWorkstage m = new MesRoutingWorkstage();
        m.setRoutingId(BigInteger.valueOf(1));
        m.setWorkstageId(BigInteger.valueOf(2));
        m.setOrderNum(1);
        AjaxResult rs = mesRoutingWorkstageController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200):"测试新增失败";


    }

    @Test
    @DisplayName("测试产品关联工艺路线")
    void testProductRouting() {
        // 取id=1的产品，关联工艺路线id=1的路线"挤制"
        BigInteger productId = BigInteger.valueOf(1);
        BigInteger routingId = BigInteger.valueOf(1);

        R<MdProduct> productInfoRs = remoteMdService.getProduct(productId, SecurityConstants.INNER);
        System.out.println(productInfoRs.getMsg());
        MdProduct productInfo = productInfoRs.getData();
        System.out.println(productInfo);

        MesProcessRouting mesProcessRouting = iMesProcessRoutingService.queryById(routingId);

        MesProductRouting m = new MesProductRouting();
        m.setRoutingId(mesProcessRouting.getId());
        m.setRoutingName(mesProcessRouting.getRoutingName());
        m.setProductId(productInfo.getId());
        m.setProductName(productInfo.getProductName());
        m.setProductCode(productInfo.getProductCode());
        // 设置为默认工艺路线
        m.setIsDefault(1);

        AjaxResult rs = mesProductRoutingController.add(m);
        System.out.println(rs);
        assert rs.get("code").equals(200) : "测试新增失败";
//
//        MesProductRouting m2 = new MesProductRouting();
//        AjaxResult rs3 = mesProductRoutingController.selectAll(m2);
//        System.out.println(rs3);
//        assert rs3.get("code").equals(200) : "测试列表失败";

        MesProductRouting m3 = new MesProductRouting();
        m3.setId(BigInteger.valueOf(3));
        m3.setIsDefault(1);
        AjaxResult rs4 = mesProductRoutingController.edit(m3);
        System.out.println(rs4);
        assert rs4.get("code").equals(200):"测试修改失败";
    }

}
