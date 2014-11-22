package com.future.gameplatform.recharge.common.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
@Entity(value = "smsrecharge", noClassnameStored = true)
public class SmsRecharge {

    @Id
    String id;
    //应用号
    String appid;
    @Indexed
    String shortcode;
    @Indexed
    String orderno;
    @Indexed
    String channel;

    String operator;

    String mobile;

    String smscode;

    String smsChannel;

    String fee;

    int status;

    int state;

    Date createdDate;

    Date channelNoticeDate;

    Date noticeCpDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public String getSmsChannel() {
        return smsChannel;
    }

    public void setSmsChannel(String smsChannel) {
        this.smsChannel = smsChannel;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getChannelNoticeDate() {
        return channelNoticeDate;
    }

    public void setChannelNoticeDate(Date channelNoticeDate) {
        this.channelNoticeDate = channelNoticeDate;
    }

    public Date getNoticeCpDate() {
        return noticeCpDate;
    }

    public void setNoticeCpDate(Date noticeCpDate) {
        this.noticeCpDate = noticeCpDate;
    }
}
