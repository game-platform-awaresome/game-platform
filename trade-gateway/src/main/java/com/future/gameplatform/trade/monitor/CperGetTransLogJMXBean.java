package com.future.gameplatform.trade.monitor;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface CperGetTransLogJMXBean {
    long getRequestCount();
    long getRequestSuccessCount();

    long resetRequestCount();
    long resetRequestSuccessCount();
}
