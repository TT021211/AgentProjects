package com.cuixing.ecm;

import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableScheduling
public class CuixingEcmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuixingEcmApplication.class, args);
    }

}
