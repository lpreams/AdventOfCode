package aoc2023;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day07 {

	public static void main(String[] args) {
		System.out.println(score(false));
		System.out.println(score(true));
	}
	
	private static int score(boolean hasJokers) {
		final List<Hand> ranked = IO.lines(2023, 7).map(str -> new Hand(str, hasJokers)).sorted().toList();
		int total = 0;
		for (int i = 0; i < ranked.size(); ++i) {
			final int rank = ranked.size() - i;
			total += rank * ranked.get(i).bid;
		}
		return total;
	}
	
	private static class Hand implements Comparable<Hand> {
		
		private final List<Integer> cards;
		private final HandType type;
		private final int bid;
		
		public Hand(String str, boolean hasJokers) {
			final String[] arr = str.split(" ");
			bid = Integer.parseInt(arr[1]);
			cards = arr[0].chars().mapToObj(c -> {
				return switch (c) {
					case 'A' -> 14;
					case 'K' -> 13;
					case 'Q' -> 12;
					case 'J' -> hasJokers ? -1 : 11;
					case 'T' -> 10;
					default -> c - '0';
				};
			}).toList();
			
			final int jokers = hasJokers ? (int) cards.stream().filter(c -> c==-1).count() : 0;
			final List<Integer> freqs = cards.stream()
					.filter(c -> !hasJokers || c!=-1)
					.sorted()
					.collect(Collectors.groupingBy(z->z, Collectors.counting()))
					.values()
					.stream()
					.map(Long::intValue)
					.sorted(Comparator.reverseOrder())
					.toList();
			
			if (freqs.isEmpty() || freqs.get(0) + jokers == 5) type = HandType.FIVE;
			else if (freqs.get(0) + jokers == 4) type = HandType.FOUR;
			else if (freqs.get(0) + jokers == 3) {
				if (freqs.get(1) == 2) type = HandType.FULL;
				else type = HandType.THREE;
			} else if (freqs.get(0) + jokers == 2) {
				if (freqs.get(1) == 2) type = HandType.TWO;
				else type = HandType.ONE;
			} else type = HandType.HIGH;
		}

		public int compareTo(Hand that) {
			int comp = this.type.compareTo(that.type);
			if (comp != 0) return comp;
			for (int i=0; i<5; ++i) {
				comp = this.cards.get(i).compareTo(that.cards.get(i));
				if (comp != 0) return -comp;
			}
			return 0;
		}
		
	}
	
	private static enum HandType implements Comparable<HandType> {
		FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH;
	}
}
