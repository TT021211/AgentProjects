package com.cuixing.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 * 
 * @author cuixing
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class CuiXingSystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
