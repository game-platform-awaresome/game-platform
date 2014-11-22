package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeGatewayClient extends RestfulClient {

    private String ip;

    private int port;

    public TradeGatewayClient(String ip, int port){
        super();
        this.ip = ip;
        this.port = port;
    }


    @Override
    protected String getIp() {
        return ip;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getPort() {
        return port;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testTradeGet(){
        String data = "";
        setHttpMethod(new HttpGet("/ap/aptrade/api?sign="+URLEncoder.encode("6f365d767750c2d5103ba524b882adb2 1748064978ed03785bd14b54260fb3f2 41c998f4c18cdc5103e34ac3f655c5e ")
                +"&transdata="+ URLEncoder.encode("{\"exorderno\":\"51b2ffca9679b760eda65c6e7f955605\",\"transid\":\"01013060817562345947\",\"waresid\":1,\"appid\":\"10043000000001100430\",\"feetype\":0,\"money\":100,\"count\":1,\"result\":0,\"transtype\":0,\"transtime\":\"2013-06-08 17:56:33\",\"cpprivate\":\"123456\"}")));
        execute();
    }

    public void testSmsTrade(String uid, String token){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("appid","10043000000001100430") ;
        info.put("channel","jch");
        info.put("amount",new Long(2));
        info.put("uid",uid);
        info.put("content","1271030563052006136000006135900110000000000000000000001310000000000000");
        info.put("result","0");
        String contentStr = JSONObject.fromObject(info).toString();
        setHttpMethod(new HttpPut("/1/api/sms/smsnotify"));
        setHttpContentType("application/json;charset=UTF-8");
        setHeader("uid",uid);
        setHeader("token",token);
        ByteArrayEntity byteArray = null;
        try {
            byteArray = new ByteArrayEntity(contentStr.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }


    public void testTrade(){
        String trde = "";
        String sign = "";

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("transdata",trde) ;
        info.put("sign",sign);

        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/aptrade/api"));
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
