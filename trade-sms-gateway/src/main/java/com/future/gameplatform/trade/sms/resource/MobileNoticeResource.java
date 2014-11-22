package com.future.gameplatform.trade.sms.resource;

import com.future.gameplatform.trade.service.MobileNoticeService;
import com.future.gameplatform.trade.service.RechargeService;
import com.future.gameplatform.trade.util.RechargeString;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/1/api/sms")
public class MobileNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(MobileNoticeResource.class);

    private MobileNoticeService mobileNoticeService;

    public void setMobileNoticeService(MobileNoticeService mobileNoticeService) {
        this.mobileNoticeService = mobileNoticeService;
    }

    @PUT
    @Path("/smsnotify")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notify(@HeaderParam("uid")String uid, @HeaderParam("token")String token, Map<String, Object> noticeInfo){
        logger.debug("mobile notice one recharge result with uid:[{}] info:[{}]",uid, noticeInfo);
        if(!checkData(noticeInfo)){
            logger.error("mobile notice one recharge result with bad params");
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        final String result = mobileNoticeService.notify(uid, token, noticeInfo);
        if(result != null && !RechargeString.RESULT_FAILED.equalsIgnoreCase(result)){
            logger.debug("mobile notice one recharge result, success,next accounting it");
            return Response.ok(result).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    private boolean checkData(Map<String, Object> noticeInfo) {
        if(noticeInfo != null && noticeInfo.containsKey("appid") && noticeInfo.containsKey("channel") && noticeInfo.containsKey("amount") && noticeInfo.containsKey("content")){
            return true;
        }
        return false;
    }

}
