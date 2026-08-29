package com.cuixing.system.domain;


public class SysAppVersion {

    private Integer id;

    private String appVersion;

    private String downloadUrl;

    private String renewalContent;

    public String getRenewalContent() {
        return renewalContent;
    }

    public void setRenewalContent(String renewalContent) {
        this.renewalContent = renewalContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "SysAppVersion{" +
                "id=" + id +
                ", appVersion='" + appVersion + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
