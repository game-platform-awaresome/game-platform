package com.future.gameplatform.recharge.common.service;

import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.util.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public interface SdkNoticeService {

    ServiceResult<String> cmccsdkNotice(String shortCode, String orderno, String channel);

    ServiceResult<String> ctpageNotice(String shortCode, String orderno, String mobile, String fee, String channel);

    ServiceResult<String> ctpageResult(String shortCode, String orderno, String smscode);

    ServiceResult<SmsRecharge> cuDynamicNotice(String shortcode, String orderno, String fee);

    ServiceResult<String> cupageNotice(String shortCode, String orderno, String mobile, String fee, String channel);

    ServiceResult<String> cupageResult(String shortCode, String orderno, String smscode);

    ServiceResult<String> smsDirectNotice(String shortcode, String orderno, String mobile, String sms, String fee, String channel);

    ServiceResult<SmsRecharge> cuPlainSmsNotice(String shortcode, String orderno, String fee);

    ServiceResult<SmsRecharge> cuPlainSmsGameNotice(String shortcode, String orderno, String fee);

    ServiceResult<String> smsDirectCUNotice(String shortcode, String orderno, Object o, String sms, String fee, String channel);

    ServiceResult<SmsRecharge> cmccDynamicGameNotice(String shortcode, String orderno, String fee);

    ServiceResult<SmsRecharge> cmccDynamicSmsNotice(String shortcode, String orderno, String fee);

    ServiceResult<SmsRecharge> dynamicAllinoneNotice(String shortcode, String orderno, String fee, String channel);

    ServiceResult<String> alipayNoticeOrderno(String shortCode, String orderno, String channel, String fee);

}
