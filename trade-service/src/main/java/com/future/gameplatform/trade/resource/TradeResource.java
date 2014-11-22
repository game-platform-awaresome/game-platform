package com.future.gameplatform.trade.resource;

import com.future.gameplatform.trade.entity.Trade;
import com.future.gameplatform.trade.service.TradeService;
import com.future.gameplatform.trade.util.TradeString;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
@Path("/1/api")
public class TradeResource {
    private final static Logger logger = LoggerFactory.getLogger(TradeResource.class);

    private static AtomicLong totalRequestCount = new AtomicLong(0L);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0L);

    private TradeService tradeService;

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @POST
    @Path("/trade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response trade(@HeaderParam("uid")String uid,@HeaderParam("token")String token, Map<String,Object> tradeInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("new trade with uid:[{}],info:[{}]",uid, tradeInfo);
        if(!checkData(tradeInfo)){
           logger.error("bad request param when create new trade,uid:[{}],info:[{}]",uid,tradeInfo);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        final String result = tradeService.save(uid, token, tradeInfo);
        if(TradeString.RESULT_FAILED.equalsIgnoreCase(result)) {
            logger.error("new trade but failed.");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }


    /***
     * should no request
     * @param uid
     * @param token
     * @param tradeid
     * @param tradeInfo
     * @return
     */

    @Deprecated
    @POST
    @Path("/trade/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTrade(@HeaderParam("uid")String uid, @HeaderParam("token")String token, @PathParam("id")String tradeid,Map<String, Object> tradeInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("update trade with uid:[{}],id:[{}],info:[{}]",new Object[]{uid,tradeid,tradeInfo});
        final String result = tradeService.update(tradeid, token, tradeInfo);
        if(TradeString.RESULT_FAILED.equalsIgnoreCase(result)){
            logger.error("fatal error occur when update trade:[{}]",tradeid);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok().build();
    }

    @GET
    @Path("/list/trade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTrade(@HeaderParam("uid")String uid){
        totalRequestCount.getAndIncrement();
        logger.debug("list trade with uid:[{}]",uid);
        List<Trade> result = tradeService.listByUid(uid);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @GET
    @Path("/trade/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrade(@PathParam("id")String tradeid){
        totalRequestCount.getAndIncrement();
        logger.debug("get trade with id:[{}]",tradeid);
        Trade result = tradeService.getById(tradeid);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @GET
    @Path("/trade/order")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTradeByOrder(@QueryParam("appid")String appid,@QueryParam("orderno")String orderno){
        totalRequestCount.getAndIncrement();
        logger.debug("get trade with appid:[{}] exorderno:[{}]",appid,orderno);
        Trade result = tradeService.getByOderno(appid,orderno);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    private boolean checkData(Map<String, Object> tradeInfo) {
        if(tradeInfo.containsKey("appid") && tradeInfo.containsKey("exorderno") && tradeInfo.containsKey("type")
                && tradeInfo.containsKey("item") && tradeInfo.containsKey("quantity") && tradeInfo.containsKey("price")){
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

    public synchronized static long getTotalRequestSuccessCount(boolean reset) {
        if(reset){
            return totalRequestSuccessCount.getAndSet(0L);
        }
        return totalRequestSuccessCount.get();
    }
}
