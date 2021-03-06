package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
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
 * Time: 下午1:03
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class SMSDirectAllInOneNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(SMSDirectAllInOneNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }

    /**
    @POST
    @Path("/all/smsdirect")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@FormParam("MchNo") String MchNo,
                                  @FormParam("Fee")String Fee,
                                  @FormParam("Mobile")String Mobile,
                                  @FormParam("sign")String sign,
                                  @FormParam("MoSmsMsg")String MoSmsMsg,
                                  @FormParam("MobileType")String MobileType,
                                  @FormParam("PayChannel")String PayChannel){
        logger.debug("received notice from sms direct,mchno:[{}],Fee:[{}],Mobile:[{}],MoSmsMsg:[{}],MobileType:[{}],PayChannel:[{}]",new Object[]{MchNo, Fee, Mobile, MoSmsMsg, MobileType, PayChannel});
        if(!SignUtil.checkChannelSign(sign, MchNo, Mobile, Fee, MoSmsMsg, RechargeConstants.SMS_DIRECT_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        String[] Mos = MoSmsMsg.split("__");
        if(Mos == null || Mos.length < 3){
            return Response.ok("111~传入参数有误~").build();
        }
        String shortcode = Mos[1];
        String orderno = Mos[2];
        SmsRecharge smsRecharge = channelNoticeService.getSmsRechargeByCode(shortcode, orderno);
        if(smsRecharge == null){
            return Response.ok("500~未知错误（可能是数据库服务器无法访问等其它错误）~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveNotice(smsRecharge.getId());
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.ok(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
    **/

    @POST
    @Path("/all/smsdirect")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@QueryParam("MchNo") String MchNo,
                                  @QueryParam("Fee")String Fee,
                                  @QueryParam("Mobile")String Mobile,
                                  @QueryParam("Sign")String sign,
                                  @QueryParam("MoSmsMsg")String MoSmsMsg,
                                  @QueryParam("MobileType")String MobileType,
                                  @QueryParam("PayChannel")String PayChannel){
        logger.debug("received notice from sms direct,mchno:[{}],Fee:[{}],Mobile:[{}],MoSmsMsg:[{}],MobileType:[{}],PayChannel:[{}]",new Object[]{MchNo, Fee, Mobile, MoSmsMsg, MobileType, PayChannel});
        if(!SignUtil.checkChannelSign(sign, MchNo, Mobile, Fee, MoSmsMsg, RechargeConstants.SMS_DIRECT_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        String[] Mos = MoSmsMsg.split("__");
        if(Mos == null || Mos.length < 3){
            return Response.ok("111~传入参数有误~").build();
        }
        String shortcode = Mos[1];
        String orderno = Mos[2];
        SmsRecharge smsRecharge = channelNoticeService.getSmsRechargeByCode(shortcode, orderno);
        if(smsRecharge == null){
            return Response.ok("500~未知错误（可能是数据库服务器无法访问等其它错误）~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveNotice(smsRecharge.getId());
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.ok(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
}
