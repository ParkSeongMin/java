package com.seongmin.test.instrument.objectsize;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.datatype.PlatformDataType;

public class ObjectSizeTest {
	
	/*
	 * D:\seongmin\java-code\target>jar cvfm test.jar objectsize/MANIFEST.MF -c objectsize/
	 * 
	 * D:\seongmin\java-code\target>java -ea -javaagent:test.jar -jar test.jar
	 * 
	 */
	
	// bin>java -cp ".;..\lib\xplatform-xapi-1.0.jar;..\lib\commons-logging-api-1.1.jar;..\lib\SizeOf.jar;..\lib\h2-1.3.155.jar" -Xms1G -Xmx1G -javaagent:..\lib\SizeOf.jar DataSetSizeTest 
	
	
	private static Instrumentation	inst;

	public static void premain(String options, Instrumentation inst) {
		ObjectSizeTest.inst = inst;
	}

	public static long sizeOf(Object o) {
		assert inst != null;
		return inst.getObjectSize(o);
	}

	public static void main(String[] args) {

//		System.out.println("Size of Object: " + sizeOf(new byte[1]));
//		System.out.println("Size of Object9: " + sizeOf(new byte[9]));
//		System.out.println("Size of Object10: " + sizeOf(new byte[17]));
//		System.out.println("Size of Object: " + sizeOf(new byte[25]));
//		System.out.println("Size of Object: " + sizeOf(new byte[33]));
//		System.out.println("Size of Object: " + sizeOf(new byte[41]));
//		List<String> al = new ArrayList<String>();
//		al.add("000000000");
//		System.out.println("Size of arrayList: " + sizeOf(new byte[41]));
		
		// System.out.println("Size of Object: " + sizeOf(new Object()));
		// System.out.println("Size of String: " + sizeOf(new String()));
		// System.out.println("Size of String111: " + sizeOf(new
		// String("111.111.111.111")));
		// System.out.println("Size of StringBuffer: " + sizeOf(new String()));
		// System.out.println("Size of HashMap: " + sizeOf(new HashMap()));
		// System.out.println("Size of direct subclass: " + sizeOf(new
		// ObjectSizeTest()));
		// System.out.println("Size of Calendar: " +
		// sizeOf(Calendar.getInstance()));

		Runtime r = Runtime.getRuntime();
		
		
		System.out.println("전체 힙 메모리" + r.totalMemory());
		long firstMemorySize = r.freeMemory();
		System.out.println("가용한 힙 메모리" + firstMemorySize);

		DataSet ds1 = new DataSet("ds1");
		ds1.addColumn("Ds1column1", PlatformDataType.STRING);
		ds1.addColumn("Ds1column2", PlatformDataType.STRING);

		int addIndex = 0;

		for (int i = 0; i < 10000; i++) {
			addIndex = ds1.newRow();

			ds1.set(addIndex, 0, "ds1_row1_column1" + i);
			ds1.set(addIndex, 1, "ds1_row1_column2" + i);

		}

		long lastMemorySize = r.freeMemory();
		System.out.println("객체 생성 후 가용한 힙 메모리" + lastMemorySize);
		System.out.println("Object 객체 하나의 메모리 크기는 " + (firstMemorySize - lastMemorySize) / 10000);
	
		/*******************************************************************************************************************
		 ******************************************************************************************************************
		 *******************************************************************************************************************/
		
		System.out.println("전체 힙 메모리" + r.totalMemory());
		firstMemorySize = r.freeMemory();
		System.out.println("가용한 힙 메모리" + firstMemorySize);

		
		DataSet ds2 = new DataSet("ds2");
		ds2.addColumn("Ds1column1", PlatformDataType.STRING);
		ds2.addColumn("Ds1column2", PlatformDataType.STRING);

		addIndex = 0;

		for (int i = 0; i < 100000; i++) {
			addIndex = ds2.newRow();

			ds2.set(addIndex, 0, "ds1_row1_column1" + i);
			ds2.set(addIndex, 1, "ds1_row1_column2" + i);

		}
		
//		System.out.println(sizeOf(ds2));
		lastMemorySize = r.freeMemory();
		System.out.println("객체 생성 후 가용한 힙 메모리" + lastMemorySize);
		System.out.println("Object 객체 하나의 메모리 크기는 " + (firstMemorySize - lastMemorySize) / 10000);

		

	}
}