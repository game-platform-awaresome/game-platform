/**
 * 
 */
package com.babeeta.butterfly.testkit.server.servlet.app.gateway;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class OldQueryClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public OldQueryClient(String ip, int port, String appId, String appKey) {
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

	public void query(String msgId) {
		setHttpMethod(new HttpGet("/service/client/all/trade/"
				+ msgId));
		execute();
	}
}
