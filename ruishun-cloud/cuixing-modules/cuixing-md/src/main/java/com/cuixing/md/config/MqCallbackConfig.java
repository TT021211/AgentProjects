//package com.cuixing.md.config;
//
//import com.cuixing.common.rabbitmq.config.RabbitMqConfig;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//
//@Configuration
//@RequiredArgsConstructor
//public class MqCallbackConfig {
//
//    private static final Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);
//
//    private final RabbitTemplate rabbitTemplate;
//
//    @PostConstruct
//    public void init() {
//        rabbitTemplate.setReturnsCallback(returned -> {
//            log.error("接收到rabbitmq的回调消息");
//            log.info("交换机：{}",returned.getExchange());
//            log.info("消息内容：{}",returned.getMessage());
//            log.info("路由key：{}",returned.getRoutingKey());
//            log.info("replyCode：{}",returned.getReplyCode());
//            log.info("replyText：{}",returned.getReplyText());
//        });
//    }
//
//}
