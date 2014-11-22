/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.app.account;

import org.apache.http.client.methods.HttpGet;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class AppAuthClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public AppAuthClient(String ip, int port) {
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

	/**
	 * @param appId
	 *            app id
	 * @param appKey
	 *            app key
	 */
	public void auth(String appId, String appKey) {
		setHttpMethod(new HttpGet("/0/api/user/login/" + appId + "/"
		        + appKey));
		execute();
	}

}
