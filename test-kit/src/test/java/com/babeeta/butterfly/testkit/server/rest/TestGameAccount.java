package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.rest.app.account.GameAccountClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TestGameAccount {

    private static String ip = "pay.2066.cn";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGameAccount(){
        //GameAccountClient gameAccountClient = new GameAccountClient("119.167.156.248", 8081);
        GameAccountClient gameAccountClient = new GameAccountClient(ip, 8090);
        gameAccountClient.testDelete("10043000000001100430");

        gameAccountClient.testCreateRechargeAccount("10043000000001100430","QjZENjE1QTI4QTAyQzg2Mjg5MkI1OTc3M0RGOTk4NzdDN0FBNzE1M01UQTVNRE0zT0RZNE5EZzVPVGN5TURneE1Ua3JNVGN6TWpVMk16TTBNVFF4T0RZMk5qazFNalU0TXpFMk16SXdPRFEyTURZMU5URTVNRFV4","100","http://gateway.2066.cn:8090/aptrade/api");
        String result = null;
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result );

        gameAccountClient.testGetById(result);
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get result = " + result );

        gameAccountClient.testDelete("10043000000002100430");

        gameAccountClient.testCreateRechargeAccount("10043000000002100430","ODQwRjgyNTQ2NzVDOEJBMjExODE0ODA0RDNGMjVGNzZCNkIwMDg0M01UTTFNemM1Tnpjd05qWXdNVGMzT1RVMU1Ua3JNVEEzT0RNMk56TXdNemd5TURBMk5ESTJPRFUzTkRVeE56QTVOek14TXprME1qZzBOelUz","101","http://inf.wawagame.cn:9566/2066yx/receive.jsp");
        result = null;
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result );

        gameAccountClient.testGetById(result);
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get result = " + result );

        gameAccountClient.testDelete("10043000000003100430");

        gameAccountClient.testCreateRechargeAccount("10043000000003100430","Q0EzNjdERTJFNEI2RDUzQTdFQTQwQzAzMEY2MUVFQ0Q5NDZBRTExM01UVTROVFkxTURJNU5EY3dNelkzT1RrM01ERXJNakE0TVRFMU5UWXpNRE13TnpJeE1EUXpPRFF3TlRNeU1ETXhNRGcwTXpFNU9UQXpNekV4","102","http://gateway.2066.cn:8090/aptrade/api");
        result = null;
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result );

        gameAccountClient.testGetById(result);
        try {
            result = EntityUtils
                    .toString(gameAccountClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get result = " + result );

    }

    @After
    public void tearDown() throws Exception {
    }
}
