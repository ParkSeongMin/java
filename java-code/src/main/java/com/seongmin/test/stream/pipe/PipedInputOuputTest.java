package com.seongmin.test.stream.pipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedInputOuputTest {

	public static void main(String[] args) {
		try {
			int ch;
			while (true) {
				PipedInputStream writeIn = new PipedInputStream();

				// PipedInputStream snk에 연결하는 PipedOutputStream을 생성(아래)
				PipedOutputStream readOut = new PipedOutputStream(writeIn);

				ReadThread rt = new ReadThread(System.in, readOut);
				ReadThread wt = new ReadThread(writeIn, System.out);

				rt.start();
				wt.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

class ReadThread extends Thread {
	InputStream		pi	= null;
	OutputStream	po	= null;

	ReadThread(InputStream pi, OutputStream po) {
		this.pi = pi;
		this.po = po;
	}

	public void run() {
		int ch;
		byte[] buffer = new byte[512];
		int bytes_read;

		try {
			for (;;) {
				bytes_read = pi.read(buffer);
				if (bytes_read == -1) {
					return;
				}
				po.write(buffer, 0, bytes_read);
			}
		} catch (Exception e) {

		}
	}
}