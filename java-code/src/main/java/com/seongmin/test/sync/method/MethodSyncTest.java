package com.seongmin.test.sync.method;


public class MethodSyncTest {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        final BlackOrWhite bow = new BlackOrWhite();
 
        Thread white = new Thread() {
            public void run() {
                while (true) {
                	bow.test3();
//                    bow.white();
                }
            }
        };
        Thread black = new Thread() {
            public void run() {
                while (true) {
                	bow.test1();
//                    bow.black();
                }
            }
        };
        white.start();
        black.start();
    }
}



