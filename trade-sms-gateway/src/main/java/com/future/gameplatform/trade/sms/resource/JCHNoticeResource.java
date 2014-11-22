package com.future.gameplatform.trade.sms.resource;

import com.future.gameplatform.trade.service.MobileNoticeService;
import com.future.gameplatform.trade.entity.RequestEntity;
import com.future.gameplatform.trade.bean.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Path("/ChargeResult")
public class JCHNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(JCHNoticeResource.class);

    private MobileNoticeService mobileNoticeService;

    public void setMobileNoticeService(MobileNoticeService mobileNoticeService) {
        this.mobileNoticeService = mobileNoticeService;
    }

    @POST
    @Produces("text/xml; charset=utf-8")
    public ResponseEntity messageArrived(RequestEntity requestEntity) {
        logger.debug("receive one notice[{}]", marshal(requestEntity));
        ResponseEntity responseEntity = mobileNoticeService.executeJCHNotice(requestEntity);
        logger.debug("response[{}]", marshal(responseEntity));
        return responseEntity;
    }

    private String marshal(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JAXB.marshal(obj, out);
        return new String(out.toByteArray()).replaceAll("\r|\n", "");
    }

}
