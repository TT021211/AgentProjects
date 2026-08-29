package com.cuixing.md;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 主数据模块
 *
 * @author cuixing
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class CuiXingMdApplication
{
    private static final Logger log = LoggerFactory.getLogger(CuiXingMdApplication.class);
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingMdApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  主数据模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        log.info("(♥◠‿◠)ﾉﾞ  主数据模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}