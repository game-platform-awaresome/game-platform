package com.babeeta.butterfly.testkit.server.rest.app.account;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GameAccountClient extends RestfulClient {

    private String ip;

    private int port;

    public GameAccountClient(String ip, int port){
        super();
        this.ip = ip;
        this.port = port;
    }


    @Override
    protected String getIp() {
        return this.ip;
    }

    @Override
    protected int getPort() {
        return this.port;
    }

    public void testCreateRechargeAccount(String appid,String appkey,String shortcode,String noticeurl) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("appid", appid);
        info.put("appkey", appkey);
        info.put("shortcode",shortcode);
        info.put("noticeurl",noticeurl);
        //info.put("payway","ap");

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        /****
        Map<String, Object> cm = new HashMap<String, Object>();
        cm.put("channel","jch");
        cm.put("type","sms_cm");
        cm.put("amount","1");
        cm.put("targetcode","106588992");
        cm.put("instruct_fixed","127103056305200613600000613590011000000000000000000000131");
        cm.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm.put("status","OK");
        list.add(cm);

        Map<String, Object> cm1 = new HashMap<String, Object>();
        cm1.put("channel","jch");
        cm1.put("type","sms_cm");
        cm1.put("amount","2");
        cm1.put("targetcode","106588992");
        cm1.put("instruct_fixed","127103056305200613600000613590021000000000000000000000131");
        cm1.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm1.put("status","OK");
        list.add(cm1);

        Map<String, Object> cm2 = new HashMap<String, Object>();
        cm2.put("channel","jch");
        cm2.put("type","sms_cm");
        cm2.put("amount","3");
        cm2.put("targetcode","106588992");
        cm2.put("instruct_fixed","127103056305200613600000613590031000000000000000000000131");
        cm2.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm2.put("status","OK");
        list.add(cm2);

        Map<String, Object> cm3 = new HashMap<String, Object>();
        cm3.put("channel","jch");
        cm3.put("type","sms_cm");
        cm3.put("amount","4");
        cm3.put("targetcode","106588992");
        cm3.put("instruct_fixed","127103056305200613600000613590041000000000000000000000131");
        cm3.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm3.put("status","OK");
        list.add(cm3);

        Map<String, Object> cm4 = new HashMap<String, Object>();
        cm4.put("channel","jch");
        cm4.put("type","sms_cm");
        cm4.put("amount","5");
        cm4.put("targetcode","106588992");
        cm4.put("instruct_fixed","127103056305200613600000613590051000000000000000000000131");
        cm4.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm4.put("status","OK");
        list.add(cm4);

        Map<String, Object> cm5 = new HashMap<String, Object>();
        cm5.put("channel","jch");
        cm5.put("type","sms_cm");
        cm5.put("amount","6");
        cm5.put("targetcode","106588992");
        cm5.put("instruct_fixed","127103056305200613600000613590061000000000000000000000131");
        cm5.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm5.put("status","OK");
        list.add(cm5);

        Map<String, Object> cm6 = new HashMap<String, Object>();
        cm6.put("channel","jch");
        cm6.put("type","sms_cm");
        cm6.put("amount","7");
        cm6.put("targetcode","106588992");
        cm6.put("instruct_fixed","127103056305200613600000613590071000000000000000000000131");
        cm6.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm6.put("status","OK");
        list.add(cm6);

        Map<String, Object> cm7 = new HashMap<String, Object>();
        cm7.put("channel","jch");
        cm7.put("type","sms_cm");
        cm7.put("amount","8");
        cm7.put("targetcode","106588992");
        cm7.put("instruct_fixed","127103056305200613600000613590081000000000000000000000131");
        cm7.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm7.put("status","OK");
        list.add(cm7);

        Map<String, Object> cm8 = new HashMap<String, Object>();
        cm8.put("channel","jch");
        cm8.put("type","sms_cm");
        cm8.put("amount","9");
        cm8.put("targetcode","106588992");
        cm8.put("instruct_fixed","127103056305200613600000613590091000000000000000000000131");
        cm8.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm8.put("status","OK");
        list.add(cm8);

        Map<String, Object> cm9 = new HashMap<String, Object>();
        cm9.put("channel","jch");
        cm9.put("type","sms_cm");
        cm9.put("amount","10");
        cm9.put("targetcode","106588992");
        cm9.put("instruct_fixed","127103056305200613600000613590101000000000000000000000131");
        cm9.put("instruct_sub","[a-zA-Z0-9]{13,13}");
        cm9.put("status","OK");
        list.add(cm9);
        ****/

        Map<String, Object> cm = new HashMap<String, Object>();
        cm.put("channel","jch");
        cm.put("type","sms_cm");
        cm.put("amount","1");
        cm.put("targetcode","1065889919");
        cm.put("instruct_fixed","ybbihp1311181890");
        cm.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm.put("status","OK");
        list.add(cm);

        Map<String, Object> cm1 = new HashMap<String, Object>();
        cm1.put("channel","jch");
        cm1.put("type","sms_cm");
        cm1.put("amount","2");
        cm1.put("targetcode","1065889919");
        cm1.put("instruct_fixed","ybbihp1311181891");
        cm1.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm1.put("status","OK");
        list.add(cm1);

        Map<String, Object> cm2 = new HashMap<String, Object>();
        cm2.put("channel","jch");
        cm2.put("type","sms_cm");
        cm2.put("amount","3");
        cm2.put("targetcode","1065889919");
        cm2.put("instruct_fixed","ybbihp1311181892");
        cm2.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm2.put("status","OK");
        list.add(cm2);

        Map<String, Object> cm3 = new HashMap<String, Object>();
        cm3.put("channel","jch");
        cm3.put("type","sms_cm");
        cm3.put("amount","4");
        cm3.put("targetcode","1065889919");
        cm3.put("instruct_fixed","ybbihp1311181893");
        cm3.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm3.put("status","OK");
        list.add(cm3);

        Map<String, Object> cm4 = new HashMap<String, Object>();
        cm4.put("channel","jch");
        cm4.put("type","sms_cm");
        cm4.put("amount","5");
        cm4.put("targetcode","1065889919");
        cm4.put("instruct_fixed","ybbihp1311181894");
        cm4.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm4.put("status","OK");
        list.add(cm4);

        Map<String, Object> cm5 = new HashMap<String, Object>();
        cm5.put("channel","jch");
        cm5.put("type","sms_cm");
        cm5.put("amount","6");
        cm5.put("targetcode","1065889919");
        cm5.put("instruct_fixed","ybbihp1311181895");
        cm5.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm5.put("status","OK");
        list.add(cm5);

        Map<String, Object> cm6 = new HashMap<String, Object>();
        cm6.put("channel","jch");
        cm6.put("type","sms_cm");
        cm6.put("amount","7");
        cm6.put("targetcode","1065889919");
        cm6.put("instruct_fixed","ybbihp1311181896");
        cm6.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm6.put("status","OK");
        list.add(cm6);

        Map<String, Object> cm7 = new HashMap<String, Object>();
        cm7.put("channel","jch");
        cm7.put("type","sms_cm");
        cm7.put("amount","8");
        cm7.put("targetcode","1065889919");
        cm7.put("instruct_fixed","ybbihp1311181897");
        cm7.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm7.put("status","OK");
        list.add(cm7);

        Map<String, Object> cm8 = new HashMap<String, Object>();
        cm8.put("channel","jch");
        cm8.put("type","sms_cm");
        cm8.put("amount","9");
        cm8.put("targetcode","1065889919");
        cm8.put("instruct_fixed","ybbihp1311181898");
        cm8.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm8.put("status","OK");
        list.add(cm8);

        Map<String, Object> cm9 = new HashMap<String, Object>();
        cm9.put("channel","jch");
        cm9.put("type","sms_cm");
        cm9.put("amount","10");
        cm9.put("targetcode","1065889919");
        cm9.put("instruct_fixed","ybbihp1311181899");
        cm9.put("instruct_sub","[a-zA-Z0-9]{0,108}");
        cm9.put("status","OK");
        list.add(cm9);

        Map<String, Object> ct = new HashMap<String, Object>();
        ct.put("channel","pxrh");
        ct.put("type","sms_ct");
        ct.put("amount","1");
        ct.put("targetcode","1066916531");
        ct.put("instruct_fixed","111#HJ45#PX");
        ct.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        ct.put("status","OK");
        list.add(ct);

        Map<String, Object> ct1 = new HashMap<String, Object>();
        ct1.put("channel","pxrh");
        ct1.put("type","sms_ct");
        ct1.put("amount","2");
        ct1.put("targetcode","1066916503");
        ct1.put("instruct_fixed","130#HJ46#PX");
        ct1.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        ct1.put("status","OK");
        list.add(ct1);

        Map<String, Object> ct2 = new HashMap<String, Object>();
        ct2.put("channel","pxrh");
        ct2.put("type","sms_ct");
        ct2.put("amount","5");
        ct2.put("targetcode","106605502");
        ct2.put("instruct_fixed","czPX");
        ct2.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        ct2.put("status","OK");
        list.add(ct2);

        Map<String, Object> ct3 = new HashMap<String, Object>();
        ct3.put("channel","pxrh");
        ct3.put("type","sms_ct");
        ct3.put("amount","10");
        ct3.put("targetcode","1066916504");
        ct3.put("instruct_fixed","170#HJ47#PX");
        ct3.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        ct3.put("status","OK");
        list.add(ct3);

        Map<String,Object> cu = new HashMap<String, Object>();
        cu.put("channel","pxrh");
        cu.put("type","sms_cu");
        cu.put("amount","1");
        cu.put("targetcode","10655556003");
        cu.put("instruct_fixed","3#HJ19#PX");
        cu.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        cu.put("status","OK");
        list.add(cu);

        Map<String,Object> cu1 = new HashMap<String, Object>();
        cu1.put("channel","pxrh");
        cu1.put("type","sms_cu");
        cu1.put("amount","2");
        cu1.put("targetcode","10655556006");
        cu1.put("instruct_fixed","4#HJ20#PX");
        cu1.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        cu1.put("status","OK");
        list.add(cu1);

        Map<String,Object> cu2 = new HashMap<String, Object>();
        cu2.put("channel","pxrh");
        cu2.put("type","sms_cu");
        cu2.put("amount","5");
        cu2.put("targetcode","10669202");
        cu2.put("instruct_fixed","zf#1020$PX");
        cu2.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        cu2.put("status","OK");
        list.add(cu2);

        Map<String,Object> cu3 = new HashMap<String, Object>();
        cu3.put("channel","pxrh");
        cu3.put("type","sms_cu");
        cu3.put("amount","10");
        cu3.put("targetcode","10669202");
        cu3.put("instruct_fixed","zf#1021$PX");
        cu3.put("instruct_sub","[a-zA-Z0-9]{0,10}");
        cu3.put("status","OK");
        list.add(cu3);
        //Map<String, Object> cu = new HashMap<String, Object>();
        info.put("items", list);

        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPut("/0/api/rechargeapp/account"));
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

    public void testDelete(String appid){
        setHttpMethod(new HttpDelete("/0/api/rechargeapp/account/"+appid));
        execute();
    }

    public void testGetById(String appid){
        setHttpMethod(new HttpGet("/0/api/rechargeapp/account/"+appid));
        execute();
    }

    public void testUpdate(String appid, String appkey){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("status","DELETED");
        String contentStr = JSONObject.fromObject(info).toString();

        setHttpMethod(new HttpPost("/0/api/rechargeapp/account"+appid));
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
