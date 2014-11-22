package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.security.SignUtil;
import com.future.gameplatform.common.service.AbstractHttpRPCService;
import com.future.gameplatform.trade.util.CpTransSyncSignValid;
import com.future.gameplatform.trade.util.RechargeString;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class AccountingHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory
            .getLogger(AccountingHelper.class);

    private final String accountingdomain;

    private final int accountingport;

    private final String userbalancedomain;

    private final int userbalanceport;

    private final String pfappid;

    protected AccountingHelper(int maxConnection, String accountingdomain, int accountingport, String userbalancedomain, int userbalanceport, String pfappid) {
        super(maxConnection);
        this.accountingdomain = accountingdomain;
        this.accountingport = accountingport;
        this.userbalancedomain = userbalancedomain;
        this.userbalanceport = userbalanceport;
        this.pfappid = pfappid;
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }

    public String doTradeAccounting(String uid, String token, String id, String type, String appid, int amount) {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-doTradeAccounting] uid = {}, id = {}, type = {}, appid = {}, amount= {}",
                new Object[] { stamp, uid, id, type, appid, amount });
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        final byte[] payload = ("{\"uid\":\""+uid+"\",\"type\":\"trade\",\"origid\":\""+id+"\",\"origtype\":\""+type+"\",\"amount\":"+amount+",\"appid\":\""+this.pfappid+"\",\"origappid\":\""+appid+"\"}").getBytes();
        final byte[] payloadnotice = ("{\"uid\":\""+uid+"\",\"origtype\":\""+type+"\",\"amount\":"+amount+"}").getBytes();
        final Map<String,String> headers = new HashMap<String,String>();
        headers.put("uid",uid);
        headers.put("token",token);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokePostWithHeader(
                        composeURI(accountingdomain, accountingport, "/1/api/com.future.gameplatform.account.game/trade"), headers,
                        APPLICATION_JSON, payload, HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doTradeAccounting] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        logger.debug("[{}] [RPC-doTradeAccounting] Job costed {} ms.", stamp,
                (System.currentTimeMillis() - stamp));
        if(ret != null && RechargeString.RESULT_OK.equalsIgnoreCase(ret)) {
            ret = execute(new Callback() {
                @Override
                public String doIt() {
                    HttpRPCResult result = invokePostWithHeader(
                            composeURI(userbalancedomain, userbalanceport, "/1/api/user/tradenotice"), headers,
                            APPLICATION_JSON, payloadnotice, HttpStatus.SC_OK);

                    if (result.getStatusCode() == HttpStatus.SC_OK) {
                        return new String(result.getPayload());
                    }
                    logger.error(
                            "[RPC-doNoticeUser] failed! statusCode: {}; message: {}",
                            result.getStatusCode(), result.getMessage());
                    return null;
                }
            });
        }
        return ret;
    }

    public String doRechargeAccounting(String uid, String token, String rechargeId, String appid, String transtype, Integer amount, Integer money, String source) {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-doRechargeAccounting] uid = {}, id = {}, type = {}, amount = {}, money = {}",
                new Object[] { stamp, uid, rechargeId, transtype, amount, money });
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        final byte[] payload = ("{\"uid\":\""+uid+"\",\"type\":\"recharge\",\"appid\":\""+appid+"\",\"origid\":\""+rechargeId+"\",\"origtype\":\""+transtype+"\",\"amount\":"+amount+",\"money\":"+money+",\"source\":\""+source+"\"}").getBytes();
        final byte[] payloadnotice = ("{\"uid\":\""+uid+"\",\"origtype\":\""+transtype+"\",\"amount\":"+amount+"}").getBytes();
        final Map<String,String> headers = new HashMap<String,String>();
        headers.put("uid",uid);
        headers.put("token",token);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokePostWithHeader(
                        composeURI(accountingdomain, accountingport, "/1/api/com.future.gameplatform.account.game/recharge"),headers,
                        APPLICATION_JSON, payload, HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doRechargeAccounting] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });

        logger.debug("[{}] [RPC-doRechargeAccounting] Job costed {} ms.", stamp,
                (System.currentTimeMillis() - stamp));
        if(RechargeString.RESULT_OK.equalsIgnoreCase(ret)) {
            if(isPlatform(appid)){
                ret = execute(new Callback() {
                    @Override
                    public String doIt() {
                        HttpRPCResult result = invokePostWithHeader(
                                composeURI(userbalancedomain, userbalanceport, "/1/api/user/rechargenotice"), headers,
                                APPLICATION_JSON, payloadnotice, HttpStatus.SC_OK);

                        if (result.getStatusCode() == HttpStatus.SC_OK) {
                            return new String(result.getPayload());
                        }
                        logger.error(
                                "[RPC-doNoticeUser] failed! statusCode: {}; message: {}",
                                result.getStatusCode(), result.getMessage());
                        return null;
                    }
                });
            }
        }
        return ret;
    }

    public String doNoticeGame(String uid, String token,String tradeid, Map<String,Object> tradeInfo) {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-doNoticeGame] uid = {}, id = {}, tradeinfo = {}",
                new Object[] { stamp, uid, tradeid,tradeInfo });
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());
        final String appid = (String)tradeInfo.get("appid");
        String noticeUrl = (String)tradeInfo.get("url");
        String appkey = null;
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        composeURI(accountingdomain, accountingport, "/0/api/tradeapp/account/" + appid),
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeGame] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret != null){
            JSONObject jsonObject = JSONObject.fromObject(ret);
            if(noticeUrl == null || noticeUrl.isEmpty()){
                noticeUrl  = jsonObject.getString("notifyurl");
            }
            final String finalNoticeUrl = noticeUrl;
            appkey = jsonObject.getString("appkey");

            String orderno = (String)tradeInfo.get("exorderno");
            int amount = (Integer)tradeInfo.get("price")*(Integer)tradeInfo.get("quantity");
            String type = (String)tradeInfo.get("type");
            String result = "0";
            final String tranData = "{\"appid\":\""+appid+"\",\"orderno\":\""+orderno+"\",\"tradeid\":\""+tradeid+"\",\"amount\":"+amount+",\"type\":\""+type+"\",\"result\":\""+result+"\",\"tradedt\":"+new Date().getTime()+"}";
            final String sign = SignUtil.genSign(tranData, appkey);
            ret = execute(new Callback() {
                @Override
                public String doIt() {
                    HttpRPCResult result = invokeGet(
                            finalNoticeUrl+"tradedata="+tranData+"&sign="+sign,
                            HttpStatus.SC_OK);

                    if (result.getStatusCode() == HttpStatus.SC_OK) {
                        return new String(result.getPayload());
                    }
                    logger.error(
                            "[RPC-noticeTrade] failed! statusCode: {}; message: {}",
                            result.getStatusCode(), result.getMessage());
                    return null;
                }
            });
        }
        return ret;
    }


    private boolean isPlatform(String appid) {
        return appid.equalsIgnoreCase(this.pfappid);
    }
}
