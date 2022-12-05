package aoc2022;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		System.out.println(IO.lines(2022, 4).map(Day04::parseLine)
				.filter(a -> a[0] <= a[3] && a[2] <= a[1])
				.count());
	}

	private static void a() {
		System.out.println(IO.lines(2022, 4).map(Day04::parseLine)
				.filter(a -> (a[0] <= a[2] && a[1] >= a[3]) || (a[2] <= a[0] && a[3] >= a[1]))
				.count());
	}

	private static int[] parseLine(String line) {
		String[] arr = line.split(",");
		String[] left = arr[0].split("-");
		String[] right = arr[1].split("-");
		int a = Integer.parseInt(left[0]);
		int b = Integer.parseInt(left[1]);
		int c = Integer.parseInt(right[0]);
		int d = Integer.parseInt(right[1]);
		return new int[] {a, b, c, d};
	}

}
