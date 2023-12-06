package aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aocutil.IO;

public class Day03 {

	public static void main(String[] args) {
		final Grid g = new Grid(IO.lines(2023, 3).toList());
		int sum = 0;
		final HashMap<Coord, List<Integer>> gears = new HashMap<>();

		final StringBuilder sb = new StringBuilder();
		final Set<Coord> coords = new HashSet<>();

		for (int y = 0; y < g.height(); ++y) {
			for (int x = 0; x < g.width() + 1; ++x) {
				final char c = g.at(x, y);
				if (isNumber(c)) {
					sb.append(c);
					coords.addAll(g.neighborSymbols(x, y));
				} else {
					if (sb.length() > 0) {
						if (!coords.isEmpty()) {
							final int val = Integer.parseInt(sb.toString());
							sum += val;
							coords.stream()
								.filter(coord -> coord.c() == '*')
								.forEach(coord -> gears.computeIfAbsent(coord, z -> new ArrayList<>()).add(val));
							coords.clear();
						}
						sb.setLength(0);
					}
				}

			}
		}
		System.out.println(sum);
		System.out.println(gears.values().stream().filter(list -> list.size() == 2).mapToInt(list -> list.get(0) * list.get(1)).sum());
	}

	private static boolean isNumber(char c) {
		return '0' <= c && c <= '9';
	}

	private static boolean isSymbol(char c) {
		return c != '.' && !isNumber(c);
	}

	private static record Grid(List<String> grid) {

		int width() {
			return grid.get(0).length();
		}

		int height() {
			return grid.size();
		}

		char at(int x, int y) {
			if (y < 0 || y >= grid.size()) return '.';
			final String row = grid.get(y);
			if (x < 0 || x >= row.length()) return '.';
			return row.charAt(x);
		}

		Set<Coord> neighborSymbols(int x, int y) {
			Set<Coord> ret = new HashSet<>();
			for (int i = -1; i <= 1; ++i) {
				for (int j = -1; j <= 1; ++j) {
					if (i == 0 && j == 0) continue;
					final char c = at(x + i, y + j);
					if (isSymbol(c)) {
						ret.add(new Coord(x + i, y + j, c));
					}
				}
			}
			return ret;
		}
	}

	private static record Coord(int x, int y, char c) { }
}
