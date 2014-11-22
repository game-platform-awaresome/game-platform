package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.TradeDao;
import com.future.gameplatform.trade.entity.Trade;
import com.future.gameplatform.trade.util.TradeString;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeDaoImpl extends BasicDaoImpl implements TradeDao {

    public TradeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(Trade trade) {
        datastore.save(trade);
        return TradeString.RESULT_OK;
    }

    @Override
    public Trade updateResult(String tradeid, String result) {
        Query<Trade> tradeQuery = datastore.createQuery(Trade.class);
        UpdateOperations<Trade> uo = datastore.createUpdateOperations(Trade.class).set("result",result);
        return datastore.findAndModify(tradeQuery, uo);
    }

    @Override
    public List<Trade> listByUid(String uid) {
        return datastore.find(Trade.class, "uid", uid).filter("status", TradeString.STATUS_OK).asList();
    }

    @Override
    public Trade getById(String tradeid) {
        return datastore.get(Trade.class, tradeid);
    }

    @Override
    public Trade getByOrderno(String appid, String orderno) {
        return datastore.find(Trade.class).filter("exorderno", orderno).filter("appid",appid).get();
    }
}
