package com.seongmin.test.instrument.objectsize;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.datatype.PlatformDataType;

public class DataSetSizeTest {
	
	// http://sizeof.sourceforge.net/
	// bin>java -cp ".;..\lib\xplatform-xapi-1.0.jar;..\lib\commons-logging-api-1.1.jar;..\lib\SizeOf.jar;..\lib\h2-1.3.155.jar" -Xms1G -Xmx1G -javaagent:..\lib\SizeOf.jar DataSetSizeTest
	
	public static void main(String[] args) {
		
		DataSet dataSet1 = getDataSet("ds1", 1500);
		DataSet dataSet2 = getDataSet("ds2", 3000);
		DataSet dataSet3 = getDataSet("ds3", 4500);
		DataSet dataSet4 = getDataSet("ds4", 6000);
		
//		try {
//			Thread.sleep(1000000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
	public static DataSet getDataSet(String dsName, int rowCnt) {
		
		DataSet ds = new DataSet(dsName);
		
		ds.addColumn("col1", PlatformDataType.STRING);
		
		int setIdx = -1;
		for(int i=0; i<rowCnt; i++) {

			setIdx = ds.newRow();
			ds.set(0, 0, "data"+i);
		}
		
		return ds;
	}
	

}
