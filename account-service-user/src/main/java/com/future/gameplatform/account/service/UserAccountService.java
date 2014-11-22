package com.future.gameplatform.account.service;

import com.future.gameplatform.account.entity.User;

import java.util.List;
import java.util.Map;


public interface UserAccountService {

	String auth(String id, String key, String channelid);

    User getAccountById(String id);

    String register(Map<String, Object> info);

    User updateUser(String id, Map<String,Object> userInfo);

    String getValidCode(String mobile);

    String getbackUser(Map<String,Object> mobileInfo);

    String bindMobile(String uid, Map<String,Object> bindInfo);

    String doTradeNotice(String uid, Map<String,Object> tradeinfo);

    String doRechargeNotice(String uid, Map<String,Object> rechargeinfo);

    User accountingbalance(String uid,String token);
}
