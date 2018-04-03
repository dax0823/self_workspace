package com.pandadai.common.utils;

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


public class SendMsgUtil {

	// 短信网关地址
//	private final static String REQUEST_DOMIAN = PropertiesUtil.getInstance().getMapValue("REQUEST_DOMIAN");
	private final static String REQUEST_DOMIAN = "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend?";
	// "http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend?";
	// sn
//	private final static String REQUEST_SN = PropertiesUtil.getInstance().getMapValue("REQUEST_SN");
	private final static String REQUEST_SN = "SDK-WSS-010-06757";
	// "SDK-WSS-010-06757";
	// 密码
	// private final static String REQUEST_PWD = "";
	// 加密后的密码
//	private final static String REQUEST_PWD_MD5 = PropertiesUtil.getInstance().getMapValue("REQUEST_PWD_MD5");
	private final static String REQUEST_PWD_MD5 = "B1D9013EC249BB225A9C01C2FBBA6B09";
	// "B1D9013EC249BB225A9C01C2FBBA6B09";
	// 商户签名，追加在发送内容最后，必加，否则发送无效
//	private final static String REQUEST_SIGN = PropertiesUtil.getInstance().getMapValue("REQUEST_SIGN");
	private final static String REQUEST_SIGN = "【熊猫贷】";
	// "【熊猫贷】";
	// 其他参数
//	private final static String REQUEST_OTHERS = PropertiesUtil.getInstance().getMapValue("REQUEST_OTHERS");
	private final static String REQUEST_OTHERS = "&ext=&msgfmt=&rrid=&stime=";
	// "&ext=&msgfmt=&rrid=&stime=";

//	private final static String CHARSET = PropertiesUtil.getInstance().getMapValue("CHARSET");
	private final static String CHARSET = "utf-8";
	// "utf-8";

	private String proxyHost = null;
	private Integer proxyPort = null;

	/**
	 * 发送短信，内容自定义
	 * 
	 * @param phone
	 * @param content
	 * @return
	 */
	public boolean sendMsg(String phone, String content) throws Exception {
		String retStr = null;
		// retStr = methodGet(phone, content);
		retStr = methodPost(phone, content);
		return retStr == null ? false : true;
	}

	/**
	 * get 方式
	 * 
	 * @param phone
	 * @param content
	 * @return
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	private String methodGet(String phone, String content) throws Exception {
		// 拼接短信请求url
		StringBuilder url = new StringBuilder();
		url.append(REQUEST_DOMIAN).append("&sn=").append(REQUEST_SN)
				.append("&pwd=").append(REQUEST_PWD_MD5).append("&mobile=")
				.append(phone).append("&content=")
				.append(URLEncoder.encode(content + REQUEST_SIGN))
				.append(REQUEST_OTHERS);

		System.out.println("短信url：" + url.toString());

		URL localURL = new URL(url.toString());
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
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
	 * 
	 * @param phone
	 * @param content
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String methodPost(String phone, String content) throws Exception {
		StringBuilder params = new StringBuilder();
		params.append("&sn=").append(REQUEST_SN).append("&pwd=")
				.append(REQUEST_PWD_MD5).append("&mobile=").append(phone)
				.append("&content=")
				.append(URLEncoder.encode(content + REQUEST_SIGN))
				.append(REQUEST_OTHERS);
//		System.out.println("短信post参数：" + params.toString());

		URL localURL = new URL(REQUEST_DOMIAN);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(params.length()));

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
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ httpURLConnection.getResponseCode());
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
	 * 
	 * @param localURL
	 * @return
	 * @throws IOException
	 */
	private URLConnection openConnection(URL localURL) throws IOException {
		URLConnection connection;
		if (proxyHost != null && proxyPort != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyHost, proxyPort));
			connection = localURL.openConnection(proxy);
		} else {
			connection = localURL.openConnection();
		}
		return connection;
	}
}
