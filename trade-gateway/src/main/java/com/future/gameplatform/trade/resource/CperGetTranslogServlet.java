package com.future.gameplatform.trade.resource;

import com.future.gameplatform.trade.util.CpTransSyncSignValid;
import com.future.gameplatform.trade.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CperGetTranslogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(CperGetTranslogServlet.class);

    private static AtomicLong totalRequestCount = new AtomicLong(0);
    private static AtomicLong totalRequestSuccessCount = new AtomicLong(0);

	private String pkey = "";
	private String appid = "";
	private String exorderno = "";
	private String url = "";
    private String getapplisturl = "";

    private RPCHelper rpcHelper;

    private Map<String,Object> appMap = new HashMap<String, Object>();

	@Override
	public void init(ServletConfig config) throws ServletException {
        rpcHelper = (RPCHelper) WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()).getBean("rpcHelper");
		pkey = config.getServletContext().getInitParameter("pkey");
		appid = config.getInitParameter("appid");
		exorderno = config.getInitParameter("exorderno");
		url = config.getInitParameter("url");
        getapplisturl = config.getInitParameter("getapplisturl");
        logger.debug("init param url:[{}]",getapplisturl);
        String result = rpcHelper.getRechargeApps(getapplisturl);
        if(result != null){
            JSONArray jsonArray = JSONArray.fromObject(result);
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String appid = jsonObject.getString("appid");
                appMap.put(appid, jsonObject);
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
		String tappid = request.getParameter("appid");
		if(tappid==null || "".equals(tappid)){
			tappid = appid;
		}
		String texorderno = request.getParameter("exorderno");
		if(texorderno==null || "".equals(texorderno)){
			texorderno = exorderno;
		}

		String json = "{\"appid\":\"" + tappid + "\",\"exorderno\":\""
				+ texorderno + "\"}";
        logger.debug("CperGet request with info:[{}]",json);

        pkey = ((JSONObject)appMap.get(tappid)).getString("key");

		String sign = CpTransSyncSignValid.genSign(json, pkey);
		if (sign == null || "".equals(sign)) {
            logger.error("CperGet request with null sign");
			return;
		}

		String message = "transdata=" + json + "&sign=" + sign;

		String result = HttpUtils.sentPost(url, message);
        logger.debug("CperGet get response:[{}]",result);

        totalRequestSuccessCount.getAndIncrement();
	}

    public synchronized static long getTotalRequestCount(boolean reset) {
        if(reset){
            return totalRequestCount.getAndSet(0L);
        }
        return totalRequestCount.get();
    }

    public synchronized static long getTotalRequestSuccessCount(boolean reset) {
        if(reset){
            return totalRequestSuccessCount.getAndSet(0L);
        }
        return totalRequestSuccessCount.get();
    }
}
