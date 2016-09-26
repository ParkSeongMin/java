package com.seongmin.test.sort;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public abstract class Sorter {

	public static void timSort(List list, Comparator c) {
		Object[] a = list.toArray();
		TimSort.sort(a, c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}

	public static void heapSort(List list, Comparator c) {
		Object[] a = list.toArray();
		heapSort(a, c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}

	public static void heapSort(Object[] a, Comparator c) {

		int len = a.length;
		for (int k = len / 2; k > 0; k--) {
			downHeap(a, k, len, c);
		}
		// System.out.println("### First Heap Builiding Finished ###\n");
		do {
			Object temp = a[0];
			a[0] = a[len - 1];
			a[len - 1] = temp;
			len = len - 1;
			downHeap(a, 1, len, c);
		} while (len > 1);
	}

	private static void downHeap(Object arr[], int k, int len, Comparator c) {
		Object temp = arr[k - 1];
		while (k <= len / 2) {
			int j = 2 * k; // Left Child Node
			
			if ((j < len) && c.compare(arr[j-1], arr[j]) < 0) {
				j++;
			}
			if (c.compare(temp, arr[j-1]) >= 0) {
				break;
			} else {
				arr[k - 1] = arr[j - 1];
				k = j;
			}
		}
		arr[k - 1] = temp;
	}

	public static void quickSort(List list, Comparator c) {
		Object[] a = list.toArray();
		quicksort(a, 0, a.length -1, c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}
	
	private static void quicksort(Object[] obj, int p, int r, Comparator c) {
		if (p < r) {
			int q = partition(obj,p,r, c);
//			if (q == r) {
//				q--;
//			}
			quicksort(obj,p,q-1, c);
			quicksort(obj,q+1,r, c);
		}
	}

	private static int partition (Object[] obj, int p, int r, Comparator c) {
		Object pivot = obj[p];
		int lo = p;
		int hi = r;

		while (true) {
			while (c.compare(obj[hi], pivot) >= 0 &&
					lo < hi) {
				hi--;
			}
			while (c.compare(obj[hi], pivot) < 0 &&
					lo < hi) {
				lo++;
			}
			if (lo < hi) {
				swap(obj, lo, hi);
			} else {
				return hi;
			}
		}
	}      

	private static void q_sort(Object numbers[], int left, int right, Comparator c)
	{
	    Object pivot; 
	    int l_hold, r_hold;
	    l_hold = left;
	    r_hold = right;
	    pivot = numbers[left]; // 0번째 원소를 피봇으로 선택
	    while (left < right)
	    {
	        // 값이 선택한 피봇과 같거나 크다면, 이동할 필요가 없다
//	        while ((numbers[right] >= pivot) && (left < right))
	        while (c.compare(numbers[right], pivot) >= 0 && (left < right))
	            right --;

	        // 그렇지 않고 값이 피봇보다 작다면,
	        // 피봇의 위치에 현재 값을 넣는다.
	        if (left != right)
	        {
	             numbers[left] = numbers[right];
	        }
	        // 왼쪽부터 현재 위치까지 값을 읽어들이면서
	        // 피봇보다 큰 값이 있다면, 값을 이동한다.
//	        while ((numbers[left] <= pivot) && (left < right))
	        while (c.compare(numbers[left], pivot) <= 0 && (left < right))
	            left ++;
	        if (left != right)
	        {
	             numbers[right] = numbers[left];
	             right --;
	        }
	    }
	    // 모든 스캔이 끝났다면, 피봇값을 현재 위치에 입력한다.
	    // 이제 피봇을 기준으로 왼쪽에는 피봇보다 작거나 같은 값만 남았다.
	    numbers[left] = pivot;
	    int pivotIndex = left;
//	    pivot = left;
	    left = l_hold;
	    right = r_hold;

	    // 재귀호출을 수행한다.
	    if (left < pivotIndex)
//    	if (c.compare(numbers[left], pivot) < 0)
	        q_sort(numbers, left, pivotIndex-1, c);
	    if (right > pivotIndex)
//	   	if (c.compare(numbers[right], pivot) > 0)
	        q_sort(numbers, pivotIndex+1, right, c);
	}

	/* O(n2) Sorts */
	public static void bubbleSort(List list, Comparator c) {

		Object[] a = list.toArray();
		bubbleSort(a, (Comparator) c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}

	}

	public static void bubbleSort(Object[] a, Comparator c) {
		boolean changed = false;
		do {
			changed = false;
			for (int i = 0; i < a.length - 1; i++) {
				if (c.compare(a[i], a[i + 1]) > 0) {
					swap(a, i, i + 1);
					changed = true;
				}
			}
		} while (changed);
	}

	public static void insertSort(List list, Comparator c) {
		/*
		 * An O(n2) sorting algorithm which moves elements one at a time into
		 * the correct position. The algorithm consists of inserting one element
		 * at a time into the previously sorted part of the array, moving higher
		 * ranked elements up as necessary. To start off, the first (or
		 * smallest, or any arbitrary) element of the unsorted array is
		 * considered to be the sorted part. Although insertion sort is an O(n2)
		 * algorithm, its simplicity, low overhead, good locality of reference
		 * and efficiency make it a good choice in two cases (i) small n, (ii)
		 * as the final finishing-off algorithm for O(n logn) algorithms such as
		 * mergesort and quicksort. The algorithm is as follows (from
		 * wikipedia):
		 */

		Object[] a = list.toArray();
		insertSort(a, (Comparator) c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}

	}

	public static void insertSort(Object[] a, Comparator c) {
		for (int i = 1; i < a.length; i++) {
			Object value = a[i];
			int j = i - 1;
			while (j >= 0 && c.compare(a[j], value) > 0) {
				a[j + 1] = a[j];
				j = j - 1;
			}
			a[j + 1] = value;
		}
	}

	private static void swap(Object x[], int a, int b) {
		Object t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

}
