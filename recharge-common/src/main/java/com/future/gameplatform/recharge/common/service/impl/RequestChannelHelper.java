package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.common.service.AbstractHttpRPCService;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.util.RechargeConstants;
import com.future.gameplatform.recharge.common.util.ServiceResult;
import com.future.gameplatform.recharge.common.util.SignUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class RequestChannelHelper extends AbstractHttpRPCService {

    private static final Logger logger = LoggerFactory
            .getLogger(RequestChannelHelper.class);


    /**
     * Constructor
     *
     * @param maxConnection allow max quantity of connection
     */
    protected RequestChannelHelper(int maxConnection) {
        super(maxConnection);
    }

    public ServiceResult<String> cpPageNotice(String id, String mobile, String fee) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        final String url = RechargeConstants.CT_PAGE_REQUEST_URL+"?MchId="+RechargeConstants.CT_PAGE_APPID+"&MchNo="+id+"&Mobile="+mobile+"&Fee="+fee+"&Sign="+ SignUtil.getChannelSign(id, fee, mobile, RechargeConstants.CT_PAGE_APPKEY);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            serviceResult.setSuccess(true);
            serviceResult.setValue(ret);
        }else {
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> cuDynamicNotic(String id, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CU_DYNAMIC_URL+"?MchNo="+id+"&MchId="+RechargeConstants.CU_DYNAMIC_APPID+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.CU_DYNAMIC_APPID,fee,RechargeConstants.CU_DYNAMIC_APPKEY);
        logger.debug("request code for cu dynamic, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeDynamicChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request cu dynamic success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request cu dynamic failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<String> cpPageResult(String id, String smscode) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        final String url = RechargeConstants.CT_PAGE_POST_URL+"?MchId="+RechargeConstants.CT_PAGE_APPID+"&MchNo="+id+"&CodeSms="+smscode+"&Sign="+ SignUtil.getChannelSign(RechargeConstants.CT_PAGE_APPID, id, smscode, RechargeConstants.CT_PAGE_APPKEY);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doPostChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            serviceResult.setSuccess(true);
            serviceResult.setValue(ret);
        }else {
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<String> cuPageNotice(String id, String mobile, String fee) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        final String url = RechargeConstants.CU_PAGE_REQUEST_URL+"?MchId="+RechargeConstants.CU_PAGE_APPID+"&MchNo="+id+"&Mobile="+mobile+"&Fee="+fee+"&Sign="+ SignUtil.getChannelSign(id, fee, mobile, RechargeConstants.CU_PAGE_APPKEY);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            serviceResult.setSuccess(true);
            serviceResult.setValue(ret);
        }else {
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<String> cuPageResult(String id, String smscode) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        final String url = RechargeConstants.CU_PAGE_POST_URL+"?MchId="+RechargeConstants.CU_PAGE_APPID+"&MchNo="+id+"&CodeSms="+smscode+"&Sign="+ SignUtil.getChannelSign(RechargeConstants.CU_PAGE_APPID, id, smscode, RechargeConstants.CU_PAGE_APPKEY);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doPostChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            serviceResult.setSuccess(true);
            serviceResult.setValue(ret);
        }else {
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> cuPlainSmsNotice(String id, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CU_PLAIN_URL+"?MchNo="+id+"&MchId="+RechargeConstants.CU_PLAIN_APPID+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.CU_PLAIN_APPID,fee,RechargeConstants.CU_PLAIN_APPKEY);
        logger.debug("request code for cu plain sms, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticePlainSmsChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request cu plain sms success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request cu plain sms failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> cuPlainSmsGameNotice(String id, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CU_PLAIN_GAME_URL+"?MchNo="+id+"&MchId="+RechargeConstants.CU_PLAIN_GAME_APPID+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.CU_PLAIN_GAME_APPID,fee,RechargeConstants.CU_PLAIN_GAME_APPKEY);
        logger.debug("request code for cu plain sms, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticePlainSmsChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request cu plain sms success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request cu plain sms failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> cmccDynamicGameNotice(String id, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CMCC_DYNAMIC_GAME_URL+"?MchNo="+id+"&MchId="+RechargeConstants.CMCC_DYNAMIC_GAME_APPID+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.CMCC_DYNAMIC_GAME_APPID,fee,RechargeConstants.CMCC_DYNAMIC_GAME_APPKEY);
        logger.debug("request code for cmcc dynamic game, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeDynamicChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request cmcc dynamic game success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request cmcc dynamic game failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> cmccDynamicSmsNotice(String id, String fee) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CMCC_DYNAMIC_SMS_URL+"?MchNo="+id+"&MchId="+RechargeConstants.CMCC_DYNAMIC_SMS_APPID+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.CMCC_DYNAMIC_SMS_APPID,fee,RechargeConstants.CMCC_DYNAMIC_SMS_APPKEY);
        logger.debug("request code for cmcc dynamic sms, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeDynamicChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request cmcc dynamic sms success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request cmcc dynamic cmcc failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    public ServiceResult<SmsRecharge> dynamicAllinoneNotice(String id, String fee, String channel) {
        ServiceResult<SmsRecharge> serviceResult = new ServiceResult<SmsRecharge>();
        final String url = RechargeConstants.CMCC_DYNAMIC_SMS_URL+"?MchNo="+id+"&MchId="+RechargeConstants.getAppidByChannel(channel)+"&Fee="+fee+"&Sign="+SignUtil.getChannelSign(id,RechargeConstants.getAppidByChannel(channel),fee,RechargeConstants.getAppkeyByChannel(channel));
        logger.debug("request code for dynamic sms, url:[{}]", url);
        String ret = execute(new Callback() {
            @Override
            public String doIt() {
                HttpRPCResult result = invokeGet(
                        url,
                        HttpStatus.SC_OK);

                if (result.getStatusCode() == HttpStatus.SC_OK) {
                    return new String(result.getPayload());
                }
                logger.error(
                        "[RPC-doNoticeDynamicChannel] failed! statusCode: {}; message: {}",
                        result.getStatusCode(), result.getMessage());
                return null;
            }
        });
        if(ret.startsWith("000")){
            logger.debug("request dynamic sms success");
            SmsRecharge smsRecharge = new SmsRecharge();
            String[] retvalue = ret.split("~");
            smsRecharge.setSmscode(retvalue[2]);
            smsRecharge.setSmsChannel(retvalue[3]);
            serviceResult.setSuccess(true);
            serviceResult.setValue(smsRecharge);
        }else {
            logger.debug("request dynamic cmcc failed");
            serviceResult.setSuccess(false);
            serviceResult.setErrorCode(Integer.parseInt(ret.substring(0,3)));
            serviceResult.setErrorMessage(ret.substring(4));
        }
        return serviceResult;
    }

    private interface Callback {
        String doIt();
    }

    private String execute(Callback callback) {
        return callback.doIt();
    }


}
