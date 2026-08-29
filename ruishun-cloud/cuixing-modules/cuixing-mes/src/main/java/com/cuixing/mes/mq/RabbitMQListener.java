//package com.cuixing.mes.mq;
//
//import com.cuixing.mes.controller.MesProductRoutingController;
//import com.cuixing.mes.service.IMesProductRoutingService;
//import com.cuixing.system.api.domain.mes.MesProductRouting;
//import com.cuixing.system.api.factory.RemoteUserFallbackFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.math.BigInteger;
//
//@Component
//public class RabbitMQListener {
//
//    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);
//
//    @Autowired
//    IMesProductRoutingService mesProductRoutingService;
//
//    @RabbitListener(bindings = @QueueBinding(
//        value=@Queue(name="${spring.rabbitmq.queue.product_routing_name}"),
//        exchange = @Exchange(name="${spring.rabbitmq.exchange.name}", type = "direct"),
//        key = "${spring.rabbitmq.routing.key}",
//            arguments = @Argument(name="x-queue-mode",value = "lazy")
//    ))
//    public void productRoutingBind(MesProductRouting mesProductRouting) throws InterruptedException {
//        log.info("receive mq msg: {}", mesProductRouting);
//        // 自动设为默认工序
//        mesProductRouting.setIsDefault(1);
//        mesProductRoutingService.insert(mesProductRouting);
//    }
//}