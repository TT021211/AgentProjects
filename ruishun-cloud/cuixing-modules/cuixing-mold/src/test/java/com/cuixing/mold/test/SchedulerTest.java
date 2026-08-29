package com.cuixing.mold.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerTest {

    private Scheduler scheduler;

    @BeforeEach
    public void setup() throws SchedulerException {
        // 初始化 Scheduler
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
    }

    @Test
    public void stopScheduler() {
        try {
            // 停止 Scheduler
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
