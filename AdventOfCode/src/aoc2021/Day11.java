package aoc2021;

import java.util.HashSet;
import java.util.Scanner;

import aocutil.Point;

public class Day11 {

	public static void main(String[] args) {
		int[][] arr = getArr();
		int flashes = 0;
		int steps = 0;
		for (int i=0; i<100; ++i) {
			flashes += step(arr);
			++steps;
		}
		System.out.println(flashes);
		
		while (true) {
			++steps;
			if (step(arr) == 100) break;
		}
		
		System.out.println(steps);
	}
	
	private static int step(int[][] arr) {
		int flashes = 0;
		
		for (int i=0; i<10; ++i) for (int j=0; j<10; ++j) ++arr[i][j];
		
		HashSet<Point> flashPoints = new HashSet<>();
		do {
			
			flashPoints.clear();
			
			for (int i=0; i<10; ++i) for (int j=0; j<10; ++j) if (arr[i][j] > 9) {
				arr[i][j] = 0;
				++flashes;
				flashPoints.add(new Point(i, j));
			}
			
			for (Point p : flashPoints) {
				final int i = p.x;
				final int j = p.y;
				for (int io=-1; io<=1; ++io) {
					for (int jo=-1; jo<=1; ++jo) {
						if (io==0&&jo==0) continue;
						int ii = i+io;
						if (ii < 0 || ii > 9) continue;
						int jj = j+jo;
						if (jj < 0 || jj > 9) continue;
						if (arr[ii][jj] != 0) ++arr[ii][jj];
					}
				}
			}
			
		} while (flashPoints.size() > 0);
		
		
		return flashes;
	}

	private static int[][] getArr() {
//		String s = """
//				5483143223
//				2745854711
//				5264556173
//				6141336146
//				6357385478
//				4167524645
//				2176841721
//				6882881134
//				4846848554
//				5283751526
//				""";
		String s = """
				7777838353
				2217272478
				3355318645
				2242618113
				7182468666
				5441641111
				4773862364
				5717125521
				7542127721
				4576678341
				""";
		int[][] arr = new int[10][10];
		try (Scanner scan = new Scanner(s)) {
			for (int i = 0; i < 10; ++i) {
				String line = scan.nextLine();
				for (int j = 0; j < 10; ++j) {
					arr[i][j] = line.charAt(j) - '0';
				}
			}
		}

		return arr;
	}

}
