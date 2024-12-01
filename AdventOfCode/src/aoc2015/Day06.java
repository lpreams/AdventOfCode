package aoc2015;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		boolean[][] on = new boolean[1000][1000];
		int[][] bright = new int[1000][1000];
		for (Inst inst : (Iterable<Inst>)() -> IO.lines(2015, 6).map(Inst::parse).iterator()) {
			inst.apply(on);
			inst.apply(bright);
		}
		int count = 0, sum = 0;
		for (int i = 0; i < 1000; ++i) {
			for (int j = 0; j < 1000; ++j) {
				if (on[i][j]) ++count;
				sum += bright[i][j];
			}
		}
		System.out.println(count);
		System.out.println(sum);
	}

	private static record Inst(Act act, int x0, int y0, int x1, int y1) {
		static Inst parse(String line) {
			final String[] arr = line.split(" ");
			final Act act = arr.length == 4 ? Act.TOGGLE : (arr[1].equals("on") ? Act.ON : Act.OFF);
			final String[] start = arr[arr.length - 3].split(",");
			final String[] end = arr[arr.length - 1].split(",");
			return new Inst(act, Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
		}

		void apply(boolean[][] on) {
			for (int i = x0; i <= x1; ++i) {
				for (int j = y0; j <= y1; ++j) {
					switch (act) {
						case ON:
							on[i][j] = true;
							break;
						case OFF:
							on[i][j] = false;
							break;
						case TOGGLE:
							on[i][j] = !on[i][j];
							break;
					}
				}
			}
		}

		void apply(int[][] on) {
			for (int i = x0; i <= x1; ++i) {
				for (int j = y0; j <= y1; ++j) {
					switch (act) {
						case ON:
							++on[i][j];
							break;
						case OFF:
							on[i][j] = Integer.max(0, on[i][j] - 1);
							break;
						case TOGGLE:
							on[i][j] += 2;
					}
				}
			}
		}
	}

	private static enum Act {
		ON, OFF, TOGGLE
	}
}
