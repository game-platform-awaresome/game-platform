package com.future.gameplatform.trade.monitor;

import com.future.gameplatform.trade.resource.CperSyncServlet;

public class CperSyncJMX implements CperSyncJMXMBean {

    @Override
    public long getRequestCount() {
        return CperSyncServlet.getTotalRequestCount(false);
    }

    @Override
    public long getRequestSuccessCount() {
        return CperSyncServlet.getTotalRequestSuccessCount(false);
    }

    @Override
    public long resetRequestCount() {
        return CperSyncServlet.getTotalRequestCount(true);
    }

    @Override
    public long resetRequestSuccessCount() {
        return CperSyncServlet.getTotalRequestSuccessCount(true);
    }
}
