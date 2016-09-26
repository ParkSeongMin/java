package com.seongmin.test.fileutils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.seongmin.test.utils.file.FileUtil;

public class FileUtilsTest extends TestCase {

	public void testListFiles() {

		Collection files = null;

		IOFileFilter fileFilter = null;
		IOFileFilter dirFilter = null;

		// Second, find recursively
		fileFilter = FileFilterUtils.trueFileFilter();
		dirFilter = FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter("svn"));
		files = FileUtils.listFiles(new File("src/fileutils"), fileFilter, dirFilter);
		
		Collection filenames = filesToFilenames(files);

		// assertTrue("'dummy-build.xml' is missing",
		// filenames.contains("dummy-build.xml"));
		// assertTrue("'dummy-index.html' is missing",
		// filenames.contains("dummy-index.html"));
		// assertFalse("'Entries' shouldn't be found",
		// filenames.contains("Entries"));

		Iterator i = files.iterator();
		while (i.hasNext()) {
			System.out.println(((File) i.next()).getAbsolutePath());
		}

	}

	private static Collection filesToFilenames(Collection files) {
		Collection filenames = new java.util.ArrayList(files.size());
		Iterator i = files.iterator();
		while (i.hasNext()) {
			filenames.add(((File) i.next()).getName());
		}
		return filenames;
	}

	// ///////////////////////////////////////////////////////////////////////////

	// public static String buildWithAnt() {
	//
	// Project project = new Project();
	// ProjectHelper.configureProject(project, new File(buildFile));
	// DefaultLogger consoleLogger = new DefaultLogger();
	// // consoleLogger.setErrorPrintStream(System.err);
	// consoleLogger.setOutputPrintStream(System.out);
	// consoleLogger.setMessageOutputLevel(Project.MSG_DEBUG);
	// // consoleLogger.setEmacsMode(true);
	//
	// project.addBuildListener(consoleLogger);
	// project.init();
	// project.executeTarget(EXECUTE_TARGET);
	//
	// return project.getProperty(OUTPUT_DIR_CORE);
	// }

	public void testBuild() {
		try {
			buildWithCopy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String	DIR_COMPILED_CORE	= "bin";

	public static final String	DIR_SOURCE_CORE		= "ddd";
	public static final String	DIR_RESOURCE_CORE	= "src/main/resource";
	public static final String	DIR_OUTPUT_CORE		= "working/bin";

	public static final String	DIR_DEPENDANCY_LIB	= "dev_dependancy_lib/bundle";

	public static void buildWithCopy() throws IOException {

		
		
		String[] sourceCode = {"aaa", "ddd", "src"};
		
		for(int i=0; i<10; i++) {
			
			for(int j=0; j<sourceCode.length; j++) {
				
				try {
					FileUtil.removeDirectory(DIR_OUTPUT_CORE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				innerBuild(sourceCode[j], DIR_COMPILED_CORE, DIR_OUTPUT_CORE);
				
			}
			
			
		}

		// Find files
//		innerBuild(DIR_SOURCE_CORE, DIR_COMPILED_CORE, DIR_OUTPUT_CORE);
		// innerBuild(DIR_RESOURCE_CORE, DIR_COMPILED_CORE,
		// DIR_OUTPUT_CORE, "");

	}

	private static void innerBuild(String sourceTarget, String compiledTarget, String desc)
			throws IOException {

		File file = new File(sourceTarget);
		File[] found = file.listFiles();

		if (found != null) {
			for (int i = 0; i < found.length; i++) {

				if (found[i].isDirectory()) {

					String tmpAppendTarget = found[i].getName();
					
					String newSourceTarget = sourceTarget + "/" + tmpAppendTarget;
					String newDesc = desc + "/" + tmpAppendTarget;
					String newCompiledTarget = compiledTarget + "/" + tmpAppendTarget;

					innerBuild(newSourceTarget, newCompiledTarget, newDesc);

					// TODO append target 초기화 !!!!!!!!
					
				} else {

					File[] similarFile = findSimilarFile(new File(compiledTarget), found[i].getName());
					// files.add(found[i]);
					if (similarFile != null) {
						// copy file
						try {
							copyFile(similarFile, desc);
						} catch (IOException e) {
							throw e;
						}
					}

				}

			}
		}

	}

	private static File[] findSimilarFile(File target, String compareFileName) {

		if (target == null) {
			return null;
		}

		if (compareFileName == null) {
			return null;
		}

		File[] found = target.listFiles();

		if (found == null) {
			return null;
		}

		List<File> fileList = new ArrayList<File>();

		for (int i = 0; i < found.length; i++) {

			if (found[i].isDirectory()) {
				continue;
			}

			String fileName = found[i].getName();

			String compareFileExtension = FileUtil.getFileExtension(compareFileName);

			if ("java".equals(compareFileExtension)) {
				// .class 파일만 찾도록 한다.
				String fileExtension = FileUtil.getFileExtension(fileName);

				if ("class".equals(fileExtension)) {
					String removedExtensionFileName = fileName.substring(0, fileName.indexOf('.'));
					String removedExtensionCompareFileName = compareFileName.substring(0, compareFileName.indexOf('.'));

					if (removedExtensionFileName.equals(removedExtensionCompareFileName)
							|| removedExtensionFileName.startsWith(removedExtensionCompareFileName + "$")) {
						fileList.add(found[i]);
					}
				}
			} else {
				// 파일 이름 비교 하여 그냥 찾자
				if (compareFileName.equals(fileName)) {
					fileList.add(found[i]);
				}
			}

		}

		File[] files = new File[fileList.size()];
		files = fileList.toArray(files);
		return files;

	}

	private static void copyFile(File[] similarFile, String desc) throws IOException {

		if (similarFile == null) {
			throw new IOException("source directory is null");
		}

		File descDir = new File(desc);

		if (!descDir.exists()) {
			descDir.mkdirs();
		}

		for (int i = 0; i < similarFile.length; i++) {
			String fileName = similarFile[i].getName();
			FileUtil.copyFile(similarFile[i], new File(desc + "/" + fileName));
		}

	}

	
	public void testFindFile() {
		File test = findFile(new File("lib"), "xup-manager.jar");
		System.out.println(test.getAbsolutePath());
	}
	
	
	private File findFile(File startDirectory, String fileName) {
		
		if(startDirectory == null) {
			return null;
		}
		
		if(fileName == null) {
			return null;
		}
		
		if(!startDirectory.isDirectory()) {
			return null;
		}
		
		File[] files = startDirectory.listFiles();
		
		for(int i=0; i<files.length; i++) {
			
			if(fileName.equals(files[i].getName())) {
				return files[i];
			}
			
			if(files[i].isDirectory()) {
				File isFile = findFile(files[i], fileName);
				
				if(isFile != null) {
					return isFile;
				}
			}
		}
		
		return null;
	}
}
