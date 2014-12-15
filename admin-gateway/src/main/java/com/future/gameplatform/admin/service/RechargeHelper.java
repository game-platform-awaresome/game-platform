package com.future.gameplatform.admin.service;

import com.future.gameplatform.account.game.util.SignUtil;
import com.future.gameplatform.admin.Constants;
import com.future.gameplatform.common.service.AbstractHttpRPCService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by user on 2014/12/12.
 */
public class RechargeHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory.getLogger(RechargeHelper.class);
    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected RechargeHelper(int maxConnection) {
        super(maxConnection);
    }

    public boolean doOrgAuth(String shortcode, String key) {

        final String url = Constants.ORG_AUTH_URL+"?shortcode="+shortcode+"&key="+key;
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return "000";
                }
                logger.error(
                        "[RPC-doOrgAuth] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            return true;
        }else {
            return false;
        }
    }

    public Map<String, String> doGetCpList() {
        final String url = Constants.ORG_LIST_URL;
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
                        "[RPC-doGetCpList] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret != null){
            JSONArray jsonArray = JSONArray.fromObject(ret);
            Map<String, String> oneEntry = new HashMap<String, String>();
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                oneEntry.put(jsonObject.getString("shortcode"), jsonObject.getString("cpName"));
            }
            return oneEntry;
        }else {
            return Collections.EMPTY_MAP;
        }
    }

    public Map<String, String> doGetTemplateChannelList() {
        final String url = Constants.TEMPLATE_CHANNEL_URL;
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
                        "[RPC-doGetTemplateChannelList] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret != null){
            JSONObject jsonObject = JSONObject.fromObject(ret);
            Map<String, String> oneEntry = new HashMap<String, String>();
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonEntry = jsonArray.getJSONObject(i);
                oneEntry.put(jsonObject.getString("channel"), jsonObject.getString("channelName"));
            }
            return oneEntry;
        }else {
            return Collections.EMPTY_MAP;
        }
    }

    public List<Map<String, String>> doGetSettle(String selectedShortcode, String selectedChannel, String beginDate, String endDate) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public List<Map<String, String>> doQueryOrder(String shortcode, String mobile, String orderno, String id, String begindate, String enddate) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }
}
