package com.future.gameplatform.trade.monitor;

import com.future.gameplatform.trade.resource.RechargeResource;

public class RechargeServiceJMX implements RechargeServiceJMXMBean {

    @Override
    public long getRequestCount() {
        return RechargeResource.getTotalRequestCount(false);
    }

    @Override
    public long getRequestSuccessCount() {
        return RechargeResource.getTotalRequestSuccessCount(false);
    }

    @Override
    public long resetRequestCount() {
        return RechargeResource.getTotalRequestCount(true);
    }

    @Override
    public long resetRequestSuccessCount() {
        return RechargeResource.getTotalRequestSuccessCount(true);
    }
}
