package aoc2021;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

import java.util.HashMap;

import aocutil.IO;

public class Day05 {

	public static void main(String[] args) {
		HashMap<String, Integer> counts1 = new HashMap<>();
		HashMap<String, Integer> counts2 = new HashMap<>();

		for (String[] line : IO.lines(2021, 5).map(l -> l.split(" -> ")).toList()) {
			String[] a = line[0].split(",");
			String[] b = line[1].split(",");

			int x1 = Integer.parseInt(a[0]);
			int y1 = Integer.parseInt(a[1]);
			int x2 = Integer.parseInt(b[0]);
			int y2 = Integer.parseInt(b[1]);

			if (x1 == x2) {

				for (int y = min(y1, y2); y <= max(y1, y2); ++y) {
					String point = x1 + "|" + y;
					counts1.merge(point, 1, (t, u) -> t + u);
					counts2.merge(point, 1, (t, u) -> t + u);
				}

			} else if (y1 == y2) {
				for (int x = min(x1, x2); x <= max(x1, x2); ++x) {
					String point = x + "|" + y1;
					counts1.merge(point, 1, (t, u) -> t + u);
					counts2.merge(point, 1, (t, u) -> t + u);
				}
			} else {

				if (x1 < x2 == y1 < y2) {
					for (int x = min(x1, x2), y = min(y1, y2); x <= max(x1, x2);) {
						String point = x + "|" + y;
						counts2.merge(point, 1, (t, u) -> t + u);
						++x;
						++y;
					}
				} else {
					for (int x = min(x1, x2), y = max(y1, y2); x <= max(x1, x2);) {
						String point = x + "|" + y;
						counts2.merge(point, 1, (t, u) -> t + u);
						++x;
						--y;
					}
				}

			}

		}

		System.out.println(counts1.entrySet().stream().filter(e -> e.getValue() > 1).count());
		System.out.println(counts2.entrySet().stream().filter(e -> e.getValue() > 1).count());
	}

}
