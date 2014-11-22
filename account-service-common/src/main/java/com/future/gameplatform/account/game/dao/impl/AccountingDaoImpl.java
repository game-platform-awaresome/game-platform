package com.future.gameplatform.account.game.dao.impl;


import com.future.gameplatform.account.game.dao.AccountingDao;
import com.future.gameplatform.account.game.entity.Accounting;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class AccountingDaoImpl extends BasicDaoImpl implements AccountingDao {


    public AccountingDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public Accounting save(Accounting accounting) {
        datastore.save(accounting);
        return accounting;
    }
}
