package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.RestfulClient;
import junit.framework.Assert;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TestSMS extends RestfulClient {
    private String ip;
    private int port;

    @Override
    protected String getIp() {
        return this.ip;
    }

    @Override
    protected int getPort() {
        return this.port;
    }

    public TestSMS(String ip, int port){
        this.port = port;
        this.ip = ip;
    }

    public void send(String mobile,String code) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("http://211.152.9.213:6600/Send.aspx?CorpID=gameyz&Pwd=yz12345&Mobile=");
        sb.append(mobile);
        sb.append("&Content=");
        sb.append(URLEncoder.encode("您的验证码：" + code + "【XX游戏】", "GBK"));
        setHttpMethod(new HttpGet(sb.toString()));
        setHttpContentType("charset=GBK");
        execute();
    }

    public static void main(String[] args){
        TestSMS test = new TestSMS("211.152.9.213",6600);
        try {
            int codeInt = Math.abs((new Random()).nextInt())%10000;
            if(codeInt < 1000){
                codeInt = codeInt + 1000;
            }
            test.send("13910673711",String.valueOf(codeInt));
            Assert.assertEquals(test.getResponseStatus(), 200);
            String result = EntityUtils
                    .toString(test.getResponseEntity());
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
