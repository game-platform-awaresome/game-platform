package com.future.gameplatform.trade.dao.impl;

import com.future.gameplatform.trade.dao.RechargeDao;
import com.future.gameplatform.trade.entity.Recharge;
import com.future.gameplatform.trade.util.RechargeString;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeDaoImpl extends BasicDaoImpl implements RechargeDao {

    private static final Logger logger = LoggerFactory.getLogger(RechargeDaoImpl.class);

    public RechargeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public String save(Recharge recharge) {
        logger.debug("save recharge info with id:[{}]",recharge.getOrderno());
        datastore.save(recharge);
        return recharge.getOrderno();
    }

    @Override
    public String update(String rechargeId, Map<String, Object> rechargeInfo) {
        logger.debug("update recharge info with id:[{}]", rechargeId);
        try{
        Query<Recharge> rechargeQuery = datastore.createQuery(Recharge.class).filter("_id", rechargeId);
        UpdateOperations<Recharge> rechargeUpdateOperations = datastore.createUpdateOperations(Recharge.class).set("lastUpdateTime",new Date());
        if(rechargeInfo.containsKey("transid")){
            rechargeUpdateOperations.set("transid",(String)rechargeInfo.get("transid"));
        }
        if(rechargeInfo.containsKey("transtype")){
            rechargeUpdateOperations.set("transtype",String.valueOf(rechargeInfo.get("transtype")));
        }
        if(rechargeInfo.containsKey("privateinfo")){
            rechargeUpdateOperations.set("privateinfo",(String)rechargeInfo.get("privateinfo"));
        }
        if(rechargeInfo.containsKey("status")){
            rechargeUpdateOperations.set("status",(String)rechargeInfo.get("status"));
        }
        if(rechargeInfo.containsKey("result")){
            rechargeUpdateOperations.set("result",String.valueOf(rechargeInfo.get("result")));
        }
        /**
        if(rechargeInfo.containsKey("transTime")){
            rechargeUpdateOperations.set("transTime",(String)rechargeInfo.get("transTime")).set("syncTime", new Date());
        }
         **/
        datastore.findAndModify(rechargeQuery, rechargeUpdateOperations);
        }catch (Exception e){
            logger.error("update recharge error", e);
            return RechargeString.RESULT_FAILED;
        }
        return RechargeString.RESULT_OK;
    }

    @Override
    public List<Recharge> listByUid(String uid) {
        logger.debug("list recharge info with uid:[{}]", uid);
        return datastore.find(Recharge.class, "uid", uid).asList();
    }

    @Override
    public Recharge getById(String rechargeid) {
        logger.debug("get recharge info with id:[{}]", rechargeid);
        return datastore.get(Recharge.class, rechargeid);
    }
}
