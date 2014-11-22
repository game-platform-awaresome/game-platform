package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
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
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class CUSMSDirectNoticeResource {

    private final static Logger logger = LoggerFactory.getLogger(CTSMSDirectNoticeResource.class);

    private ChannelNoticeService channelNoticeService;

    public void setChannelNoticeService(ChannelNoticeService channelNoticeService) {
        this.channelNoticeService = channelNoticeService;
    }

    @POST
    @Path("/cu/smsdirect")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@FormParam("MchNo") String MchNo, @FormParam("Fee")String Fee, @FormParam("Mobile")String Mobile, @FormParam("sign")String sign,@FormParam("MoSmsMsg")String MoSmsMsg){
        logger.debug("received notice from cu sms direct,mchno:[{}]", MchNo);
        if(!SignUtil.checkChannelSign(sign, MchNo, Mobile, Fee, MoSmsMsg, RechargeConstants.CU_SMS_DIRECT_APPKEY)){
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("111~传入参数有误~").build();
        }
        String shortcode = MoSmsMsg.substring(9, 12);
        String orderno = MoSmsMsg.substring(12);
        SmsRecharge smsRecharge = channelNoticeService.getSmsRechargeByCode(shortcode, orderno);
        if(smsRecharge == null){
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity("500~未知错误（可能是数据库服务器无法访问等其它错误）~").build();
        }
        ServiceResult<String> serviceResult = channelNoticeService.receiveNotice(smsRecharge.getId());
        if(serviceResult.isSuccess()){
            return Response.ok("000~success~").build();
        }else {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(serviceResult.getErrorCode()+"~"+serviceResult.getErrorMessage()+"~").build();
        }
    }
}
