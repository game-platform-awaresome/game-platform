package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.MHNoticeDao;
import com.future.gameplatform.trade.entity.MHNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class MHNoticeDaoImpl extends BasicDaoImpl implements MHNoticeDao {

    private final static Logger logger = LoggerFactory.getLogger(MHNoticeDaoImpl.class);

    public MHNoticeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(MHNotice mhNotice) {
        datastore.save(mhNotice);
        return mhNotice.getId();
    }
}
