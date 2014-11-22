/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.message;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class MessageQueryClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public MessageQueryClient(String ip, int port) {
		super();
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
		setHttpMethod(new HttpGet("/api/trade/status/query?id=" + msgId));
		execute();
	}
}
