package com.panda.rsa;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import com.panda.rsa.utils.Base64Utils;
import com.panda.rsa.utils.EncryptMD5;
import com.panda.rsa.utils.RSAUtils;
import com.panda.rsa.utils.Utils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/***
 * 
 * @author 仵作
 *
 */
public class TestRSA {
	
	/*
	 * 服务器配置信息
	 */
	private final static String IP = "47.88.161.66";
	private final static String PORT = "8080";
	
	/*
	 * 密钥对，可随时根据需要重新生成
	 */
	//服务端密钥对
//	private final static String SERVER_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJXMoyFLVabYJS1GJr/MLDAxvdhGEfTXxfxBm2qYK1DkGADIwZ/wxSF2yivP/eEZE1FtLT1mF9tYnMFqPYDWqvArT2Bgxyvu1N2YjwRiHC+lIaA7OIepq2D06lz+H0AOC/TFMWqpIcn/zpRSxXHyQeIWI2IdzOy8qQmqMly+gTJfAgMBAAECgYAXBin4nxSeHVCxgbvI9P/BtJOCX1q5MET8/bR+kL7vou9Rh4nDJIWYHCL80u4pn+oJNOekLUHzc848KDEl0VjabqOqXfaNeWN50X7NawSHUAHS1UXtZ3Br/OZ76PgcDuV6LlCdJDONoHHdjcplmvmifx85l1xH/OFgpWEodvH7YQJBANvaSZ1A4JK4NXSQd8VFM+2I0uoKXxta9PYbOaZMbC6TUe5GIDiZIim/naAnDPUQJ0nzwCyA7q7yF7pOZO23blECQQCubcT2yaGxvA0WbsGhvwJPsvUjnkUWSTf9OLBuxbaLYvSg6ybwtO6XEaHY+G2LVNCpBP6QPgFt+bINH0OS0PmvAkEAjoPTImfy68kThcs8iJlSjxEZv/MXMdFlZ7/AQCfcHNkMzEuTCX1i4vM+h5mo0Zc2EL88fNzbXHa8oN2+mUZF8QJAUAr7Sax5UNBUySiGHq1CaERdDb1BQtrSoJb1D8AW5pvAWl/OfMLqgdSLir9ltXx0TDuIfbSWf1sIA9GG9saC7QJAUMgutiud1K77ubtHmiw1i1bEnVTqc2e/nlFqcPFmdB5lxgrie1tiulPuigKvTAMF83zIWVBdQye0oqOyR2gARw==";
//	private final static String SERVER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVzKMhS1Wm2CUtRia/zCwwMb3YRhH018X8QZtqmCtQ5BgAyMGf8MUhdsorz/3hGRNRbS09ZhfbWJzBaj2A1qrwK09gYMcr7tTdmI8EYhwvpSGgOziHqatg9Opc/h9ADgv0xTFqqSHJ/86UUsVx8kHiFiNiHczsvKkJqjJcvoEyXwIDAQAB";
//	//客户端密钥对
//	private final static String CLIENT_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALIK5Yg2yAc/yYLDbUQuvrakuAnlbe9fRB0/9GmM7oIcMbCpaWqW0eDwvBD0oTAKNp9MhaJdOp3HXD8hGBQ6A+9FSmRDCj/m0BLqkmXTN2Qle4qFG1UEeXfzptjjLUS1cmjsLGu7Rqf+YYczerqsPLyp7pbJMNVjDuJQJx5fDE+jAgMBAAECgYAsujAPmDOIHq4w+4dnqZafq6HQWqLTj8s8zeRI3+7LIOoax3SEIn93UwLr/4i+2NIAEuk/cMxY9sxlz5qM+kYiWdRFU2qyybSfbm/Tkp/XiwU5tPMdm30S8Y1699HA2MDUFytn1K3Nammfo7/9LJ38JTcswn6oL8xYJpHYx98ZgQJBAPJdTL1JTZAZD/H40weryZiEnZfFx4sQKRALR/YDGJKypvdU95N2rRUB27YaHgqnHLab6AtL3oOPes3rLWAHCxUCQQC8DzBIDfk8eip6+ZLByiO2EHoUMbATvau1FAhCzD7hSuCucXejH09e8I4shyjAAvQ9KRrQIIG4HoDW6hnlBj3XAkEAspre+n8SkaZmgcMEgRqvrZ3NJROzQV/fYtttZHZTnYfaU1piOGG0m06LQ19r1B3iow+xqOF4UStjLGyrO6D1IQJBALqwDUsKbCioUjGAPjsY0vizRK11YglG2gVJnKXLIoT+kys5Qv3xYBaskUcM6dwrUpDAlHiWPJAt39L51LjxyEMCQQDAZRBprcDEpFzjmmkYqZfSuND7b3bz+iSQRzdJRpmVbwX8ZYeNd8wK+PDHjzi5wVuxrLArSSAaniSICnhEuDIJ";
//	private final static String CLIENT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyCuWINsgHP8mCw21ELr62pLgJ5W3vX0QdP/RpjO6CHDGwqWlqltHg8LwQ9KEwCjafTIWiXTqdx1w/IRgUOgPvRUpkQwo/5tAS6pJl0zdkJXuKhRtVBHl386bY4y1EtXJo7Cxru0an/mGHM3q6rDy8qe6WyTDVYw7iUCceXwxPowIDAQAB";
	
