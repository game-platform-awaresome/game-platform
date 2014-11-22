package com.future.gameplatform.trade.dao;

import com.future.gameplatform.trade.entity.Recharge;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface RechargeDao {
    String save(Recharge recharge);

    String update(String rechargeId, Map<String,Object> rechargeInfo);

    List<Recharge> listByUid(String uid);

    Recharge getById(String rechargeid);
}
