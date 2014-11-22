package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.common.service.AbstractHttpRPCService;
import com.future.gameplatform.recharge.common.util.RechargeConstants;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
public class NoticeCpHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory.getLogger(NoticeCpHelper.class);

    private static Map<Long,Map<String,Map<String,Object>>> appMap = new HashMap<Long, Map<String, Map<String,Object>>>();

    static {
        appMap.put(100000L, new HashMap<String, Map<String, Object>>());
    }

    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected NoticeCpHelper(int maxConnection) {
        super(maxConnection);
    }

    public ServiceResult<String> noticeCp(String shortCode, String orderno, String id, String fee) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        Map<String,String> params = new HashMap<String, String>(4);
        params.put("MchId", shortCode);
        params.put("MchNo", orderno);
        params.put("TradeId", id);
        params.put("Fee", fee);
        Map<String, Object> appMap = getAppByShortcode(shortCode);
        final String url = appMap.get("noticeurl").toString()+"?MchId="+shortCode+"&MchNo="+orderno+"&TradeId="+id+"&Fee="+fee+"&Sign="+ SignUtil.getCPSign(appMap.get("appkey").toString(), params);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doPostChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            serviceResult.setSuccess(true);
            serviceResult.setValue(ret);
        }else {
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public Map<String,Object> getAppByShortcode(String shortcode) {
        Long currentTime = System.currentTimeMillis();

        Long lastCache = 10000L;
        if(appMap.size() == 1){
            lastCache = appMap.keySet().iterator().next();
        }
        if(currentTime > lastCache + 10 * 60 * 60 * 1000 || appMap.size() < 1){
            appMap.clear();
            lastCache = currentTime;
            Map<String,Map<String,Object>> cacheMap = new HashMap<String, Map<String, Object>>();
            String accountStr = getRechargeApps(RechargeConstants.GET_RECHARGE_APP_LIST_URL);
            logger.debug("init get apps result:[{}]",accountStr);
            if(accountStr != null){
                JSONArray jsonArray = JSONArray.fromObject(accountStr);
                for(int i=0; i<jsonArray.size(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String scode = jsonObject.getString("shortcode");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("appkey",jsonObject.get("appkey"));
                    map.put("appid",jsonObject.get("appid"));
                    map.put("searchurl",jsonObject.get("searchurl"));
                    map.put("noticeurl",jsonObject.get("noticeurl"));
                    cacheMap.put(scode, map);
                }
                appMap.put(currentTime,cacheMap);
            }
        }
        Map<String, Map<String,Object>> cacheApps = appMap.get(lastCache);
        Map<String,Object> rechargeAccount = cacheApps.get(shortcode);
        return rechargeAccount;
    }


    private String getRechargeApps(final String searchUrl)  {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-getRechargeApps] searchUrl = {}",
                new Object[] { stamp, searchUrl});
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        String ret = execute(new Callback() {
            @Override
            public String doIt()  {
                HttpRPCResult result = invokeGet(searchUrl, HttpStatus.SC_OK);

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



    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }
}
