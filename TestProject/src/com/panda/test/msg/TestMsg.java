package com.panda.test.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TestMsg {

	//短信网关地址
	private final static String REQUEST_DOMAIN = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend?";
	//sn
	private final static String REQUEST_SN= "SDK-WSS-010-06757";
	//密码
//	private final static String REQUEST_PWD = "";
	//加密后的密码
	private final static String REQUEST_PWD_MD5 = "B1D9013EC249BB225A9C01C2FBBA6B09";
	//商户签名，追加在发送内容最后，必加，否则发送无效
	private final static String REQUEST_SIGN = "【熊猫贷】";
	//其他参数
	private final static String REQUEST_OTHERS = "&ext=&msgfmt=&rrid=&stime=";
	
	private final static String CHARSET = "utf-8";
	
	private String proxyHost = null;
	private Integer proxyPort = null;
	
	/**
	 * 发送短信，内容自定义
	 * @param phone
	 * @param content
	 * @return
	 */
	public boolean sendMsg(String phone, String content) throws Exception {
		String retStr = null;
//		retStr = methodGet(phone, content);
		retStr = methodPost(phone, content);
		return retStr == null ? false : true;
	}
	
	/**
	 * get 方式
	 * @param phone
	 * @param content
	 * @return
	 */
	private String methodGet(String phone, String content) throws Exception {
		//拼接短信请求url
		StringBuilder url = new StringBuilder();
		url.append(REQUEST_DOMAIN).append("&sn=").append(REQUEST_SN)
			.append("&pwd=").append(REQUEST_PWD_MD5)
			.append("&mobile=").append(phone)
//			.append("&content=").append(URLEncoder.encode(content + REQUEST_SIGN))
			.append("&content=").append(URLEncoder.encode(content + REQUEST_SIGN, CHARSET))
			.append(REQUEST_OTHERS);
		
		System.out.println("短信url：" + url.toString());
		
		URL localURL = new URL(url.toString());
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return resultBuffer.toString();
	}
	
	/**
	 * post 方式
	 * @param phone
	 * @param content
	 * @return
	 */
	private String methodPost(String phone, String content) throws Exception {
//		StringBuffer parameterBuffer = new StringBuffer();
//        if (parameterMap != null) {
//            Iterator iterator = parameterMap.keySet().iterator();
//            String key = null;
//            String value = null;
//            while (iterator.hasNext()) {
//                key = (String)iterator.next();
//                if (parameterMap.get(key) != null) {
//                    value = (String)parameterMap.get(key);
//                } else {
//                    value = "";
//                }
//                
//                parameterBuffer.append(key).append("=").append(value);
//                if (iterator.hasNext()) {
//                    parameterBuffer.append("&");
//                }
//            }
//        }
		StringBuilder params = new StringBuilder();
		params.append("&sn=").append(REQUEST_SN)
			.append("&pwd=").append(REQUEST_PWD_MD5)
			.append("&mobile=").append(phone)
//			.append("&content=").append(URLEncoder.encode(content + REQUEST_SIGN))
			.append("&content=").append(URLEncoder.encode(content + REQUEST_SIGN, CHARSET))
			.append(REQUEST_OTHERS);
		System.out.println("短信post参数：" + params.toString());
        
        URL localURL = new URL(REQUEST_DOMAIN);
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(params.toString());
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return resultBuffer.toString();
	}
	
	/**
	 * 创建连接
	 * @param localURL
	 * @return
	 * @throws IOException
	 */
	private URLConnection openConnection(URL localURL) throws IOException {
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} else {
			connection = localURL.openConnection();
		}
		return connection;
	}
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		
		final String phone = "13501320540";
//		final String phone = "13801576362";
		final String content = "测试短信网关是否可用，dax。";
		
		TestMsg msg = new TestMsg();
		try {
			msg.sendMsg(phone, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " end.");
	}
}
