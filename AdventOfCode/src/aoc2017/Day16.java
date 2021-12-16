package aoc2017;

import java.util.Arrays;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day16 {

	private static int[] arr, spare;

	public static void main(String[] args) {
		arr = IntStream.range(0, 16).toArray();
		spare = new int[arr.length];
		String[] list = IO.string(2017, 16).split(",");
		dance(list);
		print();

		int dances = 1;

		do {
			dance(list);
			++dances;
		} while (!isSorted(arr));

		int remaining = 1000000000 % dances;
		while (remaining > 0) {
			dance(list);
			--remaining;
		}
		print();
	}

	private static boolean isSorted(int[] arr) {
		for (int i = 1; i < arr.length; ++i) if (arr[i - 1] > arr[i]) return false;
		return true;
	}

	private static void dance(String[] list) {
		for (int i = 0; i < 1; ++i) {

			for (String s : list) {
				char c = s.charAt(0);
				s = s.substring(1);
				String[] sarr = null;
				if (c == 's') {
					spin(Integer.parseInt(s));
				} else try {
					sarr = s.split("/");
					int x, y;
					if (c == 'x') {
						x = Integer.parseInt(sarr[0]);
						y = Integer.parseInt(sarr[1]);
					} else {
						if (c != 'p') throw new RuntimeException("you suck");
						x = indexOf(arr, sarr[0].charAt(0) - 'a');
						y = indexOf(arr, sarr[1].charAt(0) - 'a');
					}
					int temp = arr[x];
					arr[x] = arr[y];
					arr[y] = temp;
				} catch (NumberFormatException e) {
					throw new RuntimeException(c + " " + s + " " + Arrays.toString(sarr));
				}
			}
		}
	}

	private static void print() {
		Arrays.stream(arr).mapToObj(c -> Character.toString('a' + c)).forEach(System.out::print);
		System.out.println();
	}

	private static int indexOf(int[] arr, int c) {
		for (int i = 0; i < arr.length; ++i) if (arr[i] == c) return i;
		return -1;
	}

	private static void spin(int N) {
		cut(-N);
	}

	private static void cut(int N) {
		int[] newArr = spare;
		if (N > 0) {
			copyRange(arr, newArr, 0, arr.length - N, N);
			copyRange(arr, newArr, N, 0, arr.length - N);
		} else if (N < 0) {
			N = -N;
			copyRange(arr, newArr, arr.length - N, 0, N);
			copyRange(arr, newArr, 0, N, arr.length - N);
		}
		spare = arr;
		arr = newArr;
	}

	private static void copyRange(int[] in, int[] out, int startIn, int startOut, int num) {
		if (startIn + num > in.length) throw new IllegalArgumentException();
		if (startOut + num > out.length) throw new IllegalArgumentException();

		for (int i = 0; i < num; ++i) {
			out[i + startOut] = in[i + startIn];
		}
	}

}
