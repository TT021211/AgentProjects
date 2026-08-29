package com.cuixing;

import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class CuiXingEqApplication {
    public static void main(String[] args) {
        SpringApplication.run(CuiXingEqApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备服务启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}