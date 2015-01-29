package com.gp.test.gateway.mobile.recharge;

import com.future.gameplatform.recharge.common.util.RechargeConstants;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-23
 * Time: 下午10:31
 * To change this template use File | Settings | File Templates.
 */
public class MobileTest {

   // tested
    @Test
    public void TestCmccResource(){
        try{
            String shortcode = "101";
            String orderno = "423444232";
            String channel="APEX-cmcc-sdkoffline-1-2";
            String key = "544260cd58b0e4b0043b0a710eaadefa";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("channel",channel);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/cmcc/sdk/notice?code="+shortcode+"&orderno="+orderno+"&channel="+channel+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: ct mobile
     */
    @Test
    public void TestCTPageResource1(){
        try{
            String shortcode = "101";
            String orderno = "ct-jjjjsdfsfsdf";
            String mobile = "1891022222";
            String fee = "1";
            String key = "ODQwRjgyNTQ2NzVDOEJBMjExODE0ODA";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("mobile",mobile);
            params.put("fee",fee);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/ct/page/code?code="+shortcode+"&orderno="+orderno+"&mobile="+mobile+"&fee="+fee+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO:ct mobile
     */
    @Test
    public void TestCTPageResource2(){
        try{
            String shortcode = "101";
            String orderno = "ct-jjjjsdfsfsdf";
            String smscode = "3423444";
            String key = "ODQwRjgyNTQ2NzVDOEJBMjExODE0ODA";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("smscode",smscode);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/ct/page/result?code="+shortcode+"&orderno="+orderno+"&smscode="+smscode+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void TestCUPageResource1(){
        try{
            String shortcode = "101";
            String orderno = "cu-fdasdfsa";
            String mobile = "18510326416";
            String fee = "1";
            String channel="APEX-cu-page-1-1";
            String key = "544260cd58b0e4b0043b0a710eaadefa";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("mobile",mobile);
            params.put("channel",channel);
            params.put("fee",fee);
            GetMethod getMethod = new GetMethod("http://localhost:8080/0/api/mobilerecharge/cu/page/code?code="+shortcode+"&orderno="+orderno+"&mobile="+mobile+"&fee="+fee+"&channel="+channel+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestCUPageResource2(){
        try{
            String shortcode = "101";
            String orderno = "cu-jjjjsdfsfsdf1";
            String smscode = "644897";
            String key = "ODQwRjgyNTQ2NzVDOEJBMjExODE0ODA";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("smscode",smscode);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/cu/page/result?code="+shortcode+"&orderno="+orderno+"&smscode="+smscode+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestCUDynamicResource(){
        try{
            String shortcode = "101";
            String orderno = "12347645634654634890233";
            String fee = "2";
            String channel = "APEX-cmcc-dynconf-1-2";
            String key = "544260cd58b0e4b0043b0a710eaadefa";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("fee",fee);
            params.put("channel", channel);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/all/dynamic/code?code="+shortcode+"&orderno="+orderno+"&fee="+fee+"&channel="+channel+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            System.out.println(getMethod.getQueryString());
            if (status == 200) {
                String repStr = inputStream2String(getMethod.getResponseBodyAsStream());
                System.out.println(repStr);
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private    String   inputStream2String(InputStream is)   throws   IOException{
        ByteArrayOutputStream   baos   =   new ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }

    @Test
    public void testAlipayNotice(){
        try{
            String shortcode = "101";
            String orderno = "123411414034833333";
            String fee = "1";
            String channel = "APEX-alipay-mobile-1-1";
            String key = "544260cd58b0e4b0043b0a710eaadefa";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("orderno",orderno);
            params.put("fee",fee);
            params.put("channel", channel);
            GetMethod getMethod = new GetMethod("http://gateway.tonggewang.com/0/api/mobilerecharge/alipay/orderno?code="+shortcode+"&orderno="+orderno+"&fee="+fee+"&channel="+channel+"&sign="+ SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            System.out.println(getMethod.getQueryString());
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error [RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGETAcccResource(){
        try{
            String shortcode = "101";
            String operator = "cmcc";
            String fee = "2";
            String version = "1.001.001";
            String key = "544260cd58b0e4b0043b0a710eaadefa";
            Map<String,String> params = new HashMap<String, String>();
            params.put("code",shortcode);
            params.put("operator",operator);
            params.put("fee",fee);
            params.put("version",version);
            GetMethod getMethod = new GetMethod("http://pay.tonggewang.com/0/api/rechargeapp/account/short?code="+shortcode+"&operator="+operator+"&fee="+fee+"&version="+version+"&sign="+SignUtil.signsdk(params,key)+"&mobile=13910671067&did=12345678&osversion=android4.5");
            //GetMethod getMethod = new GetMethod("http://localhost:8080/0/api/rechargeapp/account/short?code="+shortcode+"&operator="+operator+"&fee="+fee+"&version="+version+"&sign="+SignUtil.signsdk(params,key)+"&mobile=13910671067&did=12345678&osversion=android4.5");
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            System.out.println(getMethod.getQueryString());
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHuogeCUPage(){
        try{
            String id = "123411414638421003";
            String mobile = "18601070512";
            String fee = "0.1";
            //String url = RechargeConstants.CU_PAGE_REQUEST_URL+"?MchId="+RechargeConstants.CU_PAGE_APPID+"&MchNo="+id+"&Mobile="+mobile+"&Fee="+fee+"&Sign="+ SignUtil.getChannelSign(RechargeConstants.CU_PAGE_APPID, id, mobile, fee, RechargeConstants.CU_PAGE_APPKEY);
            String url = RechargeConstants.CU_PAGE_REQUEST_URL+"?MchId="+RechargeConstants.CU_PAGE_APPID+"&MchNo="+id+"&Mobile="+mobile+"&Fee="+fee+"&Sign="+ SignUtil.getChannelSign(id, fee, mobile, RechargeConstants.CU_PAGE_APPKEY);
            GetMethod getMethod = new GetMethod(url);
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            System.out.println(getMethod.getQueryString());
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestSomeString(){
        String channel="APEX-cu-dynamic-1-1";
        String t= channel.substring(0, channel.lastIndexOf("-"));
        System.out.println("---->"+t);
    }

    @Test
    public void TestActiveDevice(){
        try{
            String code="101";
            String did = "123411414638421003";
            String mobile = "18601070512";
            String dtype = "android1";
            String brand="huawei";
            String os = "ad";
            String version="1.001.001";
            String key = "544260cd58b0e4b0043b0a710eaadefa";

            Map<String,String> params = new HashMap<String, String>();
            params.put("code",code);
            params.put("did",did);
            params.put("dType",dtype);
            params.put("brand",brand);
            params.put("os",os);
            params.put("version",version);
            //String url = RechargeConstants.CU_PAGE_REQUEST_URL+"?MchId="+RechargeConstants.CU_PAGE_APPID+"&MchNo="+id+"&Mobile="+mobile+"&Fee="+fee+"&Sign="+ SignUtil.getChannelSign(RechargeConstants.CU_PAGE_APPID, id, mobile, fee, RechargeConstants.CU_PAGE_APPKEY);
            GetMethod getMethod = new GetMethod("http://pay.tonggewang.com/0/api/device/active?code="+code+"&did="+did+"&mobile="+mobile+"&dType="+dtype+"&brand="+brand+"&os="+os+"&version="+version+"&sign="+SignUtil.signsdk(params,key));
            getMethod.addRequestHeader("Connection", "Keep-Alive");
            getMethod.addRequestHeader("Accept-Charset", "UTF-8");
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            System.out.println(getMethod.getQueryString());
            if (status == 200) {
                System.out.println(getMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+" :"+getMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
