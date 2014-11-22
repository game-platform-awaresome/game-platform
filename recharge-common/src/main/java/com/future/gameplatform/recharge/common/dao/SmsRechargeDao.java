package com.future.gameplatform.recharge.common.dao;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
public interface SmsRechargeDao {

    String save(SmsRecharge smsRecharge);

    SmsRecharge getByCode(String shortCode, String orderno);

    SmsRecharge getById(String mchNo);
}
