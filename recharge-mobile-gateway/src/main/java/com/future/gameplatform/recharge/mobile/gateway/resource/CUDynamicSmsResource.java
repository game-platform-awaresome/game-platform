package com.future.gameplatform.recharge.mobile.gateway.resource;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
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
 * Date: 14-8-17
 * Time: 上午8:05
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/mobilerecharge/cu/dynamic")
public class CUDynamicSmsResource {

    private final static Logger logger = LoggerFactory.getLogger(CUDynamicSmsResource.class);

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
    public Response getSms(@QueryParam("code")String shortcode,
                           @QueryParam("orderno")String orderno,
                           @QueryParam("fee")String fee,
                           @QueryParam("sign")String sign){
        logger.debug("sdk request code with shortcode:[{}], orderno:[{}]", shortcode, orderno);
        Map<String,String> result = new HashMap<String, String>();
        Map<String,String> params = new HashMap<String, String>();
        Map<String, Object> accountMap = noticeCpHelper.getAppByShortcode(shortcode);
        params.put("code",shortcode);
        params.put("orderno",orderno);
        params.put("fee",fee);
        if(!SignUtil.checkSdkSign(sign, params, accountMap.get("appkey").toString())) {
            result.put("rtCode","555");
            result.put("rtMsg","数字签名错误");
            return Response.ok(result).build();
        }
        ServiceResult<SmsRecharge> serviceResult = sdkNoticeService.cuDynamicNotice(shortcode, orderno, fee);
        if(serviceResult.isSuccess()){
            result.put("rtCode","000");
            result.put("rtMsg","success");
            result.put("MoSmsMsg",serviceResult.getValue().getSmscode());
            result.put("PayChannel",serviceResult.getValue().getSmsChannel());
            return Response.ok(result).build();
        }else {
            result.put("rtCode",String.valueOf(serviceResult.getErrorCode()));
            result.put("rtMsg",serviceResult.getErrorMessage());
            return Response.ok(result).build();
        }
    }


}
