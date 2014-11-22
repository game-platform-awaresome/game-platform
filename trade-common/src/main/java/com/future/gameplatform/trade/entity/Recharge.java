package com.future.gameplatform.trade.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Entity(value = "recharge", noClassnameStored = true)
public class Recharge {
    @Id
    String orderno;
    //支付途径
    String payway;
    //支付号
    String transid;
    //类型：0正常1冲正
    String transtype;
    //应用号
    String appid;

    @Indexed
    String uid;
    //物品
    String waresid;
    //价格    人民币分
    int price;
    //数量
    int quantity;
    //私信
    String privateinfo;
    //单据状态，【ok|deleted】
    String status;
    //支付结果，0成功，1失败
    String result;

    Date createdTime;
    //同步通知时间
    Date syncTime;
    //充值时间
    Date transTime;

    Date lastUpdateTime;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWaresid() {
        return waresid;
    }

    public void setWaresid(String waresid) {
        this.waresid = waresid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrivateinfo() {
        return privateinfo;
    }

    public void setPrivateinfo(String privateinfo) {
        this.privateinfo = privateinfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
