package com.seongmin.test.httpclient;

import java.io.IOException;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;

public class HttpClientContinueRequestTest {

	public static void main(String[] args) {
		
		String data = "";
		String url = "";
		
		Protocol httpsProtocol = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		
		PostMethod method =  new PostMethod(url);
		
		String expectHeaderName = "";
		String expectHeaderValue = "";
		
		method.setRequestHeader(expectHeaderName, expectHeaderValue);
		
		
		
		
		try {

			RequestEntity requestEntity = new ByteArrayRequestEntity(data.getBytes());
			
			method.setRequestEntity(requestEntity);
			// Execute the method.
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("error="+statusCode);
			}
			
		} catch (HTTPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			
		}
		
		
	}
	
}
