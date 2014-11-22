package com.future.gameplatform.account.game.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-31
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
@Entity(value = "device", noClassnameStored = true)
public class Device {

    @Id
    private String id;

    @Indexed
    private String mobile;

    @Indexed
    private String did;

    private String osversion;

    private Date createdDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getOsversion() {
        return osversion;
    }

    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
