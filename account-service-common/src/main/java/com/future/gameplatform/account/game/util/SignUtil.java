package com.future.gameplatform.account.game.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-8-17
 * Time: 下午1:04
 * To change this template use File | Settings | File Templates.
 */
public class SignUtil {

    /**
     * for sdk
     * @param sign
     * @param params
     * @param secret
     * @return
     */
    public static boolean checkSdkSign(String sign, Map<String, String> params, String secret){
        return sign.equalsIgnoreCase(signsdk(params,secret));
    }

    /**
     * from channel
     * @param id
     * @param sign
     * @param others
     * @return
     */
    public static boolean checkChannelSign(String sign, String id, String...others){
        return sign.equalsIgnoreCase(getChannelSign(id, others));
    }

    /**
     * to channel
     * @param id
     * @param others
     * @return
     */
    public static String getChannelSign(String id, String...others) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(id);
            for(int i=0; i<others.length;i++){
                sb.append(others[i]);
            }
            byte[] md5Digest = getMD5Digest(sb.toString());
            return parseByte2HexStr(md5Digest).toLowerCase();
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * to CP
     * @param secret
     * @param paramValues
     * @return
     */
    public static String getCPSign(String secret, Map<String,String> paramValues) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());

            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] md5Digest = getMD5Digest(sb.toString());
            return parseByte2HexStr(md5Digest);
        } catch (IOException e) {
            return null;
        }
    }

    public static String signsdk(Map<String, String> paramValues, String secret) {
        return signsdk(paramValues, null, secret);
    }

    public static String signsdk(Map<String, String> paramValues,
                              List<String> ignoreParamNames, String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return parseByte2HexStr(sha1Digest);
        } catch (IOException e) {
            return null;
        }
    }

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("utf-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("utf-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    // 字节数组转十六进制字符串
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    public static void main(String[] args){

    }
}
