package com.future.gameplatform.warehouse.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "com.future.gameplatform.account.game", noClassnameStored = true)
public class Game {
    @Id
    String id;

    String title;

    String pkgname;

    String picuri;

    String desc;

    String stars;

    String ingamepay;

    int downcount;

    String downuri;

    String type;    //[stand|net|banner]

    String status;

    String version;

    List<Map<String,Object>> channels;

    Date createdTime;

    Date lastUpdateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public String getPicuri() {
        return picuri;
    }

    public void setPicuri(String picuri) {
        this.picuri = picuri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getIngamepay() {
        return ingamepay;
    }

    public void setIngamepay(String ingamepay) {
        this.ingamepay = ingamepay;
    }

    public int getDowncount() {
        return downcount;
    }

    public void setDowncount(int downcount) {
        this.downcount = downcount;
    }

    public String getDownuri() {
        return downuri;
    }

    public void setDownuri(String downuri) {
        this.downuri = downuri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Map<String,Object>> getChannels() {
        return channels;
    }

    public void setChannels(List<Map<String,Object>> channels) {
        this.channels = channels;
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
