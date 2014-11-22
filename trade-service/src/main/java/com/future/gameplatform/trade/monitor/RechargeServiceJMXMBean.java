package com.future.gameplatform.trade.monitor;

public interface RechargeServiceJMXMBean {
    // transaction
	long getRequestCount();
	long getRequestSuccessCount();

	
	long resetRequestCount();
	long resetRequestSuccessCount();

}
