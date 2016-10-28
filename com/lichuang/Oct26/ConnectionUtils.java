package com.lichuang.Oct26;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionUtils {
	
	/**
	 * 1.HTTP请求之GET请求
	 * @throws MalformedURLException 
	 * 
	 */
	public static String doGet() throws Exception{
		
		URL localURL = new URL("");
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection=(HttpURLConnection)connection;
		
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;       
        InputStream inputStream = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }
		
		return resultBuffer.toString();
	}

}
