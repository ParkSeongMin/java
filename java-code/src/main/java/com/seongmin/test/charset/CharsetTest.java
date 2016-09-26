package com.seongmin.test.charset;


public class CharsetTest {

	public static void main(String[] args) throws Exception {
		
		byte[] strs = null;
		
		String name = new String("박성민");
		strs = name.getBytes();
		System.out.println("Length : " + strs.length);
		System.out.println("Hex    : " + BinToHex(strs));
		System.out.println("Value  : " + new String(strs));
		System.out.println();
		
		strs = name.getBytes("utf-8");
		System.out.println("Length : " + strs.length);
		System.out.println("Hex    : " + BinToHex(strs));
		System.out.println("Value  : " + new String(strs, "utf-8"));
		System.out.println();
		
		name = new String(strs, "utf-8");
		strs = name.getBytes();
		System.out.println("Length : " + strs.length);
		System.out.println("Hex    : " + BinToHex(strs));
		System.out.println("Value  : " + name);
		System.out.println();
		
		// 여기서 부터 깨진다.
		byte[] tmp = name.getBytes("euc-kr");
		String convert = new String(tmp, "utf-8");
		System.out.println("Length : " + tmp.length);
		System.out.println("Hex    : " + BinToHex(tmp));
		System.out.println("Value  : " + new String(tmp, "euc-kr"));
		System.out.println("Value  : " + new String(tmp));
		System.out.println("convertValue  : " + convert);
		System.out.println();
		
		strs = convert.getBytes();
		System.out.println("Length : " + strs.length);
		System.out.println("euc-kr Hex    : " + BinToHex(strs));
		
		strs = convert.getBytes("utf-8");
		System.out.println("Length : " + strs.length);
		System.out.println("utf-8 Hex    : " + BinToHex(strs));
		System.out.println("Value  : " + new String(strs));
		System.out.println();
	}

	public static String BinToHex(byte[] buf) {
		String res = "";
		String token = "";
		for (int ix = 0; ix < buf.length; ix++) {
			token = Integer.toHexString(buf[ix]);
			// CommonUtil.println("[" + ix + "] token value : " + token +
			// " len : " + token.length());
			if (token.length() >= 2)
				token = token.substring(token.length() - 2);
			else {
				for (int jx = 0; jx < 2 - token.length(); jx++)
					token = "0" + token;
			}
			res += " " + token;
		}

		return res.toUpperCase();
	}

}
