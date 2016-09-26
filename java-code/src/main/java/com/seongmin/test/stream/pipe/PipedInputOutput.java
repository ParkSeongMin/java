package com.seongmin.test.stream.pipe;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.io.PipedInputStream;

import java.io.PipedOutputStream;

public class PipedInputOutput extends Thread {

	InputStream		is	= null;

	OutputStream	os	= null;

	PipedInputOutput(InputStream is, OutputStream os) {

		this.is = is;

		this.os = os;

	}

	public void run() {

		byte[] buff = new byte[100];

		int i = 0;

		try {

			for (;;) {

				i = is.read(buff);

				if (i == -1) {

					return;

				} else {

					os.write(buff, 0, i);

				}

			}

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	public static void main(String[] args) throws IOException {

		PipedInputStream pis = new PipedInputStream();

		PipedOutputStream pos = new PipedOutputStream(pis);

		PipedInputOutput t1 = new PipedInputOutput(System.in, pos);

		PipedInputOutput t2 = new PipedInputOutput(pis, System.out);

		t1.start();

		t2.start();

	}

}
