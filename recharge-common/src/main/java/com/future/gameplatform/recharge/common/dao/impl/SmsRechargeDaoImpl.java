package com.future.gameplatform.recharge.common.dao.impl;

import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class SmsRechargeDaoImpl extends BasicDaoImpl implements SmsRechargeDao {

    public SmsRechargeDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }


    @Override
    public String save(SmsRecharge smsRecharge) {
        datastore.save(smsRecharge);
        return smsRecharge.getId();
    }

    @Override
    public SmsRecharge getByCode(String shortCode, String orderno) {
        SmsRecharge smsRecharge = datastore.find(SmsRecharge.class).filter("shortcode", shortCode).filter("orderno",orderno).get();
        return smsRecharge;
    }

    @Override
    public SmsRecharge getById(String mchNo) {
        return datastore.get(SmsRecharge.class, mchNo);
    }
}
