package slidingWindow;

//Largest/Max Sum Contiguous Subarray
//https://www.youtube.com/watch?v=2MmGzdiKR9Y
//Max subarray sum (works for negative numbers as well)
//https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
public class KadanesALgo {

	static int maxSubArraySum(int a[], int size) {
		int max_so_far = a[0];
		int curr_max = a[0];

		for (int i = 1; i < size; i++) {
			curr_max = Math.max(a[i], curr_max + a[i]);// required incase a negative number+positive number,current sum
														// may be max for only positive number
			max_so_far = Math.max(max_so_far, curr_max);// compare existing max
		}
		return max_so_far;
	}

	static int maxSubArraySum(int a[]) {
		int size = a.length;
		int max_so_far = Integer.MIN_VALUE, max_ending_here = 0;

		for (int i = 0; i < size; i++) {
			max_ending_here = max_ending_here + a[i];
			if (max_so_far < max_ending_here)
				max_so_far = max_ending_here;
			if (max_ending_here < 0)
				max_ending_here = 0;
		}
		return max_so_far;
	}

	/* Driver program to test maxSubArraySum */
	public static void main(String[] args) {
		int a[] = { -2, -3, 4, -1, -2, 1, 5, -3 };
		int n = a.length;
		int max_sum = maxSubArraySum(a, n);
		System.out.println("Maximum contiguous sum is " + max_sum);
	}
}
