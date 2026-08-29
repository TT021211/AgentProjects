//package com.cuixing.mes;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//import com.cuixing.mes.entity.MesInputLog;
//import com.cuixing.mes.entity.MesOuputLog;
//import com.cuixing.mes.entity.MesWiredrawingWorkorder;
//import com.cuixing.mes.mapper.MesInputLogMapper;
//import com.cuixing.mes.service.IMesInputLogService;
//import com.cuixing.mes.service.IMesOuputLogService;
//import com.cuixing.mes.service.IMesWiredrawingWorkorderService;
//import com.cuixing.system.api.domain.md.MdProduct;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@SpringBootTest
//public class BatchProcessTest {
//    @Autowired
//    private IMesWiredrawingWorkorderService mesWiredrawingWorkorderService;
//    @Autowired
//    private MesInputLogMapper mesInputLogMapper;
//    @Autowired
//    private IMesOuputLogService workReportDao;
//    @Autowired
//    private RedisTemplate<String, Object> redisClient;
//    private Random random = new Random();
//
//    /**
//     * 批量处理拉丝工单的投料和报工
//     */
//    @Test
//    public void testBatchProcess() {
//        // 获取所有拉丝工单
//        List<MesWiredrawingWorkorder> workOrders = mesWiredrawingWorkorderService.selectMesWiredrawingWorkorderList(null);
//        if (workOrders.isEmpty()) {
//            System.out.println("没有可处理的拉丝工单");
//            return;
//        }
//
//        // 遍历工单
//        for (MesWiredrawingWorkorder workOrder : workOrders) {
//            processWorkOrder(workOrder);
//            updateWorkOrderCompletion(workOrder);
//        }
//    }
//
//    /**
//     * 处理单个工单的投料和报工
//     * @param workOrder 工单对象
//     */
//    private void processWorkOrder(MesWiredrawingWorkorder workOrder) {
//        BigInteger workOrderId = workOrder.getWorkorderId();
//        double workOrderWeight = workOrder.getWeight();
//        double completedWeight = workOrder.getWightCompletion();
//        BigInteger productId = workOrder.getProductId();
//        String equipmentCodes = workOrder.getEquipmentCode();
//        Date startTime = workOrder.getStartTime();
//        Date endTime = workOrder.getEndTime();
//
//        // Redis 键前缀
//        String inputRedisKeyPrefix = "input:workorder:" + workOrderId + ":";
//        String reportRedisKeyPrefix = "report:workorder:" + workOrderId + ":";
//
//        // 获取所有机台
//        List<String> equipmentCodeList = Arrays.asList(equipmentCodes.split(","));
//
//        // 投料处理：每个机台投料一次
//        processInput(workOrder, inputRedisKeyPrefix, startTime, equipmentCodeList);
//
//        // 报工处理：每个机台至少报工一次
//        double totalReportedWeight = processWorkReport(workOrder, reportRedisKeyPrefix, productId, startTime, endTime, equipmentCodeList);
//
//        // 如果报工量不足，随机选择机台继续报工
//        while (totalReportedWeight + completedWeight < workOrderWeight && !equipmentCodeList.isEmpty()) {
//            String randomEquipment = equipmentCodeList.get(random.nextInt(equipmentCodeList.size()));
//            totalReportedWeight += reportForEquipment(workOrder, reportRedisKeyPrefix + randomEquipment, productId,
//                    startTime, endTime, randomEquipment);
//        }
//
//        // 更新工单完成量
//        workOrder.setWightCompletion(completedWeight + totalReportedWeight);
//    }
//
//    /**
//     * 处理投料逻辑：每个机台投料一次
//     * @param workOrder 工单
//     * @param redisKeyPrefix Redis 键前缀
//     * @param startTime 工单开始时间
//     * @param equipmentCodeList 机台编号列表
//     */
//    private void processInput(MesWiredrawingWorkorder workOrder, String redisKeyPrefix, Date startTime,
//                              List<String> equipmentCodeList) {
//        LocalDateTime inputTime = convertToLocalDateTime(startTime);
//
//        for (String equipmentCode : equipmentCodeList) {
//            String redisKey = redisKeyPrefix + equipmentCode;
//            if (redisClient.hasKey(redisKey)) {
//                System.out.println("工单 " + workOrder.getWorkorderId() + " 机台 " + equipmentCode + " 已投料，跳过");
//                continue;
//            }
//
//            // 查询投料记录
//            MesOuputLog mesOuputLog = mesInputLogMapper.findByWorkOrderIdAndEquipmentAndTimeRange(startTime).stream().findFirst().orElse(null);
//            MesInputLog selectedInput=new MesInputLog();
//            if (selectedInput == null) {
//                selectedInput = createNewInputRecord(workOrder, startTime, equipmentCode);
//            }
//
//            selectedInput.setCreateTime(convertToDate(inputTime.plusHours(30)));
//            mesInputLogMapper.insert(selectedInput);
//            redisClient.opsForValue().set(redisKey, selectedInput.getInputSn());
//            System.out.println("工单 " + workOrder.getWorkorderId() + " 机台 " + equipmentCode + " 投料 " + selectedInput.getInputWeight() + "kg");
//        }
//    }
//
//    /**
//     * 创建新的投料记录
//     */
//    private MesInputLog createNewInputRecord(MesWiredrawingWorkorder workOrder, Date startTime, String equipmentCode) {
//        MesInputLog input = new MesInputLog();
//        MdProduct mdProduct = mesInputLogMapper.selectMdProduct(workOrder.getProductId());
//        //MdProduct mdProduct = mesInputLogMapper.selectMdItem(workOrder.getProductId());
//        input.setInputName(mdProduct.getProductName());
//        input.setInputCode(mdProduct.getProductCode());
//        input.setInputId(mdProduct.getId());
//        input.setInputType("产品");
//        input.setInputSn("ITEM_" + System.currentTimeMillis());
//        input.setWorkstageId(BigInteger.valueOf(13));
//        input.setWorkstageName("中拉");
//        input.setWorkstationId(equipmentCode);
//        input.setWorkstationCode(equipmentCode);
//        input.setWorkorderId(workOrder.getWorkorderId());
//        input.setWorkorderSn(workOrder.getWorkorderCode());
//        input.setTaskId(workOrder.getWorkorderId());
//        input.setTaskSn(workOrder.getWorkorderCode() + "BS" + System.currentTimeMillis() % 1000);
//        input.setTaskType("4");
//        input.setProductId(workOrder.getProductId());
//        input.setProductName(workOrder.getProductName());
//        input.setProductCode(workOrder.getProductCode());
//        input.setProductSpecification(workOrder.getSpecification());
//        input.setInputWeight(BigDecimal.valueOf(300.0));
//        input.setCreateBy("lsg01");
//        input.setCreateTime(startTime);
//        input.setBatchCode("20250220ph");
//        input.setPartNumber("20250220lh");
//        input.setMeasureName("kg");
//        input.setItemSpecification(workOrder.getSpecification());
//        input.setProductModel("DXBS");
//        input.setOrderType("0");
//        return input;
//    }
//
//    /**
//     * 处理报工逻辑：每个机台至少报工一次
//     */
//    private double processWorkReport(MesWiredrawingWorkorder workOrder, String redisKeyPrefix, BigInteger productId,
//                                     Date startTime, Date endTime, List<String> equipmentCodeList) {
//        double totalReportedWeight = 0;
//        LocalDateTime reportTime = convertToLocalDateTime(startTime);
//
//        for (String equipmentCode : equipmentCodeList) {
//            totalReportedWeight += reportForEquipment(workOrder, redisKeyPrefix + equipmentCode, productId,
//                    startTime, endTime, equipmentCode);
//            reportTime = reportTime.plusHours(6);
//        }
//
//        return totalReportedWeight;
//    }
//
//    /**
//     * 为单个机台报工
//     */
//    private double reportForEquipment(MesWiredrawingWorkorder workOrder, String redisKey, BigInteger productId,
//                                      Date startTime, Date endTime, String equipmentCode) {
//        if (redisClient.hasKey(redisKey)) {
//            System.out.println("工单 " + workOrder.getWorkorderId() + " 机台 " + equipmentCode + " 已报工，跳过");
//            return 0;
//        }
//
//        MdProduct product = mesWiredrawingWorkorderService.findProductById(productId);
//        if (product == null) {
//            System.out.println("产品ID " + productId + " 未找到");
//            return 0;
//        }
//
//        // 假设的称重记录查询
//        List<WeighRecord> weighRecords = weighRecordDao.findByProductCodeAndTimeRange(
//                product.getProductCode(), startTime, endTime
//        );
//
//        double reportWeight;
//        WeighRecord selectedWeigh = null;
//
//        Set<Object> usedWeighIds = redisClient.opsForSet().members(redisKey);
//        if (!weighRecords.isEmpty()) {
//            for (WeighRecord weigh : weighRecords) {
//                if (!usedWeighIds.contains(weigh.getId())) {
//                    selectedWeigh = weigh;
//                    break;
//                }
//            }
//        }
//
//        if (selectedWeigh != null) {
//            reportWeight = selectedWeigh.getNetWeight();
//            redisClient.opsForSet().add(redisKey, selectedWeigh.getId());
//        } else {
//            reportWeight = 40 + random.nextDouble() * 10;
//        }
//
//        MesOuputLog report = createWorkReport(workOrder, product, reportWeight, convertToLocalDateTime(startTime).plusHours(6), equipmentCode);
//        workReportDao.save(report);
//        redisClient.opsForValue().set(redisKey, report.getOutputSn());
//        System.out.println("工单 " + workOrder.getWorkorderId() + " 机台 " + equipmentCode + " 报工 " + reportWeight + "kg");
//
//        return reportWeight;
//    }
//
//    /**
//     * 创建报工记录
//     */
//    private MesOuputLog createWorkReport(MesWiredrawingWorkorder workOrder, MdProduct product, double reportWeight,
//                                         LocalDateTime reportTime, String equipmentCode) {
//        MesOuputLog report = new MesOuputLog();
//        report.setOutputName(product.getProductName());
//        report.setOutputCode(product.getProductCode());
//        report.setOutputType("产品");
//        report.setOutputSn("ITEM_" + System.currentTimeMillis());
//        report.setWorkstageId("15");
//        report.setWorkstageName("微拉");
//        report.setWorkstationId(equipmentCode);
//        report.setWorkstationCode(equipmentCode);
//        report.setWorkorderId(workOrder.getWorkorderId());
//        report.setWorkorderSn(workOrder.getWorkorderCode());
//        report.setTaskId(workOrder.getWorkorderId());
//        report.setTaskSn(workOrder.getWorkorderCode());
//        report.setProductId(product.getId());
//        report.setProductName(product.getProductName());
//        report.setProductCode(product.getProductCode());
//        report.setProductSpecification(product.getSpecification());
//        report.setOutputWeight(reportWeight);
//        report.setCreateBy("zlg01");
//        report.setCreateTime(convertToDate(reportTime));
//        report.setBatchCode("20250220ph");
//        report.setPartNumber("20250220lh");
//        report.setMeasureName("kg");
//        report.setItemSpecification(product.getSpecification());
//        report.setTaskType("3");
//        report.setOrderType("0");
//        report.setProductSizeInternational(product.getProductSizeInternational());
//        return report;
//    }
//
//    /**
//     * 更新工单完成量和状态
//     */
//    private void updateWorkOrderCompletion(MesWiredrawingWorkorder workOrder) {
//        double completedWeight = workOrder.getWightCompletion();
//        double totalWeight = workOrder.getWeight();
//        if (completedWeight >= totalWeight) {
//            workOrder.setStatus("1"); // 完成
//        } else {
//            workOrder.setStatus("0"); // 未完成
//        }
//        mesWiredrawingWorkorderService.updateMesWiredrawingWorkorder(workOrder);
//        System.out.println("工单 " + workOrder.getWorkorderId() + " 完成量更新为 " + completedWeight + "kg，状态为 " + workOrder.getStatus());
//    }
//
//    /**
//     * Date 转 LocalDateTime
//     */
//    private LocalDateTime convertToLocalDateTime(Date date) {
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//    }
//
//    /**
//     * LocalDateTime 转 Date
//     */
//    private Date convertToDate(LocalDateTime dateTime) {
//        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
//    }
//
//}