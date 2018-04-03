package com.panda.test.pay.baofoo.util;

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

import net.sf.json.JSONObject;

/***
 * http 请求
 * 
 * @author 仵作
 *
 */
public class RequestMethod {

	private String proxyHost = null;
	private Integer proxyPort = null;
	
	/**
	 * get 方式
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String methodGet(String url) throws Exception {
		if ("".equals(url.trim())) {
			throw new Exception("url 为空。");
		}
		
		URL localURL = new URL(url.toString());
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", Constants.ENCODE_TYPE_UTF8);
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
	public String methodPost(String url) throws Exception {
		if ("".equals(url.trim())) {
			throw new Exception("url 为空。");
		}
		
		String[] arr = url.split("\\?");
		String domain = arr[0];
		String params = arr[1] == null ? "" : arr[1];
		
        URL localURL = new URL(domain);
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", Constants.ENCODE_TYPE_UTF8);
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
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
		String domain = Constants.URL_DOMAIN;
//		String params = "amount=1.10&bank_code=BOCSH_D_B2C&card_bind_mobile_phone_no=13888888888&card_no=6217853560025230060&cert_no=420621199009023449&cert_type=01&customer_id=9bef20ab570340788fb8ba6e92b74685&input_charset=UTF-8&out_trade_no=20151009151916-23064&partner=201508191714122264&real_name=张三&service=ebatong_mp_dyncode&sign_type=MD52XYEF5RDNQ0U7H25WWSHM3IF8YK0YVvgyftw";
//		try {
//			RequestMethod req = new RequestMethod();
//			String result = req.methodPost(Constants.URL_DOMAIN + "?" + params);
//
//			System.out.println("result : " + result);
//			JSONObject resJson = JSONObject.fromObject(result);
//			String rr = resJson.getString("result");
//			System.out.println("resJosn.result = " + rr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName() + " begin.");
	}

}
