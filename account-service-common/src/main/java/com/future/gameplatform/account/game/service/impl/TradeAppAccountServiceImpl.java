package com.future.gameplatform.account.game.service.impl;

import com.future.gameplatform.account.game.dao.TradeAppAccountDao;
import com.future.gameplatform.account.game.entity.TradeAppAccount;
import com.future.gameplatform.account.game.service.TradeAppAccountService;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeAppAccountServiceImpl implements TradeAppAccountService {

    private TradeAppAccountDao tradeAppAccountDao;

    public void setTradeAppAccountDao(TradeAppAccountDao tradeAppAccountDao) {
        this.tradeAppAccountDao = tradeAppAccountDao;
    }

    @Override
    public TradeAppAccount getById(String appid) {
        return tradeAppAccountDao.getById(appid);
    }
}
