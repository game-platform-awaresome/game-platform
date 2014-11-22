/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.message;

import org.apache.http.client.methods.HttpPut;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class MessageUpdateClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public MessageUpdateClient(String ip, int port) {
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

	public void update(String msgId, String status) {
		setHttpMethod(new HttpPut("/api/trade/" + msgId
				+ "/status/update?status=" + status));
		execute();
	}
}
