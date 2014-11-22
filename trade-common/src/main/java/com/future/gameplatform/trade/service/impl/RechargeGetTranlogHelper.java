package com.future.gameplatform.trade.service.impl;

import com.future.gameplatform.common.service.AbstractHttpRPCService;
import com.future.gameplatform.trade.util.CpTransSyncSignValid;
import com.future.gameplatform.trade.util.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class RechargeGetTranlogHelper extends AbstractHttpRPCService {

    private final static Logger logger = LoggerFactory.getLogger(RechargeGetTranlogHelper.class);

    private String domain;

    private int port;

    private String queryAppAccountinfoUrl;
    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected RechargeGetTranlogHelper(int maxConnection, String domain, int port, String queryAppAccountinfoUrl) {
        super(maxConnection);
        this.domain = domain;
        this.port = port;
        this.queryAppAccountinfoUrl = queryAppAccountinfoUrl;
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }

    public String getTranlog(final String rechargeid, final String appid) {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-getTranlog] rechargeid = {}, appid = {}",
                new Object[] { stamp, rechargeid, appid });
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());
        String ret = execute(new Callback() {

            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(queryAppAccountinfoUrl+appid, HttpStatus.SC_OK);
                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                return null;
            }
        });
        if (ret != null){
            JSONObject jsonObject = JSONObject.fromObject(ret);

            String appkey =  jsonObject.getString("appkey");
            final String searchurl = jsonObject.getString("searchurl");

            String json = "{\"appid\":\"" + appid + "\",\"exorderno\":\""
                    + rechargeid + "\"}";

            String sign = CpTransSyncSignValid.genSign(json, appkey);
            final String message = "transdata=" + json + "&sign=" + sign;

            ret = execute(new Callback() {

                @Override
                public String doIt() {
                    return HttpUtils.sentPost(searchurl, message);
                }
            });
            if(ret != null){
                return ret;
            }

        }
        return null;
    }
}
