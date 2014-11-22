package com.babeeta.butterfly.testkit.server.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.bson.BSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.babeeta.butterfly.testkit.server.rest.app.account.AppAuthClient;
import com.babeeta.butterfly.testkit.server.rest.app.account.AppRegisterClient;
import com.babeeta.butterfly.testkit.server.rest.dev.account.DeviceAuthClient;
import com.babeeta.butterfly.testkit.server.rest.dev.account.DeviceRegisterClient;
import com.babeeta.butterfly.testkit.server.rest.dev.message.MessageCountClient;
import com.babeeta.butterfly.testkit.server.rest.dev.message.MessageCreateClient;
import com.babeeta.butterfly.testkit.server.rest.dev.message.MessageListClient;
import com.babeeta.butterfly.testkit.server.rest.dev.message.MessageQueryClient;
import com.babeeta.butterfly.testkit.server.rest.dev.message.MessageUpdateClient;
import com.babeeta.butterfly.testkit.server.rest.dev.subscription.BindClient;
import com.babeeta.butterfly.testkit.server.rest.dev.subscription.QueryClient;
import com.babeeta.butterfly.testkit.server.rest.dev.subscription.UnbindClient;
import com.mongodb.Bytes;

public class TestRestInterface {
	private static final String IP = "192.168.0.23";

	@Before
	public void setUp() throws Exception {

	}

