package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.trade.dao.*;
import com.future.gameplatform.trade.entity.*;
import com.future.gameplatform.trade.bean.ResponseEntity;
import com.future.gameplatform.trade.service.MobileNoticeService;
import com.future.gameplatform.trade.service.NoticeCPTaskService;
import com.future.gameplatform.trade.service.RechargeService;
import com.future.gameplatform.trade.util.RechargeString;
import com.future.gameplatform.trade.util.TradeString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class MobileNoticeServiceImpl implements MobileNoticeService {

    private final static Logger  logger = LoggerFactory.getLogger(MobileNoticeServiceImpl.class);

    private SmsNoticeDao smsNoticeDao;

    public void setSmsNoticeDao(SmsNoticeDao smsNoticeDao) {
        this.smsNoticeDao = smsNoticeDao;
    }

    private RechargeService rechargeService;

    public void setRechargeService(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    private RequestEntityDao requestEntityDao;

    public void setRequestEntityDao(RequestEntityDao requestEntityDao) {
        this.requestEntityDao = requestEntityDao;
    }

    private JCHShortRequestEntityDao jchShortRequestEntityDao;

    public void setJchShortRequestEntityDao(JCHShortRequestEntityDao jchShortRequestEntityDao) {
        this.jchShortRequestEntityDao = jchShortRequestEntityDao;
    }

    private DCDBNoticeDao dcdbNoticeDao;

    public void setDcdbNoticeDao(DCDBNoticeDao dcdbNoticeDao) {
        this.dcdbNoticeDao = dcdbNoticeDao;
    }

    private MHNoticeDao mhNoticeDao;

    public void setMhNoticeDao(MHNoticeDao mhNoticeDao) {
        this.mhNoticeDao = mhNoticeDao;
    }

    private RechargeNoticeCPHelper rechargeNoticeCPHelper;

    public void setRechargeNoticeCPHelper(RechargeNoticeCPHelper rechargeNoticeCPHelper) {
        this.rechargeNoticeCPHelper = rechargeNoticeCPHelper;
    }

    private NoticeCPRecordDao noticeCPRecordDao;

    public void setNoticeCPRecordDao(NoticeCPRecordDao noticeCPRecordDao) {
        this.noticeCPRecordDao = noticeCPRecordDao;
    }

    private NoticeCPTaskService noticeCPTaskService;

    public void setNoticeCPTaskService(NoticeCPTaskService noticeCPTaskService) {
        this.noticeCPTaskService = noticeCPTaskService;
    }

    @Override
    public String notify(String uid, String token, Map<String, Object> noticeInfo) {
        logger.debug("notify uid:[{}] info:[{}]",uid, noticeInfo);
        try {
            SmsNotice smsNotice = new SmsNotice();
            smsNotice.setId(IdFactory.generateId());
            rechargeService.doSmsNotice(uid, token, noticeInfo);
            smsNotice.setAppid((String)noticeInfo.remove("appid"));
            smsNotice.setUid(uid);
            smsNotice.setChannel((String)noticeInfo.remove("channel"));
            smsNotice.setAmount((Integer)noticeInfo.remove("amount"));
            smsNotice.setMoney(smsNotice.getAmount());
            smsNotice.setContent((String)noticeInfo.remove("content"));
            smsNotice.setResult((String)noticeInfo.remove("result"));
            smsNotice.setStatus(RechargeString.STATUS_OK);
            smsNotice.setCreatedDate(new Date());
            smsNotice.setExtra(noticeInfo);
            return smsNoticeDao.save(smsNotice);
        } catch (Exception e){
            logger.error("something wrong",e);
        }
        return null;

    }

    @Override
    public ResponseEntity executeJCHNotice(RequestEntity requestEntity) {
        String ChargeRequestID = requestEntity.getChargeRequestID();
        String ServiceID = requestEntity.getServiceID();
        String ChargeResult = requestEntity.getChargeResult();
        requestEntity.setId(IdFactory.generateId());
        String id = requestEntityDao.save(requestEntity);
        rechargeNoticeCPHelper.doNoticeCP(ServiceID, id, null, null, ServiceID, ChargeResult, "sms", "jch");
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMsgType("ChargeResultResp");
        responseEntity.setTransferred("0");
        if(ServiceID != null && ServiceID.length() > 13){
            String shortServiceId = ServiceID.substring(ServiceID.length() - 13);
            NoticeCPRecord noticeCPRecord = new NoticeCPRecord();
            noticeCPRecord.setId(IdFactory.generateId());
            noticeCPRecord.setAppid(shortServiceId.substring(0, 3));
            noticeCPRecord.setChannel("jch");
            noticeCPRecord.setFailedTimes(0);
            noticeCPRecord.setNoticeResult(TradeString.RESULT_INIT);
            noticeCPRecord.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            noticeCPRecord.setOrderid(shortServiceId.substring(3));
            noticeCPRecord.setTradeid(id);
            noticeCPRecord.setType("sms");
            noticeCPRecord.setRechargeResult(ChargeResult);
            noticeCPRecordDao.save(noticeCPRecord);

            noticeCPTaskService.setupTask(new NoticeCPTask(noticeCPRecord.getId(), 0, noticeCPRecordDao, rechargeNoticeCPHelper));
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity executeJCHShortNotice(JCHShortRequestEntity jchShortRequestEntity) {
        jchShortRequestEntity.setId(IdFactory.generateId());
        String id = jchShortRequestEntityDao.save(jchShortRequestEntity);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setMsgType("ChargeResultResp");
        responseEntity.setTransferred("0");

        String ServiceID = jchShortRequestEntity.getServiceID();
        if(ServiceID != null && ServiceID.length() > 13){
            String shortServiceId = ServiceID.substring(ServiceID.length() - 13);
            NoticeCPRecord noticeCPRecord = new NoticeCPRecord();
            noticeCPRecord.setId(IdFactory.generateId());
            noticeCPRecord.setAppid(shortServiceId.substring(0, 3));
            noticeCPRecord.setChannel("jch");
            noticeCPRecord.setFailedTimes(0);
            noticeCPRecord.setNoticeResult(TradeString.RESULT_INIT);
            noticeCPRecord.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            noticeCPRecord.setOrderid(shortServiceId.substring(3));
            noticeCPRecord.setTradeid(id);
            noticeCPRecord.setType("sms");
            noticeCPRecord.setOrderFee(jchShortRequestEntity.getChargeFee());
            noticeCPRecord.setRechargeResult(jchShortRequestEntity.getChargeResult());
            noticeCPRecordDao.save(noticeCPRecord);

            noticeCPTaskService.setupTask(new NoticeCPTask(noticeCPRecord.getId(), 0, noticeCPRecordDao, rechargeNoticeCPHelper));
        }

        return responseEntity;
    }

    @Override
    public String executeDCDBnotice(String from, String to, String msg, String linkid, String serviceid, String servicecode, String backurl, String msgtype) {
        DCDBNotice dcdbNotice = new DCDBNotice();
        dcdbNotice.setId(IdFactory.generateId());
        dcdbNotice.setFrom(from);
        dcdbNotice.setTo(to);
        dcdbNotice.setMsg(msg);
        dcdbNotice.setLinkid(linkid);
        dcdbNotice.setServiceid(serviceid);
        dcdbNotice.setServicecode(servicecode);
        dcdbNotice.setBackurl(backurl);
        dcdbNotice.setMsgtype(msgtype);
        dcdbNotice.setCreatedDate(new Date());
        return dcdbNoticeDao.save(dcdbNotice);
    }

    @Override
    public String executeMHnotice(String mchNo, String phone, String fee, String MarketCode, String orderId, String sign) {
        MHNotice mhNotice = new MHNotice();
        mhNotice.setId(IdFactory.generateId());
        mhNotice.setMchNo(mchNo);
        mhNotice.setPhone(phone);
        mhNotice.setFee(fee);
        mhNotice.setMarketCode(MarketCode);
        mhNotice.setOrderId(orderId);
        mhNotice.setCreatedDate(new Date());

        if(orderId != null && orderId.length() > 13){
            String shortServiceId = orderId.substring(orderId.length() - 13);
            NoticeCPRecord noticeCPRecord = new NoticeCPRecord();
            noticeCPRecord.setId(IdFactory.generateId());
            noticeCPRecord.setAppid(shortServiceId.substring(0, 3));
            noticeCPRecord.setChannel("mh");
            noticeCPRecord.setFailedTimes(0);
            noticeCPRecord.setNoticeResult(TradeString.RESULT_INIT);
            noticeCPRecord.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            noticeCPRecord.setOrderid(shortServiceId.substring(3));
            noticeCPRecord.setTradeid(mhNotice.getId());
            noticeCPRecord.setType("sms");
            noticeCPRecord.setRechargeResult(TradeString.RESULT_OK);
            noticeCPRecordDao.save(noticeCPRecord);

            noticeCPTaskService.setupTask(new NoticeCPTask(noticeCPRecord.getId(), 0, noticeCPRecordDao, rechargeNoticeCPHelper));
        }


        return mhNoticeDao.save(mhNotice) ;
    }
}
