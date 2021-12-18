package aoc2021;

import aocutil.IO;

public class Day17 {

	
	public static void main(String[] args) {
		int max = 0, mx = -1, my = -1, count = 0;
		for (int vy = -1000; vy <= 1000; ++vy) { // range chosen arbitrarily
			for (int vx = 0; vx <= xb; ++vx) { // assumes 0 < xa < xb
				int h = simulate(vx, vy);
				if (h >= 0) {
					++count;
					if (h > max) {
						max = h;
						mx = vx;
						my = vy;
					}
				}
			}
		}
		System.out.println(max + " (" + mx + "," + my + ")");
		System.out.println(count);
	}
	
	private static int simulate(int vx, int vy) {
		
		int x = 0, y = 0, max = 0;
		
		while (true) {
			x += vx;
			y += vy;
			vx -= Integer.signum(vx);
			vy -= 1;
			
			if (y > max) max = y;
			if (xa <= x && x <= xb && ya <= y && y <= yb) return max;
			
			if (vy < 0 && y < ya) return -1;
		}		
	}
	
//	private static final String input = "target area: x=20..30, y=-10..-5";
	private static final String input = IO.string(2021, 17);
	private static final int xa, xb, ya, yb;
	static {
		String[] arr = input.split(", y=");
		arr[0] = arr[0].split("=")[1];
		String[] x = arr[0].split("\\.\\.");
		String[] y = arr[1].split("\\.\\.");
		xa = Integer.parseInt(x[0]);
		xb = Integer.parseInt(x[1]);
		ya = Integer.parseInt(y[0]);
		yb = Integer.parseInt(y[1]);
	}

}
