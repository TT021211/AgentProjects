package com.agententerprise.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MES只读数据源配置，对应本地rsmes_cloud数据库。
 * 密码只从环境变量读取，不写入代码或提交到仓库。
 */
@ConfigurationProperties(prefix = "agent.mes.datasource")
public class MesDataSourceProperties {

    /**
     * MES数据库连接地址。
     */
    private String url;
    /**
     * MES数据库用户名。
     */
    private String username;
    /**
     * MES数据库密码。
     */
    private String password;
    /**
     * JDBC驱动类名。
     */
    private String driverClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
