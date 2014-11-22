/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.message;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class MessageListClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public MessageListClient(String ip, int port) {
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

	public void list(String did, String status) {
		setHttpMethod(new HttpGet("/api/trade/list?device=" + did
				+ "&status=" + status));
		execute();
	}

	public void list(String did, String status, int pageSize, int pageIndex) {
		setHttpMethod(new HttpGet("/api/trade/list?device=" + did
				+ "&status=" + status + "&pageSize=" + pageSize + "&pageIndex="
				+ pageIndex));
		execute();
	}
}
