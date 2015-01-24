package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.service.SdkNoticeService;
import com.future.gameplatform.recharge.common.util.RechargeStateEnum;
import com.future.gameplatform.recharge.common.util.RechargeStatusEnum;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class SdkNoticeSerivceImpl implements SdkNoticeService {

    private static final Logger logger = LoggerFactory.getLogger(SdkNoticeSerivceImpl.class);

    private SmsRechargeDao smsRechargeDao;

    public void setSmsRechargeDao(SmsRechargeDao smsRechargeDao) {
        this.smsRechargeDao = smsRechargeDao;
    }

    private RequestChannelHelper requestChannelHelper;

    public void setRequestChannelHelper(RequestChannelHelper requestChannelHelper) {
        this.requestChannelHelper = requestChannelHelper;
    }

    private NoticeCpHelper noticeCpHelper;

    public void setNoticeCpHelper(NoticeCpHelper noticeCpHelper) {
        this.noticeCpHelper = noticeCpHelper;
    }

    @Override
    public ServiceResult<String> cmccsdkNotice(String shortCode, String orderno, String channel) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setOrderno(orderno);
        smsRecharge.setShortcode(shortCode);
        smsRecharge.setOperator("cmcc");
        smsRecharge.setChannel(channel);
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cmcc notice success with code:[{}],no:[{}]",shortCode,orderno);
        serviceResult.setSuccess(true);
        serviceResult.setValue(id);
        return serviceResult;
    }

    @Override
    public ServiceResult<String> ctpageNotice(String shortCode, String orderno, String mobile, String fee, String channel) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortCode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setMobile(mobile);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("ct");
        smsRecharge.setChannel(channel);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("ct notice success with code:[{}],no:[{}]",shortCode,orderno);
        ServiceResult<String> noticeChannelResult = requestChannelHelper.cpPageNotice(id,mobile,fee);
        logger.debug("ct request code from channel,result:[{}]", noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
            serviceResult.setSuccess(true);
            serviceResult.setValue(id);
        }else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
            serviceResult = noticeChannelResult;
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> ctpageResult(String shortCode, String orderno, String smscode) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(111);
            serviceResult.setErrorMessage("订单不存在");
            return serviceResult;
        }
        logger.debug("ct notice result with code:[{}],no:[{}]",shortCode,orderno);
        ServiceResult<String> noticeChannelResult = requestChannelHelper.cpPageResult(smsRecharge.getId(), smscode);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.CHANNEL_NOTICE.getIndex());
            smsRecharge.setChannelNoticeDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
            /**
            ServiceResult<String> serviceResult1 = noticeCpHelper.noticeCp(shortCode,orderno,smsRecharge.getId(),smsRecharge.getFee());
            if(serviceResult1.isSuccess()){
                smsRecharge.setState(RechargeStateEnum.NOTICED_CP_OK.getIndex());
                smsRecharge.setNoticeCpDate(new Date());
            }else {
                smsRecharge.setNoticeCpDate(new Date());
                smsRecharge.setState(RechargeStateEnum.NOTICED_CP_FAILED.getIndex());
            }
            id = smsRechargeDao.save(smsRecharge);
             **/
        }
        serviceResult = noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> cuDynamicNotice(String shortcode, String orderno, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cu");
        smsRecharge.setChannel("APEX-cu-dynamic-1-"+fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cu notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.cuDynamicNotic(id, fee);
        logger.debug("cu notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<String> cupageNotice(String shortCode, String orderno, String mobile, String fee, String channel) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortCode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setMobile(mobile);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cu");
        smsRecharge.setChannel(channel);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cu notice success with code:[{}],no:[{}]",shortCode,orderno);
        ServiceResult<String> noticeChannelResult = requestChannelHelper.cuPageNotice(id, mobile, fee);
        logger.debug("cu request code from channel,result:[{}]", noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
            serviceResult.setSuccess(true);
            serviceResult.setValue(id);
        }else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
            serviceResult = noticeChannelResult;
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> cupageResult(String shortCode, String orderno, String smscode) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(111);
            serviceResult.setErrorMessage("订单不存在");
            return serviceResult;
        }

        logger.debug("cu notice result with code:[{}],no:[{}]",shortCode,orderno);
        ServiceResult<String> noticeChannelResult = requestChannelHelper.cuPageResult(smsRecharge.getId(), smscode);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.CHANNEL_NOTICE.getIndex());
            smsRecharge.setChannelNoticeDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
            /**
            ServiceResult<String> serviceResult1 = noticeCpHelper.noticeCp(shortCode,orderno,smsRecharge.getId(),smsRecharge.getFee());
            if(serviceResult1.isSuccess()){
                smsRecharge.setState(RechargeStateEnum.NOTICED_CP_OK.getIndex());
                smsRecharge.setNoticeCpDate(new Date());
            }else {
                smsRecharge.setNoticeCpDate(new Date());
                smsRecharge.setState(RechargeStateEnum.NOTICED_CP_FAILED.getIndex());
            }
            id = smsRechargeDao.save(smsRecharge);
             **/
        }
        serviceResult = noticeChannelResult;
        return serviceResult;

    }

    @Override
    public ServiceResult<String> smsDirectNotice(String shortcode, String orderno, String mobile, String sms, String fee, String channel) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setOrderno(orderno);
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOperator("ct");
        smsRecharge.setChannel(channel);
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("ct sms direct notice success with code:[{}],no:[{}]",shortcode,orderno);
        serviceResult.setSuccess(true);
        serviceResult.setValue(id);
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> cuPlainSmsNotice(String shortcode, String orderno, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cu");
        smsRecharge.setChannel("APEX-cu-plain-1-"+fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cu plain sms notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.cuPlainSmsNotice(id, fee);
        logger.debug("cu plain sms notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> cuPlainSmsGameNotice(String shortcode, String orderno, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cu");
        smsRecharge.setChannel("APEX-cu-plain-2-"+fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cu plain sms notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.cuPlainSmsGameNotice(id, fee);
        logger.debug("cu plain sms notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<String> smsDirectCUNotice(String shortcode, String orderno, Object o, String sms, String fee, String channel) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setOrderno(orderno);
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOperator("cu");
        smsRecharge.setChannel(channel);
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cu sms direct notice success with code:[{}],no:[{}]",shortcode,orderno);
        serviceResult.setSuccess(true);
        serviceResult.setValue(id);
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> cmccDynamicGameNotice(String shortcode, String orderno, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cmcc");
        smsRecharge.setChannel("APEX-cmcc-dynamic-1-"+fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cmcc notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.cmccDynamicGameNotice(id, fee);
        logger.debug("cmcc notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> cmccDynamicSmsNotice(String shortcode, String orderno, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cmcc");
        smsRecharge.setChannel("APEX-cmcc-dynamic-2-"+fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("cmcc notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.cmccDynamicSmsNotice(id, fee);
        logger.debug("cmcc notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<SmsRecharge> dynamicAllinoneNotice(String shortcode, String orderno, String fee, String channel) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortcode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortcode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setFee(fee);
        smsRecharge.setOperator("cmcc");
        //smsRecharge.setChannel("APEX-cmcc-dynamic-2");
        String temp = channel.substring(5);

        smsRecharge.setOperator(temp.substring(0, temp.indexOf("-")));
        smsRecharge.setChannel(channel);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        logger.debug("notice success with code:[{}],no:[{}]",shortcode,orderno);
        ServiceResult<SmsRecharge> noticeChannelResult = requestChannelHelper.dynamicAllinoneNotice(id, fee, channel);
        logger.debug("dynamic notice channel result:[{}]",noticeChannelResult);
        if(noticeChannelResult.isSuccess()){
            smsRecharge.setSmsChannel(noticeChannelResult.getValue().getSmsChannel());
            smsRecharge.setSmscode(noticeChannelResult.getValue().getSmscode());
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_OK.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        } else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CHANNEL_FAILED.getIndex());
            id = smsRechargeDao.save(smsRecharge);
        }
        serviceResult=noticeChannelResult;
        return serviceResult;
    }

    @Override
    public ServiceResult<String> alipayNoticeOrderno(String shortCode, String orderno, String channel, String fee) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getByCode(shortCode, orderno);
        if(smsRecharge != null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(HttpStatus.SC_BAD_REQUEST);
            serviceResult.setErrorMessage("重复订单号");
            return serviceResult;
        }
        smsRecharge = new SmsRecharge();
        smsRecharge.setId(IdFactory.generateId());
        smsRecharge.setShortcode(shortCode);
        smsRecharge.setOrderno(orderno);
        smsRecharge.setOperator("alipay");
        smsRecharge.setChannel(channel);
        smsRecharge.setFee(fee);
        smsRecharge.setCreatedDate(new Date());
        smsRecharge.setState(RechargeStateEnum.SDK_NOTICE.getIndex());
        smsRecharge.setStatus(RechargeStatusEnum.OK.getIndex());
        String id = smsRechargeDao.save(smsRecharge);
        serviceResult.setSuccess(true);
        serviceResult.setValue(id);
        return serviceResult;
    }


}
