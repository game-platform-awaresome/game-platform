package com.future.gameplatform.account.game.service;

import java.util.Map;


public interface UserAccountService {

    String tradeAccounting(String uid, Map<String, Object> accountingInfo);

    String rechargeAccounting(String uid, Map<String, Object> accountingInfo);

    long accountingbalance(String uid, Map<String, Object> balanceinfo);
}
