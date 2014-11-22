/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.subscription;

import org.apache.http.client.methods.HttpDelete;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class UnbindClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public UnbindClient(String ip, int port) {
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

	public void unbind(String aid, String cid, String did) {
		setHttpMethod(new HttpDelete("/0/api/subscription/unbind/" + did + "/"
		        + aid + "/" + cid));
		execute();
	}
}
