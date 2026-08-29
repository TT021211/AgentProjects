package com.cuixing.qc;

import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 质量模块
 *
 * @author cuixing
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CuiXingQcApplication
{
    private static final Logger log = LoggerFactory.getLogger(CuiXingQcApplication.class);
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingQcApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  质量检测模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        log.info("(♥◠‿◠)ﾉﾞ  质量检测模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
