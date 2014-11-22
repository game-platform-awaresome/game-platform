package com.future.gameplatform.trade.dao;

import com.future.gameplatform.trade.entity.SmsNotice;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface SmsNoticeDao {

    String save(SmsNotice smsNotice);

    String update(String id, Map<String, Object> updateInfo);

    List<SmsNotice> getByUid(String uid);

    List<SmsNotice> getByAppid(String appid);

    List<SmsNotice> getByAppidUid(String appid, String uid);

    SmsNotice getById(String id);

}
