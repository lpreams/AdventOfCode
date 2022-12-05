package aoc2017;

import aocutil.IO;

public class Day01 {

	public static void main(String[] args) {
		String s = IO.string(2017, 1).trim();
		int len = s.length();
		
		s = s+s;
		
		int sumA = 0;
		int sumB = 0;
		for (int i = 0; i < len; ++i) {
			if (s.charAt(i) == s.charAt(i + 1)) sumA += s.charAt(i) - '0';
			if (s.charAt(i) == s.charAt(i + len/2)) sumB += s.charAt(i) - '0';
		}
		System.out.println(sumA);
		System.out.println(sumB);
	}

}
