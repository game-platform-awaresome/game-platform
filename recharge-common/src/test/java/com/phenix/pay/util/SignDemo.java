package com.phenix.pay.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-9
 * Time: 下午8:43
 * To change this template use File | Settings | File Templates.
 */
public class SignDemo {

    /**
     *  加密算法
     * @param secret CP的key
     * @param paramValues 参与加密的参数map，不包括key
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

    public static void main(String[] args){
        String secret = "asdfsafsfsfsgygretrefsdffafsafafas";
        Map<String,String> params = new HashMap<String, String>(4);
        params.put("MchId", "100");
        params.put("MchNo", "or99999999");
        params.put("TradeId", "tid8788776666677788");
        params.put("Fee", "1");
        System.out.println("sign-->"+getCPSign(secret, params));
    }
}
