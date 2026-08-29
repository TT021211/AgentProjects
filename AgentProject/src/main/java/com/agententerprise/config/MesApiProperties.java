package com.agententerprise.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ruishun-cloud MES和WMS HTTP API配置。
 */
@ConfigurationProperties(prefix = "agent.mes.api")
public class MesApiProperties {

    /**
     * 是否启用API接入。启用后优先于数据库直连。
     */
    private boolean enabled;
    /**
     * ruishun-mes服务地址或网关地址。
     */
    private String mesBaseUrl;
    /**
     * ruishun-wms服务地址或网关地址。
     */
    private String wmsBaseUrl;
    /**
     * 内部调用来源请求头，直连服务时通常为inner。
     */
    private String fromSource;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMesBaseUrl() {
        return mesBaseUrl;
    }

    public void setMesBaseUrl(String mesBaseUrl) {
        this.mesBaseUrl = mesBaseUrl;
    }

    public String getWmsBaseUrl() {
        return wmsBaseUrl;
    }

    public void setWmsBaseUrl(String wmsBaseUrl) {
        this.wmsBaseUrl = wmsBaseUrl;
    }

    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }
}
