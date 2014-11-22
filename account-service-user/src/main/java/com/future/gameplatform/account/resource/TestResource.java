package com.future.gameplatform.account.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-9-30
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
@Path("/0/api/account/test")
public class TestResource {

    private static final Logger logger = LoggerFactory.getLogger(TestResource.class);

    @GET
    @Path("/123")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(){
        logger.info("call test");
        return Response.ok("success call test").build();
    }
}
