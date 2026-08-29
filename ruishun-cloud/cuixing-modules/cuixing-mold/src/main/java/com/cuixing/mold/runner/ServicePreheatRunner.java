package com.cuixing.mold.runner;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteMesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ServicePreheatRunner implements CommandLineRunner {

    private final RemoteMdService remoteMdService;
    private final RemoteMesService remoteMesService;

    public ServicePreheatRunner(RemoteMdService remoteMdService, RemoteMesService remoteMesService) {
        this.remoteMdService = remoteMdService;
        this.remoteMesService = remoteMesService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 异步预热
        new Thread(() -> {
            try {
                System.out.println("预热开始...");
                long l = System.currentTimeMillis();
                this.remoteMdService.getWorkbenchWiredrawingById(BigInteger.ONE, SecurityConstants.INNER);
                this.remoteMesService.getSpecification("defaultWorkstationCode", SecurityConstants.INNER);
                System.out.println("预热结束，耗时：" + (System.currentTimeMillis() - l) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
