package com.cuixing.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author cuixing
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CuiXingGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
