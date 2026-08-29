package com.cuixing.mes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    @Bean(name = "outputLogExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 根据 CPU 核心数和任务负载调整
        int cpuCores = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(cpuCores * 2); // 核心线程数：CPU核心数 * 2，例如 16（假设 8 核 CPU）
        executor.setMaxPoolSize(cpuCores * 4); // 最大线程数：CPU核心数 * 4，例如 32
        executor.setQueueCapacity(5000); // 增加队列容量，适应更大任务量
        executor.setThreadNamePrefix("OutputLog-"); // 线程名前缀，便于调试
        executor.setWaitForTasksToCompleteOnShutdown(true); // 优雅关闭
        // 设置拒绝策略：调用者线程执行任务（避免直接抛出 RejectedExecutionException）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}