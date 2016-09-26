package com.seongmin.test.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputStreamTest {

	public static void main(String[] args) {

		MemoryByteArrayOutputStream out = new MemoryByteArrayOutputStream();
		try {
			out.write(new String("testetest").getBytes());
			
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Transfers all bytes that can be read from <tt>in</tt> to <tt>out</tt>.
	 * 
	 * @param in
	 *            The InputStream to read data from.
	 * @param out
	 *            The OutputStream to write data to.
	 * @return The total number of bytes transfered.
	 */
	public static final long transfer(InputStream in, OutputStream out) throws IOException {
		long totalBytes = 0;
		int bytesInBuf = 0;
		byte[] buf = new byte[4096];

		while ((bytesInBuf = in.read(buf)) != -1) {
			out.write(buf, 0, bytesInBuf);
			totalBytes += bytesInBuf;
		}

		return totalBytes;
	}

}
