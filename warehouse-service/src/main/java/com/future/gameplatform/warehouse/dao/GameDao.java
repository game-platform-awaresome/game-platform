package com.future.gameplatform.warehouse.dao;

import com.future.gameplatform.warehouse.entity.Game;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface GameDao {
    List<Game> listByType(String type);

    Game getById(String id);

    Game insertGame(Game game);

    String updateGame(String id, Map<String,Object> gameInfo);
}
