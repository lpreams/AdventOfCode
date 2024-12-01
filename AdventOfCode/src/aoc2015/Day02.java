package aoc2015;

import java.util.Arrays;
import java.util.stream.Stream;

import aocutil.IO;

public class Day02 {

	public static void main(String[] args) {
		System.out.println(get().mapToInt(arr -> surface(arr[0], arr[1], arr[2])).sum());
		System.out.println(get().mapToInt(arr -> ribbon(arr[0], arr[1], arr[2])).sum());
	}

	private static Stream<int[]> get() {
		return IO.lines(2015, 2).map(line -> Arrays.stream(line.split("x")).mapToInt(Integer::parseInt).toArray());
	}

	private static int surface(int l, int w, int h) {
		final int lw = l * w, wh = w * h, hl = h * l;
		return (lw + wh + hl) * 2 + Integer.min(Integer.min(lw, wh), hl);
	}

	private static int ribbon(int l, int w, int h) {
		return Integer.min(Integer.min(l + w, w + h), h + l) * 2 + l * w * h;
	}

}
