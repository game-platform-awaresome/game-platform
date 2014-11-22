package com.babeeta.butterfly.testkit.server.rest;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.babeeta.butterfly.testkit.server.rest.dev.subscription.BindClient;
import com.babeeta.butterfly.testkit.server.rest.dev.subscription.QueryClient;
import com.babeeta.butterfly.testkit.server.rest.dev.subscription.UnbindClient;

public class TestRestSubscription {
	private static final String IP = "192.168.20.83";

	@Before
	public void setUp() throws Exception {

	}

	@Ignore
	@Test
	public void testDevSubscription() throws ParseException, IOException {
		String did = "445d00727c5d5c479f8c7a6ae6462ff2";
		String aid = "cb6e7c6eb5a74023b2bc4087c7b8e1cc";

		BindClient bindClient = new BindClient(IP, 8083);
		bindClient.bind(aid, null, did);
		Assert.assertEquals(bindClient.getResponseStatus(), 200);

		String cid = null;
		cid = EntityUtils.toString(bindClient.getResponseEntity());
		System.out.println(cid);

		QueryClient queryClient = new QueryClient(IP, 8083);
		queryClient.query(aid, cid);
		Assert.assertEquals(queryClient.getResponseStatus(), 200);
		String returnDid = EntityUtils
		        .toString(queryClient.getResponseEntity());

		Assert.assertEquals(returnDid, did);

		//UnbindClient unbindClient = new UnbindClient(IP, 8083);
		//unbindClient.unbind(aid, cid, did);
		//Assert.assertEquals(unbindClient.getResponseStatus(), 200);
	}

	@After
	public void tearDown() throws Exception {
	}

}