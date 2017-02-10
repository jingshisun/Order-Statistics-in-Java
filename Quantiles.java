package edu.iastate.cs228.hw2;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class for generating and analyzing q-quantiles of datasets
 * 
 * @author Jingshi Sun
 */
public class Quantiles {

	/* Variables to store the quantiles, size of the data, totals, etc */

	/* NOTE: Do not store the original data */
	private int n;// the length of the original data array
	private int q; // the number of quantiles
	private int[] quantiles; // stores the q-1 q-quantiles
	private int topTotal;
	private int bottomTotal;

	/**
	 * A constructor that creates a new Quantiles object, whose quantiles array
	 * has length q - 1, and contains the q-quantiles of the data array. The
	 * expected time complexity of this method must be O(n * q), or better,
	 * where n = data.length.
	 * 
	 * @param data
	 *            - The integers to split into q-quantiles
	 * @param q
	 *            - The number of q-quantiles to create
	 * 
	 * @throws IllegalArgumentException
	 *             - If q < 1 or if q > n
	 */
	public Quantiles(int[] data, int qu) {

		n = data.length;
		q = qu;
		if (q <= 1 || q > n) {
			throw new IllegalArgumentException("q is out of bound.");
		}

		quantiles = new int[q - 1];

		for (int i = 0; i < quantiles.length; i++) { // This for loop is to
														// record the quantiles
														// array.
			quantiles[i] = OrderStatistics.findOrderStatistic(data,
					(int) Math.ceil((float) (i + 1) * ((float) n / (float) q)));
		}
		for (int i = 0; i < data.length; i++) { // This for loop is to record
												// the bottomTotal with a if
												// statement inside.
			if (data[i] <= quantiles[0]) {
				bottomTotal += data[i];
			}

		}
		for (int i = 0; i < data.length; i++) { // This for loop is to record
												// the topTotal with a if
												// statement inside.
			if (data[i] > quantiles[q - 2]) {
				topTotal += data[i];
			}

		}

	}

	/**
	 * A constructor that creates a new Quantiles object, whose quantiles array,
	 * has length three, and is initialized to contain the three quantiles of
	 * data. The expected time complexity of this method must be O(n), where n =
	 * data.length.
	 * 
	 * @param data
	 *            - The integers to split into q-quantiles
	 * @throws IllegalArgumentException
	 *             - If n < 4
	 */
	public Quantiles(int[] data) {

		this(data, 4); // I call the constructor above with q equals to 4. This
						// could decrease the duplicated code significantly.
	}

	/**
	 * An optional constructor that creates a new Quantiles object, whose
	 * quantiles array has length q - 1, and is initialized to contain the
	 * q-quantiles of the data array.
	 * 
	 * If fast is true, then the expected time complexity of this method must be
	 * O(n log q), where n = data.length, where n = data.length (for this, you
	 * must implement the method such as the one outlined in Section 2.2.
	 * 
	 * If fast is false, then the expected time complexity of this method is
	 * only required to be O(n * q), but may be faster
	 * 
	 * @param data
	 *            - The integers to split into q-quantiles
	 * @param q
	 *            - The number of q-quantiles to create
	 * @param fast
	 *            - Flag to request a O(n log q) construction
	 * 
	 * @throws IllegalArgumentException
	 *             - If q < 1 or if q > n
	 */
	public Quantiles(int[] data, int qu, boolean fast) {
		// TODO
		q = qu;
		if (q <= 1 || q > data.length) {
			throw new IllegalArgumentException(
					"q need to be greater than one and less than or equal to data.length");
		}
		if (fast == true) // if it fast==true is true, we will run O(n*log q)
							// expecting times
		{
			int first = 0;
			int last = q - 2;
			Quantilesfast(data, first, last); // this is the helper method, the
												// detail is shown below. The
												// expecting time is O(n*log q).
			quickSort(quantiles, 0, q - 2); // this is the helper method, the
											// detail is shown below. The
											// expecting time is O(logq).

		}

		else if (fast == false) {
			QuantilesSlow(data, q); // this is the helper method which is
									// exactly the
									// same as the first Quantiles constructor
									// runs at O(n*q) expecting time.
		}

	}

	/**
	 * The quickSort helper method. It sort the arr recursively.
	 * 
	 * @param arr
	 * @param left
	 * @param right
	 */
	private void quickSort(int[] arr, int left, int right) {

		int idx = partition(arr, left, right);
		if (left < idx - 1) {
			quickSort(arr, left, idx - 1);
		}
		if (idx < right) {
			quickSort(arr, idx, right);
		}

	}

