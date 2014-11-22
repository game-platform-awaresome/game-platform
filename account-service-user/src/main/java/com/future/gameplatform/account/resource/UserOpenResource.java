package com.future.gameplatform.account.resource;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.future.gameplatform.account.UserAccountString;
import com.future.gameplatform.account.service.UserAccountService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Path("/0/api/user")
public class UserOpenResource {

	private final static Logger logger = LoggerFactory
	        .getLogger(UserOpenResource.class);

	private UserAccountService userAccountService;

	private static AtomicLong totalRegisterCount = new AtomicLong(0);
	private static AtomicLong totalAuthCount = new AtomicLong(0);

	private static AtomicLong totalRegisterSucceedCount = new AtomicLong(0);
	private static AtomicLong totalAuthSucceedCount = new AtomicLong(0);

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@HeaderParam("channelid")String channelid, Map<String, Object> info) {
        final long stamp = System.nanoTime();
        logger.debug("[{}]Register account with [{}]", stamp, info.toString());
        totalRegisterCount.getAndIncrement();
        try {
            if(channelid == null || channelid.isEmpty()){
                channelid = "000000";
            }
            info.put("channelid", channelid);
            final String result = userAccountService.register(info);
            totalRegisterSucceedCount.getAndIncrement();
            return Response.ok(result).build();
        } catch (Exception e) {
            logger.error("[{}]Register account with [{}] failed: {}",
                    new Object[] { stamp,
                            info.toString(), e.getMessage() });
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }


	@GET
	@Path("/login/{id}/{passwd}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response auth(@PathParam("id") String id,
	        @PathParam("passwd") String passwd, @HeaderParam("channelid")String channelid) {
		totalAuthCount.getAndIncrement();
		if (id == null || passwd == null) {
			logger.error("Login request not contain valid id[{}] or passwd[{}].",
                    id, passwd);
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		} else {
			try {
				final String result = userAccountService.auth(id, passwd, channelid);
				totalAuthSucceedCount.getAndIncrement();
				return Response.ok(result).build();
			} catch (Exception e) {
				logger.error("Auth account[{}]:[{}] failed: {}", new Object[] {
				        id, passwd, e.getMessage() });
				return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
				        .build();
			}
		}
	}

    @GET
    @Path("/getcode/{mobile}")
    public Response getValidCode(@PathParam("mobile")String mobile){
        logger.debug("request getvalid with mobile:[{}]",mobile);
        final String result = userAccountService.getValidCode(mobile);
        if(!UserAccountString.RESULT_OK.equalsIgnoreCase(result)){
            logger.error("get valid code error");
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/getbackUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbackUser(Map<String, Object> mobileInfo){
        logger.debug("request getback userinfo with info:[{}]",mobileInfo);
        if(!checkGetbackData(mobileInfo)){
            logger.error("request getback with error param:[{}]",mobileInfo);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        final String result = this.userAccountService.getbackUser(mobileInfo);
        if(!result.equalsIgnoreCase(UserAccountString.RESULT_FAILED)){
            return Response.ok(result).build();
        }
        return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    private boolean checkGetbackData(Map<String, Object> mobileInfo) {
        if(mobileInfo.containsKey("mobile") && mobileInfo.containsKey("code")){
            return true;
        }
        return false;
    }


    public synchronized static long getRegisterCount(boolean reset) {
		if (reset) {
			return totalRegisterCount.getAndSet(0);
		} else {
			return totalRegisterCount.get();
		}
	}

	public synchronized static long getRegisterSucceedCount(boolean reset) {
		if (reset) {
			return totalRegisterSucceedCount.getAndSet(0);
		} else {
			return totalRegisterSucceedCount.get();
		}
	}

	public synchronized static long getAuthCount(boolean reset) {
		if (reset) {
			return totalAuthCount.getAndSet(0);
		} else {
			return totalAuthCount.get();
		}
	}

	public synchronized static long getAuthSucceedCount(boolean reset) {
		if (reset) {
			return totalAuthSucceedCount.getAndSet(0);
		} else {
			return totalAuthSucceedCount.get();
		}
	}
}
