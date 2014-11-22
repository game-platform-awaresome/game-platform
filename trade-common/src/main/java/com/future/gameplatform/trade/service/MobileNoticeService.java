package com.future.gameplatform.trade.service;

import com.future.gameplatform.trade.entity.JCHShortRequestEntity;
import com.future.gameplatform.trade.entity.RequestEntity;
import com.future.gameplatform.trade.bean.ResponseEntity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface MobileNoticeService {

    String notify(String uid, String token, Map<String,Object> noticeInfo);

    ResponseEntity executeJCHNotice(RequestEntity requestEntity);

    ResponseEntity executeJCHShortNotice(JCHShortRequestEntity jchShortRequestEntity);

    String executeDCDBnotice(String from, String to, String msg, String linkid, String serviceid, String servicecode, String backurl, String msgtype);

    String executeMHnotice(String mchNo, String phone, String fee, String MarketCode,String orderId, String sign);
}
