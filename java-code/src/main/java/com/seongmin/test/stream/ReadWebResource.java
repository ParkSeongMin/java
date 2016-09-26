package com.seongmin.test.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadWebResource {

	public static void main(String[] args) {
		
		try {
//			URL url = new URL("https://www.google.co.kr/images/nav_logo225.png");
//			URL url = new URL("http://comic.naver.com/webtoon/detail.nhn?titleId=25455&no=370&amp;weekday=tue");
//			URL url = new URL("http://www.docjar.com/html/api/org/springframework/web/servlet/DispatcherServlet.java.html");
			
			StringBuffer sb = new StringBuffer();
			sb.append("http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService/ForecastGrib");
			sb.append("?").append("base_date=").append("20150901");
			sb.append("&").append("base_time=").append("1100");
			sb.append("&").append("nx=").append("1");
			sb.append("&").append("ny=").append("1");
			
			URL url = new URL(sb.toString());

			InputStream inputStream = url.openStream();
			InputStreamReader streamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader reader = new BufferedReader(streamReader);

			String temp = "";

			while ((temp = reader.readLine()) != null) {
				System.out.println(temp);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
