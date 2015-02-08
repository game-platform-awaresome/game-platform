package com.future.gameplatform.recharge.common.dao.impl;

import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.google.code.morphia.query.Query;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @Override
    public List<SmsRecharge> listForQuery(String shortcode, String mobile, String orderno, String id, String begindate, String enddate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Query<SmsRecharge> query = datastore.find(SmsRecharge.class).filter("state > ", 3);
        try {
            query.filter("createdDate > ", simpleDateFormat.parse(begindate)).filter("createdDate < ", simpleDateFormat.parse(enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(StringUtils.hasText(shortcode)){
            query.filter("shortcode", shortcode);
        }
        if(StringUtils.hasText(mobile)){
            query.filter("mobile", mobile);
        }
        if(StringUtils.hasText(orderno)){
            query.filter("orderno", orderno);
        }
        if(StringUtils.hasText(id)){
            query.filter("_id", id);
        }
        return query.asList();
    }

    @Override
    public List<SmsRecharge> listForStatistic(String selectedShortcode, String selectedChannel, String beginDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       // Query<SmsRecharge> query = datastore.find(SmsRecharge.class).filter("state > ", 3);
        Query<SmsRecharge> query = datastore.find(SmsRecharge.class);
        try {
            query.filter("createdDate > ", simpleDateFormat.parse(beginDate)).filter("createdDate < ", simpleDateFormat.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(selectedShortcode != null && !selectedShortcode.startsWith("all_")){
            query.filter("shortcode", selectedShortcode);
        }
        if(selectedChannel != null && !selectedChannel.startsWith("all_")){
            query.field("channel").startsWithIgnoreCase(selectedChannel);
        }
        return query.asList();
    }
}
