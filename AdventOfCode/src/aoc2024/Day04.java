package aoc2024;

import java.util.List;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) {
		final Grid grid = new Grid(IO.lines(2024, 4).toList());
		
		int sum = 0;
		for (int i=0; i<grid.a(); ++i) {
			for (int j=0; j<grid.b(i); ++j) {
				sum += grid.countAt(i, j);
			}
		}
		System.out.println(sum);

		sum = 0;
		for (int i=0; i<grid.a(); ++i) {
			for (int j=0; j<grid.b(i); ++j) {
				sum += grid.countAtX(i, j);
			}
		}
		System.out.println(sum);
	}
	
	private static class Grid {
		private final List<String> list;
		public Grid(List<String> list) {
			this.list = list;
		}
		public int a() { return list.size(); }
		public int b(int a) { return list.get(a).length(); }
		public Character get(int a, int b) {
			if (a < 0 || b < 0 || a >= list.size() || b >= list.get(a).length()) return 'z';
			return list.get(a).charAt(b);
		}
		
		public int countAt(int a, int b) {
			if (this.get(a, b) != 'X') return 0;
			int count = 0;
			if (this.get(a+1, b) == 'M' && this.get(a+2, b) == 'A' && this.get(a+3, b) == 'S') ++count;
			if (this.get(a-1, b) == 'M' && this.get(a-2, b) == 'A' && this.get(a-3, b) == 'S') ++count;
			if (this.get(a, b+1) == 'M' && this.get(a, b+2) == 'A' && this.get(a, b+3) == 'S') ++count;
			if (this.get(a, b-1) == 'M' && this.get(a, b-2) == 'A' && this.get(a, b-3) == 'S') ++count;
			if (this.get(a+1, b+1) == 'M' && this.get(a+2, b+2) == 'A' && this.get(a+3, b+3) == 'S') ++count;
			if (this.get(a-1, b+1) == 'M' && this.get(a-2, b+2) == 'A' && this.get(a-3, b+3) == 'S') ++count;
			if (this.get(a+1, b-1) == 'M' && this.get(a+2, b-2) == 'A' && this.get(a+3, b-3) == 'S') ++count;
			if (this.get(a-1, b-1) == 'M' && this.get(a-2, b-2) == 'A' && this.get(a-3, b-3) == 'S') ++count;
			return count;
		}
		public int countAtX(int a, int b) {
			if (this.get(a, b) != 'A') return 0;
			final int w = this.get(a+1, b+1);
			final int x = this.get(a-1, b+1);
			final int y = this.get(a-1, b-1);
			final int z = this.get(a+1, b-1);
			if (w == 'M' && y == 'S' || w == 'S' && y == 'M') {
				if (x == 'M' && z == 'S' || x == 'S' && z == 'M') {
					return 1;
				}
			}
			return 0;
		}
	}
}
