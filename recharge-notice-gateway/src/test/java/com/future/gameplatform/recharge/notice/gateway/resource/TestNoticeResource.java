package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-23
 * Time: 下午11:45
 * To change this template use File | Settings | File Templates.
 */
public class TestNoticeResource {

    @Test
    public void TestDynamicAllResource(){
        try {
            PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/all/dynamic");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            postMethod.addParameter(new NameValuePair("MchId", "WSDKK"));
            postMethod.addParameter(new NameValuePair("MchNo", "53f913fd58b0e4b025e3df2f7ccdc96b"));
            postMethod.addParameter(new NameValuePair("Fee", "1"));
            postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("53f913fd58b0e4b025e3df2f7ccdc96b","WSDKK", "1", "3E04000BBC414EEE")));


            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+postMethod.getResponseBodyAsString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestPageAllResource(){
        try {
            //PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/all/page");
            //postMethod.addRequestHeader("Connection", "Keep-Alive");
            //postMethod.addRequestHeader("Accept-Charset", "UTF-8");
            //http://gateway.tonggewang.com/0/api/rechargenotice/all/page?MchId=SDLA&MchNo=545fc92258b0e4b04866c123a80189ce&Mobile=18601070512&Fee=0.1&Sign=3167ed02f6d89c7c5ec3809091604a61
            //postMethod.addParameter(new NameValuePair("MchId", "SDLA"));
            //postMethod.addParameter(new NameValuePair("MchNo", "545fc92258b0e4b04866c123a80189ce"));
            //postMethod.addParameter(new NameValuePair("Fee", "0.1"));
            //postMethod.addParameter(new NameValuePair("Mobile", "18601070512"));
            //postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("545fc92258b0e4b04866c123a80189ce","0.1","18601070512", "3E04000BBC414EEE")));
            PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/all/page?MchId=SDLA&MchNo=545fdebe58b0e4b04866c123a80189cf&Mobile=18601070512&Fee=0.1&Sign="+SignUtil.getChannelSign("545fdebe58b0e4b04866c123a80189cf","0.1","18601070512", "3E04000BBC414EEE"));

            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status+postMethod.getResponseBodyAsString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
         public void TestCTResource(){
        try {
            PostMethod postMethod = new PostMethod("http://117.79.231.23:80/0/api/rechargenotice/ct/smsdirect");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            postMethod.addParameter(new NameValuePair("Mobile", "13910771077"));
            postMethod.addParameter(new NameValuePair("MchNo", "53f913fd58b0e4b025e3df2f7ccdc96b"));
            postMethod.addParameter(new NameValuePair("Fee", "1"));
            postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("53f913fd58b0e4b025e3df2f7ccdc96b", "13910771077","1","emSD101jjjjsdfsfsdf", "3E04000BBC414EEE")));
            postMethod.addParameter(new NameValuePair("MoSmsMsg", "emSD101jjjjsdfsfsdf"));


            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestSmsDResource(){
        try {
            //PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/all/smsdirect");
            //PostMethod postMethod = new PostMethod("http://localhost:8080/0/api/rechargenotice/all/smsdirect");
            //postMethod.addRequestHeader("Connection", "Keep-Alive");
            //postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            //postMethod.addParameter(new NameValuePair("Mobile", "13910771077"));
            //postMethod.addParameter(new NameValuePair("MchNo", "53f913fd58b0e4b025e3df2f7ccdc96b"));
            //postMethod.addParameter(new NameValuePair("Fee", "1"));
            //postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("53f913fd58b0e4b025e3df2f7ccdc96b", "13910771077","1","fmSD__101__123411415182331546", "3E04000BBC414EEE")));
            //postMethod.addParameter(new NameValuePair("MoSmsMsg", "fmSD__101__123411415182331546"));
            PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/all/smsdirect?Mobile=18601070512&MchNo=546091a158b0e4b04866c123a80189d4&Fee=5&MoSmsMsg=fmSD__101__123411415587469133&Sign="+SignUtil.getChannelSign("546091a158b0e4b04866c123a80189d4","18601070512","5","fmSD__101__123411415587469133","3E04000BBC414EEE"));

            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestStringSplit(){
        String s = "fmSD_101_123411415180301512_123";
        String[]  ss = s.split("_");
        System.out.println("ss size:"+ss.length+" ss0-->"+ss[0]+" ss1-->"+ss[1]+" ss2-->"+ss[2]);
    }

    @Test
    public void TestCUDirectResource(){
        try {
            PostMethod postMethod = new PostMethod("http://117.79.231.23:80/0/api/rechargenotice/cu/smsdirect");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            postMethod.addParameter(new NameValuePair("Mobile", "13910771077"));
            postMethod.addParameter(new NameValuePair("MchNo", "53f913fd58b0e4b025e3df2f7ccdc96b"));
            postMethod.addParameter(new NameValuePair("Fee", "1"));
            postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("53f913fd58b0e4b025e3df2f7ccdc96b", "13910771077","1","wo#awa&SD101123411415180301512", "3E04000BBC414EEE")));
            postMethod.addParameter(new NameValuePair("MoSmsMsg", "wo#awa&SD101123411415180301512"));


            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestCMCCResource(){
        try {
            PostMethod postMethod = new PostMethod("http://gateway.tonggewang.com/0/api/rechargenotice/cmcc/sdk");
            postMethod.addRequestHeader("Connection", "Keep-Alive");
            postMethod.addRequestHeader("Accept-Charset", "UTF-8");

            postMethod.addParameter(new NameValuePair("Mobile", "13910771077"));
            postMethod.addParameter(new NameValuePair("MchNo", "53f913fd58b0e4b025e3df2f7ccdc96b"));
            postMethod.addParameter(new NameValuePair("Fee", "1"));
            postMethod.addParameter(new NameValuePair("sign", SignUtil.getChannelSign("53f913fd58b0e4b025e3df2f7ccdc96b","1", "13910771077", "3E04000BBC414EEE")));
            postMethod.addParameter(new NameValuePair("merPriv", "somthing"));


            HttpClient client = new HttpClient();
            int status = client.executeMethod(postMethod);
            if (status == 200) {
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void TestSysProp(){
        String s = System.getProperty("java.io.tmpdir");
        System.out.println(s);
    }

}
