package com.future.gameplatform.recharge.notice.gateway.resource;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088711055876442";
	// 商户的私钥
	public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANCkvX/VaQ+I1y1VIWZriw1Tl9opFaYBcJyxvi89hqT369MbA3IIoAuLpuExxJMxlaLj0Kse6yDAR/sTnWOGpxZg7wnMQQln/mOVV41WoJ/L7HOHtE6WknncAwS626JNmxMytuZPPO8B8WjOiZaTTW7a9vblml+coIEOFNyeHif5AgMBAAECgYApybUCk3W1XMzsEW7dGpEBHEr/sNbbJtKtL5RN1d7Pz6/12iU4g626YkAMPnGGoZm8xDAY9dpV9uzzxpvxQRjZr/14Tm4XPQZmlidQQfXpaQLnVd5WyER9uZ58xUcvI4lFahxmobBMiaOlj1Xxe9RrET7MOpFHGgGuTJ9uL8K7WQJBAOrqmM4t4XiwTG1Bw4UhQNcANQd7pVQElY0G4A+OPATEFlqb1Y2DUC4gW2C4GdccJPw2/MJZDyK/4zU4rU9mxjsCQQDjXoBw5wHNujlkvgP9xETN6P/1U0o6aoNWCn1z3/pg3B408eYo6xOJRYCsluwka4IB0cY7fOJB7T+t6021mQNbAkB52AQ5rIJt3sNFR/7pZj4DRhA7OV6fHWGULCS67MMklbAifwFczOchhZbfq517fRWR29Nt2lOHao+mYydBL8VRAkA469St7zdFYMSzktp+8l7jSdyidzqTC01cAf5MPxt4mkK5sIGVZtyaNhszmgj+SSff+rts8yPVq2aQzOLEfs0XAkA+SutWTGG98E6rG4/2Id18KrK1N9vuwhJuVDop5EopsICtuk7n4oArZOd6QPA3meKAnSvo8LM26FzxLU9lkfN+";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/app/recharge-notice-gateway/logs/";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
