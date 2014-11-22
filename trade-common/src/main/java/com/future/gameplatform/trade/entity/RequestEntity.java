package com.future.gameplatform.trade.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Entity(value = "jchnotice", noClassnameStored = true)
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestEntity {

    @Id
    private String id;

    @XmlElement(name = "msgType")
    private String msgType;

    @XmlElement(name = "ChargeRequestID")
    private String ChargeRequestID;

    @Indexed
    @XmlElement(name = "ServiceID")
    private String ServiceID;

    @XmlElement(name = "ChargeResult")
    private String ChargeResult;

    private Date createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public RequestEntity setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getChargeRequestID() {
        return ChargeRequestID;
    }

    public RequestEntity setChargeRequestID(String chargeRequestID) {
        ChargeRequestID = chargeRequestID;
        return this;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public RequestEntity setServiceID(String serviceID) {
        ServiceID = serviceID;
        return this;
    }

    public String getChargeResult() {
        return ChargeResult;
    }

    public RequestEntity setChargeResult(String chargeResult) {
        ChargeResult = chargeResult;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public RequestEntity setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}
