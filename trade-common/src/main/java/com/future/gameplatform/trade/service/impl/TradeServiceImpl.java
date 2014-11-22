package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.trade.dao.TradeDao;
import com.future.gameplatform.trade.entity.Trade;
import com.future.gameplatform.trade.service.TradeService;
import com.future.gameplatform.trade.util.TradeString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeServiceImpl implements TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    private TradeDao tradeDao;

    private AccountingHelper accountingHelper;

    public void setTradeDao(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    public void setAccountingHelper(AccountingHelper accountingHelper) {
        this.accountingHelper = accountingHelper;
    }

    @Override
    public String save(String uid, String token, Map<String, Object> tradeInfo) {
        Trade trade = new Trade();
        trade.setId(IdFactory.generateId());
        trade.setUid(uid);
        trade.setExorderno((String)tradeInfo.get("exorderno"));
        trade.setAppid((String)tradeInfo.get("appid"));
        trade.setType(tradeInfo.containsKey("type")?(String)tradeInfo.get("type"):"0");
        trade.setItem((String)tradeInfo.get("item"));
        trade.setQuantity((Integer)tradeInfo.get("quantity"));
        trade.setPrice((Integer)tradeInfo.get("price"));
        trade.setAmount(trade.getPrice() * trade.getQuantity());
        trade.setStatus(TradeString.STATUS_OK);
        //trade.setResult((Integer)tradeInfo.get("result"));
        trade.setCreatedTime(new Date());
        trade.setLastUpdateTime(new Date());

        String saveResult = tradeDao.save(trade);

        Map<String, Object> updateInfo = new HashMap<String, Object>();
        if(!TradeString.RESULT_FAILED.equalsIgnoreCase(saveResult)){
            String accountingResult = accountingHelper.doTradeAccounting(uid, token, trade.getId(), trade.getType(), trade.getAppid(), trade.getAmount());
            if(accountingResult != null && !TradeString.RESULT_FAILED.equalsIgnoreCase(accountingResult)){
                updateInfo.put("result",TradeString.TRADE_RESULT_SUCCESS);
                String updateResult = update(trade.getId(), token, updateInfo);
                if(!TradeString.RESULT_FAILED.equalsIgnoreCase(updateResult)){
                    String noticeResult = accountingHelper.doNoticeGame(uid,token,trade.getId(),tradeInfo);
                    return TradeString.RESULT_OK;
                }
            }
        }
        return TradeString.RESULT_FAILED;
    }

    @Override
    public String update(String tradeid, String token, Map<String, Object> tradeInfo) {
        if(tradeInfo.containsKey("result")){
            Trade trade = tradeDao.updateResult(tradeid, (String)tradeInfo.get("result"));
            return TradeString.RESULT_OK;
        }
        return TradeString.RESULT_FAILED;
    }

    @Override
    public List<Trade> listByUid(String uid) {
        return tradeDao.listByUid(uid);
    }

    @Override
    public Trade getById(String tradeid) {
        return tradeDao.getById(tradeid);
    }

    @Override
    public Trade getByOderno(String appid, String orderno) {
        return tradeDao.getByOrderno(appid,orderno);
    }
}
