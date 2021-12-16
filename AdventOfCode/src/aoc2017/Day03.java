package aoc2017;

import java.util.HashMap;

public class Day03 {

	public static void main(String[] args) {
		System.out.println(a(277678));
		System.out.println(b(277678));
	}
	
	private static int b(int target) {
		
		HashMap<String, Integer> board = new HashMap<>();
		board.put("0,0", 1);
		
		int x = 0, y = 0;

		boolean dir = true;
		for (int steps = 1; true; ++steps) {
			
			
			// move right/left
			for (int i=0; i<steps; ++i) {
				x += (dir ? 1 : -1);
				int val = updateBoard(x, y, board);
				if (val > target) return val;
			}
			
			// move up/down
			for (int i=0; i<steps; ++i) {
				y += (dir ? -1 : 1);
				int val = updateBoard(x, y, board);
				if (val > target) return val;
			}
			
			dir = !dir;
		}

	}
	
	private static int updateBoard(int x, int y, HashMap<String, Integer> board) {
		int sum = 0;

		for (int ii = -1; ii <= 1; ++ii) {
			for (int jj = -1; jj <= 1; ++jj) {
				String str = (x + ii) + "," + (y + jj);
				sum += board.getOrDefault(str, 0);
			}
		}

		board.put(x + "," + y, sum);
		return sum;
	}

	private static int manDist(int x, int y) {
		return (x < 0 ? -x : x) + (y < 0 ? -y : y);
	}
	
	private static int a(int target) {
		if (target < 1) throw new IllegalArgumentException();
		if (target == 1) return 0;
		
		int x = 0, y = 0, current = 1;

		boolean dir = true;
		for (int steps = 1; true; ++steps) {
			
			
			// move right/left
			for (int i=0; i<steps; ++i) {
				x += (dir ? 1 : -1);
				++current;
				if (current == target) return manDist(x, y);
				
//				System.out.println(current + " at " + x + "," + y);
			}
			
			// move up/down
			for (int i=0; i<steps; ++i) {
				y += (dir ? -1 : 1);
				++current;
				if (current == target) return manDist(x, y);
				
//				System.out.println(current + " at " + x + "," + y);

			}
			
			dir = !dir;
		}
		
	}
}
