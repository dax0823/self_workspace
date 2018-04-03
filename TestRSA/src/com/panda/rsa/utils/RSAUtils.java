package com.panda.rsa.utils;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.security.pkcs.PKCS8Key;

/***
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * @author 仵作
 *
 */
public class RSAUtils {
 
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    
//    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String CIPHER_ALGORITHM = "RSA";
     
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
     
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
     
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
     
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
 
    /**
     * php.json_encode
     */
    private static final String[] signs = {"/", "\""};
    
    /**
     * 1. 会在“"”，“/” 前增加“\”；
     * 2. 会在字符串的开头和结尾增加“"”；
     * @param source
     * @return
     * @throws Exception
     */
    public static String phpJsonEncode(String source) throws Exception {
    	String result = source;
    	//1. 模拟 php.json_encode 添加“\”
    	for (int i=0; i<signs.length; i++) {
    			result = result.replace(signs[i], "\\" + signs[i]);
    	}
    	System.out.println("result: " + result);
    	
    	//2. 前后补齐“"”
    	result = "\"" + result + "\"";
    	System.out.println("result2: " + result);
    	
    	return result;
    }
    
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
     
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     * 
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * 
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }
    
    /**
     * <p>
     * 校验数字签名
     * </p>
     * 
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * 
     * @return
     * @throws Exception
     * 
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
    	KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    	byte[] keyBytes = Base64Utils.decode(publicKey);
    	X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    	PublicKey publicK = keyFactory.generatePublic(keySpec);
    	Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    	//Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }
 
    /**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    public static String decryptByPublicKey(String publicKey, byte[] encryptedData) throws Exception {
    	byte[] bytes = decryptByPublicKey(Base64Utils.decode(encryptedData), publicKey);
    	return new String(bytes);
    }
    
    /**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
//      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//      Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    public static String encryptByPrivateKey(String privateKey, byte[] data) throws Exception {
    	byte[] bytes = encryptByPrivateKey(data, privateKey);
    	return new String(Base64Utils.encode(bytes));
    }
    
    /**
     * <p>
     * 获取私钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
 
    /**
     * <p>
     * 获取公钥
     * </p>
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main2(String[] args) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
//		final String str = "测试RSA加密，先不带签名。";
		final String str = "http://47.88.161.66:8080/server_system/queryBannerPicsList?order=1";

		RSAUtils rsa = new RSAUtils();
		Map<String, Object> keyMap = rsa.genKeyPair();
		String privateKey = rsa.getPrivateKey(keyMap);
		String publicKey = rsa.getPublicKey(keyMap);
		System.out.println("私钥：" + privateKey);
		System.out.println("公钥：" + publicKey);
		
//		byte[] encryptByPubKeyStr = rsa.encryptByPublicKey(str.getBytes(), publicKey);
//		System.out.println("公钥加密后：" + encryptByPubKeyStr);
//		System.out.println("公钥加密后：" + new String(encryptByPubKeyStr, "GB2312"));
//		System.out.println("公钥加密后：" + new String(encryptByPubKeyStr, "utf-8"));
//		byte[] encryptByPriKeyStr = rsa.decryptByPrivateKey(encryptByPubKeyStr, privateKey);
//		System.out.println("私钥解密后：" + encryptByPriKeyStr);

//		byte[] encryptByPriKeyStr = rsa.encryptByPrivateKey(str.getBytes("UTF-8"), privateKey);
//		byte[] encryptByPriKeyStr = rsa.encryptByPrivateKey(str.getBytes("GB2312"), privateKey);
		byte[] encryptByPriKeyStr = rsa.encryptByPrivateKey(str.getBytes(), privateKey);
		String newStr = new String(Base64Utils.encode(encryptByPriKeyStr));
		System.out.println("私钥加密后：" + encryptByPriKeyStr);
//		System.out.println("私钥加密后：" + encryptByPriKeyStr.toString());
		System.out.println("私钥加密后：" + newStr);
//		System.out.println("私钥加密后：" + new String(encryptByPriKeyStr, "UTF-8"));
		
//		byte[] encryptByPubKeyStr = rsa.decryptByPublicKey(encryptByPriKeyStr, publicKey);
		byte[] encryptByPubKeyStr = rsa.decryptByPublicKey(Base64Utils.decode(newStr.getBytes()), publicKey);
		System.out.println("公钥解密后：" + encryptByPubKeyStr);
		System.out.println("私钥加密后：" + new String(encryptByPubKeyStr, "UTF-8"));
//		System.out.println("私钥加密后：" + new String(encryptByPubKeyStr, "GB2312"));
//		System.out.println("私钥加密后：" + new String(encryptByPubKeyStr, "gbk"));
//		System.out.println("私钥加密后：" + new String(encryptByPubKeyStr, "iso8859-1"));

		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		final String test = "{\"list\":[\"http://www.pandadai.com/UF/Uploads/Ad/20150305134337488.png\"],\"msg\":\"\u64cd\u4f5c\u6210\u529f\uff01\",\"status\":0}c288527191a7ddb4ae6773abe0fbb649";
		
		phpJsonEncode(test);
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
}
