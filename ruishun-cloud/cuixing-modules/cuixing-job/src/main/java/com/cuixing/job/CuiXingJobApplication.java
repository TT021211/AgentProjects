package com.cuixing.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cuixing.common.security.annotation.EnableCustomConfig;
import com.cuixing.common.security.annotation.EnableRyFeignClients;
import com.cuixing.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author cuixing
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients   
@SpringBootApplication
public class CuiXingJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CuiXingJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