	//服务端密钥对
//	private final static String SERVER_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJsa+X2BiASwKPFJ6gLnghNLi/8sdFFXOWKjp0hcBl5aMT7Skw+1gS/yYA334q5PP15lZrRIBDsG4dTYrY6Y0UrAP4FMczNZHRfIOyPUHzJXFyT27wruAr5US4p91HR+LSagwgoysg1AS/eze54ZV4HFBwJ5oxD1Am67RYUC15MJAgMBAAECgYEAi+UKfd6ktcXFR8vo2vG/6nNLM73+s5a+VW9R4/vMarFilU0MAzEr8fjkFfUsaRdlCljfB1+lITAV5AoCkAjZSvEVbVrvkcMw49i9g2Np1MDdlbYg0eKo84Qm5r40SiKHoxr74eYeGEaWMFPJF5bM2aruViMGhh5gINH8eiMJRgECQQDKozPyyxySunhdvHQtQDJ66czAldRVM2lcIBhVRgRS9UQ/XrzWyYj114Gtrd+zm7D6O22QMA4hxcIIhIwOGUahAkEAw/NlEsgXYvq6d+xnoivNlXgg0juf24lylopiXPbuz0hi+s91XjHsiGkwI6VqwmVXt96AzUulk6iAGRrY0qK7aQJAS4MByspRvYPrctLVYD96ED+r42ByHcXoz0HOj4hl+BwZ8Jt3N3smpKZIVtZidGXiT31x2LdvrLorEk2Ce4aDwQJBAImYrGzX3LRAZBg6BTu3jpFa3ZFDFSnQBor3ecLWEzOzHO4KJ9yRKgtP8eHWEnWXPFV9BO0kJYzeUbVPSNoAM6ECQQCDTJb8+j4JFppEPD3c8rnPNqF+4T9HDSSTBLF97BAGNTFUPatIHZ4Vsdoi6um04X6x3uNB2sozFqaAH6+UWav4";
//	private final static String SERVER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbGvl9gYgEsCjxSeoC54ITS4v/LHRRVzlio6dIXAZeWjE+0pMPtYEv8mAN9+KuTz9eZWa0SAQ7BuHU2K2OmNFKwD+BTHMzWR0XyDsj1B8yVxck9u8K7gK+VEuKfdR0fi0moMIKMrINQEv3s3ueGVeBxQcCeaMQ9QJuu0WFAteTCQIDAQAB";
	//客户端密钥对
//	private final static String CLIENT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKpPtL0uRxZ6eSW09p/DFypstCb7q0ZDyohFz4zj3101uwTEdfgtUqKHPmjTUNUBOeq68CGbd2t2LHsaZYj7p6VIeXumnZaAVl3EbqmNMnSHRf7xHZlEptuPQBucy0e6TrwhbdCyNmz93DbBjvqd0ijRoqxiRQnloNhaZ3k9iWZnAgMBAAECgYAQTJB/im5DOHVfpnHz2DffV4fmMy+xCcvYPtYeJbC6uumrCyI/HhdKdqcCYTbbK8LfNATHMpm7LxQSs/QDqBHjF5QBrhDGNSyWz1LmIvD0821EGDObbHiDFzRenZaZodUJSOMBoHox2vpgGnIUBGqyw2PVwnemMs/zHYmcn2EgAQJBANPV1/w10r6FwXJ37c/t/HuTStPWgeSspIbm62KL2gbJOKiSLdh1CR1nKXFza+9FT7tYCwbLIU4hT/38msVdXWECQQDN0aD4BPpfFqbvm7wnbUonCUDz0gud24n7or8rA7SKbtsRLC/FBywny3Tz791v6KL2Ba93qeGiWWnFCnROd9DHAkEA0i/Vne+9ysMmvbCXq3/kzKzKS0UFH2PZ3+zSU7aWP7N633AjwynlQGJy8Znn18K7KRVj43q78i0FlOsGmpxYIQJBAK8vumrBmfbsCgWzMlEA5ngZmU6vv7GK14T3fu53Beo6935DdRmoxD3PIOothfcmNedg6XzafbK5lpzgDCVqOKsCQCeCYewWkT7tRlSlESj+Yi/jBy40IKaMepQF7L6AX8qXDptAQdR/N+ENRKPtmGRBC8IG+aZJ8Af7qpkCzFdMVQg=";
//	private final static String CLIENT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqT7S9LkcWenkltPafwxcqbLQm+6tGQ8qIRc+M499dNbsExHX4LVKihz5o01DVATnquvAhm3drdix7GmWI+6elSHl7pp2WgFZdxG6pjTJ0h0X+8R2ZRKbbj0AbnMtHuk68IW3QsjZs/dw2wY76ndIo0aKsYkUJ5aDYWmd5PYlmZwIDAQAB";
		

