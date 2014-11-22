package com.future.gameplatform.trade.monitor;

public interface CperSyncJMXMBean {

	long getRequestCount();
	long getRequestSuccessCount();
	
	long resetRequestCount();
	long resetRequestSuccessCount();
}
