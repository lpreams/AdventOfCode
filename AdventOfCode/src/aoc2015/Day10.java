package aoc2015;

import aocutil.IO;

public class Day10 {

	public static void main(String[] args) {
		String str = IO.string(2015, 10);
		for (int i = 0; i < 40; ++i) str = say(str);
		System.out.println(str.length());
		for (int i = 0; i < 10; ++i) str = say(str);
		System.out.println(str.length());
	}

	private static String say(String s) {
		final StringBuilder sb = new StringBuilder();
		final char[] arr = s.toCharArray();
		int count = 1;
		for (int i = 0; i < arr.length; ++i) {
			if (i < arr.length - 1 && arr[i] == arr[i + 1]) {
				++count;
			} else {
				sb.append(count);
				sb.append(arr[i]);
				count = 1;
			}
		}
		return sb.toString();
	}

}
