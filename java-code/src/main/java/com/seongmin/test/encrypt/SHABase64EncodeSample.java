package com.seongmin.test.encrypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SHABase64EncodeSample {
	

/*
 
 SHA-256 is a cryptographic (one-way) hash function, 
 so there is no direct way to decode it. 
 The entire purpose of a cryptographic hash function is that you can't undo it.

One thing you can do is a brute-force strategy (무차별 대입 전략)

*/

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		String password = "test";
		String encryptedPassword = new SHABase64EncodeSample().encode(password);
		
		System.out.println(encryptedPassword);
		
	}
	
	private static final int	ITERATION_COUNT	= 0;

	String saltChars = "salt..";
	
	public synchronized String encode(String password) throws NoSuchAlgorithmException, IOException {

		String encodedPassword = null;
		
		byte[] salt = base64ToByte(saltChars);

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);

		byte[] btPass = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < ITERATION_COUNT; i++) {
			digest.reset();
			btPass = digest.digest(btPass);
		}

		encodedPassword = byteToBase64(btPass);
		return encodedPassword;
	}

	private byte[] base64ToByte(String str) throws IOException {

		BASE64Decoder decoder = new BASE64Decoder();
		byte[] returnbyteArray = decoder.decodeBuffer(str);

		return returnbyteArray;
	}

	private String byteToBase64(byte[] bt) {

		BASE64Encoder endecoder = new BASE64Encoder();
		String returnString = endecoder.encode(bt);

		return returnString;
	}

}
