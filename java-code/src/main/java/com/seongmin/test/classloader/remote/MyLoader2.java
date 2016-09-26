package com.seongmin.test.classloader.remote;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;

public class MyLoader2 {

	public static void main(String argv[]) throws Exception {

		
		// http://localhost:8080/examples/classes/
		
		URLClassLoader loader = new URLClassLoader(
				new URL[] { new URL("http://localhost:8080/examples/classes/")
							, new URL("http://localhost:8080/examples/classes/test.jar")
							}
			);

		// Load class from class loader. argv[0] is the name of the class to be
		// loaded
//		Class c = loader.loadClass(argv[0]);
		Class c = loader.loadClass("com.seongmin.test.classloader.remote.Tester");
		
		System.out.println(c.getClassLoader());

		Method m = c.getMethod("main", new Class[] { argv.getClass() });
		m.setAccessible(true);
		int mods = m.getModifiers();
		if (m.getReturnType() != void.class || !Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
			throw new NoSuchMethodException("main");
		}
		try {
			m.invoke(null, new Object[] { argv });
		} catch (IllegalAccessException e) {
		}
		
		Class z = loader.loadClass("com.seongmin.test.classloader.remote.Tester");
		System.out.println(z);
		
	}
}
