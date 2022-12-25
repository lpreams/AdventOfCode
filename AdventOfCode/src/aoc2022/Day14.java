package aoc2022;

import java.util.HashSet;
import java.util.Scanner;

import aocutil.IO;
import aocutil.Point;

public class Day14 {

	public static void main(String[] args) {
		run(false);
		run(true);
	}

	private static void run(boolean hasFloor) {
		final RockSet rocks = new RockSet(hasFloor);
		int numSands = 0;
		while (!rocks.contains(new Point(500, 0))) {
			if (rocks.simulateSand()) ++numSands;
			else break;
		}
		System.out.println(numSands);
	}

	private static class RockSet extends HashSet<Point> {

		private static final long serialVersionUID = -399122680762870735L;

		private final int bottom;
		private final boolean hasFloor;

		/**
		 * Simulates a single piece of sand falling
		 * 
		 * @param rocks  current rocks and settled sand
		 * @param bottom the lowest rock in the initial set
		 * @return true if the piece of sand settled, false if it fell into the abyss
		 */
		public boolean simulateSand() {
			Point sand = new Point(500, 0);
			while (true) {
				if (!hasFloor && sand.y > bottom) return false;
				if (!this.contains(sand.up())) sand = sand.up();
				else if (!this.contains(sand.ul())) sand = sand.ul();
				else if (!this.contains(sand.ur())) sand = sand.ur();
				else {
					this.add(sand);
					return true;
				}
			}
		}

		@Override
		public boolean contains(Object o) {
			if (o instanceof Point p) {
				if (hasFloor && p.y >= bottom + 2) return true;
				else return super.contains(o);
			} else {
				return false;
			}
		}

		public RockSet(boolean hasFloor) {
			this.hasFloor = hasFloor;
			try (Scanner scan = IO.scanner(2022, 14)) {
				while (scan.hasNext()) {
					final String[] pointStrings = scan.nextLine().split(" -> ");
					final Point[] points = new Point[pointStrings.length];
					for (int i = 0; i < points.length; ++i) {
						final String[] xyString = pointStrings[i].split(",");
						final int x = Integer.parseInt(xyString[0]);
						final int y = Integer.parseInt(xyString[1]);
						points[i] = new Point(x, y);
					}
					this.add(points[0]);
					for (int i = 1; i < points.length; ++i) {
						final Point start = points[i - 1];
						final Point end = points[i];
						if (start.x == end.x) {
							for (int y = Integer.min(start.y, end.y); y <= Integer.max(start.y, end.y); ++y) {
								this.add(new Point(start.x, y));
							}
						} else {
							for (int x = Integer.min(start.x, end.x); x <= Integer.max(start.x, end.x); ++x) {
								this.add(new Point(x, start.y));
							}
						}
					}

				}
			}
			this.bottom = this.stream().mapToInt(p -> p.y).max().getAsInt();
		}
	}
}
