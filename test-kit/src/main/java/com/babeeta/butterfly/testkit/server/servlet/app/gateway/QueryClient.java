/**
 * 
 */
package com.babeeta.butterfly.testkit.server.servlet.app.gateway;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class QueryClient extends RestfulClient {

	private final String ip;
	private final int port;
	private final String appId;

	/**
	 */
	public QueryClient(String ip, int port, String appId, String appKey) {
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

	public void query(String msgId) {
		setHttpMethod(new HttpGet("/1/api/" + appId + "/trade?messageId="
				+ msgId));
		execute();
	}
}
