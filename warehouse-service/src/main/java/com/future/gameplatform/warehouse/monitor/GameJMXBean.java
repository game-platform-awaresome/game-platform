package com.future.gameplatform.warehouse.monitor;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public interface GameJMXBean {
    long getRequestCount();
    long getRequestSuccessCount();

    long resetRequestCount();
    long resetRequestSuccessCount();
}
