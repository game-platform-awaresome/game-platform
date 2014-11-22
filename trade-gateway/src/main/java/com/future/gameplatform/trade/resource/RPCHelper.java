package com.future.gameplatform.trade.resource;

import com.future.gameplatform.common.service.AbstractHttpRPCService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */

public class RPCHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory.getLogger(RPCHelper.class);

    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected RPCHelper(int maxConnection) {
        super(maxConnection);
    }

    private interface Callback {
        String doIt() ;
    }

    private String execute(Callback callback)  {
        return callback.doIt();
    }


    public String getRechargeApps(final String searchUrl)  {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-getRechargeApps] searchUrl = {}",
                new Object[] { stamp, searchUrl});
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());

        String ret = execute(new Callback() {
            @Override
            public String doIt()  {
                HttpRPCResult result = invokeGet(searchUrl, HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-getRechargeApps] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        return ret;
    }


}
