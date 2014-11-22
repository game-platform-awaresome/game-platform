package com.future.gameplatform.trade.service;

import com.future.gameplatform.trade.entity.Trade;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface TradeService {
    String save(String uid, String token, Map<String, Object> tradeInfo);

    String update(String tradeid, String token, Map<String, Object> tradeInfo);

    List<Trade> listByUid(String uid);

    Trade getById(String tradeid);

    Trade getByOderno(String appid, String orderno);
}
