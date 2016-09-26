package com.seongmin.test.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;

public class SourceFindAnotherClassPathTest {

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		File dir = new File("working/classloadertest/com/seongmin/test/classloader");
		dir.mkdirs();

		copyFile(new File("target/classes/com/seongmin/test/classloader/SerializationClass.class")
			, new File(dir, "SerializationClass.class"));

//		removeFile(new File("bin/classloader/SerializationClass.class"));
		
		String path = "working/classloadertest";
		

		// load class files in directory

		ArrayList<URL> urls = new ArrayList<URL>();

		URLStreamHandler streamHandler = null;
		File classPath = new File(path);
		urls.add(new URL(null, "file:" + classPath.getCanonicalPath() + File.separator, streamHandler));

		// load jar files
		File[] files = classPath.listFiles();
		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".jar")) {
				urls.add(new URL(null, "file:" + file.getCanonicalPath(), streamHandler));
			}
		}

		URLClassLoader loader = new URLClassLoader((URL[]) urls.toArray(new URL[urls.size()]));

		Class clazz = null;
		try {
			clazz = loader.loadClass("com.seongmin.test.classloader.SerializationClass");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println(clazz.getSimpleName());

		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		System.out.println(object.toString());
		ClassLoader cl = SourceFindAnotherClassPathTest.class.getClassLoader();
		
		try {
			Class clazz1 = cl.loadClass("com.seongmin.test.classloader.SerializationClass");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("testdddd");
		// String[] classNames = { "test.classloader.SampleClassLoader",
		// "test.classloader.SamplePrototype" };
		//
		// for (String className : classNames)
		//
		// {
		//
		// Class clazz = loader.loadClass(className);
		//
		// Object obj = clazz.newInstance();
		//
		// SamplePrototype sample = (SamplePrototype) obj;
		//
		// sample.desc();
		//
		// System.out.println();
		//
		// }

	}
	
	

	public static void removeFile(File target) throws IOException {

		if (target == null) {
			return;
		}
		if (!target.exists()) {
			return;
		}

		if (target.isDirectory()) {
			File[] subPathes = target.listFiles();
			for (int i = 0; i < subPathes.length; i++) {
				removeFile(subPathes[i]);
			}
		}

		if (!target.delete()) {
			throw new IOException("removing " + target + " failed.");
		}

	}

	// copy from http://www.exampledepot.com/egs/java.io/CopyFile.html
	public static void copyFile(File src, File dst) throws IOException {
		if (src == null) {
			throw new IOException("source file is null");
		}
		if (!src.exists()) {
			throw new IOException("source file is not exsits");
		}

		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

}
