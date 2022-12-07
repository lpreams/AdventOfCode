package aoc2022;

import java.util.HashSet;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		final String input = IO.string(2022, 6);
		f(input, 4);
		f(input, 14);
	}

	private static void f(String input, int n) {		
		for (int i=0; i<input.length()-n+1; ++i) {
			HashSet<Character> set = new HashSet<>();
			for (int o=0; o<n; ++o) {
				if (!set.add(input.charAt(i+o))) break;
			}
			if (set.size() == n) {
				System.out.println(i+n);
				break;
			}
		}
	}
}
