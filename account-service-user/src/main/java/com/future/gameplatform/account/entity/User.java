package com.future.gameplatform.account.entity;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

import com.future.gameplatform.common.security.MD5Util;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "user", noClassnameStored = true)
public class User {
    @Id
    private int id;

    private String passwd;

    private String status;

    private Date createDate;
    @Indexed
    private String nick;
    @Indexed
    private String mobile;

    private String sex;

    @Embedded(concreteClass = java.util.HashMap.class)
    private Map<String, Object> extra;

    private long balance;      //账户余额，大厅金币

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    /** 单独建立实体来存储
    private long recharge;     //累计充值，人民币分
    private long deposit;      //累计购入，大厅金币
    private long withdraw;     //累计支取，大厅金币
    **/


    public String getToken() {
        String dt = Long.toHexString(new Date().getTime());
        String sig = MD5Util.hexString(getId() + dt, Charset.forName("UTF-8"));
        String seed = sig.substring(30);
        int index  = Integer.parseInt(seed,16) % dt.length();
        String token = sig.substring(0,index) + dt + sig.substring(index);
        return token;
    }
}
