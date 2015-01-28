package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.service.ChannelNoticeService;
import com.future.gameplatform.recharge.common.util.RechargeStateEnum;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
public class ChannelNoticeServiceImpl implements ChannelNoticeService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelNoticeServiceImpl.class);

    private SmsRechargeDao smsRechargeDao;

    public void setSmsRechargeDao(SmsRechargeDao smsRechargeDao) {
        this.smsRechargeDao = smsRechargeDao;
    }

    private NoticeCpHelper noticeCpHelper;

    public void setNoticeCpHelper(NoticeCpHelper noticeCpHelper) {
        this.noticeCpHelper = noticeCpHelper;
    }

    @Override
    public ServiceResult<String> receiveNotice(String mchNo) {
        logger.debug("do receive notice id:[{}]", mchNo);
        ServiceResult<String> serviceResult =  new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getById(mchNo);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(111);
            serviceResult.setErrorMessage("传入参数有误");
            return serviceResult;
        }
        smsRecharge.setState(RechargeStateEnum.CHANNEL_NOTICE.getIndex());
        smsRecharge.setChannelNoticeDate(new Date());

        ServiceResult<String> noticeCpResult = noticeCpHelper.noticeCp(smsRecharge.getShortcode(),smsRecharge.getOrderno(),mchNo,smsRecharge.getFee());
        if(noticeCpResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_OK.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
        }else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_FAILED.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
        }

        serviceResult.setSuccess(true);
        return serviceResult;
    }

    @Override
    public SmsRecharge getSmsRechargeByCode(String shortcode, String orderno) {
        return smsRechargeDao.getByCode(shortcode, orderno);
    }

    @Override
    public ServiceResult<String> receivePlainSmsNotice(String mchNo, String mobile) {
        logger.debug("do receive notice id:[{}]", mchNo);
        ServiceResult<String> serviceResult =  new ServiceResult<String>();
        SmsRecharge smsRecharge = smsRechargeDao.getById(mchNo);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(111);
            serviceResult.setErrorMessage("传入参数有误");
            return serviceResult;
        }
        smsRecharge.setState(RechargeStateEnum.CHANNEL_NOTICE.getIndex());
        smsRecharge.setChannelNoticeDate(new Date());
        smsRecharge.setMobile(mobile);
        logger.debug("notice cp with shortcode:[{}], orderno:[{}], tradeId:[{}], fee:[{}]", new Object[]{smsRecharge.getShortcode(),smsRecharge.getOrderno(),mchNo,smsRecharge.getFee()});
        ServiceResult<String> noticeCpResult = noticeCpHelper.noticeCp(smsRecharge.getShortcode(),smsRecharge.getOrderno(),mchNo,smsRecharge.getFee());
        logger.debug("notice cp get result:[{}]", noticeCpResult);
        if(noticeCpResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_OK.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
        }else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_FAILED.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            String id = smsRechargeDao.save(smsRecharge);
        }

        serviceResult.setSuccess(true);
        return serviceResult;
    }

    @Override
    public ServiceResult<String> receiveAlipayNotice(String no) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        String[] nos = no.split("__");
        if(nos == null || nos.length < 2){
            serviceResult.setSuccess(false);
            serviceResult.setValue(null);
            return serviceResult;
        }
        String shortcode = nos[0];
        String orderno = nos[1];
        SmsRecharge smsRecharge = getSmsRechargeByCode(shortcode, orderno);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setValue(null);
            return serviceResult;
        }
        serviceResult = receiveNotice(smsRecharge.getId());
        return serviceResult;
    }

    @Override
    public ServiceResult<String> receiveSDKNotice(String mchNo, String fee, String mobile) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        String[] nos = mchNo.split("__");
        if(nos == null || nos.length < 2){
            serviceResult.setSuccess(false);
            serviceResult.setValue(null);
            return serviceResult;
        }
        String shortcode = nos[0];
        String orderno = nos[1];
        SmsRecharge smsRecharge = getSmsRechargeByCode(shortcode, orderno);
        if(smsRecharge == null){
            serviceResult.setSuccess(false);
            serviceResult.setValue(null);
            return serviceResult;
        }
        smsRecharge.setFee(fee);
        smsRecharge.setMobile(mobile);
/*        String oldChannel = smsRecharge.getChannel();
        oldChannel = oldChannel.substring(0, oldChannel.lastIndexOf("-")+1)+fee;
        smsRecharge.setChannel(oldChannel);*/
        smsRechargeDao.save(smsRecharge);
        serviceResult = receiveNotice(smsRecharge.getId());
        return serviceResult;
    }
}
