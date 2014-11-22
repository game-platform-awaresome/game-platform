package com.future.gameplatform.trade.sms.resource;

import com.future.gameplatform.trade.service.MobileNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

@Path("/DCDBChargeResult")
public class DCDBNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(DCDBNoticeResource.class);

    private MobileNoticeService mobileNoticeService;

    public void setMobileNoticeService(MobileNoticeService mobileNoticeService) {
        this.mobileNoticeService = mobileNoticeService;
    }

    @GET
    public Response notify(@QueryParam("from")String from, @QueryParam("to")String to, @QueryParam("msg")String msg,
                           @QueryParam("linkid")String linkid,@QueryParam("serviceid")String serviceid,
                           @QueryParam("servicecode")String servicecode,@QueryParam("backurl")String backurl,@QueryParam("msgtype")String msgtype){
        logger.debug("notify");
        if("10".equalsIgnoreCase(msgtype)){
            logger.debug("notify with msgtype:[{}]", msgtype);
            final String result = mobileNoticeService.executeDCDBnotice(from, to, msg, linkid, serviceid, servicecode, backurl, msgtype);

        } else {
            logger.debug("notify with msgtype:[{}]", msgtype);
        }
        return Response.ok().build();
    }

}
