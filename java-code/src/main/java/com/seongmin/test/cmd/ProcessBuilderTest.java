package com.seongmin.test.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessBuilderTest {

	public static void main(String[] args) throws Exception {

		List<String> cmd = new ArrayList<String>();
//		cmd.add("java");
//		cmd.add("-classpath");
//		cmd.add("\"d:/workspace/some project/lib/something.jar\"");
//		cmd.add("blah.blah.SomeClass");
//		cmd.add("arg1");
//		cmd.add("arg2");
		cmd.add("cmd ");
		cmd.add("java ");
		cmd.add("-version");
//		cmd.add("exit");
		
		
		// ProcessBuilder의 constructor의 argument는 무조건 List<String> 이어야 합니다 ㅠㅠ
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		// Unsynchronized 이다. .. 동기화 처리를 해주어야 할 것이다.
		
		
//		그리고, 실행할 command가 shell script 또는 executable filename이 아니라면 실행이 되지 않습니다.
//		예를 들어, ls라던가 dir 같은 명령들이 해당되는데요, 반드시 shell script를 실행하거나 shell의 argument로 넘겨서 실행해야 합니다.
//		즉, "dir"은 오류가 발생하지만 "cmd /c dir"은 실행이 가능한 것이죠.
		
//		processBuilder.directory(new File("working/processbuilder"));
		
		Process process = null;
		try {

		    // 프로세스 빌더를 통하여 외부 프로그램 실행
		    process = processBuilder.start();

		    
		    // 외부 프로그램의 표준출력 상태 버퍼에 저장
		    BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()) );
		    
		    String str = null;
		    // 표준출력 상태를 출력
		    while( (str = stdOut.readLine()) != null ) {
		        System.out.println(str);
		        process.destroy();
		    }
		    
		} catch (IOException e) {
		    e.printStackTrace();

		}

		
		test2();
		
	}
	
	public static void test2() throws Exception {
		/*
		Java 1.5부터 Runtime.exec()를 대신할 ProcessBuilder가 생겼다.

		WAS에서 배치 프로그램을 시작할 수도 있고,
		다른 시스템 프로그램을 호출 할 수도 있다.

		아래 코드는  main() 메소드를 가지고 있는 자바 클래스를 실행하는 코드다.
		실행 명령은 기존의 Runtime.exec와 다르게 명령행을 내용을 한 줄로 만드는 것이 아니라
		space로 구분되는 내용을 각각의 문자열로 지정해야 한다.
		아래 ProcessBuilder의 내용은 "java test.Callee a b=b c" 이렇게 명령행을 입력 하는 경우이다.
		*/
		
		{
			System.out.println("Runtime exec");
			Process exec = Runtime.getRuntime().exec(new String[]{"java", " cmd.processBuilderTTT"});
			InputStream i = exec.getInputStream();
			byte b[] = new byte[8192];
			int read = 0;
			while ((read = i.read(b)) > 0) {
				System.out.println("out : " + new String(b, 0, read));
			}
//			InputStream inputStream = exec.getInputStream();
//			
//			  // 외부 프로그램의 표준출력 상태 버퍼에 저장
//		    BufferedReader stdOut = new BufferedReader( new InputStreamReader(inputStream) );
//		    
//			
//			String str = null;
//			// 표준출력 상태를 출력
//			while ((str = stdOut.readLine()) != null) {
//				System.out.println(str);
//			}
		}
		
		{
			System.out.println("ProcessBuilder test gogo");
			// 각각의 command line argument까지 개별 항목으로 지정해야 한다.
//			ProcessBuilder pb = new ProcessBuilder("java", "cmd.ProcessBuilderTTT", "a", "b=b", "c");
			ProcessBuilder pb = new ProcessBuilder("java", "cmd.ProcessBuilderTTT");
			
			Map env = pb.environment();
			// -classpath옵션은 ProcessBuilder에 직접 지정할 수 없다.
			// shell script를 만들때 처럼 환경 변수 CLASSPATH로 클래스 path를 지정한다.
			env.put("CLASSPATH", "bin");
			pb.directory(new File("working/processbuilder")); // 로그 파일이 생기는 기본 위치
			File log = new File("log"); // 로그 파일명
			pb.redirectErrorStream(true); // 에러도 로그에 출력되게 설정
			// redirectOutput과 Redirect는 java se 7 에서 추가된 내용 임.
			// pb.redirectOutput(Redirect.appendTo(log)); // System.out이 이 파일에
			// 쓰여진다.
			Process p = pb.start(); // 프로그램을 실행한다.
		}
//		위의 코드는 프로그램을 실행하고 바로 return 되는 코드다.
//
//		프로그램을 실행하고 끝날때 까지 기다리기를 원하면 p.waitFor();를 호출 한다.
//		이런 경우는 아래처럼 별도의 로그 파일을 쓰지 않고 직접 출력 할 수도 있다.

		
		{
			ProcessBuilder pb = new ProcessBuilder("java", "cmd.ProcessBuilderTTT");
			Map env = pb.environment();
			env.put("CLASSPATH", "bin");
			Process p = pb.start();
			p.waitFor();

			InputStream i = p.getInputStream();
			byte b[] = new byte[8192];
			int read = 0;
			while ((read = i.read(b)) > 0) {
				System.out.println("out : " + new String(b, 0, read));
			}
			i = p.getErrorStream();
			while ((read = i.read(b)) > 0) {
				System.out.println("err : " + new String(b, 0, read));
			}
			System.out.printf("exit value : %s\n", p.exitValue()); // 0 이면 정상종료

		}
//		간혹 명령이 잘못 되면 pb.start()에서 에러가 발생하기도 한다. 헌데, windows에서는 exception의 메시지에 포함된 한글이 깨지기도 한다. 이런경우 encoding값을 직접 지정하여 한글 메시지를 확인 하는 것이 도움이 된다. 또는 로그파일을 쓰면 된다.

		try {
			ProcessBuilder pb = new ProcessBuilder("java", "cmd.ProcessBuilderTTT");
			Map env = pb.environment();
			env.put("CLASSPATH", "bin");
			Process p = pb.start();
			p.waitFor();
			InputStream i = p.getInputStream();
			byte b[] = new byte[8192];
			int read = 0;
			while ((read = i.read(b)) > 0) {
				System.out.println("out : " + new String(b, 0, read));
			}
			i = p.getErrorStream();
			while ((read = i.read(b)) > 0) {
				System.out.println("err : " + new String(b, 0, read));
			}
			System.out.printf("exit value : %s\n", p.exitValue());
		} catch (IOException e) {
			try {
				System.out.println(new String(e.getMessage().getBytes("8859_1"), "euc-kr"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
