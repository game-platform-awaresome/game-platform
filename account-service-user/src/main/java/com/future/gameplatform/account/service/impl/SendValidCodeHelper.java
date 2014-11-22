package com.future.gameplatform.account.service.impl;

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
public class SendValidCodeHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory.getLogger(SendValidCodeHelper.class);

    private String domain;

    private int port;

    private String corpId;

    private String passwd;

    protected SendValidCodeHelper(int maxConnection, String domain, int port, String corpId, String passwd) {
        super(maxConnection);
        this.domain = domain;
        this.port = port;
        this.corpId = corpId;
        this.passwd = passwd;
    }

    public void sendValidCode(final String mobile,final String code) throws UnsupportedEncodingException {
        final long stamp = System.currentTimeMillis();
        logger.debug("[{}] [RPC-sendValidCode] uid = {},code = {}",
                new Object[] { stamp, mobile, code});
        logger.info("[XYZ] connection in pool : {}",super.getConnectionInPool());
        final Map<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type","charset=GBK");
        String ret = execute(new Callback() {
            @Override
            public String doIt() throws UnsupportedEncodingException {
                HttpRPCResult result = invokeGetWithHeaders(
                        composeURI(domain, port,"/Send.aspx?CorpID="+corpId+"&Pwd="+passwd+"&Mobile="+mobile+"&Content="+ URLEncoder.encode("您的验证码："+code+"【2066游戏大厅】","GBK")), headers,
                         HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-sendValidCode] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
    }

    private interface Callback {
        String doIt() throws UnsupportedEncodingException;
    }

    private String execute(Callback callback) throws UnsupportedEncodingException {
        return callback.doIt();
    }


}