	/**
	 * The partition helper method. The expected running time for the partition
	 * method is O(logn), n equals last-first+1.
	 * 
	 * @param arr
	 * @param first
	 * @param last
	 * @return the position index of p.
	 */
	private int partition(int[] arr, int first, int last) {

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
			if (arr[k] <= pivot) {
				j++;
				temp = arr[j];
				arr[j] = arr[k];
				arr[k] = temp;
				if (k == randomIndex) {
					randomIndex = j;
				}
			}
		}
		int p;// the position index of pivot p.
		if (randomIndex > j) {
			temp = arr[j + 1];
			arr[j + 1] = arr[randomIndex];
			arr[randomIndex] = temp;
			p = j + 1;
		} else {
			temp = arr[j];
			arr[j] = arr[randomIndex];
			arr[randomIndex] = temp;
			p = j;
		}
		return p;
	}

	/**
	 * The QuantileSlow helper method. This helper method is directly copied
	 * from the first Quantiles constructor.
	 * 
	 * @param data
	 * @param qu
	 * @throws IllegalArgumentException
	 *             - If q < 1 or if q > n
	 * 
	 */
	private void QuantilesSlow(int[] data, int qu) {

		n = data.length;
		q = qu;
		if (q <= 1 || q > n) {
			throw new IllegalArgumentException("q is out of bound.");
		}

		quantiles = new int[q - 1];

		for (int i = 0; i < quantiles.length; i++) {
			quantiles[i] = OrderStatistics.findOrderStatistic(data,
					(int) Math.ceil((float) (i + 1) * ((float) n / (float) q)));
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] <= quantiles[0]) {
				bottomTotal += data[i];
			}

		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] > quantiles[q - 2]) {
				topTotal += data[i];
			}

		}

	}

	/**
	 * the Quantilesfast helper method. The running time should be O(n*log q).
	 * 
	 * @param data
	 * @param qfirst
	 * @param qlast
	 */
	private void Quantilesfast(int[] data, int qfirst, int qlast) {
		ArrayList<Integer> stuff = new ArrayList<>();// I construct an arrayList
														// in order to record
														// the quantiles.

		if (qfirst == qlast) {
			stuff.add(OrderStatistics.findOrderStatistic(data, qfirst));
			for (int i = 0; i < q - 1; i++) // the for loop records the
											// arrayList to the array runs at
											// O(q)
			{
				quantiles[i] = stuff.get(i); // at the end of the loop, I get
												// quantiles array without
												// order.
			}
		}

		if (qfirst < qlast) {
			// use select to find the kth q-quantile.
			int k = (int) Math
					.ceil(((float) ((qlast - qfirst + 1) / (n / q) - 1) / (float) 2));
			int mid = (int) Math.ceil((float) k * (float) n / (float) q);
			stuff.add(OrderStatistics.findOrderStatistic(data, mid));

			Quantilesfast(data, qfirst, mid);
			Quantilesfast(data, mid + 1, qlast);

		}

	}

	/**
	 * Returns the k-th q-quantile of this object. This method must take O(1)
	 * time in the worst case.
	 * 
	 * @param k
	 *            - Specifies which q-quantile to return
	 * @return - The k-th q-quantile
	 * 
	 * @throws IllegalArgumentException
	 *             - If k < 1 or k is greater than the number of quantiles of
	 *             this object
	 */
	public int getQuantile(int k) {

		if (k < 1 || k > quantiles.length) {
			throw new IllegalArgumentException("k is out of bound");
		}
		return quantiles[k - 1]; // The k-th q-quantile has index of k-1. So
									// here returns quantiles[k-1].
	}

	/**
	 * Returns the number of quantiles in this object. This method must take
	 * O(1) time in the worst case.
	 * 
	 * @return - The number of quantiles in this object
	 */
	public int getQ() {

		return quantiles.length;
	}

	/**
	 * Returns the index of the quantile group that contains x:
	 * 
	 * - If x is less than or equal to the first quantile, then return 1.
	 * 
	 * - If x is strictly greater than the last quantile (quantile q - 1), then
	 * return q.
	 * 
	 * - Otherwise, return the smallest index k such that x is less than or
	 * equal to the k-th q- quantile.
	 * 
	 * This method must take O(log q) time in the worst case.
	 * 
	 * @param x
	 *            - The item to find the quantile of
	 * @return - The quantile containing x as described above
	 */
	public int quantileQuery(int x) {
		// TODO

		if (x <= quantiles[0]) {
			return 1;
		}
		if (x > quantiles[q - 2]) {
			return q;
		}
		int left = 0;
		int right = q - 2;
		int mid = 0;
		while (left < right) { // the while loop use a binary search algorithm
								// to find the quantile containing x.
			mid = (left + right) / 2;
			if (x <= quantiles[mid]) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return mid + 2;
	}

	/**
	 * Returns the sum of all values that are strictly higher than the (q - 1)th
	 * q-quantile in the original data array. This method must take O(1) time in
	 * the worst case.
	 * 
	 * @return - sum of all values that are strictly higher than the (q - 1)th
	 *         q-quantile
	 */
	public int getTopTotal() {

		return topTotal;
	}

	/**
	 * Returns the sum of all vales that are smaller than or equal to the first
	 * quantile in the original data array. This method must take O(1) time in
	 * the worst case.
	 * 
	 * @return - the sum of all vales that are smaller than or equal to the
	 *         first quantile
	 */
	public int getBottomTotal() {

		return bottomTotal;
	}

	/**
	 * Returns the ratio of getTopTotal() to getBottomTotal() for this object.
	 * This method must take O(1) time in the worst case.
	 * 
	 * @return - the ratio
	 */
	public float ineqRatio() {

		return (float) topTotal / (float) bottomTotal;
	}

	/**
	 * Returns the length of the original data array. This method must take O(1)
	 * time in the worst case.
	 * 
	 * @return - the size of the original data array
	 */
	public int size() {

		return n;
	}

	/**
	 * Overrides the toString() method, returning a String in the format given
	 * on page 6 of the project specifications.
	 * 
	 * @return - the String representation of this object
	 */
	@Override
	public String toString() {

		String outPut = new String();
		for (int i = 0; i < quantiles.length; i++) { // use the for loop to
														// record the quantiles
														// in a string outPut.
			if (i == 0) {
				outPut += "[" + quantiles[i] + ", "; // if it is the first
														// quantile, include the
														// "[" before the
														// quantile. and ", "
														// after it.
			} else if (i == quantiles.length - 1) {
				outPut += quantiles[i] + "]"; // if it is the last quantile,
												// include the "]" after it.
			} else {
				outPut += quantiles[i] + ", "; // else, include the ", " after
												// it.

			}

		}
		return n + ", " + q + ", " + outPut + ", " + topTotal + ", "
				+ bottomTotal + "\n";
	}
}
