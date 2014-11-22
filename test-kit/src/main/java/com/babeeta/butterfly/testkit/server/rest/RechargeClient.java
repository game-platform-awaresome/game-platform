package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-29
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
public class RechargeClient extends RestfulClient {

    private String ip;

    private int port;

    public RechargeClient(String ip,int port){
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

    public void createRecharge(String uid,String token){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"appid\":\"appidtest00000001\",\"payway\":\"ap\",\"waresid\":99999923,\"price\":100,\"quantity\":5}");
        setHttpMethod(new HttpPost("/1/api/recharge"));
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
