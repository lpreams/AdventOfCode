package aoc2021;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import aocutil.IO;

public class Day14 {

	public static void main(String[] args) {
		f(10);
		f(40);
	}

	private static void f(int iterations) {
		HashMap<String, Long> f = breakIntoPairs(start);
		for (int i = 0; i < iterations; ++i) {
			f = insertion(f);
		}

		HashMap<Character, Long> f2 = new HashMap<>();
		for (var e : f.entrySet()) {
			f2.merge(e.getKey().charAt(0), e.getValue(), (x, y) -> x + y);
			f2.merge(e.getKey().charAt(1), e.getValue(), (x, y) -> x + y);
		}
		f2.remove(' '); // ignore beginning/end spaces
		long min = f2.values().stream().mapToLong(x -> x).min().getAsLong() / 2l; // we double counted every character
		long max = f2.values().stream().mapToLong(x -> x).max().getAsLong() / 2l;
		System.out.println(max - min);
	}

	private static HashMap<String, Long> insertion(HashMap<String, Long> pairs) {
		HashMap<String, Long> ret = new HashMap<>();
		for (var e : pairs.entrySet()) {

			String pair = e.getKey();

			if (pair.contains(" ") || !map.containsKey(pair)) {
				// handle beginning/ending pairs, and any pairs not in the map
				ret.merge(pair, e.getValue(), (g, h) -> g + h);
				continue;
			}

			String z = map.get(pair).charAt(0) + "";
			String a = pair.charAt(0) + "" + z;
			String b = z + pair.charAt(1);

			ret.merge(a, e.getValue(), (g, h) -> g + h);
			ret.merge(b, e.getValue(), (g, h) -> g + h);

		}
		return ret;
	}

	private static HashMap<String, Long> breakIntoPairs(String s) {
		HashMap<String, Long> set = new HashMap<String, Long>();
		// make sure first and last char are double-counted, since every other char will be
		String begin = " " + s.charAt(0);
		set.put(begin, 1l);
		for (int i = 1; i < s.length(); ++i) {
			String pair = s.substring(i - 1, i + 1);
			set.merge(pair, 1l, (a, b) -> a + b);
		}
		String end = s.charAt(s.length() - 1) + " ";
		set.put(end, 1l);
		return set;
	}
	
	private static final Map<String, String> map;
	private static final String start;
	static {
		try (Scanner scan = IO.scanner(2021, 14)) {
			start = scan.nextLine();
			scan.nextLine(); // empty
			HashMap<String, String> temp = new HashMap<>();
			while (scan.hasNext()) {
				String[] line = scan.nextLine().split(" -> ");
				temp.put(line[0], line[1]);
			}
			map = Collections.unmodifiableMap(temp);
		}
	}
}
