package com.seongmin.test.sort;

import java.util.Calendar;
import java.util.Random;

public class SortTest {

	int		arrsize	= 100000;

	int[]	arr		= null;
	long	timeL	= 0L;

	public static void main(String[] args) throws Exception {
		System.out.println("start program .........");
		SortTest st = new SortTest();
		st.setInitArray();

		System.out.println("this array set the value .........");

//		st.bubbleSort(); // 44775
//		st.selectedSort(); // 35378
//		st.insertSort(); // 3164
//		st.shellSort(); // 74
//		st.quickSort(); // 45
//		st.mergeSort(); // 10
		st.heapSort();  // 76

		System.out.println("end program .........");
	}

	public void setInitArray() throws Exception {
		arr = new int[arrsize];
		Random rnd = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rnd.nextInt(arrsize);
		}

		printArray();
	}

	public void printArray() throws Exception {
		for (int i = 0; i < arr.length; i = i + (arrsize / 10)) {
			System.out.print("[" + arr[i] + "]");
		}
		System.out.println("");

	}

	public void Swap(int a, int b) {
		int temp;
		temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	public void Swap(int[] a, int[] b) {
		int[] temp;
		temp = a;
		a = b;
		b = temp;
	}

	public void checkTime(int num) throws Exception {
		Calendar c = Calendar.getInstance();
		if (num == 1) {
			timeL = c.getTimeInMillis();
		} else {
			long gapTime = (c.getTimeInMillis() - timeL);
			System.out.println("\nTime gap ... " + (gapTime));
			timeL = 0;
		}
	}

	/**
	 * 1. 거품 정렬 한 원소와 바로 옆 원소끼리만 비교를 해서 순서가 거꾸로이면 위치를 맞바꾸는 대입만 죽어라고 하는 알고리즘입니다.
	 * 시간 복잡도는 O(n2)이며, 거품 정렬은 같은 O(n2)급 알고리즘 중에서도 상당히 비효율적인 축에 속합니다. 이 알고리즘의 동작
	 * 모습을 그래픽 (x, y)->(x, 배열의 x째 원소의 값)으로 시연해 보면 대각선 부위에서 점들이 거품처럼 부글부글 움직이는
	 * 모습을 볼 수 있습니다. 한 루프를 도는 동안 교환 연산이 한 번도 일어나지 않았다면 자료가 이미 다 정렬되어 있다는 뜻이므로,
	 * 그것을 감지하고 실행을 끝내기 위해서 flag라는 변수를 두었습니다.
	 */
	public void bubbleSort() throws Exception {

		boolean flag = false;
		int i, j = 1;
		checkTime(1);

		for (i = 0; i < arr.length; i++) {
			for (flag = false, j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					flag = true;
					Swap(j, j + 1);
				}
			}

			if (!flag)
				break;
		}

		checkTime(2);
		printArray();

	}

	/**
	 * 2. 선택 정렬 가장 큰 값부터 차례대로 리스트의 끝으로 옮겨서 뒤에서부터 앞으로 정렬하는 방법으로, 실제 상황에서 가장 코딩하기
	 * 쉽고 직관적인 알고리즘입니다. 수행 시간이 데이터 상태의 영향을 잘 받지 않고, 데이터의 대입 횟수가 적은 게 특징입니다. 거품
	 * 정렬보다 2~3배 정도 빠릅니다.
	 */
	public void selectedSort() throws Exception {

		int i, j = 1;
		checkTime(1);

		for (i = 0; i < arr.length - 1; i++) {
			for (j = i + 1; j < arr.length; j++) {
				if (arr[i] > arr[j]) {
					Swap(i, j);
				}
			}
		}

		checkTime(2);
		printArray();

	}

	/**
	 * 3. 삽입 정렬 각 원소들에 대해 자기보다 앞에 있는 원소들을 살펴서 순서가 어긋나 있으면 자신을 거기로 옮기고 원래 있던 원소들은
	 * 뒤로 한 칸씩 밀어냅니다. 선택 정렬보다 두 배 정도 빨라서 평균적인 성능이 O(n2) 알고리즘들 중에서 뛰어난 축에 들기 때문에,
	 * 이 정렬은 다른 정렬 알고리즘의 일부로도 자주 사용됩니다. 이 방법은 대입이 많으며, 데이터의 상태, 데이터 한 개의 크기에 따라
	 * 성능 편차가 심한 편입니다.
	 */
	public void insertSort() throws Exception {

		int i, j, r = 0;
		checkTime(1);

		for (i = 1; i < arr.length; i++) {
			for (j = i - 1, r = arr[i]; j >= 0 && arr[j] > r; j--)
				arr[j + 1] = arr[j];

			arr[j + 1] = r;
		}

		checkTime(2);
		printArray();

	}

	/**
	 * 4. 쉘 정렬 삽입 정렬을 일정한 간격으로 띄엄띄엄 수행한 뒤(예를 들어 d=40, 13, 4, ...점차 조밀하게), 전체적으로는
	 * 대부분 정렬되어 있는 그 결과 역시 삽입 정렬로 종합합니다(d=1). 이 알고리즘은 삽입 정렬의 특성을 응용한 것뿐인데 삽입
	 * 정렬과는 비교할 수 없을 정도로, O(n log n) 알고리즘에 버금가는 성능을 자랑합니다. 부가적인 메모리도 전혀 필요없어서 비용
	 * 대 성능도 대단히 뛰어납니다. 하지만 이 알고리즘은 '띄엄띄엄'을 어떻게 설정하는게 가장 좋을지가 엄밀하게 알려져 있지 않아 시간
	 * 복잡도를 O(n2)나 O(n log n)처럼 정확하게 계산하기 힘듭니다. 다만, 그 띄엄띄엄 수열이 서로 소가 되게 정의해야 성능이
	 * 좋아진다는 것이 알려져 있지요.
	 */
	public void shellSort() throws Exception {

		int i, j, r, k = 0, l;
		checkTime(1);
		int baseNum = 5;

		for (i = 1; i < arr.length; k = i, i = i * baseNum + 1)
			;

		do {
			for (l = 0; l < k; l++) {
				for (i = l + k; i < arr.length; i += k) {
					for (j = i - k, r = arr[i]; j >= l && arr[j] > r; j -= k)
						arr[j + k] = arr[j];
					arr[j + k] = r;
				}
			}
			k = (k - 1) / baseNum;

		} while (k >= 1);

		checkTime(2);
		printArray();

	}

	/**
	 * 5. 퀵 정렬 가장 유명하고, 정렬 알고리즘의 표준이다시피 한 방법입니다. 이 알고리즘을 보면 정말 사기-_-라는 생각이 듭니다.
	 * 실제로 코딩을 해 보면, 퀵 정렬이 코드가 가장 긴데, 실행 시간은 퀵 정렬이 다른 알고리즘들보다 기막힐 정도로 짧습니다.
	 * 중간값이라는 뭔가 적당한(모호한) 값을 선택해야 하고, 최악의 경우 시간 복잡도가 O(n2)에 메모리 복잡도가 O(n)이 될
	 * 가능성까지 있는 알고리즘이 어쩜 이럴 수 있을까요? 하지만 반대로 말하면 퀵 정렬은 자료가 이미 정렬되어 있는 상태를 파악하는 그
	 * 민감도를 극대화했기 때문에 이만치 빠를 수 있습니다. 중간값을 기준으로 데이터를 반으로 갈라 놓고, 양측에 대해서 재귀적으로 또
	 * 중간값을 설정해 정렬을 또 수행한다는 발상은 대단히 깔끔하고 멋집니다. 이런 걸 분할 정복법이라고 하지요. 이게 '퀵 정렬'이
	 * 아니었으면 '이분 검색'을 따라 '이분 정렬'이라는 이름이 붙었을 것입니다. 퀵 정렬은 본디 재귀적으로 정의되지만, 사용자 정의
	 * 스택을 구현해서 비재귀적으로 만들 수도 있으며, 본 소스 코드 역시 사용자 스택을 구현했습니다. 또한, 원소 개수가 한 자리 수로
	 * 떨어졌을 때는 또 재귀호출을 하는 게 아니라 삽입 정렬을 해서 능률을 극대화하기도 합니다.
	 */
	public void quickSort() throws Exception {

		checkTime(1);

		// sorting boundary, pivot index, and traveling pointers for partition
		// step
		int lo, hi, mid, loguy, higuy;

		// stack for saving sub-array to be processed
		int[] lostk = new int[40];
		int[] histk = new int[40];
		int stkptr = 0;
		lo = 0;
		hi = arr.length - 1; // initialize limits

		while (true) {
			mid = lo + (((hi - lo) + 1) / 2); // find middle element
			Swap(mid, lo); // swap it to beginning of array

			/*
			 * Would it be better to use insertion sort, when hi-lo is very
			 * small? * / for(loguy=lo+1; loguy<=hi; loguy++) {
			 * for(higuy=loguy-1, r=arr[loguy]; higuy>=lo && arr[higuy]>r;
			 * higuy--) arr[higuy+1]=arr[higuy]; arr[higuy+1]=r; } /*
			 */
			loguy = lo;
			higuy = hi + 1;
			while (true) {
				do
					loguy++;
				while (loguy <= hi && arr[loguy] <= arr[lo]);
				do
					higuy--;
				while (higuy > lo && arr[higuy] >= arr[lo]);

				if (higuy < loguy)
					break;
				Swap(loguy, higuy); //
			}
			Swap(lo, higuy); //

			if (higuy - 1 - lo >= hi - loguy) {
				if (lo + 1 < higuy) { // save big recursion for later
					lostk[stkptr] = lo;
					histk[stkptr] = higuy - 1;
					++stkptr;
				}
				if (loguy < hi) {
					lo = loguy;
					continue;
				} // do small recursion
			} else {
				if (loguy < hi) { // save big recursion for later
					lostk[stkptr] = loguy;
					histk[stkptr] = hi;
					++stkptr;
				}
				if (lo + 1 < higuy) {
					hi = higuy - 1;
					continue;
				} // do small recursion
			}

			--stkptr; // pop subarray from stack
			if (stkptr >= 0) {
				lo = lostk[stkptr];
				hi = histk[stkptr];
			} else
				break; // all subarrays done--sorting finished.
		}

		checkTime(2);
		printArray();

	}

	/**
	 * 6. 병합 정렬 O(n log n)인 정렬 알고리즘 중에 가장 간단하고 쉽게 떠올릴 수 있는 방법입니다. 퀵 정렬이 큰 리스트를
	 * 반씩 쪼갠다면, 이 방법은 이미 정렬이 된 리스트를 하나 둘씩 합쳐서 작업을 수행합니다. 이 알고리즘은 소요 시간이 데이터 상태에
	 * 별 영향을 받지 않고, 시간 복잡도가 O(n log n)인 알고리즘 중에 유일하게 안정성이 있다는 데 의미를 둘 수 있습니다. 단,
	 * O(n2) 알고리즘들은 모두 안정성이 있지요. 병합 정렬의 큰 결점은 데이터 전체 크기만한 메모리가 더 필요하다는 점입니다. 아주
	 * 현학적인 방법으로 한 배열 안에서 병합을 구현하는 방법도 있긴 하지만, 밀고 당기고 하는 과정으로 인해 속도가 크게 떨어지기
	 * 때문에, 메모리를 아끼려면 차라리 아래에 나오는 힙 정렬을 쓰는 게 더 낫습니다. 무조건 2의 배수씩 리스트를 합치는 게 병합
	 * 정렬의 기본 원리지만, 리스트에서 오름차순인 부분만 골라내서 지능적으로 병합을 하는 방법도 생각할 수 있습니다. 또한 퀵 정렬과
	 * 비슷한 원리로 재귀판을 만들 수도 있지요.
	 */

	public void mergeSort() throws Exception {

		checkTime(1);

		int i, j, a, b, c, d = 0;

		int[] temp = new int[arr.length];
		int[] orig = arr;
		int[] dest = temp;

		for (i = 1; i < arr.length; i <<= 1) {
			for (j = 0; j < arr.length; j += (i << 1)) { // for each fragment, d
															// = j + (i << 1);
				if (d > arr.length)
					d = arr.length;
				if (j + i >= arr.length) { // Copy the remaining elems
					for (a = j; a < arr.length; a++)
						dest[a] = orig[a];
					break;
				}

				for (a = c = j, b = j + i; c < d; c++)
					if ((orig[a] <= orig[b] && a < j + i) || b == d) {
						dest[c] = orig[a];
						a++;
					} else {
						dest[c] = orig[b];
						b++;
					}
			}
			Swap(orig, dest);
		}

		checkTime(2);
		printArray();

	}

	/**
	 * 7. 힙 정렬 이진 완전 나무를 배열에다 접목시킨 절묘한 알고리즘입니다. 이 알고리즘을 뜯어 보면 절로 감탄이 나옵니다. 처음에는
	 * 나무 아래에서 위(뿌리)로 각 원소들을 최대값 힙 조건에 맞게 정리한 뒤, 나무 뿌리에 있는 자료를 차례차례 나무 뒤로 옮기면서
	 * 힙을 정렬된 배열로 바꿉니다. 뿌리에는 힙 나무 맨 뒤에 있던 원소가 왔다가, 다시 힙 조건에 맞는 위치로 내려가지요. 시간
	 * 복잡도가 O(n log n)인 정렬 알고리즘 중에서는 부가적인 메모리가 전혀 필요없다는 게 힙 정렬의 큰 장점이지만, 하지만 수행
	 * 속도가 동급 O(n log n) 알고리즘들에 비해서는 약간(아주...) 낮은 편입니다.
	 */
	public void heapSort() throws Exception {
		heapSort(arr.length-1);
	}
	
	public void heapSort(int end) throws Exception {

		checkTime(1);

		int i = 0; // T *dt=m_pData-1; //dt is a 1-based index for m_pData
		int[] dt = arr;
		for(i = end/2; i >= 1; i--)
			fixHeap(i, end, arr[i]);

		for(i = end; i > 1; i--){
			Swap(1, i);
			fixHeap(1, i-1, arr[1]);
		}

		checkTime(2);
		printArray();

	}

	public void fixHeap(int root, int end, int key){
		int child	= 2* root;

		if(child < end && arr[child] < arr[child +1])
			child++;

		if( child <= end && key < arr[child]){
			arr[root] = arr[child];
			fixHeap(child, end, key);
		}else{
			arr[root]= key;
		}
	}

	/**
	 * 기수 정렬 기발함 그 자체입니다. 네 자리 수가 있으면, 백 자리, 십 자리, 일 자리 순으로 차례차례 정렬을 한다는 게 기본
	 * 전략입니다. 자릿수가 고정되어 있으니 각 자릿수별 정렬은 1 몇 개, 2 몇 개인지를 파악하는 식으로, 안정성이 있고(기수 정렬을
	 * 이해하는데 중요합니다. 이미 정렬된 배열 원소들의 상대적 순서가 반드시 보존되어야 합니다.) 시간 복잡도도 O(n)인 방법으로
	 * 하지요. 이 정렬법은 비교 연산을 하지 않으며, 무엇보다도 전체 시간 복잡도 역시 O(n)이어서 정수 같은 건 기가 막힐 정도로
	 * 정렬 속도가 빠릅니다. 물론 데이터 전체 크기에 기수 테이블의 크기만한 메모리가 더 필요하지요. 기수 정렬은 정렬 방법의 특수성
	 * 때문에 부동소숫점처럼 특수한 비교 연산이 필요한 데이터에는 적용할 수 없지만, 매우 특이하고 멋진 알고리즘임은 틀림없습니다. 이
	 * 프로그램에서 구현한 기수 정렬은 한 자릿수는 8비트, 즉 한 바이트로 매우 직관적이며 컴퓨터가 처리하기에도 좋은 형태입니다. 링크드
	 * 리스트 대신 원소 크기가 256인 기수 테이블 배열을 써 성능을 극대화했습니다. 현재의 BITOF 매크로는 숫자의 작은 자리수가
	 * 왼쪽에 먼저 저장되는 리틀 엔디언 컴퓨터에 맞게 작성된 것이며, BITOF 매크로와 count, index 배열을 바꾸면 임의의
	 * 기수와 자릿수를 써서 정렬할 수도 있습니다.
	 */
}