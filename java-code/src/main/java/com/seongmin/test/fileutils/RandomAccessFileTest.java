package com.seongmin.test.fileutils;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {

	RandomAccessFile	file;

	// 파일을 생성한다.
	public void createFile(String filename) {
		try { // 파일관련 예외사항 처리
			file = new RandomAccessFile(filename, "rw");
			// 파일에 정수(4바이트)를 순차적으로 기록한다.
			for (int i = 0; i < 100; i++) {
				file.writeInt(i);
			}
			file.close();
		} catch (IOException ioe) {
			System.out.println("IOException while file creation!");
		}
	}

	// 파일로부터 정수를 읽어 간다.
	public int getData(String filename, int ptr) {
		int value = -1;
		try {
			file = new RandomAccessFile(filename, "r");
			file.seek(ptr * 4); // 정수는 4바이트 차지
			value = file.readInt();
			file.close();
		} catch (IOException ioe) {
			System.out.println("IOException while data reading!");
		}
		return value;
	}

	// 파일의 내용을 수정한다.
	public void updateFile(String filename, int ptr, int value) {
		try {
			file = new RandomAccessFile(filename, "rw");
			file.seek(ptr * 4); // 정수는 4바이트 차지
			file.writeInt(value);
			file.close();
		} catch (IOException ioe) {
			System.out.println("IOException while file updating!");
		}
	}

	public static void main(String[] args) {
		String filename = "RandomFile.txt";
		int ptr, value;
		RandomAccessFileTest rf = new RandomAccessFileTest();

		// 파일을 생성한다.
		rf.createFile(filename);

		// 임의의 위치에서 숫자를 읽는다.
		for (int i = 0; i < 10; i++) {
			ptr = (int) (Math.random() * 100);
			value = rf.getData(filename, ptr);
			System.out.print("- 인덱스: " + ptr);
			System.out.print("\t포인터위치: " + ptr * 4);
			System.out.println("\t값: " + value);
		}

		// 인덱스 10의 값을 읽어서 2를 곱해 저장한 후 다시 읽는다.
		ptr = 10;
		value = rf.getData(filename, ptr);
		System.out.print("\n- 인덱스: " + ptr);
		System.out.print("\t현재 값: " + value);
		rf.updateFile(filename, ptr, value * 2);
		value = rf.getData(filename, ptr);
		System.out.print("\t변경 값: " + value);
	}

}
