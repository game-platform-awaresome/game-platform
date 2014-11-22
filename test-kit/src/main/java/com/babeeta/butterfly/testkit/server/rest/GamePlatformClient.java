package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class GamePlatformClient extends RestfulClient {

    private String ip;

    private int port;

    public GamePlatformClient(String ip, int port){
        super();
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected String getIp() {
        return ip;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected int getPort() {
        return port;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testCreateGame(String title, String downuri, String picuri, String pkgname, String version, String type){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("desc", title);
        info.put("downuri", downuri);
        info.put("picuri",picuri);
        info.put("pkgname",pkgname);
        info.put("title",title);
        info.put("version",version);
        info.put("type",type);

        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPut("/0/api/warehouse/com.future.gameplatform.account.game"));
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


    public void testUpdateGameHouse(String id, String title, String downuri, String picuri, String pkgname, String version, String type, Map[] channels){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("channels", channels);
        info.put("desc", title);
        info.put("downuri", downuri);
        info.put("picuri",picuri);
        info.put("pkgname",pkgname);
        info.put("title",title);
        info.put("version",version);
        info.put("type",type);

        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPut("/0/api/warehouse/com.future.gameplatform.account.game"));
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

    public void testVersion(String channelid, String version){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("channelid", channelid);
        info.put("version", version);

        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/0/api/warehouse/gameplatform/version"));
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

    public void testList(){
        setHttpMethod(new HttpGet("/0/api/warehouse/list/com.future.gameplatform.account.game"));
        execute();
    }


}
