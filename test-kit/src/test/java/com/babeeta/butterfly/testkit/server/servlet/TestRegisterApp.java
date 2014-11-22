package com.babeeta.butterfly.testkit.server.servlet;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.babeeta.butterfly.testkit.server.servlet.app.gateway.AppRegisterClient;

@SuppressWarnings("javadoc")
public class TestRegisterApp {
	private static final String IP = "115.182.37.78";
	private static final String SUPER_USERNAME = "chuangqianmingyueguang";
	private static final String SUPER_PASSWORD = "yisuiyikurong";

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testAppAccountRegister() throws ParseException, IOException {
		System.out.println("\n\n>>>>> Test register application account >>>>>");

		AppRegisterClient regClient = new AppRegisterClient(IP, 80,
		        SUPER_USERNAME, SUPER_PASSWORD);

		regClient.register("For SHW.(request from yun.chen@babeeta.com)",
		        "yun.chen@babeeta.com");

		Assert.assertEquals(regClient.getResponseStatus(), 200);

		String result = null;

		result = EntityUtils.toString(regClient.getResponseEntity());
		System.out.println(result);

		System.out.println(">>>>>>>Register application account is okay!>>>>>");
	}
}
