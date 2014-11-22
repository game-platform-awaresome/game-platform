package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.security.SignUtil;
import com.future.gameplatform.common.service.AbstractHttpRPCService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeNoticeCPHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory.getLogger(RechargeNoticeCPHelper.class);

    private String accountingdomain;

    private int accountingport;

    private JSONArray jsonArray = null;

    private long lastUpdateAppTime = new Date().getTime() - 1000*60*60;

    protected RechargeNoticeCPHelper(int maxConnection,String accountingdomain, int accountingport) {
        super(maxConnection);
        this.accountingdomain = accountingdomain;
        this.accountingport = accountingport;
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }

    public String getRechargeApps(final String searchUrl)  {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-getRechargeApps] searchUrl = {}",
                new Object[] { stamp, searchUrl});
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        String ret = execute(new Callback() {
            @Override
            public String doIt()  {
                HttpRPCResult result = invokeGet(composeURI(accountingdomain, accountingport, searchUrl), HttpStatus.SC_OK);
                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-getRechargeApps] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        return ret;
    }


    public String doNoticeCP(String orderid, String tradeid, String orderDate, String orderFee, String appid, String rechargeResult, String type, String channel){
        try{
            long nowTimestamp = new Date().getTime();
            if((jsonArray == null) || (nowTimestamp - lastUpdateAppTime > 10*60*1000)){
                String appListStr = getRechargeApps("/0/api/rechargeapp/account/list");
                jsonArray = JSONArray.fromObject(appListStr);
                logger.debug("load rechargeaccount---->[{}]", jsonArray);
                lastUpdateAppTime = nowTimestamp;
            }
            String itField = "appid";
            if(channel.equalsIgnoreCase("sms")){
                itField = "shortcode";
            }
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject entry = jsonArray.getJSONObject(i);
                if(entry.getString(itField).equalsIgnoreCase(appid)){
                    final String  noticeurl = entry.getString("noticeurl");
                    final String  normalappid = entry.getString("appid");
                    final String  normalappkey = entry.getString("appkey");
                    final String tranData = "{\"appid\":\""+normalappid+"\",\"orderno\":\""+orderid+"\",\"tradeid\":\""+tradeid+"\",\"amount\":"+orderFee+",\"type\":\""+type+"\",\"channel\":\""+channel+"\",\"result\":\""+rechargeResult+"\",\"tradedt\":"+new Date().getTime()+"}";
                    final String sign = SignUtil.genSign(tranData, normalappkey);
                    String ret = execute(new Callback() {
                        @Override
                        public String doIt() {
                            HttpRPCResult result = invokeGet(
                                    noticeurl+"?tradedata="+ URLEncoder.encode(tranData)+"&sign="+sign,
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
                    return ret;
                }
            }
            return null;
        }catch (Exception e){
            logger.error("[RPC-noticeTrade] failed! ",e);
            return null;
        }
    }
}
