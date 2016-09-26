package com.seongmin.test.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTest {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = new ServerSocket(8000);
		Socket socket = serverSocket.accept();

		InputStream in = socket.getInputStream();
		int oneInt = -1;
		while ((oneInt = in.read()) != -1) {
			System.out.print((char) oneInt);
		}
		in.close();
	}
}