/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.subscription;

import org.apache.http.client.methods.HttpPost;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class BindClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public BindClient(String ip, int port) {
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

	public void bind(String aid, String cid, String did) {
		StringBuilder url = new StringBuilder(
		        "/0/api/subscription/bind/").append(did)
		        .append("/").append(aid).append("/");
		if (cid != null) {
			url.append(cid);
		} else {
			url.append("null");
		}

		setHttpMethod(new HttpPost(url.toString()));
		execute();
	}
}
