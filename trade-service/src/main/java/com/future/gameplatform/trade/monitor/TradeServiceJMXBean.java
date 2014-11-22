package com.future.gameplatform.trade.monitor;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface TradeServiceJMXBean {
    long getRequestCount();
    long getRequestSuccessCount();


    long resetRequestCount();
    long resetRequestSuccessCount();
}
