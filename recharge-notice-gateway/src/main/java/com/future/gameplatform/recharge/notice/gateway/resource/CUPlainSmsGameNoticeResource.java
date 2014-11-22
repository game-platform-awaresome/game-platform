package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.service.ChannelNoticeService;
import com.future.gameplatform.recharge.common.util.RechargeConstants;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-12
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class CUPlainSmsGameNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(CUPlainSmsGameNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }

    @POST
    @Path("/cu/plaingame")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@FormParam("MchId")String mchId, @FormParam("MchNo")String MchNo, @FormParam("Fee")String Fee, @FormParam("sign") String sign, @FormParam("Mobile")String mobile){
        logger.debug("received notice from cu plain game,mchno:[{}]", MchNo);
        if(!SignUtil.checkChannelSign(sign, MchNo, mchId, Fee, RechargeConstants.CU_PLAIN_GAME_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receivePlainSmsNotice(MchNo, mobile);
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
}
