package com.seongmin.test.fileutils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class DontExecuteTestCaseFileCopyNonSvnFile {

	public static void main(String[] args) {
		
		File srcDir = new File("D:\\seongmin\\java\\src\\test\\java\\task");
		File destDir = new File("D:\\seongmin\\java-test\\src\\test\\java\\task");
		
		IOFileFilter svnFilter = FileFilterUtils.makeSVNAware(null);
		
		System.out.println("file copy start!");
		
		try {
			FileUtils.copyDirectory(srcDir, destDir, svnFilter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("file copy finished..");
	}
}
