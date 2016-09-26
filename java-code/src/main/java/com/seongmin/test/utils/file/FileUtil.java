package com.seongmin.test.utils.file;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtil {

	public static String getLastPathletName(String path) {
		
		if(path==null) { return null; }
		
		String[] pathlets = getPathlets(path);
		if(pathlets==null) { return null; }
		
		return pathlets[pathlets.length-1];
		
	}
	
	public static String[] getPathlets(String path) {
		if(path==null) { return null; }
		File file = new File(path);
		String fullPath = file.getAbsolutePath();
		fullPath = fullPath.replaceAll("\\\\", "/");
		String[] pathlets = fullPath.split("/");
		
		return pathlets;
		
	}
	
	

	public static String getCurrentPath() {
		File file = new File(".");
		return file.getAbsolutePath();
	}

	
	// removeFile이나 removeDirectory나 사실 처리하는 것은 차이가 없다.
	// 단지 메소드 이름이 뚜렷하도록 했을 뿐이다.
	public static void removeFile(String targetPath) throws IOException {
		removeDirectory(new File(targetPath));
	}
	
	public static void removeDirectory(String targetPath) throws IOException {
		removeDirectory(new File(targetPath));
	}
	
	public static void removeFile(File target) throws IOException {
		removeDirectory(target);
	}
	
	public static void removeDirectory(File target) throws IOException {
		
    	if(target==null) { return; }
    	if(!target.exists()) { return ; }
    	
    	if(target.isDirectory()) {
    		File[] subPathes = target.listFiles();
    		for(int i=0; i<subPathes.length; i++) {
    			removeDirectory(subPathes[i]);
    		}
    	} 
    	if(!target.delete()) {
    		throw new IOException("removing "+target+" failed.");
    	}
    	
	}
	
	public static void moveDirectory(String srcDirPath, String dstDirPath) throws IOException {
		copyDirectory(srcDirPath, dstDirPath);
		removeDirectory(srcDirPath);
	}
	
	public static void moveDirectory(File srcDir, File dstDir) throws IOException {
		copyDirectory(srcDir, dstDir);
		removeDirectory(srcDir);
	}
	
	public static void copyDirectory(String srcDirPath, String dstDirPath) throws IOException {
		copyDirectory(new File(srcDirPath), new File(dstDirPath));
	}
	
	// copy from http://www.exampledepot.com/egs/java.io/CopyDir.html
    public static void copyDirectory(File srcDir, File dstDir) throws IOException {
    	
    	if(srcDir==null) { 
    		throw new IOException("source directory is null");
    	}
    	if(!srcDir.exists()) {
    		throw new IOException("source directory is not exsits. directory="+srcDir.getAbsolutePath());
    	}
    	
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdirs();
            }
    
            String[] children = srcDir.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
            }
        } else {
            // This method is implemented in e1071 Copying a File
            copyFile(srcDir, dstDir);
        }
        
    }
    
    // copy from http://www.exampledepot.com/egs/java.io/CopyFile.html
    public static void copyFile(File src, File dst) throws IOException {
    	if(src==null) { 
    		throw new IOException("source file is null");
    	}
    	if(!src.exists()) {
    		throw new IOException("source file is not exsits");
    	}
    	
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static String getTempPath() {
    	
		return System.getProperty("java.io.tmpdir");
    	
    }
    
    
    public static boolean isFileUsed(File file) {
    	
    	if(file == null) { return false; }
    	
    	if(file.isHidden()) {
			return false;
		}
		else if(!file.exists()) {
			return false;
		}
		else if(!file.canRead()) {
			return false;
		}
    	
    	return true;
    	
    }
    
    public static byte[] getBytes(File file) throws IOException {
    	
    	byte[] dataBytes = new byte[(int)file.length()];
		
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			
			in.read(dataBytes);
			
		} catch (FileNotFoundException e) {
			throw new IOException("Byte in the file can not read.");
		} catch (IOException e) {
			throw new IOException("Byte in the file can not read.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new IOException("failed close inputStream");
				}
			}
		}
		
		return dataBytes;
    }
    
    public static String getUniqueFileName(String prefix, String suffix, File directory) throws IOException {
    	
    	if(directory == null) {
			throw new IOException("source file is null");
		}
    	
    	File f = null;
		try {
			f = File.createTempFile(prefix,suffix,directory);
		} catch (IOException e) {
			throw new IOException("source file is not directory");
		}
		if(!f.exists()) {
			throw new IOException("create unique file name failed..");
		}
    	String filename = f.getName();
    	f.delete();
    	
    	return filename;
    }
    
    
    /**
     * file의 확장자
     * @param path
     * @return string 확장자.
     */
    public static String getFileExtension(String path) {
    	String ext = "";
    	int i = path.lastIndexOf('.');
    	if (i > 0 &&  i < path.length() - 1) {
    		ext = path.substring(i + 1).toLowerCase();
    	}
    	return ext;
    }
    
    
    
    /**
     * 디렉토리 하위의 파일 갯수를 가져온다.
     * 
     * @param f
     * @param totalCount 최초 0
     * @return
     */
    public static int getDirectoryFileCount(File f, int totalCount) {
    	if(f.isDirectory()) {
    		String[] list = f.list();
    		for(int i=0 ; i<list.length ; i++) {
    			totalCount = getDirectoryFileCount(new File(f, list[i]), totalCount);
    		}
    	} else {
    		totalCount++;
    	}
    	return totalCount;
    }
    
    public static long getDirectoryFileSize(File f, long totalSize) {
    	if(f.isDirectory()) {
    		String[] list = f.list();
    		for(int i=0 ; i<list.length ; i++) {
    			totalSize = getDirectoryFileSize(new File(f, list[i]), totalSize);
    		}
    	} else {
    		totalSize += f.length();
    	}
    	return totalSize;
    }


    
    
}
