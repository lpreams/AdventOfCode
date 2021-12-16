package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day08 {

	public static void main(String[] args) {
		int count1478 = 0;
		int sum = 0;

		for (Scanner line : IO.lines(2021, 8).map(Scanner::new).toList()) {
			List<String> all10 = new ArrayList<>();
			List<String> toBeMapped = new ArrayList<>();

			while (line.hasNext()) {
				String str = line.next();
				if (str.length() == 1) break; // found the |
				all10.add(sortChars(str));
			}

			while (line.hasNext()) {
				String str = line.next();

				int l = str.length();
				if (l == 2 || l == 3 || l == 4 || l == 7) ++count1478;

				toBeMapped.add(sortChars(str));
			}

			sum += map(all10, toBeMapped);
		}

		System.out.println(count1478);
		System.out.println(sum);
	}
	
	private static int map(List<String> all10, List<String> toBeMapped) {
		Map<String, Integer> map = new HashMap<>();
		
		HashSet<Character> L = null;
		HashSet<Character> ONE = null;

		for (String s : all10) {
			switch (s.length()) {
			case 2:
				map.put(s, 1);
				ONE = setify(s);
				break;
			case 3:
				map.put(s, 7);
				break;
			case 4:
				map.put(s, 4);
				L = setify(s);
				break;
			case 7:
				map.put(s, 8);
				break;
			}
		}

		L.removeAll(ONE);
		
		for (String s : all10) {
			int l = s.length();
			if (l == 2 || l == 3 || l == 4 || l == 7) continue;
			if (has(s, L)) { // 5,6,9
				if (has(s, ONE)) map.put(s, 9);
				else map.put(s, s.length()); // 5 or 6
			} else { // 0,2,3
				if (has(s, ONE)) { // 0, 3
					if (s.length() == 5) map.put(s, 3);
					else map.put(s, 0); // length==6
				} else map.put(s, 2);
			}
		}
		
		int ret = 0;
		for (String s : toBeMapped) {
			ret *= 10;
			ret += map.get(s);
		}
		return ret;
	}
	
	private static boolean has(String str, HashSet<Character> L_OR_ONE) {
		return str.chars().filter(c -> L_OR_ONE.contains(Character.valueOf((char)c))).count() ==L_OR_ONE.size();
	}
	
	private static String sortChars(String str) {
		char[] arr = str.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}

	private static HashSet<Character> setify(String str) {
		return str.chars().mapToObj(c -> Character.valueOf((char)c)).collect(Collectors.toCollection(HashSet::new));
	}
}
