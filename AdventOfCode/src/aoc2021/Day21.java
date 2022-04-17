package aoc2021;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day21 {

	public static void main(String[] args) {
		Part1.main();
		Part2.main();
	}
	
	private static class Part2 {
		public static void main() {
			Iterator<String> iter = IO.linesIter(2021, 21).iterator();
			int p1start = Integer.parseInt(iter.next().split(" ")[4]);
			int p2start = Integer.parseInt(iter.next().split(" ")[4]);
			
			long[] result = roll(p1start, 0, p2start, 0);
			
			System.out.println(Long.max(result[0], result[1]));
		}
		
		private static final int[] rollCounts = new int[10];
		static {
			for (int i=1; i<=3; ++i) {
				for (int j=1; j<=3; ++j) {
					for (int k=1; k<=3; ++k) {
						++rollCounts[i+j+k];
					}
				}
			}
		}
		
		private static long[] roll(int p1pos, int p1score, int p2pos, int p2score) {
			
			if (p1score >= 21) return new long[] {1,0};
			if (p2score >= 21) return new long[] {0,1};			
						
			return IntStream.rangeClosed(3, 9).mapToObj(roll -> {
				int nextPos = (p1pos+roll)%10;
				if (nextPos == 0) nextPos = 10;
				
				long[] res = roll(p2pos, p2score, nextPos, p1score + nextPos);
				
				return new long[] {res[1]*rollCounts[roll], res[0]*rollCounts[roll]};
			}).reduce(new long[]{0,0}, (a,b)->new long[] {a[0]+b[0], a[1]+b[1]});
			
		}
		
	}

	private static class Part1 {
		

		public static void main() {
			boolean done = false;
			while (true) {
				if (done) break;
				for (Player p : players) {
					if (done) break;
					p.move(die.roll());
					p.move(die.roll());
					p.move(die.roll());
					p.score();
					if (p.score >= 1000) {
						endGame();
						done = true;
					}
				}

			}
		}

		private static final Die die = new Die();
		
		private static void endGame() {
			int loserScore = players.stream().mapToInt(p -> p.score).min().getAsInt();
			int rolls = die.rolls;
			System.out.println(loserScore * rolls);
		}

		private static ArrayList<Player> players = new ArrayList<>();

		private static class Player {
			public final String ID;
			private int pos;
			private int score = 0;

			public Player(String id, int position) {
				ID = id;
				pos = position;
			}

			public void move(int n) {
				pos += n;
				while (pos > 10) pos -= 10;
			}

			public void score() {
				score += pos;
			}

			public String toString() {
				return "Player " + ID + " has score " + score;
			}
		}

		private static class Die {
			private int value = 1;
			private int rolls = 0;

			public int roll() {
				int ret = value;
				++value;
				++rolls;
				if (value > 100) value = 1;
				return ret;
			}
		}

		static {
			for (String line : IO.linesIter(2021, 21)) {
				String[] a = line.split(" ");
				String name = a[1];
				int start = Integer.parseInt(a[4]);
				players.add(new Player(name, start));
			}
		}

	}
}
