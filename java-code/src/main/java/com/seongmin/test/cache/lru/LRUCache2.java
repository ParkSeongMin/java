package com.seongmin.test.cache.lru;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUCache2 {

	private final int						maxSize;
	private ConcurrentLinkedQueue<String>	queue	= new ConcurrentLinkedQueue<String>();

	public LRUCache2(int size) {
		this.maxSize = size;
	}

	public void insertCache(String data) {
		while (queue.size() >= maxSize) {
			queue.poll();
		}

		queue.add(data);
	}

	public String getfromCache(String data) {
		String dat = null;
		if (queue.contains(data)) {
			queue.remove(data);
			dat = data;
		}
		insertCache(data);
		return dat;
	}

	public void printqueue() {
		System.out.println(queue);
	}

	public static void main(String[] args) {
		LRUCache2 cache = new LRUCache2(3);
		cache.getfromCache("mayur");
		cache.getfromCache("sumit");
		cache.getfromCache("amit");
		cache.printqueue();
		cache.getfromCache("sumit");
		cache.printqueue();
		cache.getfromCache("ankit");
		cache.printqueue();

	}
}