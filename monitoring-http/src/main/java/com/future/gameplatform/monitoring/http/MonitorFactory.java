package com.future.gameplatform.monitoring.http;

import com.future.gameplatform.monitoring.http.monitor.AccountMonitor;
import com.future.gameplatform.monitoring.http.monitor.HttpMonitor;
import com.future.gameplatform.monitoring.http.monitor.ThirdAppMonitor;

import java.util.HashMap;

public class MonitorFactory {

	private static HashMap<String, HttpMonitor> monitorMap = new HashMap<String, HttpMonitor>();

	static {
		monitorMap.put("3rdapp", new ThirdAppMonitor());
		monitorMap.put("appaccount", new AccountMonitor("accounts.app"));
		monitorMap.put("devaccount", new AccountMonitor("accounts.dev"));
	}

	public static HttpMonitor getMonitor(String service) {
		return monitorMap.get(service);
	}

}
