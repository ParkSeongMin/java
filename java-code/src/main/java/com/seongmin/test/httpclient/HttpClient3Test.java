package com.seongmin.test.httpclient;

import org.apache.commons.httpclient.URI;

public class HttpClient3Test {

	public static void main(String[] args) throws Exception {
		
		String url = "https://172.10.2.117:28080/test";
//		String url = "http://172.10.2.117:28080/FrontControllerServlet.do";
//		String url = "http://localhost:8012/xup/FrontControllerServlet.do";
//		String url = "http://172.10.2.117:28080/xup";
//		String url = "http://tobesoft.com/xup/service";
		traceUrlInfo(url);
	}
	
	public static void generateUrl(String host, int port, String subPath) {
		
		String url = "http://"+host+":"+port+"/"+subPath;
//		traceUrlInfo(url);
	}
	
	public static void traceUrlInfo(String url) throws Exception {
		
		boolean encaped = true;
		URI uri = new URI(url, encaped);
		
		System.out.println("getAboveHierPath=" + uri.getAboveHierPath());
		System.out.println("getAuthority=" + uri.getAuthority());
		System.out.println("getCurrentHierPath=" + uri.getCurrentHierPath());
		System.out.println("getEscapedAboveHierPath=" + uri.getEscapedAboveHierPath());
		System.out.println("getEscapedAuthority=" + uri.getEscapedAuthority());
		System.out.println("getEscapedCurrentHierPath=" + uri.getEscapedCurrentHierPath());
		System.out.println("getEscapedFragment=" + uri.getEscapedFragment());
		System.out.println("getEscapedName=" + uri.getEscapedName());
		System.out.println("getEscapedPath=" + uri.getEscapedPath());
		System.out.println("getEscapedPathQuery=" + uri.getEscapedPathQuery());
		System.out.println("getEscapedQuery=" + uri.getEscapedQuery());
		System.out.println("getEscapedURI=" + uri.getEscapedURI());
		System.out.println("getEscapedURIReference=" + uri.getEscapedURIReference());
		System.out.println("getEscapedUserinfo=" + uri.getEscapedUserinfo());
		System.out.println("getFragment=" + uri.getFragment());
		System.out.println("getHost=" + uri.getHost());
		System.out.println("getName=" + uri.getName());
		System.out.println("getPath=" + uri.getPath());
		System.out.println("getPathQuery=" + uri.getPathQuery());
		System.out.println("getPort=" + uri.getPort());
		System.out.println("getProtocolCharset=" + uri.getProtocolCharset());
		System.out.println("getQuery=" + uri.getQuery());
		System.out.println("getScheme=" + uri.getScheme());
		System.out.println("getURI=" + uri.getURI());
		System.out.println("getURIReference=" + uri.getURIReference());
		System.out.println("getUserinfo=" + uri.getUserinfo());
		
		
		System.out.println("getRawAboveHierPath=" + new String(uri.getRawAboveHierPath()));
		System.out.println("getRawAuthority=" + new String(uri.getRawAuthority()));
		System.out.println("getRawCurrentHierPath=" + new String(uri.getRawCurrentHierPath()));
		System.out.println("getRawFragment=" + new String(uri.getRawFragment()));
		System.out.println("getRawHost=" + new String(uri.getRawHost()));
		System.out.println("getRawName=" + new String(uri.getRawName()));
		System.out.println("getRawPath=" + new String(uri.getRawPath()));
		System.out.println("getRawPathQuery=" + new String(uri.getRawPathQuery()));
		System.out.println("getRawQuery=" + new String(uri.getRawQuery()));
		System.out.println("getRawScheme=" + new String(uri.getRawScheme()));
		System.out.println("getRawURI=" + new String(uri.getRawURI()));
		System.out.println("getRawURIReference=" + new String(uri.getRawURIReference()));
		System.out.println("getRawUserinfo=" + new String(uri.getRawUserinfo()));
		System.out.println("=" + uri);
		System.out.println("=" + uri);
		
		
	}
	
}
