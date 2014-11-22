package com.future.gameplatform.trade.resource;

import com.future.gameplatform.trade.entity.Recharge;
import com.future.gameplatform.trade.service.RechargeService;
import com.future.gameplatform.trade.util.RechargeString;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Recharge resource,manage recharge resource with token valid.
 * User: Administrator
 */
@Path("/1/api")
public class RechargeResource {

    private final static Logger logger = LoggerFactory.getLogger(RechargeResource.class);

    private static AtomicLong totalRequestCount = new AtomicLong(0L);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0L);

    private RechargeService rechargeService;

    public void setRechargeService(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @POST
    @Path("/recharge")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recharge(@HeaderParam("uid")String uid,Map<String,Object> rechargeInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("request recharge with userid:[{}] and info:[{}]", uid, rechargeInfo);
        if(!checkData(rechargeInfo)){
            logger.error("request recharge with bad params:[{}]",rechargeInfo);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        final String result = rechargeService.insertRecharge(uid, rechargeInfo);
        if(RechargeString.RESULT_FAILED.equalsIgnoreCase(result)){
            logger.error("recharge request error,something wrong");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("result",result);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(map).build();
    }

    @POST
    @Path("/recharge/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRecharge(@HeaderParam("uid")String uid, @HeaderParam("token")String token, @PathParam("id")String rechargeId, Map<String,Object> rechargeInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("update rechage:[{}],userid:[{}],with info:[{}]",new Object[]{rechargeId, uid, rechargeInfo});
        final String result = rechargeService.updateRecharge(uid, rechargeId,token, rechargeInfo);
        if(RechargeString.RESULT_FAILED.equalsIgnoreCase(result)){
            logger.error("error in update recharge:[{}]",rechargeId);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok().build();
    }

    @GET
    @Path("/recharge/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRecharge(@HeaderParam("uid")String uid){
        totalRequestCount.getAndIncrement();
        logger.debug("list recharges with userid:[{}]",uid);
        List<Recharge> list = rechargeService.listByUid(uid);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(list).build();
    }

    @GET
    @Path("/recharge/{id}")
    public Response getRecharge(@HeaderParam("uid")String uid, @PathParam("id")String rechargeid){
        totalRequestCount.getAndIncrement();
        logger.debug("get recharge with id:[{}]",rechargeid);
        Recharge recharge = rechargeService.getById(rechargeid);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(recharge).build();
    }

    private boolean checkData(Map<String,Object> rechargeInfo) {
        if(!rechargeInfo.containsKey("appid") && !rechargeInfo.containsKey("waresid") || !rechargeInfo.containsKey("price") || !rechargeInfo.containsKey("quantity")){
            return false;
        }else{
            if(!(rechargeInfo.get("waresid") instanceof Integer) || !(rechargeInfo.get("price") instanceof Integer) || !(rechargeInfo.get("quantity") instanceof Integer)){
                return false;
            }
        }
        return true;
    }

    public synchronized static long getTotalRequestCount(boolean reset) {
        if(reset){
            return totalRequestCount.getAndSet(0L);
        }
        return totalRequestCount.get();
    }

    public synchronized static long getTotalRequestSuccessCount(boolean reset) {
        if(reset){
            return totalRequestCount.getAndSet(0L);
        }
        return totalRequestSuccessCount.get();
    }
}
