package com.babeeta.butterfly.testkit.server.servlet;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.babeeta.butterfly.testkit.server.servlet.app.gateway.OldOldPushClient;
import com.babeeta.butterfly.testkit.server.servlet.app.gateway.OldPushClient;
import com.babeeta.butterfly.testkit.server.servlet.app.gateway.OldQueryClient;
import com.babeeta.butterfly.testkit.server.servlet.app.gateway.PushClient;
import com.babeeta.butterfly.testkit.server.servlet.app.gateway.QueryClient;

public class TestServletInterface {
	private static final String IP = "192.168.0.24";
	private static final String SUPER_USERNAME = "chuangqianmingyueguang";
	private static final String SUPER_PASSWORD = "yisuiyikurong";

	private static final String appId = "449ff42e4d1143f0ad2a9f42b4bd96d2";
	private static final String appKey = "4e5848e368d745e2be58cd3864be4584";
	private static final String cid = "4fa8d6c876e44fb561f40b94dc4eb3f9";

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testNewApi() throws ParseException, IOException {
		System.out.println("\n\n>>>>>>>  Test new trade interface >>>>>>");
		PushClient push = new PushClient(IP, 8080, appId, appKey);

		push.push(cid, 20, "test~test! new api");

		Assert.assertEquals(push.getResponseStatus(), 200);

		String messageId = EntityUtils.toString(push.getResponseEntity());

		Assert.assertNotNull(messageId);
		Assert.assertTrue(messageId.length() > 0);

		System.out.println("trade id = " + messageId);

		QueryClient queryClient = new QueryClient(IP, 8080, appId, appKey);
		queryClient.query(messageId);
		Assert.assertEquals(queryClient.getResponseStatus(), 200);
		String messageStatus = EntityUtils.toString(queryClient
		        .getResponseEntity());

		System.out.println("trade[" + messageId + "] is in " + messageStatus);

		System.out.println(">>>>>>> New trade interface is okay!>>>>>");
	}

	@Ignore
	@Test
	public void testOldApi() throws ParseException, IOException {
		System.out.println("\n\n>>>>>>>  Test old trade interface >>>>>>");
		OldPushClient push = new OldPushClient(IP, 8080, appId, appKey);

		push.push(cid, 20, "test~test! old api");

		Assert.assertEquals(push.getResponseStatus(), 200);

		String messageId = EntityUtils.toString(push.getResponseEntity());
		Assert.assertNotNull(messageId);
		Assert.assertTrue(messageId.length() > 0);
		System.out.println("trade id = " + messageId);

		OldQueryClient queryClient = new OldQueryClient(IP, 8080, appId, appKey);
		queryClient.query(messageId);
		Assert.assertEquals(queryClient.getResponseStatus(), 200);
		String messageStatus = EntityUtils.toString(queryClient
		        .getResponseEntity());

		System.out.println("trade[" + messageId + "] is in " + messageStatus);

		System.out.println(">>>>>>> Old trade interface is okay!>>>>>");
	}

	@Ignore
	@Test
	public void testOldestApi() throws ParseException, IOException {
		System.out.println("\n\n>>>>>>>  Test oldest trade interface >>>>>>");
		OldOldPushClient push = new OldOldPushClient(IP, 8080, appId, appKey);

		push.push(cid, 20, "test~test! oldest api");

		Assert.assertEquals(push.getResponseStatus(), 200);

		String messageId = EntityUtils.toString(push.getResponseEntity());
		Assert.assertNotNull(messageId);
		Assert.assertTrue(messageId.length() > 0);
		System.out.println("trade id = " + messageId);

		OldQueryClient queryClient = new OldQueryClient(IP, 8080, appId, appKey);
		queryClient.query(messageId);
		Assert.assertEquals(queryClient.getResponseStatus(), 200);
		String messageStatus = EntityUtils.toString(queryClient
		        .getResponseEntity());

		System.out.println("trade[" + messageId + "] is in " + messageStatus);
		System.out.println(">>>>>>> Oldest trade interface is okay!>>>>>");
	}

}
