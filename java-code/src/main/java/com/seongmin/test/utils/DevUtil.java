package com.seongmin.test.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.seongmin.test.utils.xml.XmlParseException;
import com.seongmin.test.utils.xml.XmlUtil;


public class DevUtil {

	public static final String DEFAULT_CHARSET = "utf8"; 
	
	private DevUtil() {
	}
	
	public static String getDebugInfo(Object object) {
		StringBuffer sb = new StringBuffer();
		if(object==null) { return "null"; }
		Field[] fields = object.getClass().getFields();
		for(int i=0; i<fields.length; i++) {
			if(Modifier.isPublic(fields[i].getModifiers())) {
				try {
					String fieldName = fields[i].getName();
					Object value = fields[i].get(object);
					sb.append(fieldName).append(" = ").append(value.toString()).append("\n");
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
				
			}
		}
		return sb.toString();
	}
	
	public static void trace() {
		trace("");
	}
	
	public static void trace(String message) {
		Exception e = new Exception("not real Exception. it's just tracing for DEV. message="+message);
		e.printStackTrace(System.err);
	}


	public static String getCurrentPosition() {
		
		Exception e = new Exception("exception for dev tracing.");
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		StackTraceElement callerStack = stackTraceElements[1];
		String className = callerStack.getClassName();
		String methodName = callerStack.getMethodName();
		int lineNumber = callerStack.getLineNumber();
		
		return className+"."+methodName+"() : "+lineNumber;
		
	}
	

	public static void storeAsFile(Document document, String fileName, String charsetName) throws TransformerException, IOException {
		String xmlString = XmlUtil.transformDOM2String(document, charsetName);
		storeAsFile(xmlString, fileName, charsetName);
	}

	
	public static void storeAsFile(String string, String fileName, String charsetName) throws IOException {
		
		byte[] bytes = null;
		if(string==null) {
			bytes = new byte[0];
		}
		else {
			bytes = string.getBytes(charsetName);
		}
		
		storeAsFile(bytes, fileName);
		
	}
	
	public static void appendToFile(String string, String fileName, String charsetName) throws IOException {

		byte[] bytes = null;
		if(string==null) {
			bytes = new byte[0];
		}
		else {
			bytes = string.getBytes(charsetName);
		}
		
		appendToFile(bytes, fileName);
		
	}
	
	public static void storeAsFile(byte[] bytes, String fileName) throws IOException {
		boolean shouldAppend = false;
		doWriteToFile(bytes, fileName, shouldAppend);
	}
	
	public static void appendToFile(byte[] bytes, String fileName) throws IOException {
		boolean shouldAppend = true;
		doWriteToFile(bytes, fileName, shouldAppend);
	}
	
	private static void doWriteToFile(byte[] bytes, String fileName, boolean shouldAppend) throws IOException {
		
		File file = new File(fileName);
		
		if(!shouldAppend) {
			if(file.exists()) {
				file.delete();
			}
		}
		
		File parentPathFile = file.getParentFile();
		if(parentPathFile==null) {
			file = new File("./"+fileName);
			parentPathFile = file.getParentFile();
		}
		if(!parentPathFile.exists()) {
			parentPathFile.mkdirs();
		}

		
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName, shouldAppend));
			bufferedOutputStream.write(bytes);
			bufferedOutputStream.flush();
		} finally {
			if(bufferedOutputStream != null) {
				bufferedOutputStream.close();
			}
		}
	}
	
	
	public static byte[] getResourceAsBytes(String resourceUrl) throws IOException {
		
		InputStream inputStream = null;
		inputStream = getResourceAsInputStream(resourceUrl);

		try {
			return readBytes(inputStream);
		} finally {
			if(inputStream!=null) { try { inputStream.close(); } catch(IOException e) {} }
		}
        
	}
	
	public static Document getResourceAsDocument(Class searchBaseClass, String fileName, String charset) throws IOException, XmlParseException {
		
		String xmlString = getResourceAsString(searchBaseClass, fileName, charset);
		Document document = XmlUtil.transformString2Document(xmlString);
		return document;
	}
	
	public static Document getResourceAsDocument(Object requester, String fileName, String charset) throws IOException, XmlParseException {
		if(requester==null) {
			requester = new DevUtil();
		}
		return getResourceAsDocument(requester.getClass(), fileName, charset);
	}

	public static Document getResourceAsDocument(String fileName, String charset) throws IOException, XmlParseException {
		return getResourceAsDocument(DevUtil.class, fileName, charset);
	}
	
	
	public static String getResourceAsString(String fileName) throws IOException {
		return getResourceAsString(fileName, DEFAULT_CHARSET);
	}

	
	/**
	 * resourceUrl의 파일을 읽어서 String으로 반환한다.
	 * 사용 예 String xml = DevUtil.getResourceAsString("src/test/com/seongmin/util/data/SimpleXml.xml");
	 * @throws IOException 
	 */
	public static String getResourceAsString(String fileName, String charsetName) throws IOException {

		byte[] readBytes = getResourceAsBytes(fileName);
		String resourceString = new String(readBytes, charsetName);
		
		return resourceString;
		
	}

	public static String getResourceAsString(Object requester, String resourceUrl, String charset) throws IOException {

		if(requester==null) {
			requester = new DevUtil();
		}
		
		return getResourceAsString(requester.getClass(), resourceUrl, charset);
		
	}
	
	/**
	 * 전달된 request의 위치를 기준으로 resourceUrl의 파일을 읽어서 String으로 반환한다.
	 * @throws IOException 
	 */
	public static String getResourceAsString(Class searchBaseClass, String resourceUrl, String charsetName) throws IOException {

		InputStream inputStream = null;
		inputStream = getResourceAsInputStream(searchBaseClass, resourceUrl);

		byte[] readBytes = null;
		try {
			readBytes = readBytes(inputStream);
		} finally {
			if(inputStream!=null) { try { inputStream.close(); } catch(IOException e) {} }
		}
		
		String readString = new String(readBytes, charsetName);

		return readString;
	}
	


	// 전달된 requester 클래스의 위치에서 파일을 찾고, 없으면 클래스 패스에서 찾는다. 그리고 직접 파일시스템의 경로로도 찾는다.
	public static InputStream getResourceAsInputStream(Object requesterObject, String resourceUrl) throws IOException {

		if(requesterObject==null) {
			requesterObject = new DevUtil();
		}
		return getResourceAsInputStream(requesterObject.getClass(), resourceUrl);
		
	}
	
	// 전달된 requester 클래스의 위치에서 파일을 찾고, 없으면 클래스 패스에서 찾는다. 그리고 직접 파일시스템의 경로로도 찾는다.
	public static InputStream getResourceAsInputStream(Class searchBaseClass, String resourceUrl) throws IOException {
		
		if(resourceUrl==null) { return null; }
		
		ClassLoader loader = searchBaseClass.getClassLoader();
		if(loader==null) {
			throw new Error("get class loader failed. class = "+searchBaseClass);
		}
		
		InputStream inputStream = null;
		
		if(inputStream==null) {
			File file = new File(resourceUrl);
			if(file.exists()&&file.canRead()) {
				try {
					inputStream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// do nothing
				}
			}
		}
		
		if(inputStream==null) {
			File file = new File("/"+resourceUrl);
			if(file.exists()&&file.canRead()) {
				try {
					inputStream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// do nothing
				}
			}
		}
		

		if(resourceUrl.startsWith("/")&&resourceUrl.length()>0) {
			resourceUrl = resourceUrl.substring(1);
		}
		
		
		if(inputStream==null) {
			inputStream = loader.getResourceAsStream(resourceUrl);
		}
		
		if(inputStream==null) {
			inputStream = loader.getResourceAsStream("/"+resourceUrl);
		}

		if(inputStream==null) {
			inputStream = searchBaseClass.getResourceAsStream(resourceUrl);
		}
		
		if(inputStream==null) {
			inputStream = searchBaseClass.getResourceAsStream("/"+resourceUrl);
		}

		if(inputStream==null) {
			inputStream = DevUtil.class.getResourceAsStream(resourceUrl);
		}
		
		if(inputStream==null) {
			inputStream = DevUtil.class.getResourceAsStream("/"+resourceUrl);
		}

		
		String requesterClassPath = searchBaseClass.getName().replaceAll("\\.", "/");
		int lastSlashIndex = requesterClassPath.lastIndexOf("/");
		if(lastSlashIndex>=0) {
			requesterClassPath = requesterClassPath.substring(0, lastSlashIndex);
			String fullResourceUrl = requesterClassPath+"/"+resourceUrl;

			if(inputStream==null) {
				inputStream = loader.getResourceAsStream(fullResourceUrl);
			}
		}

		if(inputStream==null) {
			throw new IOException("resource loading failed. resource="+new File(resourceUrl).getName());
		}
		
		return inputStream;
		
	}

	/**
	 * 특정 리소스를 InputStream으로 반환한다.
	 * !주의 :반환된 InputStream을 close()하는 것을 잊지 말자.
	 * @param resourceUrl
	 * @return
	 * @throws IOException
	 */
	public static InputStream getResourceAsInputStream(String resourceUrl) throws IOException {
		return getResourceAsInputStream(DevUtil.class, resourceUrl);
	}
	

	
	public static String getResourceNameWhereClassLoaded(Object object) {
		if(object==null) { return null; }
		return getFileNameWhereClassLoaded(object.getClass());
	}
	
	// 넘겨진 클래스를 로딩한 클래스 로더에게서 리소스(파일이름)을 요청하고
	// 그값을 반환한다.
	public static String getFileNameWhereClassLoaded(Class clazz) {
		
		if(clazz==null) { return null; }

		ClassLoader classLoader = clazz.getClassLoader();
		if(classLoader==null) { 
			// bootstrap classloader가 로딩한 경우 getClassLoader()의 값이 null일 수 있다.
			classLoader = DevUtil.class.getClassLoader();
		}
		if(classLoader==null) {
			return null;
		}
		
		String className = clazz.getName();
		String classFileName = className.replace('.', '/') + ".class";
		// /D:/programs/java/j2sdk1.4.2_18/jre/lib/rt.jar!/org/w3c/dom/Document.class
		// 앞에 "/"로 시작해도 File 클래스나 기타 에서 문제 없다.
		String path = classLoader.getResource(classFileName).getPath();

		if(path.startsWith("jar:")) {
			path = path.substring("jar:".length());
		}
		
		if(path.startsWith("file:")) {
			path = path.substring("file:".length());
		}

		int index = path.indexOf("!");
		if(index >= 0) {
			path = path.substring(0, index);
		}

		// jeus 4.2의 경우 다음과 같이 //hostname이 오는 경우가 있다.
		// "//localhost/opt/tmax.co.kr/jeus42/webhome/servlet_home/webapps/web-01/WEB-INF/lib/library-1.2.jar"
		// 삭제하자.
		if(path.startsWith("//")) {
			path = path.substring(path.indexOf("/", 2));
		}

		// 윈도우에서는 " "가 %20%으로 표기된다 치환하자.
		path = path.replaceAll("%20", " ");
		
		return path;
		
	}
	
	// 클래스가 로딩된 파일이 있는 위치를 반환한다.
	public static String getPathWhereClassLoaded(Class clazz) {
		
		String fullPath = getFileNameWhereClassLoaded(clazz);

		int lastSlashIndex = fullPath.lastIndexOf("/");
		return fullPath.substring(0, lastSlashIndex);
		
	}
	
	// 어디선가 주워온 코드. getPathWhereClassLoader()와 같은 기능인것 같은데, 제대로 테스트 되진 않았다.
	// http://www.velocityreviews.com/forums/t135764-how-to-get-the-absolute-path.html
	public static String getAbsolutePath(Class clazz){
		if(clazz==null) { return null; }
		java.security.ProtectionDomain proectionDomain = clazz.getProtectionDomain();
		if (proectionDomain==null ) { return null; }
		java.security.CodeSource codeSource = proectionDomain.getCodeSource();
		if (codeSource==null ) { return null; }
		java.net.URL url = codeSource.getLocation();
		if (url==null ) { return null; }
		java.io.File file = new File( url.getFile() );
		if (file==null) { return null; }

		return file.getAbsolutePath();
	}
	
	/**
	 * DevUtil을 로딩한 클래스로더에 클래스 패스를 추가한다.
	 * 구체적으로 Thread.currentThread().getContextClassLoader()에 추가한다.
	 */
	public static void addClassPath(String path) throws Exception {
		addClassPath(path, Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 * DevUtil을 로딩한 클래스로더에 클래스 패스를 추가한다.
	 * 구체적으로 Thread.currentThread().getContextClassLoader()에 추가한다.
	 */
	public static void addClassPath(URL url) throws Exception {
		addClassPath(url, Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 *  파라매터로 전달되는 class loader에 클래스 패스를 추가한다.
	 */
	public static void addClassPath(String path, ClassLoader classLoader) throws Exception {
		File file = new File(path);
		URL url = file.toURL();
		addClassPath(url, classLoader);
	}
	

	/**
	 *  파라매터로 전달되는 class loader에 클래스 패스를 추가한다.
	 */
	public static void addClassPath(URL url, ClassLoader classLoader) throws Exception {
		if(classLoader==null) {
			classLoader = DevUtil.class.getClassLoader();
		}
		
		if(classLoader instanceof URLClassLoader) {
			URLClassLoader urlClassLoader = (URLClassLoader)classLoader;
			Class clazz = URLClassLoader.class;
			Method method = clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { url });
		} else {
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, classLoader);
			Thread.currentThread().setContextClassLoader(urlClassLoader);
		}
		
	}

	

	public static String getSystemProperties() {
		
		Properties properties = System.getProperties();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		StringBuffer sb = new StringBuffer();
		for(Entry entry : entrySet) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();
			sb.append(key).append("=").append(value).append("\n");
		}
		
		return sb.toString();
		
	}
	
	public static String getCauseStackTrace(Throwable e, boolean isShortly) {

		StringBuffer stackTraceBuffer = new StringBuffer();
		Throwable cause = e.getCause();
		if (cause != null) {
			stackTraceBuffer.append(getStackTrace(cause, isShortly));
		}
		return stackTraceBuffer.toString();

	}
	
	public static String getStackTrace(Throwable e, boolean isShortly) {

//굳이 저렇게 어렵게 해야 하나, 걍 다음과 같이 처리할까? 안된다. printStackTrace()안에서 toString()을 호출하고, 무한 루프에 빠진다.
//Writer writer = new StringWriter();
//e.printStackTrace(new PrintWriter(writer));
//String stackTrace = writer.toString();
//if(true) return stackTrace;

		StackTraceElement[] stackTraceElements = e.getStackTrace();
		StringBuffer stackTraceBuffer = new StringBuffer();

		for(int i=0; i<stackTraceElements.length; i++) {
			
			if(isShortly) { if(i>=1) { break; } }
			
			stackTraceBuffer.append("    at ");
			stackTraceBuffer.append(stackTraceElements[i].getClassName()).append(".");
			stackTraceBuffer.append(stackTraceElements[i].getMethodName());
			stackTraceBuffer.append("(").append(stackTraceElements[i].getFileName());
			stackTraceBuffer.append(":").append(stackTraceElements[i].getLineNumber()).append(")");
			stackTraceBuffer.append("\n");
			
		}

		return stackTraceBuffer.toString();

	}
	

	public static String readString(InputStream inputStream, String charset) throws IOException {
		
		
		if(inputStream==null) {
			throw new IOException("input stream is null");
		}
		if(charset==null) {
			throw new IOException("charset is null");
		}

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


		String readLineString = "";
		StringBuffer stringBuffer = new StringBuffer();
		do
		{
			readLineString = bufferedReader.readLine();
			if(readLineString!=null)
			{
				stringBuffer.append(readLineString).append("\r\n");
			}

		}
		while(readLineString!=null);

		// 맨 뒷줄에 무조건 "\r\n"이 붙는거 삭제.
		if(stringBuffer.length()>=2) {
			stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length());
		}

		return stringBuffer.toString();
		
	}
	
	/**
	 * InputStream를 byte[]로 읽어 반환한다.
	 * 읽어야 할 사이즈가 얼마인지 모르기 때문에 일단 buffer 사이즈를 어느정도로 잡고, 
	 * 만약 모자라면 더 큰 사이즈의 버퍼를 새로 만들어 카피한다.
	 * 이와중에 리소스가 소요된다.
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream inputStream) throws IOException {
	

		// TODO : 다음과 같이 개선한다.
		// from JunitClassLoader.getClassData()
		/*
		FileInputStream stream= null;
		try {
			stream= new FileInputStream(f);
			ByteArrayOutputStream out= new ByteArrayOutputStream(1000);
			byte[] b= new byte[1000];
			int n;
			while ((n= stream.read(b)) != -1) 
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();

		} catch (IOException e) {
		}
		finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
				}
			}
		}
		 */
		int available = inputStream.available();
        
		int bufferSize = 1024;
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
		
		while(available>0) {
			byte[] readBytes = readBytes(inputStream, available);
			if(byteBuffer.remaining() < readBytes.length) {
				byteBuffer = enlargeSizeByteBuffer(byteBuffer, readBytes.length);
			}
			byteBuffer.put(readBytes);
			available = inputStream.available();
		}
		
    	byte[] returnBytes = new byte[byteBuffer.capacity()-byteBuffer.remaining()];
    	int readLength = byteBuffer.capacity() - byteBuffer.remaining();
    
    	System.arraycopy(byteBuffer.array(), 0, returnBytes, 0, readLength);
    	
    	return returnBytes;
    	
	}
	
	
	private static ByteBuffer enlargeSizeByteBuffer(ByteBuffer byteBuffer, int requiredSize) {
		
		int bufferSize = byteBuffer.capacity();
		
		int enlargingSize = 1000;
		if(requiredSize>=1*1000*1000) {
			// 1 메가보다 큰 경우
			enlargingSize = requiredSize;
		}
		else if(requiredSize>=100*1000) {
			// 100K 보다 큰 경우
			enlargingSize = requiredSize*2;
		}
		else if(requiredSize>=10*1000) {
			// 10K 보다 큰 경우
			enlargingSize = requiredSize*10;
		}
		else {
			enlargingSize = 10*1000;
		}
		
		if(enlargingSize<1000) {
			enlargingSize = 1000;
		}
		
		int newBufferSize = bufferSize + enlargingSize;
		
		int oldDataSize = byteBuffer.capacity()-byteBuffer.remaining();
		
		ByteBuffer newByteBuffer = ByteBuffer.allocate(newBufferSize);
		newByteBuffer.put(byteBuffer.array(), 0, oldDataSize);
		byteBuffer.clear();
		byteBuffer = null;
		byteBuffer = newByteBuffer;
		bufferSize = newBufferSize;

		return byteBuffer;

	}


	// !!! 주의 : inputStream.available() 보다 lengthToRead가 작을 경우 데이타가 손실된다.
	// 예를 들어 inputStream에 10,000 byte가 읽을 수 있을 때 1000 byte만 읽으면 읽은것 뒤의 7192(=8192-1000) byte를 잃어 버린다.
	// 이러한 사실을 알고 써야 한다.
	private static byte[] readBytes(InputStream inputStream, int lengthToRead) throws IOException {
		
		int available = inputStream.available();

		if(available<lengthToRead) {
			lengthToRead = available;
		}
		byte[] readByte = new byte[lengthToRead];
        
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int readLength = 1;
        int offSet = 0;
        
        while(readLength>0) {
        	lengthToRead = lengthToRead - offSet;
        	if(lengthToRead<=0) {
        		break;
        	}
        	readLength = bufferedInputStream.read(readByte, offSet, lengthToRead);
        	offSet += readLength;
        }
        
    	return readByte;
    	
	}
	

	
	// 간단한 bean같은 경우에는 유용하게 사용될 수 있다.
	// 하지만 타 참조를 가지고 있는 클래스의 경우 제대로 동작하지 않는다.
	// 예를 들어 java.sql.Connection을 속성으로 같는 객체의 경우
	// deep copy 자체가 불가능하다. 
	// 또한 속도가 느리다. 객체가 클 경우 쓰지 말자.
	public static Object deepCopy(Object object) throws IOException, ClassNotFoundException {
        
		// serializing
		ByteArrayOutputStream byteArrOs = new ByteArrayOutputStream();
		ObjectOutputStream objOs = new ObjectOutputStream(byteArrOs);
		objOs.writeObject(object);
		
		// de-serializing
		ByteArrayInputStream byteArrIs = new ByteArrayInputStream(byteArrOs.toByteArray());
		ObjectInputStream objIs = new ObjectInputStream(byteArrIs);
		Object deepCopy = objIs.readObject();
		
		return deepCopy;
	}

	public static String getRootCauseMessage(Throwable e) {
		Throwable cause = getRootCause(e);
		return cause.getMessage();
	}

	public static Throwable getRootCause(Throwable exception) {
		Throwable cause = exception.getCause();
		if(cause==null) { return exception; }
		else { return getRootCause(cause); }
	}



	// xml안에서 특정 element의 text값의 byte[]값을 출력한다.
	public static void showAsBytesOfXmlElementText(String xmlString, String tagName) {
	
		byte[] xmlBytes = null;
		byte[] tagNameBytes = null;
		try {
			xmlBytes = xmlString.getBytes("utf8");
			tagNameBytes = tagName.getBytes("utf8");
		} catch (UnsupportedEncodingException e) {
		}
		
		
		byte[] tagOpenBytes = new byte[tagNameBytes.length+2];
		byte[] tagCloseBytes = new byte[tagNameBytes.length+3];
		
		tagOpenBytes[0] = 60; // <
		tagOpenBytes[tagOpenBytes.length-1] = 62; // >
		System.arraycopy(tagNameBytes, 0, tagOpenBytes, 1, tagNameBytes.length);
		
		tagCloseBytes[0] = 60; // <
		tagCloseBytes[1] = 47; // /
		tagCloseBytes[tagCloseBytes.length-1] = 62; // >
		System.arraycopy(tagNameBytes, 0, tagCloseBytes, 2, tagNameBytes.length);

		
		int startIndex = 0;
		int endIndex = 0;

		for(int i=0; i<xmlBytes.length - tagCloseBytes.length; i++) {
			for(int j=0; j<tagOpenBytes.length; j++) {
				if(xmlBytes[i+j]!=tagOpenBytes[j]) {
					break;
				}
				if(j==tagOpenBytes.length-1) { startIndex = i+tagOpenBytes.length; }
			}
			for(int j=0; j<tagCloseBytes.length; j++) {
				if(xmlBytes[i+j]!=tagCloseBytes[j]) {
					break;
				}
				if(j==tagCloseBytes.length-1) { endIndex = i-1; }
			}
		}
		
		for(int i=startIndex; i<=endIndex; i++) {
			System.out.println(i+" : "+xmlBytes[i]);
		}
	}
	
}
