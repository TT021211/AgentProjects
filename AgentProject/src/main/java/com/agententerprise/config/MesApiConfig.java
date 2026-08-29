package com.agententerprise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 启用 ruishun-cloud API 配置绑定。
 */
@Configuration
@EnableConfigurationProperties(MesApiProperties.class)
public class MesApiConfig {
}
