package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.DCDBNoticeDao;
import com.future.gameplatform.trade.entity.DCDBNotice;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class DCDBNoticeDaoImpl extends BasicDaoImpl implements DCDBNoticeDao {

    public DCDBNoticeDaoImpl(String mongoDomain,String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(DCDBNotice dcdbNotice) {
        datastore.save(dcdbNotice);
        return dcdbNotice.getId();
    }
}
