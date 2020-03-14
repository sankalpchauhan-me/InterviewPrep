package dp;

import java.util.Arrays;

//https://www.youtube.com/watch?v=CE2b_-XfVDk&list=PLrmLmBdmIlpsHaNTPP_jHHDx_os9ItYXr&index=7
//https://www.youtube.com/watch?v=S9oUiVYEq7E
//takes O(n2) using DP but can be solved in nlogn using Binary search
public class LongestIncreasingSubsequence {

	/**
	 * DP way of solving LIS
	 */
	public int longestSubsequenceWithActualSolution(int arr[]) {
		int T[] = new int[arr.length];
		int actualSolution[] = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			T[i] = 1;
			actualSolution[i] = i;
		}

		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[i] > arr[j]) {
					T[i] = Integer.max(T[i], T[j] + 1);
					// set the actualSolution to point to guy before me
					actualSolution[i] = j;
				}
			}
		}

		System.out.println("Actual sol:::" + Arrays.toString(actualSolution));
		// find the index of max number in T
		int maxIndex = 0;
		for (int i = 0; i < T.length; i++) {
			if (T[i] > T[maxIndex]) {
				maxIndex = i;
			}
		}

		// lets print the actual solution
		int t = maxIndex;
		int newT = maxIndex;
		do {
			t = newT;
			System.out.print(arr[t] + " ");
			newT = actualSolution[t];
		} while (t != newT);
		System.out.println();

		return T[maxIndex];
	}

	/**
	 * Recursive way of solving LIS
	 */
	public int longestSubsequenceRecursive(int arr[]) {
		int maxLen = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			int len = longestSubsequenceRecursive(arr, i + 1, arr[i]);
			if (len > maxLen) {
				maxLen = len;
			}
		}
		return maxLen + 1;
	}

	private int longestSubsequenceRecursive(int arr[], int pos, int lastNum) {
		if (pos == arr.length) {
			return 0;
		}
		int t1 = 0;
		if (arr[pos] > lastNum) {
			t1 = 1 + longestSubsequenceRecursive(arr, pos + 1, arr[pos]);
		}
		int t2 = longestSubsequenceRecursive(arr, pos + 1, lastNum);
		return Math.max(t1, t2);
	}

	public static void main(String args[]) {
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
		int arr[] = { 23, 10, 22, 5, 33, 8, 9, 21, 50, 41, 60, 80, 99, 22, 23, 24, 25, 26, 27 };
		int result = lis.longestSubsequenceWithActualSolution(arr);
//		int result1 = lis.longestSubsequenceRecursive(arr);
//		System.out.println(result);
//		System.out.println(result1);
	}
}
