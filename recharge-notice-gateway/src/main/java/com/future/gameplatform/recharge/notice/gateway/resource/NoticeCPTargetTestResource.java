package com.future.gameplatform.recharge.notice.gateway.resource;

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
 * Date: 14-8-23
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/rechargenotice")
public class NoticeCPTargetTestResource {

    private final static Logger logger = LoggerFactory.getLogger(NoticeCPTargetTestResource.class);

    @GET
    @Path("/cp/notice/demo")
    @Produces(MediaType.TEXT_PLAIN)
    public Response receiveNotice(@QueryParam("MchId")String mchId, @QueryParam("MchNo")String MchNo, @QueryParam("TradeId")String TradeId, @QueryParam("Fee")String Fee, @QueryParam("sign") String sign){
        logger.debug("received notice from com.future.gameplatform.account.game platform,mchid:[{}] mchNo:[{}] tradeId:[{}] fee:[{}] sign:[{}]", new Object[]{mchId,MchNo,TradeId,Fee,sign});

        return Response.ok("000~success~").build();

    }
}
