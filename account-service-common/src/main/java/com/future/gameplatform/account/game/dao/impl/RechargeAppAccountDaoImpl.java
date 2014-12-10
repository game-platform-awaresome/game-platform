package com.future.gameplatform.account.game.dao.impl;

import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.dao.RechargeAppAccountDao;
import com.future.gameplatform.account.game.entity.*;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.*;
import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeAppAccountDaoImpl extends BasicDaoImpl implements RechargeAppAccountDao {


    public RechargeAppAccountDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public RechargeAppAccount insert(RechargeAppAccount rechargeAppAccount) {
        DBObject accountObject = account2DBObject(rechargeAppAccount);
        DBCollection dbCollection = datastore.getCollection(RechargeAppAccount.class);
        dbCollection.save(accountObject);
        return rechargeAppAccount;
    }


    @Override
    public RechargeAppAccount getById(String appid) {
        try{
            DBCollection dbCollection = datastore.getCollection(RechargeAppAccount.class);
            DBObject dbObject = dbCollection.findOne(new BasicDBObject("_id", appid));
            return this.DBObject2Account(dbObject);
        } catch (Exception e){
            logger.error("error get by id", e);
            return null;
        }
    }

    @Override
    public List<RechargeAppAccount> listAll() {
        logger.debug("get account list all");
        try{
            DBCollection dbCollection = datastore.getCollection(RechargeAppAccount.class);
            DBCursor cur = dbCollection.find();
            List<RechargeAppAccount> accountList = new ArrayList<RechargeAppAccount>();
            while (cur.hasNext()) {
                accountList.add(DBObject2Account(cur.next()));
            }
            return accountList;
        }catch (Exception e){
            logger.error("get account list all", e);
            return null;
        }
    }

    @Override
    public void deleteById(String appid) {
       datastore.delete(RechargeAppAccount.class, appid);
    }

    @Override
    public String update(String appid, Map<String, Object> updateInfo) {
        Query<RechargeAppAccount> query = datastore.createQuery(RechargeAppAccount.class).filter("_id",appid);
        UpdateOperations<RechargeAppAccount> updateOperations = datastore.createUpdateOperations(RechargeAppAccount.class);
        if(updateInfo.containsKey(UserAccountString.STATUS)){
            updateOperations.set(UserAccountString.STATUS, (String)updateInfo.get(UserAccountString.STATUS));
        }
        datastore.findAndModify(query, updateOperations);
        return UserAccountString.RESULT_OK;
    }

    @Override
    /**
    public RechargeAppAccount getByShortcode(String shortcode) {
        try{
            RechargeAppAccount rechargeAppAccount = datastore.find(RechargeAppAccount.class).filter("shortcode", shortcode).get();
            List<AccountItem> itemList = rechargeAppAccount.getItems();
            Iterator<AccountItem> itemIterator = itemList.iterator();
            if(itemIterator.hasNext()){
                AccountItem item = itemIterator.next();
                if(!item.getStatus().equalsIgnoreCase(UserAccountString.STATUS_OK)){
                    itemIterator.remove();
                }
            }
            return rechargeAppAccount;
        } catch (Exception e){
            logger.error("error get by shortcode", e);
            return null;
        }
    }
    **/
    public RechargeAppAccount getByShortcode(String shortcode) {
        logger.debug("get account by shortcode:[{}]", shortcode);
        try{
            DBCollection dbCollection = datastore.getCollection(RechargeAppAccount.class);
            DBObject dbObject = dbCollection.findOne(new BasicDBObject("shortcode", shortcode));
            return this.DBObject2Account(dbObject);
        }catch (Exception e){
            logger.error("get account by shortcode [{}]", shortcode, e);
            return null;
        }
    }

    private RechargeAppAccount DBObject2Account(DBObject dbObject){
        if(dbObject == null){
            return null;
        }
        RechargeAppAccount rechargeAppAccount = new RechargeAppAccount();
        rechargeAppAccount.setId(getFieldValue(dbObject, "_id"));
        rechargeAppAccount.setShortcode(getFieldValue(dbObject, "shortcode"));
        rechargeAppAccount.setSearchurl(getFieldValue(dbObject, "searchurl"));
        rechargeAppAccount.setNoticeurl(getFieldValue(dbObject, "noticeurl"));
        rechargeAppAccount.setAppkey(getFieldValue(dbObject, "appkey"));
        rechargeAppAccount.setStatus(getFieldValue(dbObject, "status"));
        rechargeAppAccount.setCpName(getFieldValue(dbObject, "cpName"));
        BasicDBList itemList =  (BasicDBList)dbObject.get("items");
        List<AccountItem> items = new ArrayList<AccountItem>();
        Iterator iterator = itemList.iterator();
        while (iterator.hasNext()){
            DBObject itemEntry = (DBObject)iterator.next();
            AccountItem accountItem = new AccountItem();
            accountItem.setOperator(getFieldValue(itemEntry, "operator"));
            accountItem.setCate(getFieldValue(itemEntry, "cate"));
            accountItem.setFee(getFieldValue(itemEntry, "fee"));
            accountItem.setFee_min(getFieldValue(itemEntry, "fee_min"));
            accountItem.setFee_max(getFieldValue(itemEntry, "fee_max"));
            accountItem.setChannel(getFieldValue(itemEntry, "channel"));
            accountItem.setChannelName(getFieldValue(itemEntry, "channelName"));
            accountItem.setSortcode((int)Float.parseFloat(getFieldValue(itemEntry, "sortcode")));
            accountItem.setUrl_noticeno(getFieldValue(itemEntry, "url_noticeno"));
            accountItem.setUrl_postcode(getFieldValue(itemEntry, "url_postcode"));
            accountItem.setVersion(getFieldValue(itemEntry, "version"));
            accountItem.setStatus(getFieldValue(itemEntry, "status"));
            accountItem.setInstruct_fixed(getFieldValue(itemEntry, "instruct_fixed"));
            accountItem.setInstruct_sub(getFieldValue(itemEntry, "instruct_sub"));
            accountItem.setTargetcode(getFieldValue(itemEntry, "targetcode"));

            BasicDBList filterList = (BasicDBList)itemEntry.get("filters");
            if(filterList != null){
                List<SMSFilter> filters = new ArrayList<SMSFilter>();
                Iterator filterIt = filterList.iterator();
                while (filterIt.hasNext()){
                    DBObject filterEntry = (DBObject)filterIt.next();
                    SMSFilter smsFilter = new SMSFilter();
                    smsFilter.setFromCode(getFieldValue(filterEntry, "fromCode"));
                    if(filterEntry.get("keywords") != null) {
                        BasicDBList keywords = (BasicDBList)filterEntry.get("keywords");
                        List<Keyword> keywordList = new ArrayList<Keyword>();
                        Iterator keyworkIt = keywords.iterator();
                        while (keyworkIt.hasNext()){
                            Object keywordEntry = keyworkIt.next();
                            Keyword keyword = new Keyword();
                            if(keywordEntry instanceof DBObject){
                                keyword.setWord(getFieldValue((DBObject)keywordEntry,"word"));
                            }else {
                                keyword.setWord(getTrimValue((String)keywordEntry, "word"));
                            }
                            keywordList.add(keyword);
                        }
                        smsFilter.setKeywords(keywordList);
                    }
                    if(filterEntry.get("keyValids") != null){
                        BasicDBList keyValids = (BasicDBList)filterEntry.get("keyValids");
                        List<KeyValid> keyValidList = new ArrayList<KeyValid>();
                        Iterator keyvalidIt = keyValids.iterator();
                        while (keyvalidIt.hasNext()){
                            Object keyvalidEntry = keyvalidIt.next();
                            KeyValid keyValid = new KeyValid();
                            if(keyvalidEntry instanceof DBObject){
                                keyValid.setKey(getFieldValue((DBObject)keyvalidEntry, "key"));
                            }else {
                                keyValid.setKey(getTrimValue((String)keyvalidEntry, "key"));
                            }
                            keyValidList.add(keyValid);
                        }
                        smsFilter.setKeyValids(keyValidList);
                    }
                    filters.add(smsFilter);
                }
                accountItem.setFilters(filters);
            }
            items.add(accountItem);
        }
        rechargeAppAccount.setItems(items);
        return rechargeAppAccount;
    }


    private DBObject account2DBObject(RechargeAppAccount rechargeAppAccount) {
        if(rechargeAppAccount == null){
            return null;
        }
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", rechargeAppAccount.getId());
        dbObject.put("shortcode",rechargeAppAccount.getShortcode());
        dbObject.put("searchurl",rechargeAppAccount.getSearchurl());
        dbObject.put("noticeurl",rechargeAppAccount.getNoticeurl());
        dbObject.put("appkey",rechargeAppAccount.getAppkey());
        dbObject.put("status", rechargeAppAccount.getStatus());
        dbObject.put("cpName", rechargeAppAccount.getCpName());

        BasicDBList items = new BasicDBList();
        Iterator<AccountItem> itemIterator = rechargeAppAccount.getItems().iterator();
        while (itemIterator.hasNext()){
            AccountItem itemEntry = itemIterator.next();
            DBObject itemObject = new BasicDBObject();
            itemObject.put("operator",itemEntry.getOperator());
            itemObject.put("cate",itemEntry.getCate());
            itemObject.put("fee",itemEntry.getFee());
            itemObject.put("fee_min",itemEntry.getFee_min());
            itemObject.put("fee_max",itemEntry.getFee_max());
            itemObject.put("channel",itemEntry.getChannel());
            itemObject.put("channelName",itemEntry.getChannelName());
            itemObject.put("sortcode",itemEntry.getSortcode());
            itemObject.put("url_noticeno",itemEntry.getUrl_noticeno());
            itemObject.put("url_postcode",itemEntry.getUrl_postcode());
            itemObject.put("version",itemEntry.getVersion());
            itemObject.put("status",itemEntry.getStatus());
            itemObject.put("instruct_fixed",itemEntry.getInstruct_fixed());
            itemObject.put("instruct_sub",itemEntry.getInstruct_sub());
            itemObject.put("targetcode",itemEntry.getTargetcode());

            if(itemEntry.getFilters() != null){
                BasicDBList filters = new BasicDBList();
                Iterator<SMSFilter> filterIterator = itemEntry.getFilters().iterator();
                while (filterIterator.hasNext()){
                    SMSFilter filterEntry = filterIterator.next();
                    DBObject filterObject = new BasicDBObject();
                    filterObject.put("fromCode", filterEntry.getFromCode());
                    if(filterEntry.getKeywords() != null){
                        BasicDBList keywords = new BasicDBList();
                        Iterator<Keyword> keywordIterator = filterEntry.getKeywords().iterator();
                        while (keywordIterator.hasNext()){
                            Keyword keywordEntry = keywordIterator.next();
                            DBObject keywordObject = new BasicDBObject();
                            keywordObject.put("word",keywordEntry.getWord());
                            keywords.add(keywordObject);
                        }
                        filterObject.put("keywords", keywords);
                    }
                    if(filterEntry.getKeyValids() != null){
                        BasicDBList keyvalids = new BasicDBList();
                        Iterator<KeyValid> keyValidIterator = filterEntry.getKeyValids().iterator();
                        while (keyValidIterator.hasNext()){
                            KeyValid keyValidEntry = keyValidIterator.next();
                            DBObject keyValidObject = new BasicDBObject();
                            keyValidObject.put("key", keyValidEntry.getKey());
                            keyvalids.add(keyValidObject);
                        }
                        filterObject.put("keyValids",keyvalids);
                    }

                    filters.add(filterObject);
                }
                itemObject.put("filters", filters);
            }
            items.add(itemObject);
        }
        dbObject.put("items", items);

        return dbObject;
    }


    private String getTrimValue(String keywordEntry, String word) {
        if(!keywordEntry.contains(word))
            return null;
        int lastQuotesIndex = keywordEntry.lastIndexOf("\"");
        String temp = keywordEntry.substring(0, lastQuotesIndex);
        lastQuotesIndex = temp.lastIndexOf("\"");
        temp = temp.substring(lastQuotesIndex+1);
        return temp;
    }

    private String getFieldValue(DBObject dbObject, String fieldName){
        if(dbObject.get(fieldName) == null)
            return null;
        return dbObject.get(fieldName).toString();
    }

}
