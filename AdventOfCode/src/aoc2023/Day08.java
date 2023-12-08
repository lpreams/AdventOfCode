package aoc2023;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import aocutil.IO;

public class Day08 {

	public static void main(String[] args) {
		System.out.println(steps("AAA", true));
		System.out.println(map.keySet()
			.stream()
			.filter(name -> name.endsWith("A"))
			.mapToLong(start -> steps(start, false))
			.reduce(1, (a,b) -> a * b / gcd(a, b)));
	}
	
	private static final Map<String, Node> map;
	private static final String inst;
	static {
		final HashMap<String, Node> nodes = new HashMap<>();
		try (Scanner scan = IO.scanner(2023, 8)) {
			inst = scan.nextLine();
			scan.nextLine();
			while (scan.hasNext()) {
				final Node n = Node.parse(scan.nextLine());
				nodes.put(n.name(), n);
			}
		}
		map = Collections.unmodifiableMap(nodes);
	}

	private static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	private static int steps(String start, boolean allZs) {
		int steps = 0;
		for (int i = 0; allZs ? !start.equals("ZZZ") : !start.endsWith("Z"); i = (i + 1 < inst.length()) ? i + 1 : 0) {
			start = switch (inst.charAt(i)) {
				case 'L' -> map.get(map.get(start).left()).name();
				case 'R' -> map.get(map.get(start).right()).name();
				default -> "ZZZ";
			};
			++steps;
		}
		return steps;
	}

	private static record Node(String name, String left, String right) {
		static Node parse(String line) {
			return new Node(line.substring(0, 3), line.substring(7, 10), line.substring(12, 15));
		}
	}
}
