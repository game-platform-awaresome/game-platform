package com.future.gameplatform.account.cp.resource;

import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.entity.AccountItem;
import com.future.gameplatform.account.game.entity.Device;
import com.future.gameplatform.account.game.entity.RechargeAppAccount;
import com.future.gameplatform.account.game.service.DeviceService;
import com.future.gameplatform.account.game.service.RechargeAppAccountService;
import com.future.gameplatform.account.game.util.SignUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/0/api/rechargeapp")
public class RechargeAppAccountResource {

    private final static Logger logger = LoggerFactory.getLogger(RechargeAppAccountResource.class);

    private RechargeAppAccountService rechargeAppAccountService;

    private DeviceService deviceService;

    public void setRechargeAppAccountService(RechargeAppAccountService rechargeAppAccountService) {
        this.rechargeAppAccountService = rechargeAppAccountService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PUT
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertRechargeAppAccount(Map<String,Object> accountInfo){
        logger.debug("put rechargeapp with info:[{}]",accountInfo);
        final String result = rechargeAppAccountService.insert(accountInfo);
        if(!UserAccountString.RESULT_FAILED.equalsIgnoreCase(result)){
            return Response.ok(result).build();
        }
        logger.error("put rechargeapp fatal error!");
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }


    @GET
    @Path("/account/{appid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRechargeApp(@PathParam("appid")String appid){
        logger.debug("get  RechargeApp with appid:[{}]", appid);
        try{
            RechargeAppAccount result = rechargeAppAccountService.getById(appid);
            if(result != null){
                logger.debug("get recharge app account,result:[{}]", result);
                return Response.ok(result).build();
            }
        }catch (Exception e){
            logger.error("error in get", e);
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/account/short")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRechargeAppByShortcode(@QueryParam("code")String shortcode,
                                              @QueryParam("operator")String operator,
                                              @QueryParam("fee") String fee,
                                              @QueryParam("version")String version,
                                              @QueryParam("sign")String sign,
                                              @QueryParam("mobile")String mobile,
                                              @QueryParam("did")String did,
                                              @QueryParam("osversion")String osversion){
        logger.debug("get recharge app with short code:[{}]", shortcode);
        Map<String,String> result = new HashMap<String, String>();
        try{
            String appKey = rechargeAppAccountService.getKeyByCode(shortcode);
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("operator",operator);
            params.put("fee",fee);
            params.put("version",version);
            if(!SignUtil.checkSdkSign(sign, params, appKey)) {
                result.put("rtCode","555");
                result.put("rtMsg","数字签名错误");
                return Response.ok(result).build();
            }
            Device device = new Device();
            device.setMobile(mobile);
            device.setDid(did);
            device.setOsversion(osversion);
            device.setCreatedDate(new Date());

            device = deviceService.insertDevice(device);

            RechargeAppAccount serviceResult = rechargeAppAccountService.getByCode(shortcode, operator, fee, version);
            if(serviceResult != null){
                logger.debug("get recharge app account,result:[{}]", result);
                return Response.ok(serviceResult).build();
            }
        } catch (Exception e){
            logger.error("error in get by code", e);
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/account/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRechargeApp(){
        logger.debug("list recharge app");
        List<RechargeAppAccount> result = rechargeAppAccountService.listAll();
        if(result != null){
            logger.debug("list recharge app result:[{}]",result);
            return Response.ok(result).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("/account/{appid}")
    public Response deleteRechargeApp(@PathParam("appid")String appid){
        logger.debug("delete recharge app id:[{}]",appid);
        final String result = rechargeAppAccountService.deleteById(appid);
        if(result != null && UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.debug("delete recharge app success");
            return Response.ok(result).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/account/{appid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRechargeApp(@PathParam("appid")String appid,Map<String, Object> updateInfo){
        logger.debug("update recharge app id:[{}] with info:[{}]",appid, updateInfo);
        final String result = rechargeAppAccountService.updateRechargeApp(appid, updateInfo);
        if(result != null && UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.debug("update recharge app success");
            return Response.ok(result).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/account/{id}/{channel}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccountChannel(@PathParam("id")String id, @PathParam("channel") String channel,AccountItem item){
        logger.debug("update account channel [{}] [{}] with sortcode [{}] status [{}]", new Object[] {id, channel, item});
        final String result = rechargeAppAccountService.updateAccountChannel(id, channel, item);
        if(result != null && UserAccountString.RESULT_OK.equals(result)){
            logger.debug("update account channel ok");
            return Response.ok().build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/defaultaccount/{channel}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDefaultAccountChannel(@PathParam("channel")String channel, String version, String status){
        logger.debug("update default account channel,channel should added mannuly");
        AccountItem item = new AccountItem();
        item.setChannel(channel);
        item.setVersion(version);
        item.setStatus(status);
        final String result = rechargeAppAccountService.updateOrInsertDefaultAccountChannel(item);
        if(result != null && UserAccountString.RESULT_OK.equals(result)){
            logger.debug("update default account channel ok");
            return Response.ok().build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/shortcode/auth")
    @Produces(MediaType.TEXT_PLAIN)
    public Response orgAuth(@QueryParam("shortcode")String shortcode, @QueryParam("key")String key){
        logger.debug("org auth with shortcode [{}]", shortcode);
        String appKey = rechargeAppAccountService.getKeyByCode(shortcode);
        if (appKey.equalsIgnoreCase(key)){
            return Response.ok().build();
        }else {
            return Response.status(HttpStatus.SC_FORBIDDEN).build();
        }
    }


}
