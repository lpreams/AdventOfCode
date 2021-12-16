package aoc2017;


public class Day15 {

	public static void main(String[] args) {
		doTheThing(false);
		doTheThing(true);
	}
	
	private static void doTheThing(boolean part2) {
		final long MOD = 2147483647;
		final long aMult = 16807;
		final long bMult = 48271;
		
		long a = 873;
		long b = 583;
//		long a = 1352636452;
//		long b = 1233683848;
		
		int matches = 0;
		
		final int MAX = part2 ? 5_000_000 : 40_000_000;
		
		for (int i=0; i<MAX; ++i) {
			boolean match = last16match(a, b);
//			System.out.printf("%12d %12d %b%n", a, b, match);
			if (match) ++matches;
			do {
				a = (a*aMult) % MOD;
			} while (part2 && a % 4 != 0);
			do {
				b = (b*bMult) % MOD;
			} while (part2 && b % 8 != 0);
		}
		
		System.out.println(matches);

	}
	
	private static boolean last16match(long a, long b) {
		a &= 0b1111111111111111;
		b &= 0b1111111111111111;
		return a == b;
	}

}
