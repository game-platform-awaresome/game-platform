package com.future.gameplatform.trade.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseEntity {
    @XmlElement(name = "msgType")
    private String msgType;

    @XmlElement(name = "Transferred ")
    private String Transferred ;

    public String getMsgType() {
        return msgType;
    }

    public ResponseEntity setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getTransferred() {
        return Transferred;
    }

    public ResponseEntity setTransferred(String transferred) {
        Transferred = transferred;
        return this;
    }
}
