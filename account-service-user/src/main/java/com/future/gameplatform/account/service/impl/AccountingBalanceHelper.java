package com.future.gameplatform.account.service.impl;

import com.future.gameplatform.common.service.AbstractHttpRPCService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class AccountingBalanceHelper extends AbstractHttpRPCService {

    private final static Logger logger = LoggerFactory.getLogger(AccountingBalanceHelper.class);

    private String domain;

    private int port;

    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected AccountingBalanceHelper(int maxConnection,String domain,int port) {
        super(maxConnection);
        this.domain = domain;
        this.port = port;
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }

    public long doAccountingBalance(String uid, String token, long nowbalance){
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-doAccountingBalance] uid = {}",
                new Object[] { stamp, uid });
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        final Map<String,String> headers = new HashMap<String,String>();
        headers.put("uid",uid);
        headers.put("token",token);
        final byte[] payload = ("{\"balance\":"+nowbalance+"}").getBytes();
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokePostWithHeader(
                        composeURI(domain, port, "/1/api/com.future.gameplatform.account.game/accountingbalance"), headers,
                        APPLICATION_JSON, payload, HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doAccountingBalance] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        logger.debug("[{}] [RPC-doAccountingBalance] Job costed {} ms.", stamp,
                (System.currentTimeMillis() - stamp));
        if(ret == null)
            return 0;
        return Long.parseLong(ret);
    }
}
