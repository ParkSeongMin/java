package com.seongmin.test.aspectj.externallibrary;

import org.aspectj.lang.annotation.Aspect;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.datatype.PlatformDataType;

@Aspect
class XapiConvert {

	//java -cp .;..\lib\xplatform-xapi-1.0.jar;..\lib\aspectj\aspectjrt.jar;..\lib\commons-logging-api-1.1.jar -javaagent:..\lib\aspectj\aspectjweaver.jar aspectj.externallibrary.ExternalLibraryChangeTest
	// 
//	@Before(value = "(call(public * com.tobesoft.xplatform.data.DataSet.*(..)) && within(ExternalLibraryChangeTest))", argNames = "")
//	@Before (value = "call(public * com.tobesoft.xplatform.data.DataSet.*(..)) && within(aspectj.externallibrary.ExternalLibraryChangeTest)")
//	public void beforeAspect() {
//		System.out.println("before ... ");
//	}

//	@Before(value = "(call(* gc(..)))", argNames = "")
//	public void beforeGcAspect() {
//		System.out.println("before gc ... ");
//	}
	
}

public class ExternalLibraryChangeTest {

	public static void main(String[] args) {

		DataSet ds = new DataSet("ds1");
		ds.addColumn("test", PlatformDataType.STRING);
		
//		try {
//			ds.addColumn("test", PlatformDataType.STRING);
//		} catch(java.lang.IllegalStateException e) {
//			System.out.println("catch.. e");
//		}

		int newRow = ds.newRow();

		ds.set(newRow, 0, "data");
		
		System.gc();
		
		ds.newRow();
		ds.set(0, 0, "test");
		ds.startStoreDataChanges();
		ds.getAlias();

	}

}