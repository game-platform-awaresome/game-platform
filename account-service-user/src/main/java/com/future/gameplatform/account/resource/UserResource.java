package com.future.gameplatform.account.resource;

import com.future.gameplatform.account.UserAccountString;
import com.future.gameplatform.account.entity.User;
import com.future.gameplatform.account.service.UserAccountService;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/1/api/user")
public class UserResource {
    private final static Logger logger = LoggerFactory
            .getLogger(UserResource.class);

    private static AtomicLong totalRequestCount = new AtomicLong(0L);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0L);

    private UserAccountService userAccountService;

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id")String id, Map<String,Object> userInfo){
        logger.debug("update user:[{}] info:[{}]",id, userInfo);
        User userAccount = userAccountService.updateUser(id, userInfo);
        if(userAccount != null){
            return Response.ok().build();
        } else {
            logger.error("update user error!");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("id")String id){
        logger.debug("query user info with id:[{}]",id);
        User userAccount = userAccountService.getAccountById(id);
        if(userAccount == null){
            logger.error("query user info with bad params,id:[{}]",id);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        return Response.ok(userAccount).build();
    }

    @POST
    @Path("/bindmobile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bindMobile(@HeaderParam("uid")String uid, Map<String, Object> bindInfo){
        logger.debug("bind mobile with uid:[{}],info:[{}]",uid, bindInfo);
        if(!checkBindData(bindInfo)){
            logger.error("error in bind request param:[{}]",bindInfo);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        final String result = userAccountService.bindMobile(uid, bindInfo);
        if(!UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.error("fatal error in bind mobile");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    /**
     * 做了trade，此接口接收通知，更新大厅余额
     * @param uid
     * @param tradeinfo
     * @return
     */
    @POST
    @Path("/tradenotice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tradebalance(@HeaderParam("uid")String uid, Map<String,Object> tradeinfo){
        logger.debug("trade notice with uid:[{}],info:[{}]",uid, tradeinfo);
        final String result = userAccountService.doTradeNotice(uid, tradeinfo);
        if(!UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.error("fatal error in trade notice");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(UserAccountString.RESULT_OK).build();
    }

    /**
     * 做了recharge，此接口接收通知，更新大厅余额
     * @param uid
     * @param rechargeinfo
     * @return
     */
    @POST
    @Path("/rechargenotice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechargebalance(@HeaderParam("uid")String uid,Map<String, Object> rechargeinfo){
        logger.debug("recharge notice with uid:[{}],info:[{}]",uid,rechargeinfo);
        final String result = userAccountService.doRechargeNotice(uid,rechargeinfo);
        if(!UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.error("fatal error in recharge notice");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(UserAccountString.RESULT_OK).build();
    }

    /**
     * 余额有问题，此接口从记账中累计余额，目前尚未实现
     * @param uid
     * @param token
     * @return
     */
    @GET
    @Path("/accountingbalance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response accountingbalance(@HeaderParam("uid")String uid,@HeaderParam("token")String token){
        logger.debug("request accounting balance with uid:[{}]",uid);
        final User result = userAccountService.accountingbalance(uid, token);
        if(result == null){
            logger.error("fatal error in accounting balance");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(result).build();
    }


    private boolean checkBindData(Map<String, Object> bindInfo) {
        if(bindInfo.containsKey("mobile") && bindInfo.containsKey("code")){
            return true;
        }
        return false;
    }

    public synchronized static long getTotalRequestCount(boolean reset) {
        if(reset){
            return totalRequestCount.getAndSet(0L);
        }
        return totalRequestCount.get();
    }

    public synchronized static long getTotalRequestSuccessCount(boolean  reset) {
        if(reset){
            return totalRequestSuccessCount.getAndSet(0L);
        }
        return totalRequestSuccessCount.get();
    }
}
