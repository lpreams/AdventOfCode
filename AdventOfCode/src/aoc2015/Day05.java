package aoc2015;

import java.util.stream.IntStream;

import aocutil.IO;

public class Day05 {

	public static void main(String[] args) {
		System.out.println(IO.lines(2015, 5).filter(Day05::nice).count());
		System.out.println(IO.lines(2015, 5).filter(Day05::nice2).count());
	}

	private static boolean nice(String s) {
		final int vowels = s.chars().map(c -> switch (c) {
			case 'a', 'e', 'i', 'o', 'u' -> 1;
			default -> 0;
		}).sum();
		if (vowels < 3) return false;

		boolean twice = false;
		char a = s.charAt(0);
		for (int i = 1; i < s.length(); ++i) {
			char b = s.charAt(i);
			if (a == 'a' && b == 'b') return false;
			if (a == 'c' && b == 'd') return false;
			if (a == 'p' && b == 'q') return false;
			if (a == 'x' && b == 'y') return false;
			if (a == b) twice = true;
			a = b;
		}
		return twice;
	}

	private static boolean nice2(String s) {
		if (!IntStream.range(0, s.length() - 2).anyMatch(i -> s.charAt(i) == s.charAt(i + 2))) return false;
		return IntStream.range(0, s.length() - 1).anyMatch(i -> s.substring(i + 2).contains(s.substring(i, i + 2)));
	}
}
