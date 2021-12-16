package aoc2017;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;

import aocutil.IO;

public class Day08 {

	public static void main(String[] args) {
		HashMap<String, Integer> memory = new HashMap<>();
		int maxVal = Integer.MIN_VALUE;
		for (Scanner line : IO.lines(2017, 8).map(Scanner::new).toList()) {
			String reg = line.next();
			String op = line.next();
			int lit = Integer.parseInt(line.next()) * (op.equals("inc") ? 1 : -1);

			line.next(); // if
			String ifreg = line.next();
			String ifop = line.next();
			int iflit = Integer.parseInt(line.next());

			BiFunction<Integer, Integer, Boolean> comparer = switch (ifop) {
			case "==" -> ((a, b) -> a.equals(b));
			case "!=" -> ((a, b) -> !a.equals(b));
			case "<=" -> ((a, b) -> a <= b);
			case ">=" -> ((a, b) -> a >= b);
			case "<" -> ((a, b) -> a < b);
			case ">" -> ((a, b) -> a > b);
			default -> throw new IllegalArgumentException(ifop);
			};

			int ifval = memory.getOrDefault(ifreg, 0);
			if (comparer.apply(ifval, iflit)) {
				int newVal = memory.merge(reg, lit, (a, b) -> a + b);
				if (newVal > maxVal) maxVal = newVal;
			}

		}
		int max = memory.values().stream().reduce(-1, (a, b) -> (a > b ? a : b));
		System.out.println(max);
		System.out.println(maxVal);
	}

}