	//服务端密钥对
	private final static String SERVER_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJsa+X2BiASwKPFJ6gLnghNLi/8sdFFXOWKjp0hcBl5aMT7Skw+1gS/yYA334q5PP15lZrRIBDsG4dTYrY6Y0UrAP4FMczNZHRfIOyPUHzJXFyT27wruAr5US4p91HR+LSagwgoysg1AS/eze54ZV4HFBwJ5oxD1Am67RYUC15MJAgMBAAECgYEAi+UKfd6ktcXFR8vo2vG/6nNLM73+s5a+VW9R4/vMarFilU0MAzEr8fjkFfUsaRdlCljfB1+lITAV5AoCkAjZSvEVbVrvkcMw49i9g2Np1MDdlbYg0eKo84Qm5r40SiKHoxr74eYeGEaWMFPJF5bM2aruViMGhh5gINH8eiMJRgECQQDKozPyyxySunhdvHQtQDJ66czAldRVM2lcIBhVRgRS9UQ/XrzWyYj114Gtrd+zm7D6O22QMA4hxcIIhIwOGUahAkEAw/NlEsgXYvq6d+xnoivNlXgg0juf24lylopiXPbuz0hi+s91XjHsiGkwI6VqwmVXt96AzUulk6iAGRrY0qK7aQJAS4MByspRvYPrctLVYD96ED+r42ByHcXoz0HOj4hl+BwZ8Jt3N3smpKZIVtZidGXiT31x2LdvrLorEk2Ce4aDwQJBAImYrGzX3LRAZBg6BTu3jpFa3ZFDFSnQBor3ecLWEzOzHO4KJ9yRKgtP8eHWEnWXPFV9BO0kJYzeUbVPSNoAM6ECQQCDTJb8+j4JFppEPD3c8rnPNqF+4T9HDSSTBLF97BAGNTFUPatIHZ4Vsdoi6um04X6x3uNB2sozFqaAH6+UWav4";
	private final static String SERVER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbGvl9gYgEsCjxSeoC54ITS4v/LHRRVzlio6dIXAZeWjE+0pMPtYEv8mAN9+KuTz9eZWa0SAQ7BuHU2K2OmNFKwD+BTHMzWR0XyDsj1B8yVxck9u8K7gK+VEuKfdR0fi0moMIKMrINQEv3s3ueGVeBxQcCeaMQ9QJuu0WFAteTCQIDAQAB";
	//客户端密钥对
	private final static String CLIENT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKpPtL0uRxZ6eSW09p/DFypstCb7q0ZDyohFz4zj3101uwTEdfgtUqKHPmjTUNUBOeq68CGbd2t2LHsaZYj7p6VIeXumnZaAVl3EbqmNMnSHRf7xHZlEptuPQBucy0e6TrwhbdCyNmz93DbBjvqd0ijRoqxiRQnloNhaZ3k9iWZnAgMBAAECgYAQTJB/im5DOHVfpnHz2DffV4fmMy+xCcvYPtYeJbC6uumrCyI/HhdKdqcCYTbbK8LfNATHMpm7LxQSs/QDqBHjF5QBrhDGNSyWz1LmIvD0821EGDObbHiDFzRenZaZodUJSOMBoHox2vpgGnIUBGqyw2PVwnemMs/zHYmcn2EgAQJBANPV1/w10r6FwXJ37c/t/HuTStPWgeSspIbm62KL2gbJOKiSLdh1CR1nKXFza+9FT7tYCwbLIU4hT/38msVdXWECQQDN0aD4BPpfFqbvm7wnbUonCUDz0gud24n7or8rA7SKbtsRLC/FBywny3Tz791v6KL2Ba93qeGiWWnFCnROd9DHAkEA0i/Vne+9ysMmvbCXq3/kzKzKS0UFH2PZ3+zSU7aWP7N633AjwynlQGJy8Znn18K7KRVj43q78i0FlOsGmpxYIQJBAK8vumrBmfbsCgWzMlEA5ngZmU6vv7GK14T3fu53Beo6935DdRmoxD3PIOothfcmNedg6XzafbK5lpzgDCVqOKsCQCeCYewWkT7tRlSlESj+Yi/jBy40IKaMepQF7L6AX8qXDptAQdR/N+ENRKPtmGRBC8IG+aZJ8Af7qpkCzFdMVQg=";
	private final static String CLIENT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqT7S9LkcWenkltPafwxcqbLQm+6tGQ8qIRc+M499dNbsExHX4LVKihz5o01DVATnquvAhm3drdix7GmWI+6elSHl7pp2WgFZdxG6pjTJ0h0X+8R2ZRKbbj0AbnMtHuk68IW3QsjZs/dw2wY76ndIo0aKsYkUJ5aDYWmd5PYlmZwIDAQAB";
		
