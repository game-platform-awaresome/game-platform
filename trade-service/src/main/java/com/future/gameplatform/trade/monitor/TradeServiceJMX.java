package com.future.gameplatform.trade.monitor;

import com.future.gameplatform.trade.resource.TradeResource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TradeServiceJMX implements TradeServiceJMXBean {
    @Override
    public long getRequestCount() {
        return TradeResource.getTotalRequestCount(false);
    }

    @Override
    public long getRequestSuccessCount() {
        return TradeResource.getTotalRequestSuccessCount(false);
    }

    @Override
    public long resetRequestCount() {
        return TradeResource.getTotalRequestCount(true);
    }

    @Override
    public long resetRequestSuccessCount() {
        return TradeResource.getTotalRequestSuccessCount(true);
    }
}
