package aoc2021;

import java.util.Arrays;

import aocutil.IO;

public class Day07 {

	public static void main(String[] args) {
		int[] arr = getArr(); //new int[] { 16, 1, 2, 0, 4, 2, 7, 1, 2, 14 };

		double start = System.nanoTime();
		
		int min = Arrays.stream(arr).min().getAsInt();
		int max = Arrays.stream(arr).max().getAsInt();
		
		int minFuelA = Integer.MAX_VALUE;
		int minFuelB = Integer.MAX_VALUE;
		
		for (int target = min; target <= max; ++target) {
			int sumA = 0, sumB = 0;
			for (int i : arr) {
				int dist = Math.abs(i - target);
				int fuel = dist*(dist+1)/2;
				sumA += dist;
				sumB += fuel;
			}
			if (sumA < minFuelA) minFuelA = sumA;
			if (sumB < minFuelB) minFuelB = sumB;
		}
		
		double stop = System.nanoTime();
		
		System.out.println(minFuelA);
		System.out.println(minFuelB);
		System.out.println((stop-start)/1000000.0);
	}

	private static int[] getArr() {
		return Arrays.stream(IO.lines(2021, 7).findFirst().get().split(",")).mapToInt(Integer::parseInt).toArray();
	}

}
