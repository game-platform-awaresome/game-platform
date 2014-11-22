package com.future.gameplatform.trade.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Desc:cp交易同步签名验证 date:2012/12/14
 */
public final class CpTransSyncSignValid {

	/**
	 * desc:生成密钥
	 * 
	 * @param transdata
	 *            需要加密的数据，如{"appid":"1","exorderno":"2"}
	 * @param key
	 *            应用的密钥(商户可从商户自服务系统获取)
	 * @return
	 */
	public static String genSign(String transdata, String key) {
        System.out.println("----------transdata-->"+transdata+"   key--->"+key);
		String sign = "";
		try {
			// 获取privatekey和modkey
			String decodeBaseStr = Base64.decode(key);
            System.out.println("decode key-->"+decodeBaseStr);
			String[] decodeBaseVec = decodeBaseStr.replace('+', '#').split("#");

			String privateKey = decodeBaseVec[0];
			String modkey = decodeBaseVec[1];
            System.out.println("privateKey key-->"+privateKey+"modkey-->"+modkey);
			// 生成sign的规则是先md5,再rsa
			String md5Str = MD5.md5Digest(transdata);
            System.out.println("md5Str-->"+md5Str);
			sign = RSAUtil.encrypt(md5Str, new BigInteger(privateKey),
					new BigInteger(modkey));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;

	}

	/**
	 * 
	 * @param transdata
	 *            同步过来的transdata数据
	 * @param sign
	 *            同步过来的sign数据
	 * @param key
	 *            应用的密钥(商户可从商户自服务系统获取)
	 * @return 验证签名结果 true:验证通过 false:验证失败
	 */
	public static boolean validSign(String transdata, String sign, String key) {
        System.out.println("----------transdata-->"+transdata+"    sign--->"+sign+"   key--->"+key);
		try {
			String md5Str = MD5.md5Digest(transdata);
            System.out.println("md5Str-->"+md5Str);
			String decodeBaseStr = Base64.decode(key);

			String[] decodeBaseVec = decodeBaseStr.replace('+', '#').split("#");

			String privateKey = decodeBaseVec[0];
			String modkey = decodeBaseVec[1];

			String reqMd5 = RSAUtil.decrypt(sign, new BigInteger(privateKey),
					new BigInteger(modkey));
            System.out.println("reqMd5-->"+reqMd5);
			if (md5Str.equals(reqMd5)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public static void main(String[] args) {
		// String reqJson =
		// "{\"exorderno\":\"1\",\"transid\":\"2\",\"waresid\":\"3\",\"chargepoint\":31,\"feetype\":4,\"money\":5,\"count\":6,\"result\":0,\"transtype\":0,\"transtime\":\"2012-12-12 12:11:10\",\"cpprivate\":\"7\",\"sign\":\"64a04bc23987c621264a6295b8c61191 9c9ccd91cbc584316b9d99919921a9be 89c38dfa9329001a521bf4c904bb83cd \"}";
		// boolean b = CpTransSyncSignValid.validSign(reqJson,
		// "MjdFN0ExMURCM0JDMDc0QTQ3OTY1NzEwNDEzODMzMjhERkFDRDA5MU1UVTRNalkyTXpNek1ESTFNREUxT1RjME16RXJNakk0TnpjeE56ZzBNVEEyTlRJME16TTNORE00TkRBM09EY3hNemcxTkRrMU1UTXhPVEl4");
		/*
        String md5= MD5.md5Digest("{\"exorderno\":\"test00001\",\"transid\":\"00012122916363200005\",\"waresid\":\"20000100000001200001\",\"appid\":\"1\",\"feetype\":2,\"money\":1,\"count\":1,\"result\":0,\"transtype\":0,\"transtime\":\"2012-12-29 16:36:33\",\"cpprivate\":\"123456\"}");
		String sign = null;
		try {
			sign = RSAUtil.encrypt(md5, new BigInteger("57771314293114350820943284589604085519"), new BigInteger("13945683305049607291"));
		} catch (Exception e) {
		}
		System.out.println(sign);
		*/
		//3ae5508e339425e9d0d89c1bf6755183 5777a6fb3489bc48a8902636adc2e7bc 8404d152e2a891a488145c8c85f7f4ad

        String keyPre = "57771314293114350820943284589604085519+13945683305049607291";

        System.out.println("keypre--->"+keyPre);

        String reqJson = "{\"exorderno\":\"test00001\",\"transid\":\"00012122916363200005\",\"waresid\":\"20000100000001200001\",\"appid\":\"1\",\"feetype\":2,\"money\":1,\"count\":1,\"result\":0,\"transtype\":0,\"transtime\":\"2012-12-29 16:36:33\",\"cpprivate\":\"123456\"}";
        String key = null;
        try {
            key = Base64.encode(keyPre);
            System.out.println("key--->"+key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        key = "QjZENjE1QTI4QTAyQzg2Mjg5MkI1OTc3M0RGOTk4NzdDN0FBNzE1M01UQTVNRE0zT0RZNE5EZzVPVGN5TURneE1Ua3JNVGN6TWpVMk16TTBNVFF4T0RZMk5qazFNalU0TXpFMk16SXdPRFEyTURZMU5URTVNRFV4";
        String sign1 = CpTransSyncSignValid.genSign(reqJson, key);
        System.out.println("sign1--->"+sign1);
        boolean valid = CpTransSyncSignValid.validSign(reqJson, sign1, key);

        System.out.println(valid);
		
	}

}
