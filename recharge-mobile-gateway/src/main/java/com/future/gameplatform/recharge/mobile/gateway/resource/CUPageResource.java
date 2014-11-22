package com.future.gameplatform.recharge.mobile.gateway.resource;

import com.future.gameplatform.recharge.common.service.SdkNoticeService;
import com.future.gameplatform.recharge.common.service.impl.NoticeCpHelper;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-19
 * Time: 上午5:13
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/mobilerecharge/cu/page")
public class CUPageResource {

    private final static Logger logger = LoggerFactory.getLogger(CUPageResource.class);

    private SdkNoticeService sdkNoticeService;

    public void setSdkNoticeService(SdkNoticeService sdkNoticeService) {
        this.sdkNoticeService = sdkNoticeService;
    }

    private NoticeCpHelper noticeCpHelper;

    public void setNoticeCpHelper(NoticeCpHelper noticeCpHelper) {
        this.noticeCpHelper = noticeCpHelper;
    }

    @GET
    @Path("/code")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRechargeSmscode(@QueryParam("code")String shortCode,
                                       @QueryParam("orderno")String orderno,
                                       @QueryParam("mobile")String mobile,
                                       @QueryParam("fee")String fee,
                                       @QueryParam("channel")String channel,
                                       @QueryParam("sign")String sign){
        logger.debug("sdk request code with shortcode:[{}], orderno:[{}]", shortCode, orderno);
        Map<String,String> result = new HashMap<String, String>();
        Map<String,String> params = new HashMap<String, String>();
        Map<String, Object> accountMap = noticeCpHelper.getAppByShortcode(shortCode);
        params.put("code",shortCode);
        params.put("orderno",orderno);
        params.put("mobile",mobile);
        params.put("fee",fee);
        params.put("channel",channel);
        if(!SignUtil.checkSdkSign(sign, params, accountMap.get("appkey").toString())) {
            result.put("rtCode","555");
            result.put("rtMsg","数字签名错误");
            return Response.ok(result).build();
        }
        ServiceResult<String> serviceResult = sdkNoticeService.cupageNotice(shortCode, orderno, mobile, fee, channel);
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

    @GET
    @Path("/result")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRechargeResult(@QueryParam("code")String shortCode,
                                      @QueryParam("orderno")String orderno,
                                      @QueryParam("smscode")String smscode,
                                      @QueryParam("sign")String sign){
        logger.debug("sdk notice result with shortcode:[{}], orderno:[{}]", shortCode, orderno);
        Map<String,String> result = new HashMap<String, String>();
        Map<String,String> params = new HashMap<String, String>();
        Map<String, Object> accountMap = noticeCpHelper.getAppByShortcode(shortCode);
        params.put("code",shortCode);
        params.put("orderno",orderno);
        params.put("smscode",smscode);
        if(!SignUtil.checkSdkSign(sign, params, accountMap.get("appkey").toString())) {
            result.put("rtCode","555");
            result.put("rtMsg","数字签名错误");
            return Response.ok(result).build();
        }
        ServiceResult<String> serviceResult = sdkNoticeService.cupageResult(shortCode, orderno, smscode);
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
