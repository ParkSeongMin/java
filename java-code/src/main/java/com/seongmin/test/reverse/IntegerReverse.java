package com.seongmin.test.reverse;

public class IntegerReverse {

	public static void main(String[] args) {
		
		int a = 1234;
//		int a = 1342177280;
		
		
		int reverse = IntegerReverse.reverse15(a);
		System.out.println(a + " reverse = " + reverse);
		
		reverse = IntegerReverse.reverse16(a);
		System.out.println(a + " reverse = " + reverse);
		
		reverse = IntegerReverse.reverseBytes(a);
		System.out.println(a + " reverseBytes = " + reverse);
		
		System.out.println(a + " signum = " + Integer.signum(a));
		
		System.out.println(IntegerReverse.reverseNum(a));
		System.out.println(IntegerReverse.reverseNum2(a));
		
	}
	
	 public static int reverseBytes(int i) {
	        return ((i >>> 24)           ) |
	               ((i >>   8) &   0xFF00) |
	               ((i <<   8) & 0xFF0000) |
	               ((i << 24));
	    }
	 
	 public static int reverse15(int i) {
	        // HD, Figure 7-1
		i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
		i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
		i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
		i = (i << 24) | ((i & 0xff00) << 8) | ((i >>> 8) & 0xff00) | (i >>> 24);
		return i;
    }
	
	 public static int reverse16(int i) {
	        // HD, Figure 7-1
		i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
		i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
		i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
		i = (i << 24) | ((i & 0xff00) << 8) |
		    ((i >>> 8) & 0xff00) | (i >>> 24);
		return i;
	    }
	
	 public static int reverseNum(int input) {
		 
		 int reversedNum = 0;
		 int last_digit;
		 while (input != 0)
		 {    
		      last_digit = input % 10;
		      // clear 2의 배수
//		      if(last_digit % 2 != 0)
//		      {     
		             reversedNum = reversedNum * 10 + last_digit;

//		      }
		       input = input / 10; 
		 }
		 
		 return reversedNum;
	 }
	 
	 private static int reverseNum2(int number) {
	        int backup = number;
	        int count = 0;
	        // 10 진수 자리수
	        while (number != 0) {
	            number = number / 10;
	            count++;
	        }
	        number = backup;
	        
	        
	        int sum = 0;
	        for (int i = count; i > 0; i--) {
	            int sum10 = 1;
	            int last = number % 10;
	            for (int j = 1; j < i; j++) {
	                sum10 = sum10 * 10;
	            }
	            sum = sum + (last * sum10);
	            number = number / 10;
	        }
	        return sum;
	    }
}
