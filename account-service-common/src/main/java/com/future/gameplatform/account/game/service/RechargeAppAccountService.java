package com.future.gameplatform.account.game.service;

import com.future.gameplatform.account.game.entity.AccountItem;
import com.future.gameplatform.account.game.entity.RechargeAppAccount;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface RechargeAppAccountService {

    RechargeAppAccount  getById(String appid) ;

    List<RechargeAppAccount> listAll();

    String insert(Map<String, Object> accountInfo);

    String deleteById(String appid);

    String updateRechargeApp(String appid, Map<String, Object> updateInfo);

    String updateAccountChannel(String id, String channel, AccountItem item);

    String updateOrInsertDefaultAccountChannel(AccountItem item);

    RechargeAppAccount getByCode(String shortcode, String operator, String fee, String version);

    String getKeyByCode(String shortcode);

    List<RechargeAppAccount> listAllSimple();
}
