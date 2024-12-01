package aoc2015;

import java.util.HashSet;
import java.util.Set;

import aocutil.IO;

public class Day03 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void a() {
		Point curr = new Point(0, 0);
		final HashSet<Point> visited = new HashSet<>(Set.of(curr));
		for (char c : IO.string(2015, 3).toCharArray()) {
			curr = curr.move(c);
			visited.add(curr);
		}
		System.out.println(visited.size());
	}

	private static void b() {
		Point santa = new Point(0, 0);
		Point robot = santa;
		final HashSet<Point> visited = new HashSet<>(Set.of(santa));
		for (char c : IO.string(2015, 3).toCharArray()) {
			santa = santa.move(c);
			visited.add(santa);
			Point temp = santa;
			santa = robot;
			robot = temp;
		}
		System.out.println(visited.size());
	}

	private static record Point(int x, int y) {
		public Point move(char c) {
			return switch (c) {
				case '^' -> new Point(x, y + 1);
				case 'v' -> new Point(x, y - 1);
				case '<' -> new Point(x - 1, y);
				case '>' -> new Point(x + 1, y);
				default -> throw new IllegalArgumentException();
			};
		}
	}
}
