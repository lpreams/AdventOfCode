package aoc2022;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import aocutil.Dijkstra;
import aocutil.Dijkstra.NoPathExistsException;
import aocutil.IO;
import aocutil.Point;

public class Day12 {

	
	public static void main(String[] args) throws InterruptedException, NoPathExistsException {
		final Dijkstra<Point> d = new Dijkstra<Point>() {
			
			public Set<Point> getAllNodes() {
				return GRID.keySet();
			}
			
			public Map<Point, Integer> getNeighbors(Point current) {
				Map<Point, Integer> ret = new HashMap<>();
				asdf(current, ret, current.left());
				asdf(current, ret, current.right());
				asdf(current, ret, current.up());
				asdf(current, ret, current.down());
				return ret;
			}
			
			private void asdf(Point current, Map<Point, Integer> ret, Point next) {
				if (GRID.keySet().contains(next)) {
					final int currentHeight = GRID.get(current);
					final int newHeight = GRID.get(next);
					if (!(newHeight < currentHeight-1)) ret.put(next, 1); // don't step down more than 1
				}
			}
		};
		
		Map<Point, Integer> dists = d.dijkstraFull(E);
		System.out.println(dists.get(S));
		
		System.out.println(
			GRID.entrySet()
				.stream()
				.filter(e -> e.getValue() == 0)
				.flatMapToInt(e -> (dists.get(e.getKey()) == null) ? IntStream.of() : IntStream.of(dists.get(e.getKey())))
				.min()
				.getAsInt()
		);
	}
	
	private static final Map<Point, Integer> GRID;
	private static final Point S, E;
	static {
		Map<Point, Integer> grid = new HashMap<>();
		Point s = null, e = null;
		try (Scanner scan = IO.scanner(2022, 12)) {
			for (int y=0; scan.hasNext(); ++y) {
				final String line = scan.nextLine();
				for (int x=0; x<line.length(); ++x) {
					final char c = line.charAt(x);
					final Point p = new Point(x, y);
					switch (c) {
						case 'S':
							s = p;
							grid.put(p, 'a' - 'a');
							break;
						case 'E':
							e = p;
							grid.put(p, 'z' - 'a');
							break;
						default:
							grid.put(p, c - 'a');
							break;
					}
				}
			}
		}
		Objects.requireNonNull(s);
		Objects.requireNonNull(e);
		GRID = Collections.unmodifiableMap(grid);
		S = s;
		E = e;
	}
}
