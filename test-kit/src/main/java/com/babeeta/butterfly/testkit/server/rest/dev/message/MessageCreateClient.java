/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.dev.message;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class MessageCreateClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public MessageCreateClient(String ip, int port) {
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

	public void create(String sender, String recipient, String deviceId,
			String content, int life) {
		setHttpMethod(new HttpPost("/api/trade/create/" + sender + "/"
				+ recipient + "/" + deviceId));
		setHttpContentType("application/octet-stream");
		setHeader("life", Integer.toString(life));

		ByteArrayEntity byteArray = null;
		byteArray = new ByteArrayEntity(content.getBytes());
		setHttpContent(byteArray);

		execute();
	}
}