	@Ignore
	@Test
	public void testAppAccount() {
		AppRegisterClient registerClient = new AppRegisterClient(IP, 8081);
		registerClient.register("Xinyu-test", "xinyu.liu@babeeta.com");
		Assert.assertEquals(registerClient.getResponseStatus(), 200);

		String result = null;
		try {
			result = EntityUtils
			        .toString(registerClient.getResponseEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject account = JSONObject.fromObject(result);

		String appId = account.getString("id");
		String appKey = account.getString("key");

		System.out.println("appId = " + appId + "; appKey = " + appKey);
		System.out.println(account.toString());

		AppAuthClient authClient = new AppAuthClient(IP, 8081);
		authClient.auth(appId, appKey);
		Assert.assertEquals(authClient.getResponseStatus(), 200);
	}

	public static String generateImei() {
		long randomNumber = 0;

		do {
			randomNumber = Math.abs(new Random().nextLong());
		} while (Long.toString(randomNumber).length() < 15);

		return (Long.toString(randomNumber).substring(0, 15));
	}

	@Ignore
	@Test
	public void testDevAccount() {
		DeviceRegisterClient registerClient = new DeviceRegisterClient(IP, 8082);
		String imei = generateImei();
		registerClient.register(imei);
		Assert.assertEquals(registerClient.getResponseStatus(), 200);

		String result = null;
		try {
			result = EntityUtils
			        .toString(registerClient.getResponseEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject account = JSONObject.fromObject(result);

		String did = account.getString("id");
		String key = account.getString("key");

		System.out.println("id = " + did + "; key = " + key);
		System.out.println(account.toString());

		DeviceAuthClient authClient = new DeviceAuthClient(IP, 8082);
		authClient.auth(did, key);
		Assert.assertEquals(authClient.getResponseStatus(), 200);
	}

	@Ignore
	@Test
	public void testDevSubscription() throws ParseException, IOException {
		String did = "b29762c7283b40b7865efa3ae22fb1cb";
		String aid = "2a68b82348ce4c4e925b404dd34f5bce";

		BindClient bindClient = new BindClient(IP, 8083);
		bindClient.bind(aid, "", did);
		Assert.assertEquals(bindClient.getResponseStatus(), 200);

		String bindResult = null;
		bindResult = EntityUtils.toString(bindClient.getResponseEntity());
		System.out.println(bindResult);

		String cid = JSONObject.fromObject(bindResult).getString("cid");

		QueryClient queryClient = new QueryClient(IP, 8083);
		queryClient.query(aid, cid);
		Assert.assertEquals(queryClient.getResponseStatus(), 200);

		String returnDid = null;
		returnDid = JSONObject.fromObject(
		        EntityUtils.toString(queryClient.getResponseEntity()))
		        .getString("did");
		Assert.assertEquals(returnDid, did);

		UnbindClient unbindClient = new UnbindClient(IP, 8083);
		unbindClient.unbind(aid, cid, did);
		Assert.assertEquals(unbindClient.getResponseStatus(), 200);

		String unbindResult = null;
		unbindResult = EntityUtils.toString(unbindClient.getResponseEntity());
		System.out.println(unbindResult);
	}

	@Ignore
	@Test
	public void testDevMessage() throws ParseException, IOException {
		String recipient = "02f31dc2e78349d785c82017be661e20";
		String sender = "4274dfb51e684aad899ed6bfb101ab91";
		String device = "b29762c7283b40b7865efa3ae22fb1cb";
		String content = "test-content";

		MessageCreateClient createClient = new MessageCreateClient(IP, 8084);
		createClient.create(sender, recipient, device, content, 3600);
		Assert.assertEquals(createClient.getResponseStatus(), 201);

		String messageId = null;
		messageId = EntityUtils.toString(createClient.getResponseEntity());
		System.out.println(messageId);

		MessageUpdateClient update = new MessageUpdateClient(IP, 8084);
		update.update(messageId, "ACKED");
		Assert.assertEquals(update.getResponseStatus(), 200);

		MessageQueryClient query = new MessageQueryClient(IP, 8084);
		query.query(messageId);
		Assert.assertEquals(query.getResponseStatus(), 200);

		Assert.assertEquals(
		        "ACKED",
		        JSONObject.fromObject(
		                EntityUtils.toString(query.getResponseEntity()))
		                .getString("status"));

		MessageListClient list = new MessageListClient(IP, 8084);
		list.list(device, "ACKED");
		Assert.assertEquals(list.getResponseStatus(), 200);

		HttpEntity entity = list.getResponseEntity();
		byte[] result = EntityUtils.toByteArray(entity);

		BSONObject bsonList = Bytes.decode(result);
		long msgCount = 0;
		Iterator<String> it = bsonList.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			BSONObject value = (BSONObject) bsonList.get(key);
			Assert.assertEquals(value.get("_id").toString(), key);
			System.out.println("id =[" + key + "] content = ["
			        + value.toString() + "]");
			msgCount++;
		}

		MessageCountClient count = new MessageCountClient(IP, 8084);
		count.list(device, "ACKED");
		Assert.assertEquals(count.getResponseStatus(), 200);

		long messageCount = Long.parseLong(EntityUtils.toString(count
		        .getResponseEntity()));

		Assert.assertEquals(msgCount, messageCount);
	}

	@Ignore
	@Test
	public void testCountMessage() throws ParseException, IOException {
		String device = "000a35d832153e058305dcf54e6cae3f";

		MessageCountClient count = new MessageCountClient(IP, 8084);
		count.list(device, "DELIVERING");
		Assert.assertEquals(count.getResponseStatus(), 200);

		long messageCount = Long.parseLong(EntityUtils.toString(count
		        .getResponseEntity()));

		System.out.println("Count trade result = " + messageCount);
	}

	@Ignore
	@Test
	public void testListMessage() throws ParseException, IOException {
		String recipient = "02f31dc2e78349d785c82017be661e20";
		String sender = "4274dfb51e684aad899ed6bfb101ab91";
		String device = "b29762c7283b40b7865efa3ae22fb1cb";
		String content = "test-content";
		List<String> messageIdList = new ArrayList<String>();
		MessageCreateClient createClient = new MessageCreateClient(IP, 8084);

		for (int i = 0; i < 50; i++) {
			createClient.create(sender, recipient, device, content, 3600);
			Assert.assertEquals(createClient.getResponseStatus(), 201);
			String messageId = EntityUtils.toString(createClient
			        .getResponseEntity());
			System.out.println(messageId);
			if (!messageIdList.contains(messageId)) {
				messageIdList.add(messageId);
			}
		}

		MessageCountClient count = new MessageCountClient(IP, 8084);
		count.list(device, "DELIVERING");
		Assert.assertEquals(count.getResponseStatus(), 200);

		long messageCount = Long.parseLong(EntityUtils.toString(count
		        .getResponseEntity()));

		System.out.println("Count trade result = " + messageCount);

		MessageListClient list = new MessageListClient(IP, 8084);
		list.list(device, "DELIVERING", 20, 0);
		Assert.assertEquals(list.getResponseStatus(), 200);

		HttpEntity entity = list.getResponseEntity();
		byte[] result = EntityUtils.toByteArray(entity);

		BSONObject bsonList = Bytes.decode(result);
		Iterator<String> it = bsonList.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			BSONObject value = (BSONObject) bsonList.get(key);
			Assert.assertEquals(value.get("_id").toString(), key);
			System.out.println("id =[" + key + "] content = ["
			        + value.toString() + "]");
		}
	}

	@After
	public void tearDown() throws Exception {
	}

}