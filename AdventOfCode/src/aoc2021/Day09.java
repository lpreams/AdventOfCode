package aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import aocutil.IO;
import aocutil.Point;

public class Day09 {

	private static final List<List<Integer>> list;
	
	public static void main(String[] args) {

//		var list = getList();
		int risk = 0;
		HashMap<Point, Integer> map = new HashMap<>();
		
		for (int i=0; i<list.size(); ++i) {
			for (int j=0; j<list.get(i).size(); ++j) {
				Point p = new Point(i,j);
				if (list.get(i).get(j) == 9) continue;
				if (isLowPoint(p)) {
					risk += list.get(i).get(j) + 1;
				} else {
					p = trickle(p);
				}
				map.merge(p, 1, (a, b) -> a+b);
				
			}
		}
		System.out.println(risk);
//		map.values().stream().sorted(Comparator.reverseOrder()).limit(3).forEach(x -> System.out.print(x + " "));
		int part2 = map.values().stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(x->x).reduce(1, (a,b)->a*b);
		System.out.println(part2);
	}
	
	private static HashMap<Point, Point> trickleMap = new HashMap<>();
	
	private static Point trickle(Point myPoint) {

		trickleMap.computeIfAbsent(myPoint, point -> {

			Point p = point;

			while (true) {

				Point lastP = p;

				final int i = p.x, j = p.y;

				final int val = list.get(i).get(j);

				if (i > 0 && list.get(i - 1).get(j) < val) p = new Point(i-1, j);
				if (i < list.size() - 1 && list.get(i + 1).get(j) < val) p = new Point(i+1, j);

				if (j > 0 && list.get(i).get(j - 1) < val) p = new Point(i, j-1);
				if (j < list.get(i).size() - 1 && list.get(i).get(j + 1) < val) p = new Point(i, j+1);

				if (lastP == p) break;
			}

			return p;
		});

		return trickleMap.get(myPoint);
	}

	private static boolean isLowPoint(Point p) {
		final int i = p.x, j = p.y;
		
		final int val = list.get(i).get(j);
		
		if (i > 0 && list.get(i-1).get(j) <= val) return false;
		if (i < list.size()-1 && list.get(i+1).get(j) <= val) return false;
		
		if (j > 0 && list.get(i).get(j-1) <= val) return false;
		if (j < list.get(i).size()-1 && list.get(i).get(j+1) <= val) return false;
		
		return true;
	}

	static {
		List<List<Integer>> myList = new ArrayList<>();

		for (String line : IO.lines(2021, 9).toList()) {
			List<Integer> row = new ArrayList<>();
			for (char c : line.toCharArray()) row.add(c - '0');
			myList.add(row);
		}
		
		list = Collections.unmodifiableList(myList);
	}

}