	/**
	 * 生成 rsa 密钥对
	 * 每次生成一对，客户端与服务端分别保存一对
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void getKeys() throws Exception {
		RSAUtils rsa = new RSAUtils();
		Map<String, Object> keyMap = rsa.genKeyPair();
		String privateKey = rsa.getPrivateKey(keyMap);
		String publicKey = rsa.getPublicKey(keyMap);
		System.out.println("私钥：" + privateKey);
		System.out.println("公钥：" + publicKey);
	}
	
	/**
	 * 对上行请求 url 进行加密，使用客户端私钥
	 * 模拟客户端向服务端发出的请求，可用其他语言代替（如：php、js） 
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String encryptUrl(String url) throws Exception {
		String encryptUrl = null;
		if (!"".equals(url)) {
			String[] str = url.split("\\?");
			String tmp = "";
			if (str.length <= 1) 
				tmp = "";
			else tmp = str[1];

//			encryptUrl = str[0] + "?" + RSAUtils.encryptByPrivateKey(str[1].getBytes(ENCODE_TYPE_UTF8), CLIENT_PRIVATE_KEY);
//			encryptUrl = str[0] + "?" + RSAUtils.encryptByPrivateKey(CLIENT_PRIVATE_KEY, str[1].getBytes(ENCODE_TYPE_UTF8));
			encryptUrl = str[0] + "?" + RSAUtils.encryptByPrivateKey(CLIENT_PRIVATE_KEY, tmp.getBytes());
		}
		return encryptUrl;
	}
	
	/**
	 * 对上行请求 url 进行解密，使用客户端公钥
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String decryptUrl(String url) throws Exception {
		String decryptUrl = null;
		if (!"".equals(url)) {
			String[] str = url.split("\\?");
			String tmp = "";
			if (str.length <= 1) 
				tmp = "";
			else tmp = str[1];
//			decryptUrl = str[0] + "?" + RSAUtils.decryptByPublicKey(str[1].getBytes(ENCODE_TYPE_UTF8), CLIENT_PUBLIC_KEY);
//			decryptUrl = str[0] + "?" + RSAUtils.decryptByPublicKey(CLIENT_PUBLIC_KEY, str[1].getBytes(ENCODE_TYPE_UTF8));
			decryptUrl = str[0] + "?" + RSAUtils.decryptByPublicKey(CLIENT_PUBLIC_KEY, tmp.getBytes());
		}
		return decryptUrl;
	}

	/**
	 * 对下行返回结果进行加密，使用服务端私钥
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String encryptResult(String result) throws Exception {
//		return new String (RSAUtils.encryptByPrivateKey(result.getBytes(ENCODE_TYPE_UTF8), SERVER_PRIVATE_KEY), ENCODE_TYPE_UTF8);
		return RSAUtils.encryptByPrivateKey(SERVER_PRIVATE_KEY, result.getBytes());
		
	}
	
	/**
	 * 对下行返回结果进行解密，使用服务端公钥
	 * 模拟客户端向服务端发出的请求，可用其他语言代替（如：php、js） 
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String decryptResult(String result) throws Exception {
//		return new String (RSAUtils.decryptByPublicKey(result.getBytes(ENCODE_TYPE_UTF8), SERVER_PUBLIC_KEY), ENCODE_TYPE_UTF8);
		return RSAUtils.decryptByPublicKey(SERVER_PUBLIC_KEY, result.getBytes());
	}
	
	/**
	 * 对请求加签，使用客户端公钥
	 * 加签结果字符串为：所有“？”之后的参数字符串拼接客户端公钥后，进行 MD5 加密所得字符串
	 * sing = getMd5String(params + clientPublicKey)
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String signUrl(String url, String clientPublicKey) throws Exception {
		String sign = null;
		if (!"".equals(url)) {
			String[] str = url.split("\\?");
			String tmp = "";
			if (str.length <= 1) 
				tmp = clientPublicKey;
			else tmp = Utils.sortParamStr(str[1]) + clientPublicKey;
//			System.out.println("加签 params：tmp = " + tmp);
			sign = EncryptMD5.getMd5String(tmp);
		}
		return sign;
	}
	
	/**
	 * 对请求加签，使用服务端公钥
	 * 加签结果字符串为：全部返回结果字符串拼接服务端公钥后，进行 MD5 加密所得字符串
	 * 此处在加签之前，暂不对结果进行排序，若后续业务需要，可调整
	 * @param url
	 * @param serverPublicKey
	 * @return
	 * @throws Exception
	 */
	public static String signResult(String result, String serverPublicKey) throws Exception {
		return EncryptMD5.getMd5String(result + serverPublicKey);
	}
	
