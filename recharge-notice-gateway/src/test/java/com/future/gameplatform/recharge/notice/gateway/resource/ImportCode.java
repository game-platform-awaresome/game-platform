package com.future.gameplatform.recharge.notice.gateway.resource;

import com.future.gameplatform.recharge.common.util.SignUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-27
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class ImportCode {

    private Set<String> hasImported = new HashSet<String>(2500);

    private Set<String> needImport = new HashSet<String>(5000);

    public static void main(String[] args){
        ImportCode importCode = new ImportCode();
        importCode.needImport = importCode.getAllCodeFromFile("D:\\php work\\PHPnow-1.5.6\\htdocs\\Book1.csv");
        //importCode.hasImported = importCode.getAllCodeFromFile("D:\\php work\\PHPnow-1.5.6\\htdocs\\stock.csv");
        //importCode.needImport.removeAll(importCode.hasImported);
        System.out.println(importCode.needImport.size());
        StringBuffer stringBuffer = new StringBuffer();
        for(String code:importCode.needImport){
            stringBuffer.append(code).append(" ");
        }
        importCode.postCode(stringBuffer.toString().trim(),"20140310");
    }

    private void postCode(String code, String dt){
        try {
            PostMethod postMethod = new PostMethod("http://stockhotspot.sinaapp.com/postHotstock.php");

            postMethod.addParameter(new NameValuePair("code", code));
            postMethod.addParameter(new NameValuePair("rq", dt));

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

    private Set<String> getAllCodeFromFile(String filename){
        Set<String> result = new HashSet<String>(4000);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = bufferedReader.readLine()) != null){
                str = "00000"+str.substring(0, str.indexOf(","));
                str = str.substring(str.length()-6,str.length());
                result.add(str);
            }
            bufferedReader.close();
            System.out.println("result count-->"+result.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Map<String,String> getALLCode(){
        Map<String, String> result = new HashMap<String, String>(5000);
        try{
            String url = "http://quotes.money.163.com/hs/service/marketradar_ajax.php?host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fmarketradar_ajax.php&page=0&query=STYPE%3AEQA&types=&count=5000&type=query&order=deschttp://quotes.money.163.com/hs/service/marketradar_ajax.php?host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fmarketradar_ajax.php&page=0&query=STYPE%3AEQA&types=&count=5000&type=query&order=desc";
            GetMethod getMethod = new GetMethod(url);
            HttpClient client = new HttpClient();
            int status = 0;
            status = client.executeMethod(getMethod);
            if (status == 200) {
                JSONObject jsonObject = JSONObject.fromObject(getMethod.getResponseBodyAsString());
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                System.out.println("list count-->"+jsonArray.size());
                for(int i=0,j=jsonArray.size(); i<j; i++){
                    JSONObject jEntry = jsonArray.getJSONObject(i);

                    result.put(jEntry.getString("SYMBOL"),jEntry.getString("NAME"));
                }
                System.out.println("result count-->"+result.size());
            } else {
                System.out.println("error[RPC-doPostDemandOrder] error, http status:[{}]"+status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
