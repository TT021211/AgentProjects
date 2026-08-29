package com.agententerprise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

/**
 * 创建独立的MES数据源，避免MES表被JPA实体扫描或误写入。
 * 该数据源只通过JdbcTemplate执行固定的只读SQL。
 */
@Configuration
@EnableConfigurationProperties(MesDataSourceProperties.class)
public class MesDataSourceConfig {

    /**
     * 明确AgentProject自身业务库为主数据源，供JPA和默认JdbcTemplate使用。
     */
    @Bean
    @Primary
    public DataSource agentDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * 创建MES只读数据源。连接在首次查询时建立，关闭功能不会影响AgentProject启动。
     */
    @Bean(name = "mesDataSource")
    public DataSource mesDataSource(MesDataSourceProperties properties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    /**
     * 提供MES专用JdbcTemplate，禁止业务层拼接任意SQL。
     */
    @Bean(name = "mesJdbcTemplate")
    public JdbcTemplate mesJdbcTemplate(@Qualifier("mesDataSource") DataSource mesDataSource) {
        return new JdbcTemplate(mesDataSource);
    }
}
