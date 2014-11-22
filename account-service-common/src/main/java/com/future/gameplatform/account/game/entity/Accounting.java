package com.future.gameplatform.account.game.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "accounting", noClassnameStored = true)
public class Accounting {
    @Id
    String id;
    @Indexed
    String uid;
    //大厅与游戏都单独申请appid
    String appid;
    //支付时为支付途径
    String payway;
    //在trade时有效，记录trade的appid，此时appid中记录的为大厅的appid
    String origappid;
    //原单号，充值流水号或者是交易流水号
    String origid;
    //原单类型，充值有0正常1冲正，交易有0正常交易，1冲正交易
    String origtype;
    //原单来源，充值为充值渠道，交易为交易的对方app  ,全部通过appid来区分
    //String origsource;
    //类型，0充值，1交易
    String type;
    //金额，大厅金币数
    long amount;
    //充值时有效，人民币分
    long money;
    //本单状态，[ok|deleted]
    String status;

    Date createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getOrigappid() {
        return origappid;
    }

    public void setOrigappid(String origappid) {
        this.origappid = origappid;
    }

    public String getOrigid() {
        return origid;
    }

    public void setOrigid(String origId) {
        this.origid = origId;
    }

    public String getOrigtype() {
        return origtype;
    }

    public void setOrigtype(String origType) {
        this.origtype = origType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
