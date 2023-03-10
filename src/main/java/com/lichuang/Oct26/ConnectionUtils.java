package com.lichuang.Oct26;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectionUtils {
	public static void main(String[] args) throws Exception {
		/* Post Request */
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("username", "Nick Huang");
        dataMap.put("blog", "IT");
        System.out.println(ConnectionUtils.doPost("http://localhost:8080/OneHttpServer/", dataMap));
        
        /* Get Request */
        System.out.println(ConnectionUtils.doGet("http://localhost:8080/OneHttpServer/"));
	}

	/**
	 * 1.HTTP请求之GET请求
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public static String doGet(String url) throws Exception {
		// 1.通过统一资源定位器(URL)，获取连接器(URLConnection)
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		// 2.设置请求的参数
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
		}

		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		// 3.发送请求
		InputStream inputStream = httpURLConnection.getInputStream();
		// 4.以输入流的形式获取返回内容
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		while ((tempLine = reader.readLine()) != null) {
			resultBuffer.append(tempLine);
		}
		// 5.关闭输入流
		if (reader != null) {
			reader.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		return resultBuffer.toString();
	}

	/**
	 * 1.HTTP请求之POST请求
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public static String doPost(String url,Map parameterMap) throws Exception {
		/* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String)iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String)parameterMap.get(key);
                } else {
                    value = "";
                }
                
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
		//String parameterData = "username=nickhuang&blog=http://www.cnblogs.com/nick-huang/";

		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterBuffer.length()));

		OutputStream outputStream = httpURLConnection.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream);

		outputStreamWriter.write(parameterBuffer.toString());
		outputStreamWriter.flush();

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
		}

		InputStream inputStream = httpURLConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);

		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		while ((tempLine = reader.readLine()) != null) {
			resultBuffer.append(tempLine);
		}

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
		return resultBuffer.toString();

	}

}

