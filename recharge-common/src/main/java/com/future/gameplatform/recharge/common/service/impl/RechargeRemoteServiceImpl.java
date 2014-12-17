package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.common.service.RechargeRemoteInterface;
import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-17
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class RechargeRemoteServiceImpl implements RechargeRemoteInterface {

    private SmsRechargeDao smsRechargeDao;

    public void setSmsRechargeDao(SmsRechargeDao smsRechargeDao) {
        this.smsRechargeDao = smsRechargeDao;
    }

    @Override
    public List<Map<String, String>> statisticRecharge(String selectedShortcode, String selectedChannel, String beginDate, String endDate) {
        List<SmsRecharge> listRecharge = smsRechargeDao.listForStatistic(selectedShortcode, selectedChannel, beginDate, endDate);

        return null;
    }

    @Override
    public List<Map<String, String>> queryRecharge(String shortcode, String mobile, String orderno, String id, String begindate, String enddate) {
        List<SmsRecharge> listRecharge = smsRechargeDao.listForQuery(shortcode, mobile, orderno, id, begindate, enddate);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
