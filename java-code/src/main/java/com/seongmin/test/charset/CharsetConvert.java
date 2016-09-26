package com.seongmin.test.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetConvert {

	public static void main(String[] args) throws Exception{

//		String str = "한글";
//
//		Charset utf8 = lookupCharset("utf-8");
//		byte[] uBytes = str.getBytes(utf8);
//		System.out.println(uBytes.length);
//		String utf8Str = new String(uBytes, utf8);
//		System.out.println(utf8Str);
//
//		Charset euckr = lookupCharset("euc-kr");
//		byte[] eBytes = str.getBytes(euckr);
//		System.out.println(eBytes.length);
//		String euckrStr = new String(eBytes, euckr);
//		System.out.println(euckrStr);
		
		
		// other
		//euc_kr_str - euc-kr 문자열
		String str = "한글";
		byte[] eBytes = str.getBytes("euc-kr");
		CharBuffer cbuffer = CharBuffer.wrap((new String(eBytes, "euc-kr")).toCharArray());
		Charset utf8charset = Charset.forName("UTF-8");
		ByteBuffer bbuffer = utf8charset.encode(cbuffer);
		
		//변환된 UTF-8 문자열
		String tmpDecode = new String(bbuffer.array(), utf8charset);
		System.out.println(tmpDecode);
		
		
	}

	private static Charset lookupCharset(String csn) {
		if (Charset.isSupported(csn)) {
			try {
				return Charset.forName(csn);
			} catch (UnsupportedCharsetException x) {
				throw new Error(x);
			}
		}
		return null;
	}

}
