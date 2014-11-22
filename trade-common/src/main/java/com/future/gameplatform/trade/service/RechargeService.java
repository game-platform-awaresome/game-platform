package com.future.gameplatform.trade.service;

import com.future.gameplatform.trade.entity.Recharge;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface RechargeService {
    String insertRecharge(String uid, Map<String, Object> rechargeInfo);

    String updateRecharge(String uid, String rechargeId, String token, Map<String, Object> rechargeInfo);

    List<Recharge> listByUid(String uid);

    Recharge getById(String rechargeid);

    void doPayNotice(String exorderno, String transid, String appid, Integer waresid, Integer feetype, Integer money, Integer count, Integer result, Integer transtype, String transtime, String cpprivate);

    void doSmsNotice(String uid, String token, Map<String,Object> noticeInfo);
}
