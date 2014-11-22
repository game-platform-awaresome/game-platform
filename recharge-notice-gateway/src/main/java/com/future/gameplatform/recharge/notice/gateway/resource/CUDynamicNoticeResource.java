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
 * Time: 上午8:27
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class CUDynamicNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(CUDynamicNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }

    @POST
    @Path("/cu/dynamic")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@FormParam("MchId")String mchId, @FormParam("MchNo")String MchNo, @FormParam("Fee")String Fee, @FormParam("sign") String sign){
        logger.debug("received notice from cu dynamic,mchno:[{}]", MchNo);
        if(!SignUtil.checkChannelSign(sign,MchNo, mchId, Fee, RechargeConstants.CU_DYNAMIC_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveNotice(MchNo);
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }


}
