package com.cuixing.auth.form;

/**
 * @author zhan
 * @create 2023/12/13 17:20
 */
public class EnterpriseWechatIdBody {
    private Integer errcode;
    private String errmsg;
    private String userid;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}