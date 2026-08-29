package com.cuixing.mold.scheduler;

import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.PostConstruct;

/**
 *
 * 通过Spring的依赖注入和管理机制，定制Quartz的JobFactory和SchedulerFactoryBean，以适应Spring应用的需求。
 */
@Configuration
public class QuartzConfig implements DisposableBean {

    /**
     * 配置Spring管理的JobFactory，用于创建Quartz任务实例。
     * 通过Spring的依赖注入，自动装配任务实例的依赖。
     *
     * @param applicationContext Spring应用上下文。
     * @return 自定义的JobFactory实例。
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * 配置Quartz的SchedulerFactoryBean，用于创建和管理Quartz调度器。
     * 设置自定义的JobFactory，以利用Spring的依赖注入功能。
     *
     * @param jobFactory Quartz任务工厂。
     * @return 配置好的SchedulerFactoryBean实例。
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        return factory;
    }

    /**
     * 自定义的JobFactory，继承自SpringBeanJobFactory，实现ApplicationContextAware接口。
     * 通过自动装配BeanFactory，实现对任务实例的依赖注入。
     */
    private static final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        /**
         * 设置应用上下文，提供BeanFactory用于自动装配任务实例。
         *
         * @param applicationContext Spring应用上下文。
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) {
            beanFactory = applicationContext.getAutowireCapableBeanFactory();
        }

        /**
         * 创建任务实例，并对其进行依赖注入。
         *
         * @param bundle 触发器触发时的绑定信息。
         * @return 经过依赖注入的任务实例。
         * @throws Exception 如果创建或注入过程中出现异常。
         */
        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

    /**
     * 初始化调度器。由于在SchedulerFactoryBean的配置中已设置自动启动，这里不再需要显示启动调度器。
     *
     * @throws SchedulerException 如果启动调度器过程中出现异常。
     */
    @PostConstruct
    public void startScheduler() throws SchedulerException {
        // Scheduler 的启动已经在 schedulerFactoryBean 中进行了配置，这里可以省略
    }

    /**
     * 配置SchedulerFactoryBean，定制调度器的属性和行为。
     * 如设置调度器名称、是否覆盖现有任务、启动延迟、自动启动等。
     *
     * @return 配置好的SchedulerFactoryBean实例。
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("MyBusinessJob"); // 设置 Scheduler 的名称
        factory.setOverwriteExistingJobs(true);
        factory.setStartupDelay(1);
        factory.setAutoStartup(true);
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setTaskExecutor(taskExecutor());
        factory.setWaitForJobsToCompleteOnShutdown(false);
        return factory;
    }

    /**
     * 配置线程池任务执行器，用于执行Quartz任务。
     * 设置核心线程数、最大线程数、队列容量和线程名称前缀。
     *
     * @return 配置好的ThreadPoolTaskExecutor实例。
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("QuartzJob-");
        executor.initialize();
        return executor;
    }

    /**
     * 实现DisposableBean接口的destroy方法，用于在应用停止时关闭Quartz调度器。
     *
     * @throws Exception 如果关闭调度器过程中出现异常。
     */
    @Override
    public void destroy() throws Exception {
        // 在应用关闭时关闭 Scheduler
        schedulerFactoryBean().getObject().shutdown();
    }

}
