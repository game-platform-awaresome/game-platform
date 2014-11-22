package com.future.gameplatform.trade.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "noticerecord", noClassnameStored = true)
public class NoticeCPRecord {
    @Id
    private String id;

    private String orderid;

    private String tradeid;

    private String orderDate;

    private String orderFee;

    private String appid;

    private String rechargeResult;

    private String type;

    private String channel;

    private String noticeResult;

    private int failedTimes;

    private Long lastNoticeTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(String orderFee) {
        this.orderFee = orderFee;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRechargeResult() {
        return rechargeResult;
    }

    public void setRechargeResult(String rechargeResult) {
        this.rechargeResult = rechargeResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNoticeResult() {
        return noticeResult;
    }

    public void setNoticeResult(String noticeResult) {
        this.noticeResult = noticeResult;
    }

    public int getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(int failedTimes) {
        this.failedTimes = failedTimes;
    }

    public Long getLastNoticeTimestamp() {
        return lastNoticeTimestamp;
    }

    public void setLastNoticeTimestamp(Long lastNoticeTimestamp) {
        this.lastNoticeTimestamp = lastNoticeTimestamp;
    }
}
