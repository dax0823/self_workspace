package com.panda.test.rsa;

import it.sauronsoftware.base64.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述。
 * <p/>
 * <p/>
 * <br>==========================
 * <br> 公司：广发基金
 * <br> 开发：sunli
 * <br> 创建时间：2014-6-11下午2:40
 * <br>==========================
 */
public class RSAUtils {
    /**
     * 指定key的大小
     */
//	private static int KEYSIZE = 1024;
    private static int KEYSIZE = 512;

    /**
     * 字符编码
     */
//    public static final String CHAR_ENCODING = "UTF-8";
    public static final String CHAR_ENCODING = "GBK";
    /**
     * AES加密算法
     */
    public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    /**
     * RSA加密算法
     */
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    
    public static void main(String[] args) throws Exception {
        Map<String, String> stringStringMap = RSAUtils.generateKeyPair();
        System.err.println("privateKey["+stringStringMap.get("privateKey")+"]");
        System.err.println("publicKey["+stringStringMap.get("publicKey")+"]");
        String priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALySJ81i8alNA/MP0VSvcJedyllxBm9TSx7NXtg1R/XDBxVXZrD6j/Lr0xxDtOo/aqdluCxc4ziHYW9YEQpg89d0xvTsnNqJlFKqBD5kYRCb/l/nvHjEqqART6coueMgjo5eX4IK1JZ7N02P3kSyVO94ruDcoVPloHuNjury3NKTAgMBAAECgYBVrksrhXOsq3PJ8zv4MKcGHQoeTb7QwpX1bvdTYhJKVs1XIxlj/NV/WSvZQeJoNVxup0dh0Orleo4JG4jSoG7qgxeztvkfsT/dpKNbr2rqTywoJTWuWoP3/WkxYEiJT8v0iOvbVIGbNhsGHoy6q+sdjgx7iQvq5FGin+x5rU7kMQJBAP9MxvbOgMNQOVoYFJBYVOMw5AWzqD+Jm0ecOSFlkN0EAiwmRDnon8HfqtHg/gc9IFFALPBqBDMAM5u8mvtrm8UCQQC9FoivxKFiRSPZBKCmDW34Zh67WYJfP3/H/RCD9/5az5QL1+v8uUdQdR8OxrWUo7zZhsuIs9SAXSF37KyOvmJ3AkEA3iorDoXfqZe7gPppkr87h8V4+LlB1aDi/uslEDn5WUvutArM3nPf6m/AkAmyJgx8fhhRwxkO9G7dek4jtF0NXQJBAIi5boSJzZfdHpqhefgX5dijHr2hb1+n7qGRHwwNmeHYDtixfHX1EtfemGJ61HlyG+++RBXI0nMu5DBu9icRCukCQCunrlRkS4UQ47eypAc81/3Wnmf/g1VUBL2Kghg8rCu9bH1qdHYCRoK6ofO3SSaQa9ZOjDqV/ljTuo9dPe2ZEZs=";
//        String walletPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbVW0i4YZGvCumQYJa0S/Q5BtwSQuZGtehxWAUObYDmBDix2ZE7ZPZxZv+yRtY+IB928PFwWtMvCkrSxc/h/Ondd4UBZqcGxbwMWXzKMXDnZgXn2YK2+0699irN/pBEsk0ZgU09yZKnPf3yTQEmpHiseBg5Uo1pBpG+0gXHrhTVwIDAQAB";
        String PubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbVW0i4YZGvCumQYJa0S/Q5BtwSQuZGtehxWAUObYDmBDix2ZE7ZPZxZv+yRtY+IB928PFwWtMvCkrSxc/h/Ondd4UBZqcGxbwMWXzKMXDnZgXn2YK2+0699irN/pBEsk0ZgU09yZKnPf3yTQEmpHiseBg5Uo1pBpG+0gXHrhTVwIDAQAB";
//        String encrypt = RSAUtils.encrypt("{\"orderId\":\"2014061111180001\",\"amount\":\"230\"}", walletPubKey);
//        String content = "encmsg=" + encrypt + "&merchant=0001&service=gfpay&version=1.0";
//        String content = "&merchant=0001&service=gfpay&version=1.0";
//        String content = "";
//        String sign = RSAUtils.sign(content, priKey);
//        System.out.println("签名原文=" + content);
//        System.out.println("sign=" + sign);
//        sign = URLEncoder.encode(sign, "utf-8");
//        System.out.println("sign=" + sign);
//        System.out.println("encrypt=" + URLEncoder.encode(encrypt, "utf-8"));

        //客户端使用公钥加密
        //请求参数：sKey=abcd1234&test=test123
        final String REQUEST_STR = "sKey=abcd1234&test=test123";
        String rsaClientEncode = RSAUtils.encryptByPublic(REQUEST_STR, PubKey);
        System.out.println("加密前请求参数字符串：" + REQUEST_STR);
        System.out.println("加密后请求参数字符串：" + rsaClientEncode);
        
        //服务端使用私钥解密
        String rsaClientDecode = RSAUtils.decryptByPrivate(rsaClientEncode, priKey);
        System.out.println("解密后请求参数字符串：" + rsaClientDecode);
        
        //服务端使用私钥加密
        //返回值：{"orderId":"2014061111180001","amount":"230"}
        final String RETURN_STR = "{\"orderId\":\"2014061111180001\",\"amount\":\"230\"}";
        String rsaServerEncode = RSAUtils.encryptByPrivate(RETURN_STR, priKey);
        System.out.println("加密后返回值字符串：" + rsaServerEncode);
        
        //客户端使用公钥解密
        String rsaServerDecode = RSAUtils.decryptByPublic(rsaServerEncode, PubKey);
        System.out.println("解密后返回值字符串：" + rsaServerDecode);
    }

