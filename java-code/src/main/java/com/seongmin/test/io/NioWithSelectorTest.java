package com.seongmin.test.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioWithSelectorTest {

	static ServerSocketChannel	serverSocketChannel	= null;
	static Selector				selector			= null;

	public static void main(String[] args) throws IOException, InterruptedException {

		// Selector(일종의 리스너) 열기
		selector = Selector.open();
		// 소켓 열기
		serverSocketChannel = ServerSocketChannel.open();
		// 소켓 채널에 non-blocking 설정
		serverSocketChannel.socket().bind(new InetSocketAddress(8000));
		serverSocketChannel.configureBlocking(false); // Blocking false

		// Blocking 상태확인
		System.out.println("Blocking 상태확인 :::: " + serverSocketChannel.isBlocking());

		// Selector를 이용하여 감지할 이벤트 등록
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			System.out.println("connecting...");

			// 준비된 이벤트가 있는지 체크. 이벤트마다 다른 숫자 반환이 있을때까지 blocking
			selector.select();

			// 이벤트가 발생하였을 때, 셀렉터의 SelectSet에 준비된 이벤트를 하나씩 처리함.
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// Selection Key 에 따라 분기
				if (selectionKey.isAcceptable()) {
					// 서버 소켓 채널에 클라이언트가 접속시도
					accept(selectionKey);
				} else if (selectionKey.isReadable()) {
					// 이미 연결된 클라이언트가 메시지를 보내는 경우
					read(selectionKey);
				} else {
					System.out.println("그외의 셀렉션 키 : " + iterator.next());
				}

				// 처리된 이벤트는 제거
				iterator.remove();
			}
		}
	}

	/**
	 * 클라이언트가 접속되었을 때 실행되는 메서드
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private static void accept(SelectionKey selectionKey) throws IOException {
		// 받아들인 채널로 서버소켓채널 생성
		ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
		// 받아들인 서버소켓채널로 소켓채널 생성
		SocketChannel sc = server.accept();
		sc.configureBlocking(false); // non-blocking
		// 접속된 후에는 읽기모드로 변경
		sc.register(selector, SelectionKey.OP_READ);
		System.out.println(sc.toString() + " 접속되었습니다.");
	}

	/**
	 * Read 된 크기를 알려줌.
	 * 
	 * @param selectionKey
	 */
	private static void read(SelectionKey selectionKey) {
		// SelectionKey 로부터 소켓 채널을 얻어옴
		SocketChannel sc = (SocketChannel) selectionKey.channel();
		// ByteBuffer 생성함
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		try {
			// 요청한 클라이언트의 소켓 채널로 부터 데이터를 읽어들임.
			int read = sc.read(buffer);
			System.out.println(read + " byte를 읽었습니다.");
		} catch (IOException e) {
			try {
				sc.close();
			} catch (IOException e2) {
			}
		}

		// buffer 메모리 해제
		clearBuffer(buffer);
	}

	/**
	 * buffer의 내용을 클리어함.
	 * 
	 * @param buffer
	 */
	private static void clearBuffer(ByteBuffer buffer) {
		if (buffer != null) {
			buffer.clear();
			buffer = null;
		}
	}
}