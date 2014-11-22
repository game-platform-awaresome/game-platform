package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeClient extends RestfulClient {

    private String ip;
    private int port;

    public TradeClient(String ip, int port){
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

    public void createTrade(String uid, String token){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"appid\":\"appidtest00000001\",\"exorderno\":\"orderid00000001\",\"type\":\"0\",\"item\":\"99999923\",\"price\":100,\"quantity\":5}");
        setHttpMethod(new HttpPost("/1/api/trade"));
        setHttpContentType("application/json;charset=UTF-8");
        setHeader("uid",uid);
        setHeader("token",token);
        ByteArrayEntity byteArray = null;
        try {
            String contentStr = sb.toString();
            byteArray = new ByteArrayEntity(contentStr.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }
}
