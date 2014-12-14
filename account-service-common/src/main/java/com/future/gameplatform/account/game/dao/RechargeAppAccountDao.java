package com.future.gameplatform.account.game.dao;

import com.future.gameplatform.account.game.entity.RechargeAppAccount;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface RechargeAppAccountDao {

    RechargeAppAccount insert(RechargeAppAccount rechargeAppAccount);

    RechargeAppAccount getById(String appid);

    List<RechargeAppAccount> listAll();

    void deleteById(String appid);

    String update(String appid, Map<String, Object> updateInfo);

    RechargeAppAccount getByShortcode(String shortcode);

    List<RechargeAppAccount> listAllSimple();
}
