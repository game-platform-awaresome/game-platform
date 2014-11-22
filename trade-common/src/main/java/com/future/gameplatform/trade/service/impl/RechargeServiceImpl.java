package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.common.security.MD5Util;
import com.future.gameplatform.trade.dao.NoticeCPRecordDao;
import com.future.gameplatform.trade.dao.RechargeDao;
import com.future.gameplatform.trade.entity.NoticeCPRecord;
import com.future.gameplatform.trade.entity.Recharge;
import com.future.gameplatform.trade.service.NoticeCPTaskService;
import com.future.gameplatform.trade.service.RechargeService;
import com.future.gameplatform.trade.util.RechargeString;
import com.future.gameplatform.trade.util.TradeString;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeServiceImpl implements RechargeService {

    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    private RechargeDao rechargeDao;

    private AccountingHelper accountingHelper;

    private RechargeGetTranlogHelper rechargeGetTranlogHelper;

    private NoticeCPRecordDao noticeCPRecordDao;

    private NoticeCPTaskService noticeCPTaskService;

    private RechargeNoticeCPHelper rechargeNoticeCPHelper;

    public void setNoticeCPTaskService(NoticeCPTaskService noticeCPTaskService) {
        this.noticeCPTaskService = noticeCPTaskService;
    }

    public void setNoticeCPRecordDao(NoticeCPRecordDao noticeCPRecordDao) {
        this.noticeCPRecordDao = noticeCPRecordDao;
    }

    public void setRechargeDao(RechargeDao rechargeDao) {
        this.rechargeDao = rechargeDao;
    }

    public void setAccountingHelper(AccountingHelper accountingHelper) {
        this.accountingHelper = accountingHelper;
    }

    public void setRechargeGetTranlogHelper(RechargeGetTranlogHelper rechargeGetTranlogHelper) {
        this.rechargeGetTranlogHelper = rechargeGetTranlogHelper;
    }

    public void setRechargeNoticeCPHelper(RechargeNoticeCPHelper rechargeNoticeCPHelper) {
        this.rechargeNoticeCPHelper = rechargeNoticeCPHelper;
    }

    @Override
    public String insertRecharge(String uid, Map<String, Object> rechargeInfo) {
        logger.debug("insert recharge with uid:[{}]",uid);
        Recharge recharge = new Recharge();
        recharge.setOrderno(IdFactory.generateId());
        recharge.setPayway( rechargeInfo.get("payway")==null?"AP":(String) rechargeInfo.get("payway"));
        recharge.setAppid((String)rechargeInfo.get("appid"));
        recharge.setCreatedTime(new Date());
        recharge.setLastUpdateTime(new Date());
        recharge.setPrice((Integer)rechargeInfo.get("price"));
        recharge.setQuantity((Integer)rechargeInfo.get("quantity"));
        recharge.setStatus(RechargeString.STATUS_OK);
        recharge.setUid(uid);
        logger.debug("insert into recharge with waresid:"+rechargeInfo.get("waresid"));
        recharge.setWaresid(String.valueOf(rechargeInfo.get("waresid")));
        return rechargeDao.save(recharge);
    }

    @Override
    public String updateRecharge(String uid, String rechargeId, String token, Map<String, Object> rechargeInfo) {
        logger.debug("update recharge with id:[{}] info:[{}]", rechargeId, rechargeInfo);
        if(rechargeInfo.containsKey("result") && RechargeString.RECHARGE_RESULT_SUCCESS.equalsIgnoreCase(String.valueOf(rechargeInfo.get("result")))){
            String accountingResult = accountingHelper.doRechargeAccounting(uid, token, rechargeId, (String)rechargeInfo.get("appid"),String.valueOf(rechargeInfo.get("transtype")), (Integer)rechargeInfo.get("amount"), (Integer)rechargeInfo.get("money"), (String)rechargeInfo.get("payway"));
            if(RechargeString.RESULT_FAILED.equalsIgnoreCase(accountingResult)){
                logger.error("accounting result:[{}] so interrupt with error result",accountingResult);
                return RechargeString.RESULT_FAILED;
            }
        }
        return rechargeDao.update(rechargeId, rechargeInfo);
    }

    @Override
    public List<Recharge> listByUid(String uid) {
        return rechargeDao.listByUid(uid);
    }


    @Override
    public Recharge getById(String rechargeid) {
        Recharge recharge = rechargeDao.getById(rechargeid);
        if(recharge == null){
            logger.debug("get by id,get null");
            return null;
        }else {
            if(recharge.getResult().equalsIgnoreCase(RechargeString.RECHARGE_RESULT_FAIL) || recharge.getResult().equalsIgnoreCase(RechargeString.RECHARGE_RESULT_SUCCESS)) {
                logger.debug("get info with result has noticed");
                return recharge;
            } else {
                String getLogResult = rechargeGetTranlogHelper.getTranlog(rechargeid, recharge.getAppid());
                if(getLogResult != null){
                    String trandata = getLogResult.substring(10, getLogResult.indexOf("&sign="));
                    JSONObject jsonObject = JSONObject.fromObject(trandata);
                    String exorderno = (String)jsonObject.get("exorderno");
                    String transid = (String)jsonObject.get("transid");

                    String appid = (String)jsonObject.get("appid");
                    Integer waresid = (Integer)jsonObject.get("waresid");
                    Integer feetype = (Integer)jsonObject.get("feetype");
                    Integer money = (Integer)jsonObject.get("money");    //cent
                    Integer count = (Integer)jsonObject.get("count");
                    Integer result = (Integer)jsonObject.get("result");
                    Integer transtype = (Integer)jsonObject.get("transtype");
                    String transtime = (String)jsonObject.get("transtime");
                    String cpprivate = (String)jsonObject.get("cpprivate");
                    doPayNotice(exorderno, transid, appid, waresid, feetype, money, count, result, transtype, transtime, cpprivate);
                }
            }
        }
        return rechargeDao.getById(rechargeid);
    }

    @Override
    public void doPayNotice(String exorderno, String transid, String appid, Integer waresid, Integer feetype, Integer money, Integer count, Integer result, Integer transtype, String transtime, String cpprivate) {
        Recharge recharge = getByIdForGet(exorderno);
        String uid = recharge.getUid();
        String token = getTokenFromUid(uid);
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("result",result);
        info.put("transtype",transtype);
        info.put("money", money);
        info.put("amount", money / 100);
        info.put("payway", "ap");
        info.put("appid", appid);
        info.put("transid", transid);
        info.put("privateinfo", cpprivate);
        info.put("status", RechargeString.STATUS_OK);
        info.put("transTime", transtime);

        NoticeCPRecord noticeCPRecord = new NoticeCPRecord();
        noticeCPRecord.setId(IdFactory.generateId());
        noticeCPRecord.setAppid(appid);
        noticeCPRecord.setChannel("ap");
        noticeCPRecord.setFailedTimes(0);
        noticeCPRecord.setNoticeResult(TradeString.RESULT_INIT);
        noticeCPRecord.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        noticeCPRecord.setOrderid(exorderno);
        noticeCPRecord.setTradeid(transid);
        noticeCPRecord.setType("online");
        noticeCPRecord.setRechargeResult(String.valueOf(result));
        noticeCPRecordDao.save(noticeCPRecord);

        noticeCPTaskService.setupTask(new NoticeCPTask(noticeCPRecord.getId(), 0, noticeCPRecordDao,rechargeNoticeCPHelper));

        String updateResult = updateRecharge(uid, exorderno, token, info);
        logger.debug("do one ap pay notice,uid:[{}], tradeid:[{}], info:[{}]",new Object[] {uid, exorderno, info});
    }

    @Override
    public void doSmsNotice(String uid, String token, Map<String, Object> noticeInfo) {
        logger.debug("sms notice uid:[{}] info:[{}]", uid, noticeInfo);
        Map<String,Object> rechargeInfo = new HashMap<String, Object>();
        rechargeInfo.put("payway", noticeInfo.get("channel"));
        rechargeInfo.put("appid", noticeInfo.get("appid"));
        rechargeInfo.put("price", noticeInfo.get("amount"));
        rechargeInfo.put("quantity", 1);
        rechargeInfo.put("waresid", noticeInfo.get("content"));
        logger.debug("before insert,uid:[{}], info:[{}]", uid, rechargeInfo);
        String rechargeid = insertRecharge(uid, rechargeInfo);
        logger.debug("insert one recharge id:[{}]", rechargeid);
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("result", 0);
        info.put("transtype", 0);
        info.put("money", (Integer)noticeInfo.get("amount") * 100);
        info.put("amount",noticeInfo.get("amount"));
        info.put("payway", noticeInfo.get("channel"));
        info.put("appid", noticeInfo.get("appid"));
        info.put("transid",noticeInfo.get("content"));
        info.put("privateinfo", "");
        info.put("status", RechargeString.STATUS_OK);
        info.put("transTime",new SimpleDateFormat("yyyy-mm-dd").format(new Date())) ;
        logger.debug("before updateRecharge uid:[{}] rechargeid:[{}] info:[{}]", new Object[]{uid, rechargeid, info});
        String updateResult = updateRecharge(uid, rechargeid, token, info);
        logger.debug("do one sms pay notice,uid:[{}], tradeid:[{}], info:[{}]", new Object[]{uid, rechargeid, info});

    }

    private Recharge getByIdForGet(String rechargeid) {
        return rechargeDao.getById(rechargeid);
    }

    private String getTokenFromUid(String uid) {
        String dt = Long.toHexString(new Date().getTime());
        String sig = MD5Util.hexString(uid + dt, Charset.forName("UTF-8"));
        String seed = sig.substring(30);
        int index  = Integer.parseInt(seed,16) % dt.length();
        String token = sig.substring(0,index) + dt + sig.substring(index);
        return token;
    }




}
