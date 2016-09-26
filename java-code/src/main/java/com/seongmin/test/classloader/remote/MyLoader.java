package com.seongmin.test.classloader.remote;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyLoader {
	public static void main(String[] args) throws Exception {

		// java remoteclassload.MyLoader remoteclassload.Tester
		
		URLClassLoader loader = new URLClassLoader(
					new URL[] { new URL("http://localhost:8080/examples/classes/") }
				);
		
		// args0 = remoteclassload.Tester
		
//		Class c = loader.loadClass(args[0]);
		
		Class c = loader.loadClass("com.seongmin.test.classloader.remote.Tester");

		Object o = c.newInstance();
		
	}
}
