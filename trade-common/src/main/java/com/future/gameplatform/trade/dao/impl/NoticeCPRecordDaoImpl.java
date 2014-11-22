package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.NoticeCPRecordDao;
import com.future.gameplatform.trade.entity.NoticeCPRecord;
import com.future.gameplatform.trade.util.TradeString;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class NoticeCPRecordDaoImpl extends BasicDaoImpl implements NoticeCPRecordDao {

    private final static Logger logger = LoggerFactory.getLogger(NoticeCPRecordDaoImpl.class);


    public NoticeCPRecordDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(NoticeCPRecord noticeCPRecord) {
        datastore.save(noticeCPRecord);
        return noticeCPRecord.getId();
    }

    @Override
    public NoticeCPRecord getById(String id) {
        return datastore.get(NoticeCPRecord.class, id);
    }

    @Override
    public NoticeCPRecord updateResult(String id, String result) {
        Query<NoticeCPRecord> query = datastore.createQuery(NoticeCPRecord.class).filter("_id", id);
        if(result.equalsIgnoreCase(TradeString.RESULT_OK)){
            UpdateOperations<NoticeCPRecord> updateOperations = datastore.createUpdateOperations(NoticeCPRecord.class);
            updateOperations.set("noticeResult", result);
            updateOperations.set("lastNoticeTimestamp",new Date().getTime());
            return datastore.findAndModify(query, updateOperations);
        }{
            NoticeCPRecord noticeCPRecord = getById(id);
            if(noticeCPRecord.getNoticeResult() != null && noticeCPRecord.getNoticeResult().equalsIgnoreCase(TradeString.RESULT_FAILED)){
                return noticeCPRecord;
            }
            if(noticeCPRecord.getFailedTimes() == 5){
                UpdateOperations<NoticeCPRecord> updateOperations = datastore.createUpdateOperations(NoticeCPRecord.class);
                updateOperations.set("noticeResult", TradeString.RESULT_FAILED);
                updateOperations.set("failedTimes", 6);
                updateOperations.set("lastNoticeTimestamp", new Date().getTime());
                return datastore.findAndModify(query, updateOperations);
            }else if(noticeCPRecord.getFailedTimes() < 5){
                UpdateOperations<NoticeCPRecord> updateOperations = datastore.createUpdateOperations(NoticeCPRecord.class);
                updateOperations.set("failedTimes", noticeCPRecord.getFailedTimes() + 1);
                updateOperations.set("lastNoticeTimestamp", new Date().getTime());
                return datastore.findAndModify(query, updateOperations);
            }
        }
        return query.get();
    }

    @Override
    public List<NoticeCPRecord> listPendingRecord() {
        Query<NoticeCPRecord> query = datastore.createQuery(NoticeCPRecord.class);
        query.filter("noticeResult", TradeString.RESULT_INIT).field("lastNoticeTimestamp").lessThan(new Date().getTime() - 10 * 60 * 1000).order("lastNoticeTimestamp");
        return query.asList();
    }
}
