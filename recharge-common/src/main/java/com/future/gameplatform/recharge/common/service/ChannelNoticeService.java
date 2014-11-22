package com.future.gameplatform.recharge.common.service;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.util.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelNoticeService {
    ServiceResult<String> receiveNotice(String mchNo);

    SmsRecharge getSmsRechargeByCode(String shortcode, String orderno);

    ServiceResult<String> receivePlainSmsNotice(String mchNo, String mobile);

    ServiceResult<String> receiveAlipayNotice(String no);
}
