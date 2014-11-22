package com.babeeta.butterfly.testkit.server.rest.app.account;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GameTradeAccountClient extends RestfulClient {

    private String ip;

    private int port;

    public GameTradeAccountClient(String ip, int port){
        super();
        this.ip = ip;
        this.port = port;
    }


    @Override
    protected String getIp() {
        return this.ip;
    }

    @Override
    protected int getPort() {
        return this.port;
    }
    public void testCreateTradeAccount(String appid,String appkey) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("appid", appid);
        info.put("appkey", appkey);
        info.put("notifyurl","http://inf.wawagame.cn:9566/2066/receive.jsp");
        info.put("searchurl","http://trade.2066.cn:8090/trade/order");
        info.put("status","OK");
        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPut("/account/"+appid));
        setHttpContentType("application/json;charset=UTF-8");
        ByteArrayEntity byteArray = null;
        try {
            byteArray = new ByteArrayEntity(contentStr.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }
}
