package com.future.gameplatform.trade.dao;

import com.future.gameplatform.trade.entity.Trade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface TradeDao {
    String save(Trade trade);

    Trade updateResult(String tradeid, String result);

    List<Trade> listByUid(String uid);

    Trade getById(String tradeid);

    Trade getByOrderno(String appid, String orderno);
}
