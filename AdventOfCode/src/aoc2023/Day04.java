package aoc2023;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) {
		a();
		b();
	}

	private static void a() {
		System.out.println(getCards().mapToInt(card -> card.wins() == 0 ? 0 : IntStream.range(0, card.wins() - 1).map(z -> 2).reduce(1, (a, b) -> a * b)).sum());
	}

	private static void b() {
		final List<Card> cards = getCards().toList();
		int totalCards = cards.size();
		List<Card> curr = cards;
		while (!curr.isEmpty()) {
			curr = curr.stream().flatMap(card -> IntStream.range(0, card.wins()).mapToObj(i -> cards.get(card.id() + i))).toList();
			totalCards += curr.size();
		}
		System.out.println(totalCards);
	}

	private static Stream<Card> getCards() {
		return IO.lines(2023, 4).map(line -> {
			final String[] arr = line.split(":\\s+");
			final String[] nums = arr[1].split(" \\| ");
			return new Card(Integer.parseInt(arr[0].split("\\s+")[1]), (int) parseCards(nums[1]).stream().filter(parseCards(nums[0])::contains).count());
		});
	}

	private static HashSet<Integer> parseCards(String cards) {
		return Arrays.stream(cards.trim().split("\\s+"))
				.map(Integer::parseInt)
				.collect(Collectors.toCollection(HashSet::new));
	}

	private static record Card(int id, int wins) { }
}
