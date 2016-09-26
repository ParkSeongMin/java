package com.seongmin.test.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializerTest {

	public static void main(String[] args) {
//		write();
		read();
	}

	private static void write() {
		try {

			FileOutputStream fos = new FileOutputStream("d:\\test.xml");

			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(new Target("testing", 37));

			oos.flush();

			fos.close();

		}

		catch (Throwable e)

		{

			System.err.println(e);

		}
	}

	private static void read() {
		Target testobj = null;

		try

		{

			FileInputStream fis = new FileInputStream("d:\\test.xml");

			ObjectInputStream ois = new ObjectInputStream(fis);

			testobj = (Target) ois.readObject();

			fis.close();

		}

		catch (Throwable e)

		{

			System.err.println(e);

		}

		System.out.println(testobj.str);

		System.out.println(testobj.ivalue);
		
//		System.out.println(testobj.test);

	}

}
