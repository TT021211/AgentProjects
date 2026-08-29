package com.cuixing.mes;

import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 生产管理模块
 *
 * @author cuixing
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableCaching
@EnableAsync
public class CuiXingMesApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingMesApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  生产管理模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
