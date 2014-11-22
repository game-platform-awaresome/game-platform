package com.future.gameplatform.account.game.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Entity(value = "rechargeaccount", noClassnameStored = true)
public class RechargeAppAccount {
    @Id
    String id;

    String appkey;

    String payway;

    String searchurl;

    String noticeurl;

    String status;

    @Indexed
    String shortcode;

    String cpName;

    Map<String,Object> payextra;

    List<AccountItem> items;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getSearchurl() {
        return searchurl;
    }

    public void setSearchurl(String searchurl) {
        this.searchurl = searchurl;
    }

    public String getNoticeurl() {
        return noticeurl;
    }

    public void setNoticeurl(String noticeurl) {
        this.noticeurl = noticeurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public Map<String, Object> getPayextra() {
        return payextra;
    }

    public void setPayextra(Map<String, Object> payextra) {
        this.payextra = payextra;
    }

    public List<AccountItem> getItems() {
        return items;
    }

    public void setItems(List<AccountItem> items) {
        this.items = items;
    }
}
