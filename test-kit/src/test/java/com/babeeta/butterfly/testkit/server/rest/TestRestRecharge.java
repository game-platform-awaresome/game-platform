package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.rest.app.account.AppRegisterClient;
import junit.framework.Assert;
import net.sf.json.JSONObject;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-29
 * Time: 下午6:23
 * To change this template use File | Settings | File Templates.
 */
public class TestRestRecharge {
    private static final String IP = "119.167.156.248";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testRecharge() {
        AppRegisterClient registerClient = new AppRegisterClient(IP, 8085);
        registerClient.register("mingyang-test", "ming.yang@gmail.com");
        Assert.assertEquals(registerClient.getResponseStatus(), 200);
        String result = null;
        try {
            result = EntityUtils
                    .toString(registerClient.getResponseEntity());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject account = JSONObject.fromObject(result);

        String appId = account.getString("id");
        String appKey = account.getString("passwd");
        String token = account.getString("token");

         /**
        RechargeClient rechargeClient = new RechargeClient(IP, 8083);
        rechargeClient.createRecharge(appId,token);
        Assert.assertEquals(rechargeClient.getResponseStatus(), 200);
        try {
            result = EntityUtils
                    .toString(rechargeClient.getResponseEntity());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("recharge create result--->"+result);
               **/
        TradeGatewayClient tradeGatewayClient = new TradeGatewayClient("gateway.2066.cn", 8090);
        tradeGatewayClient.testSmsTrade(appId,token);
        Assert.assertEquals(tradeGatewayClient.getResponseStatus(), 200);
        /**
        TradeClient tradeClient = new TradeClient(IP, 8083);
        tradeClient.createTrade(appId,token);
        Assert.assertEquals(tradeClient.getResponseStatus(), 200);
        try {
            result = EntityUtils
                    .toString(tradeClient.getResponseEntity());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("trade create result--->"+result);
        ***/
    }

    @After
    public void tearDown() throws Exception {
    }
}
