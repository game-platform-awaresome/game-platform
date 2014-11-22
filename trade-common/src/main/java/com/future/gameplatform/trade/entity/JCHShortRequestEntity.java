package com.future.gameplatform.trade.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Entity(value = "jchshortnotice", noClassnameStored = true)
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class JCHShortRequestEntity {
    @Id
    private String id;
    @XmlElement(name = "msgType")
    private String msgType;
    @XmlElement(name = "MobileNumber")
    private String MobileNumber;
    @XmlElement(name = "UserId")
    private String UserId;
    @XmlElement(name = "Region")
    private String Region;
    @XmlElement(name = "ServiceID")
    private String ServiceID;
    @XmlElement(name = "LinkId")
    private String LinkId;
    @XmlElement(name = "ChargeResult")
    private String ChargeResult;
    @XmlElement(name = "ChargeFee")
    private String ChargeFee;
    @XmlElement(name = "ChargeStatus")
    private String ChargeStatus;

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

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getLinkId() {
        return LinkId;
    }

    public void setLinkId(String linkId) {
        LinkId = linkId;
    }

    public String getChargeResult() {
        return ChargeResult;
    }

    public void setChargeResult(String chargeResult) {
        ChargeResult = chargeResult;
    }

    public String getChargeFee() {
        return ChargeFee;
    }

    public void setChargeFee(String chargeFee) {
        ChargeFee = chargeFee;
    }

    public String getChargeStatus() {
        return ChargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        ChargeStatus = chargeStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
