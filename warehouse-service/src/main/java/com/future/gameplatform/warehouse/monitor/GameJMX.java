package com.future.gameplatform.warehouse.monitor;

import com.future.gameplatform.warehouse.resource.GameResource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class GameJMX implements GameJMXBean {
    @Override
    public long getRequestCount() {
        return GameResource.getTotalRequestCount(false);
    }

    @Override
    public long getRequestSuccessCount() {
        return GameResource.getTotalRequestSuccessCount(false);
    }

    @Override
    public long resetRequestCount() {
        return GameResource.getTotalRequestCount(true);
    }

    @Override
    public long resetRequestSuccessCount() {
        return GameResource.getTotalRequestSuccessCount(true);
    }
}
