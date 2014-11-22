package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.SmsNoticeDao;
import com.future.gameplatform.trade.entity.SmsNotice;
import com.future.gameplatform.trade.util.RechargeString;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class SmsNoticeDaoImpl extends BasicDaoImpl implements SmsNoticeDao {

    private final static Logger logger = LoggerFactory.getLogger(SmsNoticeDaoImpl.class);

    public SmsNoticeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(SmsNotice smsNotice) {
        logger.debug("save smsnotice info with id:[{}]", smsNotice.getId());
        datastore.save(smsNotice);
        return smsNotice.getId();
    }

    @Override
    public String update(String id, Map<String, Object> updateInfo) {
        logger.debug("update smsnotice info with id:[{}]", id);
        Query<SmsNotice> query = datastore.createQuery(SmsNotice.class).filter("_id",id);
        if(updateInfo != null && updateInfo.containsKey("status")){
            UpdateOperations<SmsNotice> updateOperations = datastore.createUpdateOperations(SmsNotice.class).set("status",(String)updateInfo.get("status"));
            datastore.findAndModify(query, updateOperations);
        }
        return RechargeString.RESULT_OK;
    }

    @Override
    public List<SmsNotice> getByUid(String uid) {
        return datastore.find(SmsNotice.class).filter("uid", uid).asList();
    }

    @Override
    public List<SmsNotice> getByAppid(String appid) {
        return datastore.find(SmsNotice.class).filter("appid",appid).asList();
    }

    @Override
    public List<SmsNotice> getByAppidUid(String appid, String uid) {
        return datastore.find(SmsNotice.class).filter("uid", uid).filter("appid",appid).asList();
    }

    @Override
    public SmsNotice getById(String id) {
        return datastore.get(SmsNotice.class, id);
    }
}
