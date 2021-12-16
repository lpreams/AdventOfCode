package aoc2017;

import aocutil.IO;

public class Day05 {

	public static void main(String[] args) {
		doTheThing(true);
		doTheThing(false);
	}

	public static void doTheThing(boolean pt1) {
		int[] arr = IO.lines(2017, 5).mapToInt(Integer::parseInt).toArray();

		int i = 0;
		int steps = 0;
		while (i < arr.length) {
			int jump = arr[i];
			if (pt1 || jump < 3) ++arr[i];
			else --arr[i];
			i += jump;
			++steps;
		}

		System.out.println(steps);
	}
}
