package com.future.gameplatform.account.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "mobilecode", noClassnameStored = true)
public class MobileCode {
    @Id
    String id;

    String code;

    Date createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
