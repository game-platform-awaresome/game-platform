/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.subscription;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class QueryClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public QueryClient(String ip, int port) {
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

	public void query(String aid, String cid) {
		setHttpMethod(new HttpGet("/0/api/subscription/query/" + aid + "/"
		        + cid));
		execute();
	}
}
