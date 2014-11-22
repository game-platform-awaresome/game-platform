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

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-11-6
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class PageAllInOneNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(PageAllInOneNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }
    /**
    @POST
    @Path("/all/page")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@FormParam("MchId") String MchId,
                                  @FormParam("MchNo") String MchNo,
                                  @FormParam("Fee")String Fee,
                                  @FormParam("Mobile")String Mobile,
                                  @FormParam("sign")String sign){
        logger.debug("received notice from page,MchId:[{}],MchNo:[{}],Fee:[{}],Mobile:[{}]",new Object[]{MchId, MchNo, Fee, Mobile});
        if(!SignUtil.checkChannelSign(sign, MchNo, Fee,Mobile, RechargeConstants.getAppKeyByAppIdForPage(MchId))){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveNotice(MchNo);
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
    **/

    @POST
    @Path("/all/page")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@QueryParam("MchId") String MchId,
                                  @QueryParam("MchNo") String MchNo,
                                  @QueryParam("Fee")String Fee,
                                  @QueryParam("Mobile")String Mobile,
                                  @QueryParam("Sign")String sign){
        logger.debug("received notice from page,MchId:[{}],MchNo:[{}],Fee:[{}],Mobile:[{}]",new Object[]{MchId, MchNo, Fee, Mobile});
        if(!SignUtil.checkChannelSign(sign, MchNo, Fee,Mobile, RechargeConstants.getAppKeyByAppIdForPage(MchId))){
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
