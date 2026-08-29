package com.cuixing.wms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@ConfigurationProperties(prefix = "pengyue-mes")
@Component
/* loaded from: RuoYiConfig.class */
public class RuoYiConfig {
    private String name;
    private String version;
    private String copyrightYear;
    private boolean demoEnabled;
    private static String profile;
    private static boolean addressEnabled;
    private static String captchaType;
    private static String snQrcodeUrl;
    private static String snServerUrl;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return this.copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return this.demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile2) {
        profile = profile2;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled2) {
        addressEnabled = addressEnabled2;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType2) {
        captchaType = captchaType2;
    }

    public static String getImportPath() {
        return getProfile() + "/import";
    }

    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    public static String getSnQrcodeUrl() {
        return snQrcodeUrl;
    }

    public void setSnQrcodeUrl(String snQrcodeUrl2) {
        snQrcodeUrl = snQrcodeUrl2;
    }

    public static String getSnServerUrl() {
        return snServerUrl;
    }

    public void setSnServerUrl(String snServerUrl2) {
        snServerUrl = snServerUrl2;
    }
}
