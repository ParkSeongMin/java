package com.seongmin.test.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class SerializationTest extends ClassLoader {
	public static void main(String args[]) {
		try {

			File dir = new File("target/classes");
			;
			
			URLClassLoader loader = new URLClassLoader(new URL[] { dir.toURI().toURL() });
			System.out.println("Loading SerializationClass");
			Class c = loader.loadClass("com.seongmin.test.classloader.SerializationClass");
			System.out.println("Creating an instance of SerializationClass");
			c.newInstance();
			System.out.println("Dereferencing the class loader");
			c = null;
			loader = null;

			System.out.println("Running GC...");
			System.gc();
			System.out.println("Triggering a Javadump");

			Thread.dumpStack();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
