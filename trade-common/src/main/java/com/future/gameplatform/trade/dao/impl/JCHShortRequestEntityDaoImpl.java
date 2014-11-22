package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.JCHShortRequestEntityDao;
import com.future.gameplatform.trade.entity.JCHShortRequestEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class JCHShortRequestEntityDaoImpl extends BasicDaoImpl implements JCHShortRequestEntityDao {
    public JCHShortRequestEntityDaoImpl(String mongoDomain,String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(JCHShortRequestEntity requestEntity) {
        datastore.save(requestEntity);
        return requestEntity.getId();
    }
}
