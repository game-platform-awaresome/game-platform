package com.future.gameplatform.warehouse.dao.impl;

import com.future.gameplatform.warehouse.GameString;
import com.future.gameplatform.warehouse.dao.GameDao;
import com.future.gameplatform.warehouse.entity.Game;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GameDaoImpl extends BasicDaoImpl implements GameDao {

    private static Logger loggger = LoggerFactory.getLogger(GameDaoImpl.class);

    public GameDaoImpl(String mongoDomain, String mongoDB) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, mongoDB);
        datastore.ensureIndexes();
    }

    @Override
    public List<Game> listByType(String type) {
        Query<Game> gameQuery = datastore.find(Game.class).field("pkgname").notEqual("com.air.gamehouse");
        if(type != null && !GameString.GAME_TYPE_ALL.equalsIgnoreCase(type)){
            gameQuery.filter("type", type);
        }
        return gameQuery.asList();
    }

    @Override
    public Game getById(String id) {

        try{
            DBObject dbObject = new BasicDBObject();
            dbObject.put("_id", id);
            DBObject result = datastore.getCollection(Game.class).findOne(dbObject);

            Game game = new Game();
            game.setId((String)result.get("_id"));
            game.setType((String)result.get("type"));
            game.setVersion((String)result.get("version"));
            game.setTitle((String)result.get("title"));
            game.setStatus((String)result.get("status"));
            game.setDesc((String)result.get("desc"));
            game.setDowncount(((Double)result.get("downcount")).intValue());
            game.setDownuri((String)result.get("downuri"));
            game.setIngamepay((String)result.get("ingamepay"));
            game.setPicuri((String)result.get("picuri"));
            game.setPkgname((String)result.get("pkgname"));
            game.setStars((String)result.get("stars"));
            if(result.containsField("channels")){
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                BasicDBList items = (BasicDBList)result.get("channels");
                for(Object item:items){
                    list.add(((DBObject)item).toMap());
                }
                game.setChannels(list);
            }
            return game;
        } catch (Exception e){
            loggger.error("something wrong", e);
            return null;
        }
    }

    @Override
    public Game insertGame(Game game) {
        datastore.save(game);
        return game;
    }

    @Override
    public String updateGame(String id, Map<String, Object> gameInfo) {
        Query<Game> gameQuery = datastore.createQuery(Game.class).filter("id", id);
        UpdateOperations<Game> updateOperations = datastore.createUpdateOperations(Game.class);
        if(gameInfo.containsKey("title")){
            updateOperations.set("title",(String)gameInfo.get("title"));
        }
        if(gameInfo.containsKey("pkgname")){
            updateOperations.set("pkgname",(String)gameInfo.get("pkgname"));
        }
        if(gameInfo.containsKey("picuri")){
            updateOperations.set("picuri",(String)gameInfo.get("picuri"));
        }
        if(gameInfo.containsKey("desc")){
            updateOperations.set("desc",(String)gameInfo.get("desc"));
        }
        if(gameInfo.containsKey("stars")){
            updateOperations.set("stars",(String)gameInfo.get("stars"));
        }
        if(gameInfo.containsKey("ingamepay")){
            updateOperations.set("ingamepay",(String)gameInfo.get("ingamepay"));
        }
        if(gameInfo.containsKey("downuri")){
            updateOperations.set("downuri",(String)gameInfo.get("downuri"));
        }
        if(gameInfo.containsKey("type")){
            updateOperations.set("type",(String)gameInfo.get("type"));
        }
        if(gameInfo.containsKey("status")){
            updateOperations.set("status",(String)gameInfo.get("status"));
        }
        if(gameInfo.containsKey("version")){
            updateOperations.set("version",(String)gameInfo.get("version"));
        }
        if(gameInfo.containsKey("channels")){
            updateOperations.set("channels",( List<Map<String,Object>>)gameInfo.get("channels"));
        }
        datastore.findAndModify(gameQuery, updateOperations);
        return id;
    }
}
