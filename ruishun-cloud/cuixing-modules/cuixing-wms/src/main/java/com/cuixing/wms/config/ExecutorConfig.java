package com.cuixing.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(
                10, // 核心线程数
                50, // 最大线程数
                60L, TimeUnit.SECONDS, // 空闲线程存活时间
                new LinkedBlockingQueue<>(100), // 任务队列容量
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：当线程池满时，在调用者线程执行
        );
    }
}