	/**
	 * 对请求 url 的参数进行重新排序，默认升序排列
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String sortUrl(String url) throws Exception {
		String newUrl = null;
		if (!"".equals(url)) {
			String[] str = url.split("\\?");
			String tmp = "";
			if (str.length <= 1) 
				tmp = "";
			else tmp = Utils.sortParamStr(str[1]);
			newUrl = str[0] + "?" + tmp;
		}
		return newUrl;
	}
	
	/**
	 * 对解密后的 url 进行验签
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyUrl(String originalUrl, String clientPublicKey) throws Exception {
		boolean flag = false;
		if (!"".equals(originalUrl)) {
			String[] str = originalUrl.split("\\?");
			if (str.length <= 1) {
				;	//没有任何参数，包括 sign
			}	else {
				int num = str[1].indexOf("sign=");
				if (num == -1) {
					;	//没有 sign
				} else {
					String[] st = str[1].split("sign=");
					String sign = st[1];
					if(num == 0) {	//参数中仅有 sign
						flag = EncryptMD5.getMd5String(clientPublicKey).equalsIgnoreCase(sign);
//						System.out.println("md5 11 = " + EncryptMD5.getMd5String(Utils.sortParamStr(st[0]) + clientPublicKey));
					} else {
						flag = EncryptMD5.getMd5String(Utils.sortParamStr(st[0]) + clientPublicKey).equalsIgnoreCase(sign);
//						System.out.println("md5 22 = " + EncryptMD5.getMd5String(Utils.sortParamStr(st[0]) + clientPublicKey));
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 对解密后的返回结果验签
	 * @param originalResult
	 * @param serverPublicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyResult(String originalResult, String serverPublicKey) throws Exception {
		boolean flag = false;
		if (!"".equals(originalResult)) {
//			System.out.println("originalResult = " + originalResult);
			int index = originalResult.lastIndexOf("}");
			String realResult = originalResult.substring(0, index + 1);
			String sign = originalResult.substring(index + 1);
//			System.out.println("realResult = " + realResult);
//			System.out.println("sign = " + sign);
			flag =  EncryptMD5.getMd5String(realResult + serverPublicKey).equalsIgnoreCase(sign);
		}
		return flag;
	}
	
	/**
	 * 上行加密字符串，解密操作
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		final String before = "/server_system/getUserWechatInfo?Dj+faeX5KkoYjOYeHfhjgKl7SAY8niApe7KeIZ13jN3eJZCfGpsArM9GENofujYgW8RPxS3xU2FrzO/zb7bEzQPEbpzSvXybMFqwIyoWnYvvJMqjKnY5zcFvphVCxdTQ9XdBZk08RbY78zKJOgy8fsGMSQq9iURwHS2OHMJnhc8=";
//		final String before = "/server_system/getUserWechatInfo?Ss/mh3uaJTYHDGtwB7rKFjbcwuFbl4LH4/wBPp30MhmjyEC8EZcu8YRy3SqWDLqpuWKIYBdJUtxNCA3ZOKg3zLADze8bu12t4tX5MnV2Jtb5khxjBatVtiYbgya0LUVnvD1FsF/CPM3uWKVekiz7VoDh41iWhRWyitOvTM+zsTE=";
		System.out.println("测试，原始字符串：before：" + before);
		
//		String requestDecryptStr = TestRSA.decryptUrl(before);
		String requestDecryptStr = TestRSA.decryptUrl(before);
		System.out.println("测试，模拟服务端使用客户端公钥解密：decryptStr：" + requestDecryptStr);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}	
	
	/**
	 * 下行加密字符串。解密操作
	 * @param args
	 * @throws Exception
	 */
	public static void main7(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		final String result = "TnSCjTHBkt44/2cO6rAUE81y93YPJtogUyJjkSK/RhuhG28xXGAxhUBcBYz6BQdhblVUMyZb631KfZDIZnKNhPFFMOcs0Let1PMcBkDOECGkSPfBZgTrEB9JXK9UxRawiIxILNVt7gPDm9u/CuhsyTcKSIHuJmhsfQpxdTHYfz0wxKPVevUocAPEd4IMOiKspPuDdvPcEIWcWYvtHt6kas9ZqVdwaagpRDb7eW6xlAG1GowGt0cXJ5Z2FmrSyqUSnshuGFNYwu/aHLbvdHXUqLk7Yg7w/X9LzUbGyP+HutW0w72W+pnALcrIhTau9gDTu2vAiu4YnEblogTXEDwB3Q==";
		System.out.println("测试，加密结果：result：" + result);
		
		String resultDecryptStr = TestRSA.decryptResult(result);
		System.out.println("测试，模拟客户端使用服务端公钥解密：decryptStr：" + resultDecryptStr);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
	/**
	 * 上行原始字符串，加密操作
	 * @param args
	 * @throws Exception
	 */
	public static void main6(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		final String result = "iL+gVG3t9br0XUsXfdKeV/kdAezSsb4KwhdrlBvGpJglzhkJjldc6HteEmPVICc3qEU/1FrnTdPTr8p/47ywGJhPjEOReunBj883xvjQiJkTQ0kPzpuGgj4pR4q5Sd4ATVYqgwIHc3fd8a2Zvm3Yqy+N7p6IhDUJtahiae2C3ZA9MjMHO8Vv+cg+pKxBR11pctSzqyOeKbahAp6+Xq37UbgDy8SMspDvTfa7pNB6Cs1buyGGuPSIX/OTrJ7P5IYebnXQ8RjDX/WB/Jau5iI3t8VygQqdrF664bYVc7ax4sXnv3qEsXYBAcYfhvEPmXpk5QWbg+/qvUwDZ6jMd6PD9w==";
		System.out.println("测试，加密结果：result：" + result);
		
		String resultDecryptStr = TestRSA.decryptResult(result);
		System.out.println("测试，模拟客户端使用服务端公钥解密：decryptStr：" + resultDecryptStr);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
	//*************************************************************************************************************
	
	public static void main3(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

//		System.out.println("length = " + "aaa=12&bbb=1&ccc=1".length());
		
		final String ENCODE_UTF8 = "UTF-8";
		
//		final String before = "RSA测试667";
		final String before = "{\"list22\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"sucess!\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649";
//		final String before = "{\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"操作成功！\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649";
//		final String before = "\"{\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649\"";
//		final String before = "\"{\"list\":[\"http:\\/\\/www.pandadai.com\\/UF\\/Uploads\\/Ad\\/20150305134337488.png\",\"http:\\/\\/www.pandadai.com\\/UF\\/Uploads\\/Ad\\/20140615111458791.png\",\"http:\\/\\/www.pandadai.com\\/UF\\/Uploads\\/Ad\\/2014052601072255.jpg\"],\"msg\":\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649\"";
//		final String before = "{\"list1\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\"],\"msg\":\"操作成功！\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649";
//		final String before = "\"{\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\"],\"msg\":\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649\"";
//		final String before = "\"{\\\"list\\\":[\\\"http:\\/\\/www.pandadai.com\\/UF\\/Uploads\\/Ad\\/20150305134337488.png\\\"],\\\"msg\\\":\\\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\\\",\\\"status\\\":0}c288527191a7ddb4ae6773abe0fbb649\"";
//		final String before = "\"a=12&b=1首都/&order=1\"";
//		final String before = "a=12&b=1首都/&order=1";
//		final String before = "a=12&b=1\u9996\u90fd/&order=1";
//		final String before = "\"a=12&b=1\\u9996\\u90fd\\/&order=1\"";
//		final String before = "\"a=12&b=1&order=1\"";
//		final String before = "\"aaa=12&bbb=1&ccc=1\"";
//		final String before = "aaa=12&bbb=1//&ccc=1";
//		final String before = "操作成功！abc:/,\".\\";
//		final String before = "\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\"";
//		final String before = "\"abc\\u005c\\u0026\\u0071\\u0075\\u006f\\u0074\\u003b\\u002fabc\"";
//		final String before = "{\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\"],\"msg\":\"\\u64cd\\u4f5c\\u6210\\u529f\\uff01\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649";
		
//		final String after = "f6z9T3KHyK3pdJ7RQZNxr7FM5K9wJ4d2nMq7opMf+0zcvzAJ0D4mSBXpLDNAALF/njE3cO27AIwj+4q1guitVAaUqeymEYGchofD14tZHl5FLoisXEujcwHXcx+U8h3PtzC5ldxBpqsiyUa5LjqxNF+qydnh5Rp40H3Db8KQ0J8=";
//		final String after = "fENVe9w5dT0wdbu16QfBXR5+kgda3F6L+alfFkMYXE7m6fLq1bNsGAqpOp/XKw8MLpNDSADSSl3BUYJ1VdMobMc2Wx+1end1BJLiihuHK7+mCIGmhkumUOY8FQXiMVEXF1B7JqofcIkoXduypbwtl6+LkBfsL4bhJuX7sEeAqA8=";
//		final String after = "Q4b0QdMIhcTv83QPRyI5v7JrYelVgLMkd0+Z7B1dNO0UGqcU+lIrtiyP4e+AvPQIPneQQPHzg/VPQMPoBbmM5PLyeeqPVGRsVO+4gAOi91ONveaVnT1M4HEJv1DMavUW3l+hLeaxjNvGhSTPt8IRwmOgHh0ztyXHZAXz34PEIW8=";
//		final String after = "OgqVvPcxF0SiYNafAc1A4FHekQCPmBUs39uf+GmLAIZOlEL461PNu7EDnGYnqwS0o9V3q8qgzcprwlFdIYAQ9uoOtaRU1BA7gXNEYISYtSVpgI/ycUwLTkYbfjvRG9+V2cJfcb1QordsPxnCWFXFMwBmNEHuFKQd9odtXh+QJxo=";
//		final String after = "ho7+C+aEp29VjOEiBngfq6Qgb0oJXHZ87C3zKI2eVv1NxKEE4OucUB86YaKPQ6dC8YAbnhRKrXKwgJRyfrrnpU7MUGrfN2BLuarrIz0ciQ3xqGg4armId5UiodPvM+2Gc3/R4ft2J6JuguPX6uFhRAcUombZqG6QnoLmpaXwjFw=";
//		final String after = "fMWwdzvLDMrn1aDG3V7PXTXTYRu0tdWXGaG0FYQv2yvxvuJsXqrqE3JRbcNJCLJlL0F/Vi6Zkd+VN6fQuMePUjuDZBomzX6IYHaU1TdC60xDhkjhzgk5qK7ny4SAZu0qAfFl2G2/Cbo4PE5Ilxy+GNGeBb+wfzRPnwTVJFdQK2Y=";
//		final String after = "jKa1/m7dTIbyTBZd+RNJjPlB5F/P9mgb0gYLdAfCVJnw6PbmAmfdcFlUXF+ld3GPmHkZj52Hwmi9FePHKRYROBdV+D6cIgOQdxI7dKo1ELFQaw648ZQdG370JKl1r2Y5gGQIq/QIJnq47qv3FhvdfEr8DwpmXB/cxdOOcgrPnrqKRarS+4KbRniJXX9wHsw4wIXkvxWFvVOG/EduRU2rlCbsyG1VYrXiA2SFMNqLFrFpqMB7Hty8pg3scphBhrBdrPWbzwKwpnMLsxhbROVSqacK/wYLWSMIPS5aMs7NhPhPhIJcSZ/qNr8s2YwLQ0ULvdHrDvNe/OCkgYaMbZL1kE6TYOFgsrv3YzyTrCpx6bCYjpjbhYyr3tX1CdhWBe3Yqqznrgl+muc8gBJAHvGWEjLk78CJjJzt4V3l/MaseH1ILyZaOFq1Px6MrCqNU9A+2KSeQLsdF8vYzIioIhV1yGtMJl/arJhsG+cwqNzbMnK27yGZgIBqLSZLA87nahst";
//		final String after = "liH0xdaaTSHjYcN5BgT6558k9Uz8+la1tGvZUh7Apw4fJLg6vXU0Y1bT4n8tMXCVyFSc+YZaEhf+ym7v+aco7gQQ+VuJ+ruFbkvMda0OtV1WLbpOmEQN+CPRxJYjvsrxPWVZ5U/lhJi/6yvGkyPT1K8dDMY5UV6WMJ1VjcI/iDKH36H0DFQcXp91W6eKyeKFnW2NmVvQJPWzSF1H44JajJqh5Aho+pkK6GM2fUIjsV5lm5ijshwqX4jx1V1ZGXyO/Kc266vAMVN2lzwSO//h5NewQRi60r20LPbkBVNg5jkuuLZvNt0EQ74ACkerzPXVl6el4Sike+83RNoN4+7se0OMIpP+Qju3djjqmf+yv7wzUob7ZDV6kCZuyJsp3fKRghN2s8s2Mfg8mn/bbx2sEb6HlLKS3zQbUuU0ELUXpOGmeL35d8ceUg3FTHZ4M3iavy0XYGzmehQa7JFRYe//JVq+xw9EW/0fKdY4Bnquwjp6/UNhNtjdSxVlyY5yBDF8";
//		final String result = "YvugEcnCgH3JFAqe9NsP3BGvOj5kQvEFAW4HrwUhdVLlAkqbILRY7lpBlqIyyUA/Cj5nGAfGpGcTr5pyqgdjynun2FwXtfkc5KC3U+irAe7rSf4SdWXIC1prOYtgUM2Yvf5bclbTrbvF1tXfMuTir9UxksWrON2tp83yxFQu934=";
		final String after = "Ex0dyU8UcnrCaULm5pe0/ICZTzTDUiWgayhnRQqxgbuYkT4sV6fXO3J+LvttyzUS+Q0bk64X8kgjUvKSUsoF595LPnzW71fp3HVNUM1OAfpcJI6KKlibYXQTy+M0mFMMl2LZERP1sRPTemUS8CjyZA6/15y8Co05DvSSZvKi7XI=";
		
		RSAUtils rsa = new RSAUtils();

//		System.out.println("测试，原始字符串：before：" + before);
////		String after = TestRSA.encryptResult(before).replaceAll("\\n", "");
//		
//		String jsonEncode = rsa.phpJsonEncode(before);
//		System.out.println("测试，原始字符串：before.json_encode：" + jsonEncode);
//		String after = TestRSA.encryptResult(jsonEncode).replaceAll("\\n", "");
//		
//		String encode = URLEncoder.encode(before, ENCODE_UTF8);
//		System.out.println("测试，原始字符串 before.encode() ：encode= " + encode);
////		String after = TestRSA.encryptResult(encode).replaceAll("\\n", "");
		
		System.out.println("测试，模拟服务端使用服务端私钥加密：after：" + after);
		
		String decryptStr = TestRSA.decryptResult(after);
//		String decryptStr = TestRSA.decryptResult(result);
		System.out.println("测试，模拟客户端使用服务端公钥解密：decryptStr：" + decryptStr);

		String decode = URLDecoder.decode(decryptStr, ENCODE_UTF8);
		System.out.println("测试，解密字符串 decryptStr.decode() ：decode：" + decode);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
	
	public static void main2(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");

//		String json = "{\"acc_no\":\"6230580000030050001\",\"additional_info\":\"\",\"biz_type\":\"0000\",\"commodity_amount\":\"\",\"commodity_name\":\"\",\"id_card\":\"110106198012230011\",\"id_card_type\":\"\",\"id_holder\":\"金大牙\",\"member_id\":\"100000178\",\"mobile\":\"13512345678\",\"page_url\":\"\",\"pay_code\":\"PAB\",\"req_reserved\":\"\",\"return_url\":\"\",\"terminal_id\":\"100000916\",\"trade_date\":\"20160303161735\",\"trans_id\":\"100000178_805\",\"txn_amt\":\"金大牙\",\"txn_sub_type\":\"01\",\"user_name\":\"\",\"valid_date\":\"\",\"valid_no\":\"\"}";
//		Base64.encode(binaryData);
		
//		final String encryptUrl = "http://47.88.161.66:8080/server_system/login?bXmj6zDFIZQyH6RDIqF/5Xh7tqbFEVcezeDj2D2QeDCp3UNzMIO890du5v5988UhUN63I+vRuaH4TU6Nqjw9xdbU8XjrbTNPfH96eLQ9pD0myNXZKO3JeDkDo6aTz8FzAGQDeufPsL7zRyWxq50KL/l8+8poOrvg7QZiTy7FmT0=";
//		String decryptUrl = TestRSA.decryptUrl(encryptUrl);
//		System.out.println("服务端使用客户端公钥解密：decryptUrl= " + decryptUrl);
		
//		final String encryptedData = "ctlUMvcbV80CQSoPNXMIxEwVgNItIcey8fugUkSyk/YOeQnO4RFOh8C7yykyafc2XbShxa0at8BXLR6joO0N2opqWuHOWWeEOcwduz2jjheEH+MvXM138VzUB6qOzYwfBR13AkziAQ3XAo+9sWWKwxJRRzqkWg3llUJQVgVrgwMO8lpOliTw9hPZciw/+IclwK7oqyigLKLHNFIjbSAoBtHGlSlrV3tD6eWWbXD8Lvw7bWNoKooqEAvXXRVeGxGY9qih4py3WZHB00n6bADK231np+JnUG79vKiqHSQrzTJCZFYj35mcUo8eO7o8YVb6XfsvdQ1bhoV+eEogFwP10YbZA7D2xH6XoCRRewOxKMCJ24TiXv2ig4cprQOm1cfoDKpNCARh9Y745/wkRmj37aET8GUMi/lbjp9FnfUqYNcq4IOTQi4prHSwmFObTBbZXbLYz9wTyjYWm4MLwbuaZGmGACfeTRV907fuD/Vzx4PKSkze0FOL7POn0XXDe3/p";
//		RSAUtils rsa = new RSAUtils();
//		byte[] bytes = rsa.decryptByPublicKey(Base64Utils.decode(encryptedData), SERVER_PUBLIC_KEY);
//		byte[] bytes = rsa.decryptByPublicKey(encryptedData.getBytes(), SERVER_PUBLIC_KEY);
//		byte[] bytes = Base64Utils.decode(encryptedData);
//		System.out.println("Base64Utils.decode(encryptedData) = " + new String(bytes));
//		System.out.println("Base64Utils.decode(encryptedData) = " + bytes);
		
//		final String ENCODE_UTF8 = "UTF-8";
//		final String ENCODE_GBK = "GBK";
//		final String ENCODE_GB2312 = "GB2312";
//		final String result = " {\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"操作成功！\",\"status\":0}";
//		RSAUtils rsa = new RSAUtils();
//		byte[] bytes = rsa.encryptByPrivateKey(result.getBytes(), SERVER_PRIVATE_KEY);
//		byte[] bytes_utf8 = rsa.encryptByPrivateKey(result.getBytes(ENCODE_UTF8), SERVER_PRIVATE_KEY);
//		byte[] bytes_gbk = rsa.encryptByPrivateKey(result.getBytes(ENCODE_GBK), SERVER_PRIVATE_KEY);
//		byte[] bytes_gb2312 = rsa.encryptByPrivateKey(result.getBytes(ENCODE_GB2312), SERVER_PRIVATE_KEY);
//		System.out.println("默认编码集：encryptResult= " + new String(Base64Utils.encode(bytes)));
//		System.out.println("指定 UTF-8：encryptResult= " + new String(Base64Utils.encode(bytes_utf8)));
//		System.out.println("指定 GBK：encryptResult= " + new String(Base64Utils.encode(bytes_gbk)));
//		System.out.println("指定 GB2312：encryptResult= " + new String(Base64Utils.encode(bytes_gb2312)));
		
		String test = "RSA测试667";
		System.out.println("test : " + test);
		System.out.println("test3 : " + test.getBytes());
		System.out.println("test1 : " + Base64Utils.encode(test.getBytes()));
		System.out.println("test2 : " + Base64.encode(test.getBytes()));
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main1(String args[]) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		System.out.println("客户端私钥：" + CLIENT_PRIVATE_KEY);
		System.out.println("客户端公钥：" + CLIENT_PUBLIC_KEY);
		System.out.println("服务端私钥：" + SERVER_PRIVATE_KEY);
		System.out.println("服务端公钥：" + SERVER_PUBLIC_KEY);
		
		//模拟客户端请求
		final String url = "http://" + IP + ":" + PORT + "/server_system/queryBannerPicsList?order=1&b=1&a=12";
//		final String url = "http://" + IP + ":" + PORT + "/server_system/queryProvList";
//		final String url = "http://" + IP + ":" + PORT + "/server_system/queryProvList?";
		//模拟服务端返回
//		final String result = " {\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"操作成功！\",\"status\":0}";
		final String result = " {\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\",\"http://www.pandadai.com/UF/Uploads/Ad/20140615111458791.png\",\"http://www.pandadai.com/UF/Uploads/Ad/2014052601072255.jpg\"],\"msg\":\"success!\",\"status\":0}";

		System.out.println("原始客户端请求url 为：url = " + url);
		System.out.println("原始服务端返回结果为：urlSort= " + result);
		
		//生成密钥对，可根据需要重新生成
		//0.1 生成密钥
//		test.getKeys();
		
		//0.2 签名前，先对所有参数排序
		String urlSort = null;
		urlSort = sortUrl(url);
		System.out.println("客户端排序后 url 为：urlSort= " + urlSort);
		
		//0.3 上行请求加签
		//在请求参数后，追加“&sign=***”参数
		String urlSign = null;
		if (urlSort.indexOf("?") > -1) {
			if (urlSort.endsWith("?"))
				urlSign = url +"sign=" + TestRSA.signUrl(urlSort, CLIENT_PUBLIC_KEY);
			else urlSign = url +"&sign=" + TestRSA.signUrl(urlSort, CLIENT_PUBLIC_KEY);
		}	else urlSign = url +"?sign=" + TestRSA.signUrl(urlSort, CLIENT_PUBLIC_KEY);
		System.out.println("客户端使用客户端公钥加签：urlSign= " + urlSign);
		
		//rsa 方式通信
		//1. 客户端使用客户端私钥加密
		String encryptUrl = TestRSA.encryptUrl(urlSign).replaceAll("\\n", "");
		System.out.println("客户端使用客户端私钥加密：encryptUrl= " + encryptUrl);
		
		//2. 服务端使用客户端公钥解密
		//服务端获取加密参数
		//判断请求方式：post/get
//		String method = request.getMethod();
		//get 方式：
//		String query = request.getQueryString();
		//post方式：
//		final int BUFFER_SIZE = 8 * 1024;
//		byte[] buffer = new byte[BUFFER_SIZE];
//		String bodyData = "";
//		ServletInputStream sis;
//		try {
//			sis = request.getInputStream();
//			int length = 0;
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			int bLen=0;    
//			while((bLen=sis.read(buffer))>0){     
////			     outputStream.write(bytes,0,bLen);       
//			     baos.write(buffer, 0, bLen);  
//			 }   
//			bodyData = new String(baos.toByteArray());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		String decryptUrl = TestRSA.decryptUrl(encryptUrl);
		System.out.println("服务端使用客户端公钥解密：decryptUrl= " + decryptUrl);
		
		//2.1 服务端对解密后的请求进行验签
		boolean isServerVerify = TestRSA.verifyUrl(decryptUrl, CLIENT_PUBLIC_KEY);
		System.out.println("服务端使用客户端公钥验签结果： " + isServerVerify);
		
		//3.1 服务端使用服务端公钥加签
		String resultSign = result + TestRSA.signResult(result, SERVER_PUBLIC_KEY);
		System.out.println("服务端使用服务端公钥加签：resultSign= " + resultSign);
		
		//3.2 服务端使用服务端私钥加密
		String encryptResult = TestRSA.encryptResult(resultSign).replaceAll("\\n", "");
		System.out.println("服务端使用服务端私钥加密：encryptResult= " + encryptResult);
		
		//4.1 客户端使用服务端公钥解密
		String decryptResult = TestRSA.decryptResult(encryptResult);
		System.out.println("客户端使用服务端公钥解密：decryptResult= " + decryptResult);
		
		//4.2 客户端使用服务端公钥验签
		boolean isClientVerify = TestRSA.verifyResult(decryptResult, SERVER_PUBLIC_KEY);
		System.out.println("客户端使用服务端公钥验签结果： " + isClientVerify);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
}
