package com.cuixing.sale.entity;

import java.io.Serializable;


/**
 * 科目余额表(ErpBalance)表服务类
 *
 * @author 阿江
 * @since 2025-07-14 20:38:31
 */
public class ErpBalance implements Serializable {
    private static final long serialVersionUID = -59866038786589391L;
    /**
     * 年份
     */
    private Integer fyear;
    /**
     * 期间
     */
    private Integer fperiod;
    /**
     * 账户ID
     */
    private Integer faccountid;
    /**
     * 明细ID
     */
    private Integer fdetailid;
    /**
     * 货币ID
     */
    private Integer fcurrencyid;
    /**
     * 期初余额（本位币）
     */
    private Double fbeginbalancefor;
    /**
     * 借方金额（本位币）
     */
    private Double fdebitfor;
    /**
     * 贷方金额（本位币）
     */
    private Double fcreditfor;
    /**
     * 本年累计借方（本位币）
     */
    private Double fytddebitfor;
    /**
     * 本年累计贷方（本位币）
     */
    private Double fytdcreditfor;
    /**
     * 期末余额（本位币）
     */
    private Double fendbalancefor;
    /**
     * 期初余额
     */
    private Double fbeginbalance;
    /**
     * 借方金额
     */
    private Double fdebit;
    /**
     * 贷方金额
     */
    private Double fcredit;
    /**
     * 本年累计借方
     */
    private Double fytddebit;
    /**
     * 本年累计贷方
     */
    private Double fytdcredit;
    /**
     * 期末余额
     */
    private Double fendbalance;
    /**
     * 框架ID
     */
    private Integer fframeworkid;
    /**
     * 账户名称
     */
    private String accountname;
    /**
     * 货币名称
     */
    private String currencyname;
    /**
     * 供应商或客户名称
     */
    private String name;


    public Integer getFyear() {
        return fyear;
    }

    public void setFyear(Integer fyear) {
        this.fyear = fyear;
    }

    public Integer getFperiod() {
        return fperiod;
    }

    public void setFperiod(Integer fperiod) {
        this.fperiod = fperiod;
    }

    public Integer getFaccountid() {
        return faccountid;
    }

    public void setFaccountid(Integer faccountid) {
        this.faccountid = faccountid;
    }

    public Integer getFdetailid() {
        return fdetailid;
    }

    public void setFdetailid(Integer fdetailid) {
        this.fdetailid = fdetailid;
    }

    public Integer getFcurrencyid() {
        return fcurrencyid;
    }

    public void setFcurrencyid(Integer fcurrencyid) {
        this.fcurrencyid = fcurrencyid;
    }

    public Double getFbeginbalancefor() {
        return fbeginbalancefor;
    }

    public void setFbeginbalancefor(Double fbeginbalancefor) {
        this.fbeginbalancefor = fbeginbalancefor;
    }

    public Double getFdebitfor() {
        return fdebitfor;
    }

    public void setFdebitfor(Double fdebitfor) {
        this.fdebitfor = fdebitfor;
    }

    public Double getFcreditfor() {
        return fcreditfor;
    }

    public void setFcreditfor(Double fcreditfor) {
        this.fcreditfor = fcreditfor;
    }

    public Double getFytddebitfor() {
        return fytddebitfor;
    }

    public void setFytddebitfor(Double fytddebitfor) {
        this.fytddebitfor = fytddebitfor;
    }

    public Double getFytdcreditfor() {
        return fytdcreditfor;
    }

    public void setFytdcreditfor(Double fytdcreditfor) {
        this.fytdcreditfor = fytdcreditfor;
    }

    public Double getFendbalancefor() {
        return fendbalancefor;
    }

    public void setFendbalancefor(Double fendbalancefor) {
        this.fendbalancefor = fendbalancefor;
    }

    public Double getFbeginbalance() {
        return fbeginbalance;
    }

    public void setFbeginbalance(Double fbeginbalance) {
        this.fbeginbalance = fbeginbalance;
    }

    public Double getFdebit() {
        return fdebit;
    }

    public void setFdebit(Double fdebit) {
        this.fdebit = fdebit;
    }

    public Double getFcredit() {
        return fcredit;
    }

    public void setFcredit(Double fcredit) {
        this.fcredit = fcredit;
    }

    public Double getFytddebit() {
        return fytddebit;
    }

    public void setFytddebit(Double fytddebit) {
        this.fytddebit = fytddebit;
    }

    public Double getFytdcredit() {
        return fytdcredit;
    }

    public void setFytdcredit(Double fytdcredit) {
        this.fytdcredit = fytdcredit;
    }

    public Double getFendbalance() {
        return fendbalance;
    }

    public void setFendbalance(Double fendbalance) {
        this.fendbalance = fendbalance;
    }

    public Integer getFframeworkid() {
        return fframeworkid;
    }

    public void setFframeworkid(Integer fframeworkid) {
        this.fframeworkid = fframeworkid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

