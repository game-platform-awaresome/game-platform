package com.future.gameplatform.account.dao.impl;

import com.future.gameplatform.account.dao.UserIdsDao;
import com.future.gameplatform.account.entity.UserIds;
import com.future.gameplatform.common.id.IdFactory;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class UserIdsDaoImpl extends UserBasicDaoImpl implements UserIdsDao {

    private final static Logger logger = LoggerFactory.getLogger(UserIdsDaoImpl.class);

    public UserIdsDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
        initIds();
    }

    private void initIds() {
        Query<UserIds> query = datastore.createQuery(UserIds.class);
        UserIds userIds = query.get();
        if(userIds == null){
            userIds = new UserIds();
            userIds.setId(IdFactory.generateId());
            userIds.setName("userid");
            userIds.setValue(10000000);
            datastore.save(userIds);
        }
    }

    @Override
    public synchronized int getNextIdValue() {
        logger.debug("getNextId daoimpl");
        Query<UserIds> query = datastore.createQuery(UserIds.class);
        UserIds userIds = query.get();
        UpdateOperations<UserIds> uo = datastore.createUpdateOperations(UserIds.class).set("value",userIds.getValue() + 1);
        return datastore.findAndModify(query, uo).getValue();
    }
}
