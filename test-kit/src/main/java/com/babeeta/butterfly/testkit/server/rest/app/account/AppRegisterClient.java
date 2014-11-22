/**
 * 
 */
package com.babeeta.butterfly.testkit.server.rest.app.account;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import com.babeeta.butterfly.testkit.server.RestfulClient;

/**
 * 
 */
public class AppRegisterClient extends RestfulClient {
	private final String ip;
	private final int port;

	/**
	 */
	public AppRegisterClient(String ip, int port) {
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
	 * @param email
	 *            email
	 */
	public void register(String description, String email) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("description", description);
		info.put("email", email);

		String contentStr = JSONObject.fromObject(info).toString();

		setHttpMethod(new HttpPost("/0/api/user/register"));
		setHttpContentType("application/json;charset=UTF-8");
        //setHeader("channelid","1234");
		ByteArrayEntity byteArray = null;
		try {
			byteArray = new ByteArrayEntity(contentStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setHttpContent(byteArray);

		execute();
	}


    public void update(String uid,String token){
        Map<String,Object> info = new HashMap<String, Object>();
        info.put("passwd","123456");
        info.put("mobile","3243324234");
        info.put("nick","xxx");
        info.put("sex","f");
        String content = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/1/api/user/"+uid));
        setHeader("uid",uid);
        setHeader("token",token);
        setHttpContentType("application/json;charset=UTF-8");
        ByteArrayEntity byteArray = null;
        try {
            byteArray = new ByteArrayEntity(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }

    public void sendcode(String mobile){
        setHttpMethod(new HttpGet("/0/api/user/getcode/" + mobile));
        execute();
    }

    public void validCode(String uid, String token){
        Map<String,Object> info = new HashMap<String, Object>();
        info.put("mobile","13910673711");
        info.put("code","356930");
        String content = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/1/api/user/bindmobile"));
        setHeader("uid",uid);
        setHeader("token",token);
        setHttpContentType("application/json;charset=UTF-8");
        ByteArrayEntity byteArray = null;
        try {
            byteArray = new ByteArrayEntity(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }

    public void getbackuser(String uid,String token){
        Map<String,Object> info = new HashMap<String, Object>();
        info.put("mobile","13910673711");
        info.put("code","356930");
        String content = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/0/api/user/getbackUser"));
        setHeader("uid",uid);
        setHeader("token",token);
        setHttpContentType("application/json;charset=UTF-8");
        ByteArrayEntity byteArray = null;
        try {
            byteArray = new ByteArrayEntity(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHttpContent(byteArray);

        execute();
    }

}
