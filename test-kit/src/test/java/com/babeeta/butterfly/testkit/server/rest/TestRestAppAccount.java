package com.babeeta.butterfly.testkit.server.rest;

import java.io.IOException;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.babeeta.butterfly.testkit.server.rest.app.account.AppAuthClient;
import com.babeeta.butterfly.testkit.server.rest.app.account.AppRegisterClient;

public class TestRestAppAccount {
	private static final String IP = "account.2066.cn";

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAppAccount() {
		AppRegisterClient registerClient = new AppRegisterClient(IP, 8090);
		registerClient.register("mingyang-test", "ming.yang@gmail.com");
		Assert.assertEquals(registerClient.getResponseStatus(), 200);

		String result = null;
		try {
			result = EntityUtils
			        .toString(registerClient.getResponseEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject account = JSONObject.fromObject(result);

		String appId = account.getString("id");
		String appKey = account.getString("passwd");
        String token = account.getString("token");

		System.out.println("appId = " + appId + "; appKey = " + appKey+":token="+token);
		System.out.println(account.toString());
        registerClient.update(appId,token);
        Assert.assertEquals(registerClient.getResponseStatus(), 200);
        /**
        registerClient.sendcode("13910673711");
        Assert.assertEquals(registerClient.getResponseStatus(), 200);

        registerClient.validCode(appId,token);
        Assert.assertEquals(registerClient.getResponseStatus(), 200);
        try {
            result = EntityUtils
                    .toString(registerClient.getResponseEntity());
        }catch (Exception e) {
            e.printStackTrace();
        }

        registerClient.getbackuser(appId,token);
        Assert.assertEquals(registerClient.getResponseStatus(), 200);
        try {
            result = EntityUtils
                    .toString(registerClient.getResponseEntity());
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("get back user:"+result);
         **/

		AppAuthClient authClient = new AppAuthClient(IP, 8090);
		authClient.auth(appId, appKey);
		Assert.assertEquals(authClient.getResponseStatus(), 200);
        result = null;
        try {
            result = EntityUtils
                    .toString(authClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("auth---->"+result);


	}

	@After
	public void tearDown() throws Exception {
	}

}