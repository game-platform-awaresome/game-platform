package com.future.gameplatform.account.game.dao.impl;

import com.future.gameplatform.account.game.dao.TradeAppAccountDao;
import com.future.gameplatform.account.game.entity.TradeAppAccount;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeAppAccountDaoImpl extends BasicDaoImpl implements TradeAppAccountDao {
    public TradeAppAccountDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public TradeAppAccount getById(String appid) {
        return datastore.get(TradeAppAccount.class, appid);
    }
}
