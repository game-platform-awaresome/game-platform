package com.future.gameplatform.trade.monitor;

import com.future.gameplatform.trade.resource.CperGetTranslogServlet;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class CperGetTransLogJMX implements CperGetTransLogJMXBean {
    @Override
    public long getRequestCount() {
        return CperGetTranslogServlet.getTotalRequestCount(false);
    }

    @Override
    public long getRequestSuccessCount() {
        return CperGetTranslogServlet.getTotalRequestSuccessCount(false);
    }

    @Override
    public long resetRequestCount() {
        return CperGetTranslogServlet.getTotalRequestCount(true);
    }

    @Override
    public long resetRequestSuccessCount() {
        return CperGetTranslogServlet.getTotalRequestSuccessCount(true);
    }
}