    /**
     * @return 生成的密钥对
     * @throws Exception 生成过程出错
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
//        String pub = new String(Base64.encodeBase64(publicKeyBytes), CHAR_ENCODING);
        String pub = new String(Base64.encode(publicKeyBytes), CHAR_ENCODING);
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
//        String pri = new String(Base64.encodeBase64(privateKeyBytes), CHAR_ENCODING);
        String pri = new String(Base64.encode(privateKeyBytes), CHAR_ENCODING);

        Map<String, String> map = new HashMap<String, String>();
        map.put("publicKey", pub);
        map.put("privateKey", pri);
        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
//        byte[] deBase64Value = Base64.encodeBase64(b);
        byte[] deBase64Value = Base64.encode(b);
        String retValue = new String(deBase64Value);
        map.put("modulus", retValue);
        return map;
    }

    /**
     * 加密
     *
     * @param source    源数据
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程出错
     */
    public static String encryptByPublic(String source, String publicKey) throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        InputStream inputReader = new ByteArrayInputStream(source.getBytes(CHAR_ENCODING));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();

        byte[] buf = new byte[100];
        int bufl;

        while ((bufl = inputReader.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }
//        return new String(Base64.encodeBase64(writer.toByteArray()), CHAR_ENCODING);
        return new String(Base64.encode(writer.toByteArray()), CHAR_ENCODING);
    }
    
    /**
     * 
     * @param source
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encryptByPrivate(String source, String privateKey) throws Exception {
        Key key = getPublicKey(privateKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        InputStream inputReader = new ByteArrayInputStream(source.getBytes(CHAR_ENCODING));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();

        byte[] buf = new byte[100];
        int bufl;

        while ((bufl = inputReader.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }
//        return new String(Base64.encodeBase64(writer.toByteArray()), CHAR_ENCODING);
        return new String(Base64.encode(writer.toByteArray()), CHAR_ENCODING);
    }

    /**
     * 解密算法
     *
     * @param cryptograph 密文
     * @param privateKey  私钥
     * @return 信息原文
     * @throws Exception 解密过程出错
     */
    public static String decryptByPrivate(String cryptograph, String privateKey) throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

//        InputStream ins = new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(cryptograph.getBytes()));
        InputStream ins = new ByteArrayInputStream(Base64.decode(cryptograph.getBytes()));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();

        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), CHAR_ENCODING);
    }
    
    /**
     * 
     * @param cryptograph
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String decryptByPublic(String cryptograph, String publicKey) throws Exception {
        Key key = getPrivateKey(publicKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

//        InputStream ins = new ByteArrayInputStream(org.apache.commons.codec.binary.Base64.decodeBase64(cryptograph.getBytes()));
        InputStream ins = new ByteArrayInputStream(Base64.decode(cryptograph.getBytes()));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();

        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), CHAR_ENCODING);
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return 公钥
     * @throws Exception 异常..我也不知道有哪些
     */
    public static PublicKey getPublicKey(String key) throws Exception {
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @return 私钥
     * @throws Exception 异常..我也不知道有哪些
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 签名
     *
     * @param content    信息原文
     * @param privateKey 私钥
     * @return 签名
     * @throws Exception 签名过程出错
     */
    public static String sign(String content, String privateKey) throws Exception {
        String charset = CHAR_ENCODING;
//        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes()));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);

        Signature signature = Signature.getInstance("SHA1WithRSA");

        signature.initSign(priKey);
        signature.update(content.getBytes(charset));

        byte[] signed = signature.sign();
//        return new String(Base64.encodeBase64(signed));
        return new String(Base64.encode(signed));

    }

    /**
     * 验签
     *
     * @param content   信息原文
     * @param sign      签名
     * @param publicKey 公钥
     * @return 签名是否通过验证
     * @throws Exception 验签过程出错
     */
    public static boolean checkSign(String content, String sign, String publicKey) throws Exception {
//        LOGGER.debug("验证签名，content="+content+",sign="+sign+",publicKey="+publicKey);
    	System.out.println("验证签名，content="+content+",sign="+sign+",publicKey="+publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        byte[] encodedKey = Base64.decode2(publicKey);
        byte[] encodedKey = Base64.decode(publicKey).getBytes();
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(pubKey);
        signature.update(content.getBytes("gbk"));
//        boolean bverify = signature.verify(Base64.decode2(sign));
        boolean bverify = signature.verify(Base64.decode(sign).getBytes());
//        LOGGER.debug("验证签名结果为"+bverify);
    	System.out.println("验证签名结果为"+bverify);
        return bverify;
    }

}