package com.future.gameplatform.account.cp.resource;


import com.future.gameplatform.account.game.service.RechargeAppAccountService;
import com.future.gameplatform.account.game.util.SignUtil;
import com.future.gameplatform.common.service.ActiveDeviceRemoteInterface;
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
 * Created by johnKee on 2015/1/24.
 */

@Path("/0/api/device")
public class ActiveResource {

    private final static Logger logger = LoggerFactory.getLogger(ActiveResource.class);

    private RechargeAppAccountService rechargeAppAccountService;


    public void setRechargeAppAccountService(RechargeAppAccountService rechargeAppAccountService) {
        this.rechargeAppAccountService = rechargeAppAccountService;
    }

    private ActiveDeviceRemoteInterface activeDeviceRemoteInterface;

    public void setActiveDeviceRemoteInterface(ActiveDeviceRemoteInterface activeDeviceRemoteInterface) {
        this.activeDeviceRemoteInterface = activeDeviceRemoteInterface;
    }

    @GET
    @Path("/active")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activeDevice(@QueryParam("code")String shortCode,
                             @QueryParam("did")String did,
                             @QueryParam("mobile")String mobile,
                             @QueryParam("dType")String dType,
                             @QueryParam("brand")String brand,
                             @QueryParam("os")String os,
                             @QueryParam("version")String version,
                             @QueryParam("sign")String sign){
        logger.debug("device active request with code:[{}], did:[{}]", new Object[]{shortCode, did});
        Map<String,String> result = new HashMap<String, String>();
        Map<String,String> params = new HashMap<String, String>();
        String appKey = rechargeAppAccountService.getKeyByCode(shortCode);
        params.put("code",shortCode);
        params.put("did",did);
        params.put("dType", dType);
        params.put("brand", brand);
        params.put("os", os);
        params.put("version", version);
        if(!SignUtil.checkSdkSign(sign, params, appKey)) {
            result.put("rtCode","555");
            result.put("rtMsg","数字签名错误");
            return Response.ok(result).build();
        }
        Boolean serviceResult = activeDeviceRemoteInterface.activeDeviceRecord(shortCode, did, mobile, dType, brand, os, version);
        if(serviceResult){
            result.put("rtCode","000");
            result.put("rtMsg","success");
            return Response.ok(result).build();
        }else {
            result.put("rtCode","444");
            result.put("rtMsg","启动记录异常");
            return Response.ok(result).build();
        }
    }

}
