package com.future.gameplatform.warehouse.service;

import com.future.gameplatform.warehouse.entity.Game;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface GameService {
    List<Game> listByType(String type);

    Game getGame(String id);

    String insertGame(Map<String,Object> gameInfo);

    String updateGame(String id, Map<String,Object> gameInfo);

    Map<String, String> getGameVersion(String gamePlatformResourceId, Map<String,Object> versionInfo);
}
