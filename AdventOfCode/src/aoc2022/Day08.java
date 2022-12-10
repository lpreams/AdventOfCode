package aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import aocutil.IO;
import aocutil.Point;

public class Day08 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static final List<List<Integer>> grid;
	
	private static void b() {
		int max = -1;
		for (int i=0; i<grid.size(); ++i) {
			for (int j=0; j<grid.get(i).size(); ++j) {
				int score = scenicScore(i, j);
				if (score > max) max = score;
			}
		}
		System.out.println(max);
	}
	
	private static int scenicScore(int x, int y) {
		
		final int spotHeight = grid.get(x).get(y);
		int score = 1;
		
		int count = 0;
		for (int i=x-1; i>=0; --i) {
			++count;
			if (grid.get(i).get(y) >= spotHeight) break;
		}
		score *= count;
		
		count = 0;
		for (int i=x+1; i<grid.size(); ++i) {
			++count;
			if (grid.get(i).get(y) >= spotHeight) break;
		}
		score *= count;

		count = 0;
		for (int i=y-1; i>=0; --i) {
			++count;
			if (grid.get(x).get(i) >= spotHeight) break;
		}
		score *= count;
		
		count = 0;
		for (int i=y+1; i<grid.size(); ++i) {
			++count;
			if (grid.get(x).get(i) >= spotHeight) break;
		}
		score *= count;

		
		return score;
	}
	
	private static void a() {
		HashSet<Point> visible = new HashSet<>();
		
		for (int i=0; i<grid.size(); ++i) {
			int biggestSoFar = -1;
			for (int j=0; j<grid.get(i).size(); ++j) {
				if (grid.get(i).get(j) > biggestSoFar) {
					visible.add(new Point(i, j));
					biggestSoFar = grid.get(i).get(j);
				}
			}
			
			biggestSoFar = -1;
			for (int j=grid.get(i).size()-1; j>=0; --j) {
				if (grid.get(i).get(j) > biggestSoFar) {
					visible.add(new Point(i, j));
					biggestSoFar = grid.get(i).get(j);
				}
			}
		}
		
		for (int j=0; j<grid.get(0).size(); ++j) {
			int biggestSoFar = -1;
			for (int i=0; i<grid.size(); ++i) {
				if (grid.get(i).get(j) > biggestSoFar) {
					visible.add(new Point(i, j));
					biggestSoFar = grid.get(i).get(j);
				}
			}
			
			biggestSoFar = -1;
			for (int i=grid.size()-1; i>=0; --i) {
				if (grid.get(i).get(j) > biggestSoFar) {
					visible.add(new Point(i, j));
					biggestSoFar = grid.get(i).get(j);
				}
			}
		}
		System.out.println(visible.size());
	}
	
	static {
		List<List<Integer>> g = new ArrayList<>();
		for (String line : IO.linesIter(2022, 8)) {
			List<Integer> row = new ArrayList<>();
			for (char c : line.toCharArray()) {
				row.add(c - '0');
			}
			g.add(Collections.unmodifiableList(row));
		}
		grid = Collections.unmodifiableList(g);
	}

}
