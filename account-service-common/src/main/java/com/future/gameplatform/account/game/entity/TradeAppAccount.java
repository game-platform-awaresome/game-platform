package com.future.gameplatform.account.game.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "tradeaccount", noClassnameStored = true)
public class TradeAppAccount {
    @Id
    private String appid;

    private String appkey;

    private String notifyurl;

    private String searchurl;

    private String status;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getSearchurl() {
        return searchurl;
    }

    public void setSearchurl(String searchurl) {
        this.searchurl = searchurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
