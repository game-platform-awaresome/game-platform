package com.future.gameplatform.trade.resource;

import com.future.gameplatform.trade.service.RechargeService;
import com.future.gameplatform.trade.util.CpTransSyncSignValid;
import com.future.gameplatform.trade.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class CperSyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(CperSyncServlet.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    private static AtomicLong totalRequestCount = new AtomicLong(0);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0);

    private String pkey = "123456";

    private static Map<String,Object> appMap = new HashMap<String, Object>();

    private RechargeService rechargeService;

    private RPCHelper rpcHelper;

    private Timer timer;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //pkey = config.getServletContext().getInitParameter("pkey");
        rechargeService=(RechargeService) WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()).getBean("rechargeService");
        rpcHelper=(RPCHelper) WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()).getBean("rpcHelper");
        String appListUrl = config.getInitParameter("getapplisturl");

        timer = new Timer(true);
        logger.debug("context:"+config.getServletContext());
        timer.scheduleAtFixedRate(new JobTimer(rpcHelper, appListUrl), 2*60*1000L, 60*60*1000L);
        logger.debug("timerListener initialized, scheduled delay 2m period 1h.");

        logger.debug("init param url:[{}]",appListUrl);
        String result = rpcHelper.getRechargeApps(appListUrl);

        logger.debug("init get apps result:[{}]",result);
        if(result != null){
            JSONArray jsonArray = JSONArray.fromObject(result);
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String appid = jsonObject.getString("appid");
                logger.debug("get one app:[{}],info:[{}]",appid,jsonObject);
                appMap.put(appid, jsonObject.getString("appkey"));
            }
        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        totalRequestCount.getAndIncrement();
        String transdata = request.getParameter("transdata");
        String sign = request.getParameter("sign");
        logger.debug("CperSync with transdata:[{}] sign:[{}]", transdata, sign);
        if (transdata == null || "".equalsIgnoreCase(transdata)) {
            sendMessage(FAILURE, response);
            logger.error("CperSync with null transdata");
            return;
        }
        if (sign == null || "".equalsIgnoreCase(sign)) {
            sendMessage(FAILURE, response);
            logger.error("CperSync with null sign");
            return;
        }

        JSONObject jsonObject = JSONObject.fromObject(transdata);
        String exorderno = (String)jsonObject.get("exorderno");
        String transid = (String)jsonObject.get("transid");

        String appid = (String)jsonObject.get("appid");
        Integer waresid = (Integer)jsonObject.get("waresid");
        Integer feetype = (Integer)jsonObject.get("feetype");
        Integer money = (Integer)jsonObject.get("money");    //cent
        Integer count = (Integer)jsonObject.get("count");
        Integer result = (Integer)jsonObject.get("result");
        Integer transtype = (Integer)jsonObject.get("transtype");
        String transtime = (String)jsonObject.get("transtime");
        String cpprivate = (String)jsonObject.get("cpprivate");
        logger.debug("parsed transdata,appMap:[{}]",appMap);

        pkey = (String)appMap.get(appid);
        logger.debug("get valid key:[{}]",pkey);
        boolean isvlid = validMessage(transdata, sign);
        if (!isvlid) {
            logger.error("CperSync with invalid sign");
            sendMessage(FAILURE, response);
            return;
        }
        logger.debug("do notice begin");
        rechargeService.doPayNotice(exorderno, transid, appid, waresid, feetype, money, count, result, transtype, transtime, cpprivate);
        logger.debug("do notice end");
        logger.debug("CperSync success");
        totalRequestSuccessCount.getAndIncrement();
        sendMessage(SUCCESS, response);
        return;

    }

    private boolean validMessage(String transdata, String sign) throws UnsupportedEncodingException {
        return CpTransSyncSignValid.validSign(transdata, sign, pkey);
    }

    private void sendMessage(String message, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(message);
        out.flush();
        out.close();
    }

    public synchronized static long getTotalRequestCount(boolean reset) {
        if(reset){
            return totalRequestCount.getAndSet(0L);
        }
        return totalRequestCount.get();
    }

    public synchronized static long getTotalRequestSuccessCount(boolean reset) {
        if(reset){
            return totalRequestSuccessCount.getAndSet(0);
        }
        return totalRequestSuccessCount.get();
    }

    private class JobTimer extends TimerTask {

        private RPCHelper rpcHelper;

        private String appListUrl;


        public JobTimer(RPCHelper rpcHelper, String appListUrl ) {
            this.appListUrl = appListUrl;
            this.rpcHelper = rpcHelper;
        }

        @Override
        public void run() {
            logger.debug("init param url:[{}]",appListUrl);
            String result = rpcHelper.getRechargeApps(appListUrl);

            logger.debug("init get apps result:[{}]",result);
            if(result != null){
                JSONArray jsonArray = JSONArray.fromObject(result);
                for(int i=0; i<jsonArray.size(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String appid = jsonObject.getString("appid");
                    logger.debug("get one app:[{}],info:[{}]",appid,jsonObject);
                    appMap.put(appid, jsonObject.getString("appkey"));
                }
            }
        }
    }
}
