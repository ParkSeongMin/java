package com.seongmin.test.encoding;

import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

public class UnivasalCharsetDetector {
	public static void main(String[] args) throws java.io.IOException {

		System.out.println(System.getProperty("file.encoding"));

		String a = new String("asdflasdio72394u aklsldf'al;aslkje ra9028340923'l4klfjasd".getBytes(), "UTF-8");
		String b = new String("asdflasdklfjasd".getBytes(), "euc-kr");
		
		UniversalDetector detector = new UniversalDetector(new CharsetListener() {
			public void report(String s) {
				System.out.println((new StringBuilder()).append("charset = ").append(s).toString());				
			}
		});
		
		System.out.println(detector.isDone());

		detector.handleData(a.getBytes(), 0, a.length());
		detector.handleData(b.getBytes(), 0, b.length());

		System.out.println(detector.isDone());
		
		detector.dataEnd();
		System.out.println(detector.getDetectedCharset());

		testFile(args);
		
		test();
	}

	public static void testFile(String[] args) throws java.io.IOException {
		byte[] buf = new byte[4096];
		
		
//		String fileName = args[0];
		String fileName = "src/encoding/SrcEncodingConverter.java";
//		String fileName = "conf/manifest.properties";
		
		java.io.FileInputStream fis = new java.io.FileInputStream(fileName);

		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		// (3)
		detector.dataEnd();

		// (4)
		String encoding = detector.getDetectedCharset();
		if (encoding != null) {
			System.out.println("Detected encoding = " + encoding);
		} else {
			System.out.println("No encoding detected.");
		}

		// (5)
		detector.reset();
	}
	
	private static void printIt(String string) {
        System.out.println(string);
        for (int i = 0; i < string.length(); i++) {
            System.out.print(String.format("U+%04X ", string.codePointAt(i)));
        }
        System.out.println();
    }
  
    public static void test() {
        String han = "한";
        
        
        printIt(han);
          
    }
}
