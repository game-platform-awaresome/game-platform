/**
 * 
 */
package com.babeeta.butterfly.testkit.server.servlet.app.gateway;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class PushClient extends RestfulClient {

	private final String ip;
	private final int port;
	private final String appId;

	/**
	 */
	public PushClient(String ip, int port, String appId, String appKey) {
		super(appId, appKey);
		this.appId = appId;
		this.ip = ip;
		this.port = port;
	}

	@Override
	protected String getIp() {
		return ip;
	}

	@Override
	protected int getPort() {
		return port;
	}

	public void push(String cid, int life, String content) {
		setHttpMethod(new HttpPost("/1/api/" + appId + "/trade/" + cid));
		setHttpContentType("application/octet-stream");
		setHeader("life", Integer.toString(life));

		ByteArrayEntity byteArray = null;
		byteArray = new ByteArrayEntity(content.getBytes());
		setHttpContent(byteArray);

		execute();
	}
}
