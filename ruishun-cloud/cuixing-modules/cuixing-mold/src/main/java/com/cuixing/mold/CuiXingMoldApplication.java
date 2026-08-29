package com.cuixing.mold;

import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 模具管理模块
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CuiXingMoldApplication {
    private static final Logger log = LoggerFactory.getLogger(CuiXingMoldApplication.class);
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingMoldApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  模具模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        log.info("(♥◠‿◠)ﾉﾞ  模具模块启动成功!!!    ლ(´ڡ`ლ)ﾞ  ");
    }
}
