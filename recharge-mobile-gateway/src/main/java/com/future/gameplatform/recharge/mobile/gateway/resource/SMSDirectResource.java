package com.future.gameplatform.recharge.mobile.gateway.resource;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.service.SdkNoticeService;
import com.future.gameplatform.recharge.common.service.impl.NoticeCpHelper;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.http.HttpStatus;
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
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/mobilerecharge/sms")
public class SMSDirectResource {

    private final static Logger logger = LoggerFactory.getLogger(SMSDirectResource.class);

    private SdkNoticeService sdkNoticeService;

    public void setSdkNoticeService(SdkNoticeService sdkNoticeService) {
        this.sdkNoticeService = sdkNoticeService;
    }

    private NoticeCpHelper noticeCpHelper;

    public void setNoticeCpHelper(NoticeCpHelper noticeCpHelper) {
        this.noticeCpHelper = noticeCpHelper;
    }

    @GET
    @Path("/notify")
    @Produces(MediaType.APPLICATION_JSON)
    public Response notify(@QueryParam("code")String shortcode,
                           @QueryParam("sms")String sms,
                           @QueryParam("fee")String fee,
                           @QueryParam("channel")String channel,
                           @QueryParam("sign")String sign){
        logger.debug("ct sms direct notice with shortcode:[{}], sms:[{}]", shortcode, sms);
        Map<String,String> result = new HashMap<String, String>();
        Map<String,String> params = new HashMap<String, String>();
        Map<String, Object> accountMap = noticeCpHelper.getAppByShortcode(shortcode);
        params.put("code",shortcode);
        params.put("sms",sms);
        params.put("fee", fee);
        params.put("channel",channel);
        if(!SignUtil.checkSdkSign(sign, params, accountMap.get("appkey").toString())) {
            result.put("rtCode","555");
            result.put("rtMsg", "数字签名错误");
            return Response.ok(result).build();
        }
        String[] smss = sms.split("__");
        if(smss == null || smss.length < 3){
            result.put("rtCode","111");
            result.put("rtMsg", "传入参数有误");
            return Response.ok(result).build();
        }
        String orderno = smss[2];
        ServiceResult<String> serviceResult = sdkNoticeService.smsDirectNotice(shortcode, orderno, null, sms, fee, channel);
        if(serviceResult.isSuccess()){
            result.put("rtCode","000");
            result.put("rtMsg","success");
            return Response.ok(result).build();
        }else {
            result.put("rtCode",String.valueOf(serviceResult.getErrorCode()));
            result.put("rtMsg",serviceResult.getErrorMessage());
            return Response.ok(result).build();
        }
    }


}
