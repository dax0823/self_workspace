package com.panda.rsa.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * <p>
 * 依赖javabase64-1.3.1.jar
 * </p>
 * 
 * @author IceWee
 * @date 2012-5-19
 * @version 1.0
 */
public class Base64Utils {
 
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;
     
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * 使用Base64的URL安全方式
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        //return Base64.decode(base64.getBytes());
        return Base64.decode(base64, Base64.DEFAULT);
    }
    
    /**
     * Document Start
     * URLSafe的Base64解码
     * 如果需要在url中传输，需要注意+/符号，这2个符号有时会引起一些异常。
	 * 简单做法可以在标准base64后将+/换成-*，然后需要decode的时候，先将-*换成+/，再进行decode。
     * Document End
     * 
     * 
     * 2016年1月27日 下午12:53:45
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decodeURLSafe(String base64) throws Exception {
        //return Base64.decode(base64.getBytes());
        return Base64.decode(base64, Base64.URL_SAFE);
    }
    
    public static byte[] decode(byte[] input) {
        return Base64.decode(input, Base64.DEFAULT);
    }
     
    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        //return new String(Base64.encode(bytes));
    	return Base64.encodeToString (bytes,Base64.DEFAULT);
    }
     
    /**
     * Document Start
     * URLSafe的Base64编码
     * 如果需要在url中传输，需要注意+/符号，这2个符号有时会引起一些异常。
	 * 简单做法可以在标准base64后将+/换成-*，然后需要decode的时候，先将-*换成+/，再进行decode。
     * Document End
     * 
     * 
     * 2016年1月27日 下午12:52:09
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encodeURLSafe(byte[] bytes) throws Exception {
        //return new String(Base64.encode(bytes));
    	return Base64.encodeToString (bytes,Base64.URL_SAFE);
    }
    
    /**
     * <p>
     * 将文件编码为BASE64字符串
     * </p>
     * <p>
     * 大文件慎用，可能会导致内存溢出
     * </p>
     * 
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }
     
    /**
     * <p>
     * BASE64字符串转回文件
     * </p>
     * 
     * @param filePath 文件绝对路径
     * @param base64 编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }
     
    /**
     * <p>
     * 文件转换为二进制数组
     * </p>
     * 
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
         }
        return data;
    }
     
    /**
     * <p>
     * 二进制数据写文件
     * </p>
     * 
     * @param bytes 二进制数据
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);   
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {   
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
     
     
}