package com.babeeta.butterfly.testkit.server.rest.dev.account;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class DeviceRegisterClient extends RestfulClient {

	private final String ip;
	private final int port;

	/**
	 */
	public DeviceRegisterClient(String ip, int port) {
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
	 * @param imsi
	 *            imsi
	 * @param imei
	 *            imei
	 */
	public void register(String imei) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("imei", imei);
		String contentStr = JSONObject.fromObject(info).toString();

		setHttpMethod(new HttpPost("/0/api/account/register"));
		setHttpContentType("application/json;charset=UTF-8");
		ByteArrayEntity byteArray = null;
		try {
			byteArray = new ByteArrayEntity(contentStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setHttpContent(byteArray);

		execute();
	}
}
