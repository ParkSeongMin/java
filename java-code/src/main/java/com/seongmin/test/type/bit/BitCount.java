package com.seongmin.test.type.bit;

public class BitCount {
	
	/*

	브라이언 커니건의 비트 카운트 입니다. 
처음 봤을때 많이 신기해서 저장해놨었네요..
    int bitcnt(int n)
    {
        int c, v=n;
        for (c = 0; v; c++)
        {
          v &= v - 1; // clear the least significant bit set
        }
        return c;
    }
	
	
	*/

	public static void main(String[] args) {
		
		int a = 1023;
		
	    System.out.println(bitcnt(a));
	    System.out.println(countBits(a));
		
	}
	
	static int bitcnt(int n)
    {
        int c, v=n;
        for (c = 0; c<v; c++)
        {
          v &= v - 1; // clear the least significant bit set
          System.out.println("A : " + v);
        }
        return c;
    }

	/**
	 * Counts number of 1 bits in a 32 bit unsigned number.
	 *
	 * @param x unsigned 32 bit number whose bits you wish to count.
	 *
	 * @return number of 1 bits in x.
	 * @author Roedy Green email
	 */
	public static int countBits(int x ) {
	   // collapsing partial parallel sums method
	   // collapse 32x1 bit counts to 16x2 bit counts, mask 01010101
	   x = (x >>> 1 & 0x55555555) + (x & 0x55555555);
	   // collapse 16x2 bit counts to 8x4 bit counts, mask 00110011
	   x = (x >>> 2 & 0x33333333) + (x & 0x33333333);
	   // collapse 8x4 bit counts to 4x8 bit counts, mask 00001111
	   x = (x >>> 4 & 0x0f0f0f0f) + (x & 0x0f0f0f0f);
	   // collapse 4x8 bit counts to 2x16 bit counts
	   x = (x >>> 8 & 0x00ff00ff) + (x & 0x00ff00ff);
	   // collapse 2x16 bit counts to 1x32 bit count
	   return(x >>> 16) + (x & 0x0000ffff);
	}
	
}
