package com.seongmin.test.naveropenapi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;


public class NaverOpenApiTest {
	
	// my key ; b55a65c108699c505e98bc01ceece0ce
	
	protected long timeOut = 30L*1000L;
	protected boolean shouldCompress = false;
	
	public static void main(String[] args) {
		NaverOpenApiTest test = new NaverOpenApiTest();
		
//		test.seatchDone();
//		test.searchCafeArticle();
		
		test.seatchDone();
	}
	
	private void seatchDone() {
		
//		String urls = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=요리&target=cafearticle&start=1&display=10&sort=sim";
		String urls = "http://cafe.naver.com/";
		
		try {
			URL url = new URL(urls);
			URLConnection con = url.openConnection();
			
			InputStream in = con.getInputStream();
			
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
//			String body = IOUtils.toString(in, encoding);
//			System.out.println(body);
			
			readBufferd(in);
			
//			byte[] readBytes = readBytes(in);
//			System.out.println(new String(readBytes));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void searchCafeArticle() {
		
		/*
		
요청 변수	값	설명
key	string (필수)	이용 등록을 통해 받은 key 스트링을 입력합니다.
target	string (필수) : cafearticle	서비스를 위해서는 무조건 지정해야 합니다.
query	string (필수)	검색을 원하는 질의, UTF-8 인코딩 입니다.
display	integer : 기본값 10, 최대 100	검색결과 출력건수를 지정합니다. 최대 100까지 가능합니다.
start	integer : 기본값 1, 최대 1000	검색의 시작위치를 지정할 수 있습니다. 최대 1000까지 가능합니다.
sort	string : sim (기본값), date	정렬 옵션입니다.
sim : 유사도순(기본값)
date : 최신 날짜순
		
		*/
		
		// sample = http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=요리&target=cafearticle&start=1&display=10&sort=sim
		
		String url = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=요리&target=cafearticle&start=1&display=10&sort=sim";
		
		HttpClient httpClient = new HttpClient();
		
		httpClient.getHttpConnectionManager().getParams().setSoTimeout((int)timeOut);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout((int)timeOut);
		PostMethod method = new PostMethod(url);
		
		
		byte[] bodys=null;
		
		try {

//			RequestEntity requestEntity = new ByteArrayRequestEntity(bodys);
//			method.setRequestEntity(requestEntity);
			// Execute the method.
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Error("http request failed. code=" +statusCode+ ", status line = "+method.getStatusLine()); 
			}

			
			InputStream in = method.getResponseBodyAsStream();
			
			String string = IOUtils.toString(in, "UTF-8");
			System.out.println(string);
			
			
		} catch (HttpException e) {
			throw new Error("http request failed. url="+url, e);
		} catch (IOException e) {
			throw new Error("http request failed. url="+url, e);
		} finally {
			method.releaseConnection();
		}
		
		
	}
	
	private void readBufferd(InputStream in) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String s = null;
		while((s=reader.readLine()) != null) {
			System.out.println(s);
		}
		
	}
	
	private byte[] readBytes(InputStream in) throws IOException {
		
		DataInputStream din = null;
		
		if(in instanceof DataInputStream) {
			din = (DataInputStream) in;
		} else {
			din = new DataInputStream(in);
		}
		
		short length = din.readShort();
		
		return readBytes(din, length);
	}

	private byte[] readBytes(DataInputStream in, int count) throws IOException {
		byte[] buffer = new byte[count];
		int offset = 0;

		while (true) {
			int n = in.read(buffer, offset, (count - offset));

			offset += n;

			if (offset == count) {
				break;
			}
		}

		return buffer;
	}
	
}
