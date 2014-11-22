package com.future.gameplatform.trade.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "trade", noClassnameStored = true)
public class Trade {
    @Id
    String id;

    @Indexed
    String uid;
    //来源，如斗地主game
    String appid;
    //订单号，回调使用，对端用于唯一标示
    String exorderno;
    //类型，0正常交易，1冲正交易
    String type;
    //物品
    String item;
    //数量
    int quantity;
    //价格，大厅金币计价
    int price;
    //合计总额，大厅金币计价
    int amount;
    //本流水单的状态
    String status;
    //交易结果，0成功，1失败
    String result;

    Date createdTime;

    Date lastUpdateTime;

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

    public String getExorderno() {
        return exorderno;
    }

    public void setExorderno(String exorderno) {
        this.exorderno = exorderno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
