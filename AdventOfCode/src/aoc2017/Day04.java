package aoc2017;

import java.util.HashSet;
import java.util.Scanner;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) {
		long num = IO.lines(2017, 4).filter(line -> {
			HashSet<String> set = new HashSet<>();
			try (Scanner scan = new Scanner(line)) {
				while (scan.hasNext()) {
					String next = scan.next(); // part 1
//					String next = scan.next().chars().sorted().mapToObj(Character::toString).collect(Collectors.joining()); // part 2
					if (!set.add(next)) {
						return false;
					}
				}
			}
			return true;
		}).count();

		System.out.println(num);
	}
}
