package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.RequestEntityDao;
import com.future.gameplatform.trade.entity.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RequestEntityDaoImpl extends BasicDaoImpl implements RequestEntityDao {

    private final static Logger logger = LoggerFactory.getLogger(RequestEntityDaoImpl.class);

    public RequestEntityDaoImpl(String mongoDomain,String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(RequestEntity requestEntity) {
        datastore.save(requestEntity);
        return requestEntity.getId();
    }
}
