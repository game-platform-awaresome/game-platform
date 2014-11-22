package com.future.gameplatform.trade.sms.resource;

import com.future.gameplatform.trade.service.MobileNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/MHChargeResult")
public class MHNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(MHNoticeResource.class);

    private MobileNoticeService mobileNoticeService;

    public void setMobileNoticeService(MobileNoticeService mobileNoticeService) {
        this.mobileNoticeService = mobileNoticeService;
    }

    @POST
    public Response notify(@QueryParam("MchNo")String MchNo,@QueryParam("Phone")String Phone,@QueryParam("Fee")String Fee,
                           @QueryParam("OrderId")String OrderId, @QueryParam("Sign")String Sign){
        logger.debug("notify with MchNo[{}] Phone[{}] Fee[{}] OrderId[{}] Sign[{}]", new Object[]{MchNo, Phone, Fee, OrderId, Sign});
        final String result = mobileNoticeService.executeMHnotice(MchNo, Phone, Fee, null, OrderId, Sign);
        return Response.ok("000~success~").build();
    }
}
