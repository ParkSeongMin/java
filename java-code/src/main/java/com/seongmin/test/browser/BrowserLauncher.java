package com.seongmin.test.browser;

import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URI;

import javax.swing.JOptionPane;

public class BrowserLauncher {

	private static final String	errMsg	= "Error attempting to launch web browser";

	public static void main(String[] args) {
		
		String url = "https://www.google.co.kr/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=%EB%B0%95%EC%84%B1%EB%AF%BC";
//		String url = "http://www.naver.com";
		openURL(url);
		
	}
	
	public static void openURL(String url) {

		String osName = System.getProperty("os.name");
		try {

			if (osName.startsWith("Mac OS")) {
				
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
				
			} else if (osName.startsWith("Windows")) {
				
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
				
			} else { // assume Unix or Linux
				
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				
				for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
						browser = browsers[count];
					}
				}
				
				if (browser == null) {
					throw new Exception("Could not find web browser");
				} else {
					Runtime.getRuntime().exec(new String[] { browser, url });
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, errMsg + ":\n" + e.getLocalizedMessage());
		}
	}

	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	
}
