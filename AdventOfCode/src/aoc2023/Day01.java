package aoc2023;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;

import aocutil.IO;

public class Day01 {

	public static void main(String[] args) {
		a();
		b();
	}

	private static void a() {
		System.out.println(IO.lines(2023, 1).mapToInt(line -> {
			final int[] arr = line.chars().filter(c -> '0' <= c && c <= '9').toArray();
			if (arr.length == 0) return 0;
			return (arr[0] - '0') * 10 + arr[arr.length - 1] - '0';
		}).sum());
	}

	private static void b() {
		System.out.println(IO.lines(2023, 1).mapToInt(line -> {
			return firstLastInt(line, true) * 10 + firstLastInt(line, false);
		}).sum());
	}

	private static int firstLastInt(String line, boolean first) {
		final BiPredicate<String,String> pred = first ? String::startsWith : String::endsWith;
		final UnaryOperator<String> next = first ? str -> str.substring(1) : str -> str.substring(0, str.length()-1);
		while (line.length() > 0) {
			final char start = line.charAt(first ? 0 : (line.length() - 1));
			if ('0' <= start && start <= '9') {
				return start - '0';
			}
			for (var e : NUMS.entrySet()) {
				if (pred.test(line, e.getKey())) {
					return e.getValue();
				}
			}
			line = next.apply(line);
		}
		return 0;
	}
	
	private static final Map<String, Integer> NUMS = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);
}
