package com.future.gameplatform.warehouse.resource;

import com.future.gameplatform.warehouse.GameString;
import com.future.gameplatform.warehouse.entity.Game;
import com.future.gameplatform.warehouse.service.GameService;
import org.apache.http.HttpStatus;
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
@Path("/warehouse")
public class GameResource {

    private static final Logger logger = LoggerFactory.getLogger(GameResource.class);

    private static AtomicLong totalRequestCount = new AtomicLong(0L);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0L);

    private GameService gameService;

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    private String gamePlatformResourceId;

    public void setGamePlatformResourceId(String gamePlatformResourceId) {
        this.gamePlatformResourceId = gamePlatformResourceId;
    }

    @GET
    @Path("/list/com.future.gameplatform.account.game")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGame(@QueryParam("type")String type){
        totalRequestCount.getAndIncrement();
        logger.debug("list com.future.gameplatform.account.game with type:[{}]",type);
        if(type != null &&
                !(GameString.GAME_TYPE_ALL.equalsIgnoreCase(type) ||
                 GameString.GAME_TYPE_BANNER.equalsIgnoreCase(type) ||
                 GameString.GAME_TYPE_STAND.equalsIgnoreCase(type) ||
                 GameString.GAME_TYPE_NET.equalsIgnoreCase(type))) {
            logger.error("list com.future.gameplatform.account.game with bad params,type:[{}]",type);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        List<Game> result = gameService.listByType(type);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @GET
    @Path("/my/com.future.gameplatform.account.game")
    @Produces(MediaType.APPLICATION_JSON)
    public Response myGame(@HeaderParam("uid")String uid,@QueryParam("type")String type){
        totalRequestCount.getAndIncrement();
        logger.debug("list my com.future.gameplatform.account.game with uid:[{}],type:[{}]", uid, type);
        if(type != null &&
                !(GameString.GAME_TYPE_ALL.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_BANNER.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_STAND.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_NET.equalsIgnoreCase(type))) {
            logger.error("list com.future.gameplatform.account.game with bad params,type:[{}]",type);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        List<Game> result = gameService.listByType(type);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @GET
    @Path("/hot/com.future.gameplatform.account.game")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hotGame(@QueryParam("type")String type){
        totalRequestCount.getAndIncrement();
        logger.debug("list hot com.future.gameplatform.account.game with type:[{}]", type);
        if(type != null &&
                !(GameString.GAME_TYPE_ALL.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_BANNER.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_STAND.equalsIgnoreCase(type) ||
                        GameString.GAME_TYPE_NET.equalsIgnoreCase(type))) {
            logger.error("list com.future.gameplatform.account.game with bad params,type:[{}]",type);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        List<Game> result = gameService.listByType(type);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @GET
    @Path("/com.future.gameplatform.account.game/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("id")String id){
        totalRequestCount.getAndIncrement();
        logger.debug("get com.future.gameplatform.account.game with id:[{}]",id);
        Game game = gameService.getGame(id);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(game).build();
    }

    @PUT
    @Path("/com.future.gameplatform.account.game")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(Map<String,Object> gameInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("create new com.future.gameplatform.account.game with info:[{}]",gameInfo);
        final String result = gameService.insertGame(gameInfo);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }


    @POST
    @Path("/com.future.gameplatform.account.game/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGame(@PathParam("id")String id,Map<String,Object> gameInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("update com.future.gameplatform.account.game with id:[{}] info:[{}]",id, gameInfo);
        final String result = gameService.updateGame(id, gameInfo);
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
    }

    @POST
    @Path("/gameplatform/version")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlatformVersion(Map<String, Object> versionInfo){
        totalRequestCount.getAndIncrement();
        logger.debug("get Platform with id:[{}]",gamePlatformResourceId);
        Map<String, String> result = gameService.getGameVersion(gamePlatformResourceId, versionInfo);
        if(result == null){
            logger.error("version not found for this channel:[{}]",versionInfo);
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        totalRequestSuccessCount.getAndIncrement();
        return Response.ok(result).build();
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
