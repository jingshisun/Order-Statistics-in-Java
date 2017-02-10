package edu.iastate.cs228.hw2;

import java.util.Random;

/**
 * A class used to generate order statistics of datasets
 * 
 * @author Jingshi Sun
 */
public class OrderStatistics {

	/**
	 * Returns the minimum element (first order statistic) in array arr. This
	 * method must run in worst-case O(n) time, where n = arr.length, using a
	 * linear scan of the input array.
	 * 
	 * @param arr
	 *            - The data to search
	 * @return - the minimum element of arr
	 */
	public static int findMinimum(int[] arr) {

		if (arr == null) // check if the array is null. If it is null, throws
							// IllegalArgumentException.
		{
			throw new IllegalArgumentException("The array given is null");
		}
		int min = arr[0]; // min is used to find the minimum element. Here we
							// let it equals to arr[0] temporarily.
		for (int i = 0; i < arr.length; i++)// use for loop to find the minimum
											// element.
		{
			if (min > arr[i]) {
				min = arr[i];
			}
		}
		return min;
	}

	/**
	 * Returns the maximum element (first order statistic) in array arr. This
	 * method must run in worst-case O(n) time, where n = arr.length, using a
	 * linear scan of the input array.
	 * 
	 * @param arr
	 *            - The data to search
	 * @return - the maximum element of arr
	 */
	public static int findMaximum(int[] arr) {

		if (arr == null) // check if the array is null. If it is null, throws
							// IllegalArgumentException.
		{
			throw new IllegalArgumentException("The array given is null");
		}
		int max = arr[0]; // max is used to find the maximum element. Here we
							// let it equals to arr[0] temporarily.
		for (int i = 0; i < arr.length; i++) // use for loop to find the maximum
												// element.
		{
			if (max < arr[i]) {
				max = arr[i];
			}
		}
		return max;
	}

	/**
	 * An implementation of the SELECT algorithm of Figure 1 of the project
	 * specification. Returns the ith order statistic in the subarray
	 * arr[first], ..., arr[last]. The method must run in O(n) expected time,
	 * where n = (last - first + 1).
	 * 
	 * @param arr
	 *            - The data to search in
	 * @param first
	 *            - The leftmost boundary of the subarray (inclusive)
	 * @param last
	 *            - The rightmost boundary of the subarray (inclusive)
	 * @param i
	 *            - The requested order statistic to find
	 * @return - The ith order statistic in the subarray
	 * 
	 * @throws IllegalArgumentException
	 *             - If i < 1 or i > n
	 */
	public static int select(int[] arr, int first, int last, int i) {
		if (i < 1 || i > (last - first + 1))// Check if i is out of bound. If
											// so, throw an
											// IllegalArgumentException.
		{
			throw new IllegalArgumentException(
					"i must be greater or equal to 1 and lease than or equal to last-first+1");
		}
		if (first == last) {
			return arr[first];
		}
		// Below is the partition method until we get the position p.
		Random ran = new Random();
		int temp;
		int randomIndex = ran.nextInt(last - first + 1) + first;// the index of
																// the pivot is
																// randomly
																// selected.
		int pivot = arr[randomIndex];
		int j = first - 1;
		int k; // j and k are the pointers.
		for (k = first; k <= last; k++) {
			if (arr[k] <= pivot)// if arr[k]is less than or equal to pivot
								// value, I wnat to put arr[k] to the left fo
								// the pivot value. So I firstly increment j by
								// 1, then swap arr[j] and arr[k].
			{
				j++;
				temp = arr[j];
				arr[j] = arr[k];
				arr[k] = temp;
				if (k == randomIndex) {
					randomIndex = j;
				}
			}
		}
		int p;// the position of pivot p.
		if (randomIndex > j)// if randomIndex is greater than j, swap arr[j+1]
							// and arr[randomIndex].
		{
			temp = arr[j + 1];
			arr[j + 1] = arr[randomIndex];
			arr[randomIndex] = temp;
			p = j + 1;
		} else // if randomIndex is less than or equal to j, swap arr[j] and
				// arr[randomIndex].
		{
			temp = arr[j];
			arr[j] = arr[randomIndex];
			arr[randomIndex] = temp;
			p = j;
		}

		// m stands for the number of elements in arr[first],...,arr[p].
		int m = p - first + 1;
		// blow is the recursion. If i == m true, the recursion end, and return
		// arr[p].
		if (i == m) {
			return arr[p];
		} else if (i < m)// If i<m, we recursively select i between first and
							// p-1.
		{
			return select(arr, first, p - 1, i);
		} else // If i>m, we recursively select i between p+1 and last.
		{
			return select(arr, p + 1, last, i - m);
		}

	}

	/**
	 * Returns the ith order statistic of array arr in O(n) expected time, where
	 * n = arr.length.
	 * 
	 * @param arr
	 *            - The data to search through
	 * @param i
	 *            - The requested order statistic to find in arr
	 * @return - The ith order statistic in arr
	 * 
	 * @throws IllegalArgumentException
	 *             - If i < 1 or i > n
	 */
	public static int findOrderStatistic(int[] arr, int i) {
		
		int first = 0;
		int last = arr.length - 1;
		return select(arr, first, last, i);// Call select method here can
											// decrease duplicated codes
											// significantly.

	}

	/**
	 * Returns the median (n/2th order statistic rounding up) in array arr in
	 * O(n) expected time, where n = arr.length.
	 * 
	 * @param arr
	 *            - The array to find the median of
	 * @return - The median value of arr
	 */
	public static int findMedian(int[] arr) {
		
		int first = 0;
		int last = arr.length - 1;
		int i = (int) Math.ceil((float) arr.length / (float) 2);
		return select(arr, first, last, i);// Call select method here can
											// decrease duplicated codes
											// significantly.
	}
}
