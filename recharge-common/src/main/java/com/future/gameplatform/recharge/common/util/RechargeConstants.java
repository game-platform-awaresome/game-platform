package com.future.gameplatform.recharge.common.util;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public class RechargeConstants {

    public static final String CT_PAGE_REQUEST_URL="http://fhvcdx.apexstone-t.cn/adx/GetVCode.aspx";
    public static final String CT_PAGE_POST_URL="http://fhvcdx.apexstone-t.cn/adx/SubmitVCode.aspx";
    public static final String CT_PAGE_APPID = "SDDA";
    public static final String CT_PAGE_APPKEY = "3E04000BBC414EEE";

    public static final String CU_PAGE_REQUEST_URL="http://com1.apexstone-t.cn/api/GetVCode.aspx";
    public static final String CU_PAGE_POST_URL="http://com1.apexstone-t.cn/api/SubmitVCode.aspx";
    public static final String CU_PAGE_APPID = "SDLA";
    public static final String CU_PAGE_APPKEY = "3E04000BBC414EEE";

    public static final String CU_DYNAMIC_URL="http://com1.apexstone-t.cn/EMCWS/HP/ZF_GetSMSInfo.ashx";
    public static final String CU_DYNAMIC_APPID="WSDKK";
    public static final String CU_DYNAMIC_APPKEY="3E04000BBC414EEE";

    public static final String CU_PLAIN_URL="http://com1.apexstone-t.cn/EMCWS/HP/ZF_GetSMSInfo.ashx";
    public static final String CU_PLAIN_APPID="RSDKK";
    public static final String CU_PLAIN_APPKEY="3E04000BBC414EEE";

    public static final String CU_PLAIN_GAME_URL="http://com1.apexstone-t.cn/EMCWS/HP/ZF_GetSMSInfo.ashx";
    public static final String CU_PLAIN_GAME_APPID="TSDKK";
    public static final String CU_PLAIN_GAME_APPKEY="3E04000BBC414EEE";

    public static final String CMCC_SDK_APPID="SDKK";
    public static final String CMCC_SDK_APPKEY = "3E04000BBC414EEE";

    public static final String CMCC_DYNAMIC_GAME_URL="http://com1.apexstone-t.cn/EMCWS/HP/ZF_GetSMSInfo.ashx";
    public static final String CMCC_DYNAMIC_GAME_APPID="LSDKK";
    public static final String CMCC_DYNAMIC_GAME_APPKEY="3E04000BBC414EEE";

    public static final String CMCC_DYNAMIC_SMS_URL="http://com1.apexstone-t.cn/EMCWS/HP/ZF_GetSMSInfo.ashx";
    public static final String CMCC_DYNAMIC_SMS_APPID="BSDKK";
    public static final String CMCC_DYNAMIC_SMS_APPKEY="3E04000BBC414EEE";

    public static final String CT_SMS_DIRECT_APPID="SDKK";
    public static final String CT_SMS_DIRECT_APPKEY="3E04000BBC414EEE";

    public static final String CU_SMS_DIRECT_APPID="SDKK";
    public static final String CU_SMS_DIRECT_APPKEY="3E04000BBC414EEE";

    public static final String SMS_DIRECT_APPID="SDKK";
    public static final String SMS_DIRECT_APPKEY="3E04000BBC414EEE";

    public static final String GET_RECHARGE_APP_LIST_URL = "http://pay.tonggewang.com/0/api/rechargeapp/account/list";

    public static final List<String> DYNAMIC_APPID_LIST = new ArrayList<String>();
    public static final List<String> DYNAMIC_APPKEY_LIST = new ArrayList<String>();
    public static final List<String> DYNAMIC_CHANNEL_PART_LIST = new ArrayList<String>();

    public static final List<String> PAGE_APPID_LIST = new ArrayList<String>();
    public static final List<String> PAGE_APPKEY_LIST = new ArrayList<String>();

    static {
        DYNAMIC_APPID_LIST.add(CU_DYNAMIC_APPID);
        DYNAMIC_APPID_LIST.add(CU_PLAIN_APPID);
        DYNAMIC_APPID_LIST.add(CU_PLAIN_GAME_APPID);
        DYNAMIC_APPID_LIST.add(CMCC_DYNAMIC_GAME_APPID);
        DYNAMIC_APPID_LIST.add(CMCC_DYNAMIC_SMS_APPID);

        DYNAMIC_APPKEY_LIST.add(CU_DYNAMIC_APPKEY);
        DYNAMIC_APPKEY_LIST.add(CU_PLAIN_APPKEY);
        DYNAMIC_APPKEY_LIST.add(CU_PLAIN_GAME_APPKEY);
        DYNAMIC_APPKEY_LIST.add(CMCC_DYNAMIC_GAME_APPKEY);
        DYNAMIC_APPKEY_LIST.add(CMCC_DYNAMIC_SMS_APPKEY);

        DYNAMIC_CHANNEL_PART_LIST.add("APEX-cu-dynamic-1");
        DYNAMIC_CHANNEL_PART_LIST.add("APEX-cu-plain-1");
        DYNAMIC_CHANNEL_PART_LIST.add("APEX-cu-plain-2");
        DYNAMIC_CHANNEL_PART_LIST.add("APEX-cmcc-dynamic-1");
        DYNAMIC_CHANNEL_PART_LIST.add("APEX-cmcc-dynamic-2");

        PAGE_APPID_LIST.add(CU_PAGE_APPID);
        PAGE_APPID_LIST.add(CT_PAGE_APPID);

        PAGE_APPKEY_LIST.add(CU_PAGE_APPKEY);
        PAGE_APPKEY_LIST.add(CT_PAGE_APPKEY);
    }

    public static String getAppidByChannel(String channel) {
        int index = DYNAMIC_CHANNEL_PART_LIST.indexOf(channel.substring(0, channel.lastIndexOf("-")));
        return DYNAMIC_APPID_LIST.get(index);
    }

    public static String getAppkeyByChannel(String channel) {
        int index = DYNAMIC_CHANNEL_PART_LIST.indexOf(channel.substring(0, channel.lastIndexOf("-")));
        return DYNAMIC_APPKEY_LIST.get(index);
    }

    public static String getAppKeyByAppIdForPage(String appId) {
        int index = PAGE_APPID_LIST.indexOf(appId);
        return PAGE_APPKEY_LIST.get(index);
    }
}
