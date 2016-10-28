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

public class ConnectionUtils {

	/**
	 * 1.HTTP请求之GET请求
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public static String doGet() throws Exception {
		// 1.通过统一资源定位器(URL)，获取连接器(URLConnection)
		URL localURL = new URL("");
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
	public static String doPost() throws Exception {
		String parameterData = "username=nickhuang&blog=http://www.cnblogs.com/nick-huang/";

		URL localURL = new URL("");
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterData.length()));

		OutputStream outputStream = httpURLConnection.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream);

		outputStreamWriter.write(parameterData.toString());
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
