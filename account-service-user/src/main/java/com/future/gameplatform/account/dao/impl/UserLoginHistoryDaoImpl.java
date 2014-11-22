package com.future.gameplatform.account.dao.impl;

import com.future.gameplatform.account.dao.UserLoginHistoryDao;
import com.future.gameplatform.account.entity.UserLoginHistory;
import com.google.code.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class UserLoginHistoryDaoImpl extends UserBasicDaoImpl implements UserLoginHistoryDao  {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginHistoryDaoImpl.class);

    public UserLoginHistoryDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String insert(UserLoginHistory userLoginHistory) {
        datastore.save(userLoginHistory);
        return userLoginHistory.getId();
    }

    @Override
    public long countByChannel(String cid) {
        Query<UserLoginHistory> query = datastore.find(UserLoginHistory.class, "channelid", cid);
        return datastore.getCount(query);
    }

    @Override
    public long countByUser(String uid) {
        Query<UserLoginHistory> query = datastore.find(UserLoginHistory.class, "uid", uid);
        return datastore.getCount(query);
    }
}
