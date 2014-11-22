package com.babeeta.butterfly.testkit.server.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TestTrade {
    private String domain = "gateway.2066.cn";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTrade(){
        TradeGatewayClient tradeGatewayClient = new TradeGatewayClient(domain, 8090);
        tradeGatewayClient.testTradeGet();
    }

    @After
    public void tearDown() throws Exception {
    }
}
