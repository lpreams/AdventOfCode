package aoc2017;

import aocutil.IO;

public class Day11 {

	public static void main(String[] args) {

		String[] data = getData();

		int x = 0, y = 0;

		int maxDist = -1;

		for (String move : data) {
			switch (move) {
			case "n":
				++y;
				break;
			case "s":
				--y;
				break;
			case "nw":
				--x;
				break;
			case "se":
				++x;
				break;
			case "sw":
				--x;
				--y;
				break;
			case "ne":
				++x;
				++y;
				break;
			default:
				throw new RuntimeException("you suck: " + move);
			}
			
			int dist = hexDist(x, y);
			if (dist > maxDist) maxDist = dist;
			
		}

		System.out.println(hexDist(x, y) + " " + maxDist);
	}

	private static int hexDist(int x, int y) {

		int total = 0;

		while (x < 0 && y < 0) {
			++x;
			++y;
			++total;
		}

		while (x > 0 && y > 0) {
			--x;
			--y;
			++total;
		}

		return Math.abs(x) + Math.abs(y) + total;

	}

	private static String[] getData() {
//		return "ne,ne,s,s".split(",");
		return IO.string(2017, 11).split(",");
	}

}
