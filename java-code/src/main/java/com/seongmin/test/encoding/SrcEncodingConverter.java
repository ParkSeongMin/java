package com.seongmin.test.encoding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java 소스의 인코딩을 일괄 변경한다
 * 
 * 사용법
 * args[0] : 소스 input 폴더
 * args[1] : 변경소스 output 폴더
 * args[2] : 파일패턴 정규식
 * args[3] : 현재 Encoding
 * args[4] : 바꿀 Encoding
 * 
 * Ex) java -jar kr.innofactory.util.SrcEncodingConverter C:\work\workspace\Myproject\src c:\temp\src .*\.java MS949 8859_1
 * 
 * @author gryu@innofactory.kr
 *
 */
public class SrcEncodingConverter {
	
	/** 입력 디렉토리 */
	File inputDir;
	/** 출력 디렉토리 */
	File outputDir;
	/** 파일 패턴 */
	Pattern filePattern;
	/** 기존 인코딩 */
	String oldEncoding;
	/** 신규 인코딩 */
	String newEncoding;
	
	/**
	 * 생성자
	 * @param inputDirPath
	 * @param outputDirPath
	 * @param filePattern
	 * @param oldEncoding
	 * @param newEncoding
	 */
	public SrcEncodingConverter(String inputDirPath, 
			String outputDirPath, String filePattern, String oldEncoding, String newEncoding) {
		this.inputDir = new File(inputDirPath);
		this.outputDir = new File(outputDirPath);
		this.filePattern = Pattern.compile(filePattern, Pattern.CASE_INSENSITIVE);
		this.oldEncoding = oldEncoding;
		this.newEncoding = newEncoding;
	}
	
	/**
	 * 변환 실행
	 */
	public void execute() {
		// #. 재귀적으로 대상 파일 가져오기
		List<File> inputFiles = getAllFiles(inputDir);
		
		for (int i = 0; i < inputFiles.size(); i++) {
			File inputFile = inputFiles.get(i);
			File outputFile = getOuputFile(inputFile);
			try {
				outputFile = convertSrcFile(inputFile, outputFile);
				System.out.println("[" + (i + 1) + "]" + this.oldEncoding + "-->" + this.newEncoding + " OK... " + outputFile.getAbsolutePath() + " [" + outputFile.length() + " byte]");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 전체 파일목록을 얻는다
	 * @return
	 */
	private List<File> getAllFiles(File dir) {
		ArrayList<File> allFiles = new ArrayList<File>();
		
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				allFiles.addAll(getAllFiles(file));
			} else {
				// #. 파일명이 패턴에 맞으면 추가한다
				Matcher m = filePattern.matcher(file.getName());
				if (m.matches()) {
					allFiles.add(file);
				}
			}
		}
		return allFiles;
	}
	
	/**
	 * outputDir 하위에 저장될 outputFile 객체를 얻는다 
	 * @param inputFile
	 * @return
	 */
	private File getOuputFile(File inputFile) {
		String outputDirPath = this.outputDir.getAbsolutePath();
		String inputDirPath = this.inputDir.getAbsolutePath();
		String inputFilePath = inputFile.getAbsolutePath();
		
		String relativePath = inputFilePath.substring(inputDirPath.length());
		String outputFilePath = outputDirPath + relativePath;
		
		return new File(outputFilePath);
	}
	
	/**
	 * 파일 변환을 실행한다
	 * @param inputFile
	 * @param outputFile
	 * @return 출력파일
	 * @throws IOException
	 */
	private File convertSrcFile(File inputFile, File outputFile) throws IOException {
		
		// #. output 디렉토리 존재여부
		File parentDir = outputFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		// #. 파일 변환
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			if (this.oldEncoding != null) {
				reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(inputFile), this.oldEncoding));
			} else {
				reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(inputFile)));
			}
			if (this.newEncoding != null) {
				writer = new PrintWriter(
						new OutputStreamWriter(new FileOutputStream(outputFile), this.newEncoding));
			} else {
				writer = new PrintWriter(
						new OutputStreamWriter(new FileOutputStream(outputFile)));
			}
			
			String line;
			while ((line = reader.readLine()) != null) {
				writer.println(line);
			}
		} finally {
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		
		return outputFile;
	}
	
	/**
	 * 메인 메쏘드
	 * @param args
	 */
	public static void main(String[] args) {
		String inputDirPath = args[0]; 
		String outputDirPath = args[1];
		String filePattern = args[2];
		String oldEncoding = args[3];
		if (oldEncoding.equalsIgnoreCase("null")) {
			oldEncoding = null;
		}
		String newEncoding = args[4];
		if (newEncoding.equalsIgnoreCase("null")) {
			newEncoding = null;
		}
		
		SrcEncodingConverter converter = new SrcEncodingConverter(
				inputDirPath, outputDirPath, filePattern, oldEncoding, newEncoding);
		converter.execute();
	}

}
