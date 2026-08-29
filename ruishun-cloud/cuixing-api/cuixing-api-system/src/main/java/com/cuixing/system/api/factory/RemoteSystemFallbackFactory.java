package com.cuixing.system.api.factory;


import com.cuixing.system.api.RemoteSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class RemoteSystemFallbackFactory implements FallbackFactory<RemoteSystemService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteSystemFallbackFactory.class);
    @Override
    public RemoteSystemService create(Throwable throwable) {
        log.error("系统服务调用失败:{}", throwable.getMessage());

        return new RemoteSystemService() {
            @Override
            public String getAutoCode(String ruleCode, String inputCharacter) {
                return  String.format("ruleCode:%s和inputCharacter:%s系统服务调用失败:%s", ruleCode, inputCharacter, throwable.getMessage());
            }
        };
    }
}
