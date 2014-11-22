package com.future.gameplatform.warehouse.service.impl;

import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.warehouse.GameString;
import com.future.gameplatform.warehouse.dao.GameDao;
import com.future.gameplatform.warehouse.entity.Game;
import com.future.gameplatform.warehouse.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private GameDao gameDao;

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public List<Game> listByType(String type) {
        return gameDao.listByType(type);
    }

    @Override
    public Game getGame(String id) {
        return gameDao.getById(id);
    }

    @Override
    public String insertGame(Map<String, Object> gameInfo) {
        Game game = new Game();
        game.setId(IdFactory.generateId());
        game.setCreatedTime(new Date());
        game.setDesc((String)gameInfo.get("desc"));
        game.setDowncount(0);
        game.setDownuri((String)gameInfo.get("downuri"));
        game.setIngamepay((String)gameInfo.get("ingamepay"));
        game.setLastUpdateTime(new Date());
        game.setPicuri((String)gameInfo.get("picuri"));
        game.setPkgname((String)gameInfo.get("pkgname"));
        game.setStars((String)gameInfo.get("stars"));
        game.setStatus(GameString.GAME_STATUS_OK);
        game.setTitle((String)gameInfo.get("title"));
        game.setType((String)gameInfo.get("type"));
        game.setVersion((String)gameInfo.get("version"));
        if(gameInfo.containsKey("channels")){
            game.setChannels((List<Map<String,Object>>)gameInfo.get("channels"));
        }
        gameDao.insertGame(game);
        return game.getId();
    }

    @Override
    public String updateGame(String id, Map<String, Object> gameInfo) {
        return gameDao.updateGame(id, gameInfo);
    }

    @Override
    public Map<String, String> getGameVersion(String gamePlatformResourceId, Map<String, Object> versionInfo) {
        String channelid = (String)versionInfo.get("channelid");
        String version = (String)versionInfo.get("version");
        logger.debug("getversion with cid:[{}],v:[{}]",channelid, version);
        Game game = this.getGame(gamePlatformResourceId);
        List<Map<String,Object>> channels = game.getChannels();
        for(Map<String,Object> channel:channels){
            if(channelid.equalsIgnoreCase((String)channel.get("cid"))){
                String cversion = (String)channel.get("version");
                String update = (String)channel.get("update");
                String downuri = (String)channel.get("downuri");
                if(version.compareTo(cversion) < 0){
                    Map<String, String> result = new HashMap<String, String>();
                    result.put("downuri", downuri);
                    result.put("version", cversion);
                    result.put("update", update);
                    return result;
                } else {
                    Map<String, String> result = new HashMap<String, String>();
                    result.put("downuri", downuri);
                    result.put("version", cversion);
                    result.put("update", "casual");
                    return result;
                }
            }
        }
        return null;
    }
}
