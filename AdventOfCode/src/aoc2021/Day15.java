package aoc2021;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

import aocutil.IO;
import aocutil.Point;

public class Day15 {

	public static void main(String[] args) {
		dijkstra(1);
		dijkstra(5);
	}
	
	private static void dijkstra(int reps) {
		
		final int L = reps * GRID.length;
		
		HashSet<Point> unvisited = newUnvisited(L);
		HashSet<Point> visitable = new HashSet<>();
		Integer[][] dists = newDists(L, null);
		dists[0][0] = 0;
		
		int cx = 0, cy = 0;
		
		while (true) {
			
			dijkstraInner(cx, cy, cx-1, cy, L, unvisited, dists, visitable);
			dijkstraInner(cx, cy, cx+1, cy, L, unvisited, dists, visitable);
			dijkstraInner(cx, cy, cx, cy-1, L, unvisited, dists, visitable);
			dijkstraInner(cx, cy, cx, cy+1, L, unvisited, dists, visitable);
			
			unvisited.remove(new Point(cx, cy));
			visitable.remove(new Point(cx, cy));
			
			if (!unvisited.contains(new Point(L-1, L-1))) break;
			
			Point next = visitable.stream().min(Comparator.comparing(p -> dists[p.x][p.y])).get();
			cx = next.x;
			cy = next.y;
		}
		
		System.out.println(dists[L-1][L-1]);
	}
	
	private static void dijkstraInner(int cx, int cy, int nx, int ny, int L, HashSet<Point> unvisited, Integer[][] dists, HashSet<Point> visitable) {
		Point n = new Point(nx, ny);
		
		if (nx < 0 || ny < 0 || nx >= L || ny >= L || !unvisited.contains(n)) {
			return;
		}

		int newDist = dists[cx][cy] + gridGet(nx,ny);
		if (dists[nx][ny] == null || newDist < dists[nx][ny]) {
			dists[nx][ny] = newDist;
			if (unvisited.contains(n)) visitable.add(n);
		}
	}
	
	private static HashSet<Point> newUnvisited(int L) {
		HashSet<Point> unvisited = new HashSet<>();
		for (int i=0; i<L; ++i) {
			for (int j=0; j<L; ++j) {
				unvisited.add(new Point(i, j));
			}
		}
		return unvisited;
	}
	
	private static Integer[][] newDists(int L, Integer inf) {
		Integer[][] ret = new Integer[L][L];
		for (int i=0; i<ret.length; ++i) Arrays.fill(ret[i], inf);
		return ret;
	}
	
	private static int gridGet(int i, int j) {
		final int L = GRID.length;
		return (i < L && j < L) ? GRID[i][j] : ((GRID[i%L][j%L] + i/L + j/L - 1) % 9 + 1);
	}

	private static final int[][] GRID;
	static {
		GRID = IO.lines(2021, 15).map(line -> line.chars().map(c -> c-'0').toArray()).toArray(z -> new int[z][]);
	}
}
