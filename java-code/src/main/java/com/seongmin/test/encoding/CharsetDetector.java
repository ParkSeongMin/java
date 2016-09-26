package com.seongmin.test.encoding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

public class CharsetDetector {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		String fileName = "src/encoding/SrcEncodingConverter.java";
////		String fileName = "conf/manifest.properties";
//		
//		// 정상 작동 안함.
//		List<String> textLine = new CharsetDetector().getTextLine(new File(fileName));
////		for(int i=0; i<textLine.size(); i++) {
////			System.out.println(textLine.get(i));
////		}
		
		
		
		String name = new String("jalkjerasdㅁㄴ아러ㅣㅁ작ㄷ");
		byte[] bytes = name.getBytes("utf-8");
		
		
		bytes = name.getBytes("iso-8859-1");
		
		
		String[] charsetsToBeTested = { "MS949", "KSC5601", "EUC-KR", "x-windows-949", "UTF-8", "iso-8859-1" };
		
		
		for (String charsetName : charsetsToBeTested) {
			Charset charset = Charset.forName(charsetName);
			
			CharsetDecoder decoder = charset.newDecoder();
			decoder.reset();
			
			boolean finded = identify(bytes, decoder);
			
			if (finded) {
				System.out.println(" charset=" + charset);
			}
		}
		
		
		
		
	}

	public static List<String> getTextLine(File f) {

		List<String> list = new ArrayList<String>();
		// File f = new File("d:\\TITLE.csv");

		String[] charsetsToBeTested = { "MS949", "KSC5601", "EUC-KR", "x-windows-949", "UTF-8", "iso-8859-1" };

		Charset charset = detectCharset(f, charsetsToBeTested);
		
		System.out.println(charset);
		
		if (charset != null) {
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(f), charset);
				BufferedReader br = new BufferedReader(reader);
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					list.add(line);
				}
				reader.close();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else {
			System.out.println("Unrecognized charset.");
		}
		return list;
	}

	private static Charset detectCharset(File f, String[] charsets) {

		Charset charset = null;

		for (String charsetName : charsets) {
			charset = detectCharset(f, Charset.forName(charsetName));
			if (charset != null) {
				break;
			}
		}

		return charset;
	}

	private static Charset detectCharset(File f, Charset charset) {
		try {
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));

			CharsetDecoder decoder = charset.newDecoder();
			decoder.reset();

			byte[] buffer = new byte[512];
			boolean identified = false;
			while ((input.read(buffer) != -1) && (!identified)) {
				identified = identify(buffer, decoder);
			}

			input.close();

			if (identified) {
				return charset;
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}

	public static boolean identify(byte[] bytes, CharsetDecoder decoder) {
		try {
			decoder.decode(ByteBuffer.wrap(bytes));
		} catch (CharacterCodingException e) {
			return false;
		}
		return true;
	}
}