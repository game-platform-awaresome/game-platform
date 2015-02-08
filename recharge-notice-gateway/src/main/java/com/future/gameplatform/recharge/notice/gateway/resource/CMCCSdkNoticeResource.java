package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.service.ChannelNoticeService;
import com.future.gameplatform.recharge.common.util.RechargeConstants;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class CMCCSdkNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(CMCCSdkNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }

    @POST
    @Path("/cmcc/sdk")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@QueryParam("MchNo") String MchNo,
                                  @QueryParam("Fee")String Fee,
                                  @QueryParam("Mobile")String Mobile,
                                  @QueryParam("sign")String sign,
                                  @QueryParam("merPriv")String merPriv){
        logger.debug("received notice from cmcc hfb,mchno:[{}]", MchNo);
        if(!SignUtil.checkChannelSign(sign, MchNo, Fee, Mobile, RechargeConstants.CMCC_SDK_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveSDKNotice(MchNo, Fee, Mobile);
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
}
