package com.cuixing.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信登录
 * @author zhan
 * @create 2023/12/13 16:40
 */
@Component
@ConfigurationProperties(prefix = "wxlogin")
public class  WechatLoginConfig {
    private  String access;

    private  String codeurl;

    private  String getuserurl;


    private  String appid;

    private  String secretkey;
    /**
     * 企业微信扫码登录的url
     */
    private  String loginUrl;

    public String getAccessUrl() {
        return access + "?corpid=" + appid + "&corpsecret=" + secretkey;
    }

    public String getCodeUrl(String access_token, String code) {
        return codeurl + "?access_token=" + access_token + "&code=" + code;
    }
    public String getUserData(String access_token,String userId)
    {
        return getuserurl+"?access_token="+access_token+"&userid="+userId;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getCodeurl() {
        return codeurl;
    }

    public void setCodeurl(String codeurl) {
        this.codeurl = codeurl;
    }

    public String getGetuserurl() {
        return getuserurl;
    }

    public void setGetuserurl(String getuserurl) {
        this.getuserurl = getuserurl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
