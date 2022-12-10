package aoc2022;

import java.util.ArrayList;
import java.util.HashSet;

import aocutil.IO;
import aocutil.Point;

public class Day09 {

	public static void main(String[] args) {
		for (int N : new int[]{2, 10}) {
			HashSet<Point> visited = new HashSet<>();
			ArrayList<Point> rope = new ArrayList<>();
			for (int i=0; i<N; ++i) rope.add(new Point(0, 0));
			visited.add(new Point(0, 0));
			for (String[] line : IO.linesIterSplit(2022, 9, " ")) {
				for (int n = 0; n < Integer.parseInt(line[1]); ++n) {
					Point newHead = switch (line[0]) {
						case "U" -> new Point(rope.get(0).x, rope.get(0).y + 1);
						case "D" -> new Point(rope.get(0).x, rope.get(0).y - 1);
						case "L" -> new Point(rope.get(0).x - 1, rope.get(0).y);
						case "R" -> new Point(rope.get(0).x + 1, rope.get(0).y);
						default -> throw new RuntimeException();
					};
					rope.set(0, newHead);
					for (int i=1; i<rope.size(); ++i) rope.set(i, move(rope.get(i-1), rope.get(i)));
					visited.add(rope.get(rope.size()-1));
				}
			}
			System.out.println(visited.size());
		}
	}
	
	private static Point move(Point h, Point t) {
		final int x = h.x - t.x; // position of h relative to t
		final int y = h.y - t.y; // position of h relative to t

		  if (Math.abs(x) <= 1 && Math.abs(y) <= 1) return t;

		if (x >= 2 && y == 0) return new Point(t.x + 1, t.y);
		if (x <= -2 && y == 0) return new Point(t.x - 1, t.y);
		if (y >= 2 && x == 0) return new Point(t.x, t.y + 1);
		if (y <= -2 && x == 0) return new Point(t.x, t.y - 1);

		if (x <= -2 && y <= -1) return new Point(t.x - 1, t.y - 1);
		if (x >= 2 && y >= 1) return new Point(t.x + 1, t.y + 1);
		if (x <= -1 && y <= -2) return new Point(t.x - 1, t.y - 1);
		if (x >= 1 && y >= 2) return new Point(t.x + 1, t.y + 1);

		if (x <= -2 && y >= 1) return new Point(t.x - 1, t.y + 1);
		if (x >= 2 && y <= -1) return new Point(t.x + 1, t.y - 1);
		if (x >= 1 && y <= -2) return new Point(t.x + 1, t.y - 1);
		if (x <= -1 && y >= 2) return new Point(t.x - 1, t.y + 1);

		throw new RuntimeException();
	}
}
