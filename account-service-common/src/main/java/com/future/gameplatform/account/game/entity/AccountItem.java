package com.future.gameplatform.account.game.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-9-26
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public class AccountItem {

    String cate;           //sdk,dynamic,page,direct,plain

    String operator;      //ct,cu,cmcc

    String fee;

    String fee_min;

    String fee_max;

    String channel;       //channel no

    String channelName;   //channel name

    int sortcode;

    String url_noticeno;

    String url_postcode;

    String version;

    String status;

    String instruct_fixed;

    String targetcode;

    String instruct_sub;

    List<SMSFilter> filters;

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee_min() {
        return fee_min;
    }

    public void setFee_min(String fee_min) {
        this.fee_min = fee_min;
    }

    public String getFee_max() {
        return fee_max;
    }

    public void setFee_max(String fee_max) {
        this.fee_max = fee_max;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getSortcode() {
        return sortcode;
    }

    public void setSortcode(int sortcode) {
        this.sortcode = sortcode;
    }

    public String getUrl_noticeno() {
        return url_noticeno;
    }

    public void setUrl_noticeno(String url_noticeno) {
        this.url_noticeno = url_noticeno;
    }

    public String getUrl_postcode() {
        return url_postcode;
    }

    public void setUrl_postcode(String url_postcode) {
        this.url_postcode = url_postcode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstruct_fixed() {
        return instruct_fixed;
    }

    public void setInstruct_fixed(String instruct_fixed) {
        this.instruct_fixed = instruct_fixed;
    }

    public String getTargetcode() {
        return targetcode;
    }

    public void setTargetcode(String targetcode) {
        this.targetcode = targetcode;
    }

    public String getInstruct_sub() {
        return instruct_sub;
    }

    public void setInstruct_sub(String instruct_sub) {
        this.instruct_sub = instruct_sub;
    }

    public List<SMSFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<SMSFilter> filters) {
        this.filters = filters;
    }

}
