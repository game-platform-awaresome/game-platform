/**
 * 
 */
package com.babeeta.butterfly.testkit.server.servlet.app.gateway;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class OldPushClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public OldPushClient(String ip, int port, String appId, String appKey) {
		super(appId, appKey);
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
		setHttpMethod(new HttpPost("/service/client/" + cid + "/trade"));
		setHttpContentType("application/octet-stream");
		setHeader("life", Integer.toString(life));

		// ByteArrayEntity byteArray = null;
		// byteArray = new ByteArrayEntity(content.getBytes());
		// setHttpContent(byteArray);

		try {
			setHttpContent(new StringEntity(content));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		execute();
	}
}
