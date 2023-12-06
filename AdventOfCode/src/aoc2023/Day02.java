package aoc2023;

import java.util.Arrays;
import java.util.List;

import aocutil.IO;

import static java.lang.Integer.max;

public class Day02 {

	public static void main(String[] args) {
		a();
		b();
	}

	private static void a() {
		System.out.println(IO.lines(2023, 2)
			.map(Game::parse)
			.filter(g -> g.hands.stream().allMatch(hand -> hand.r() <= 12 && hand.g() <= 13 && hand.b() <= 14))
			.mapToInt(Game::id)
			.sum());
	}
	
	private static void b() {
		System.out.println(IO.lines(2023, 2)
			.map(Game::parse)
			.map(g -> g.hands()
					.stream()
					.reduce(Hand.EMPTY, (a,b) -> new Hand(max(a.r(), b.r()), max(a.g(), b.g()), max(a.b(), b.b()))))
			.mapToInt(h -> h.r() * h.g() * h.b())
			.sum());
	}

	private static record Game(int id, List<Hand> hands) {
		static Game parse(String line) {
			final String[] arr = line.split(": ");
			final int id = Integer.parseInt(arr[0].split(" ")[1]);
			final String[] handStrings = arr[1].split("; ");
			final List<Hand> hands = Arrays.stream(handStrings).map(hand -> Hand.parse(hand)).toList();
			return new Game(id, hands);
		}
	}

	private static record Hand(int r, int g, int b) {
		static final Hand EMPTY = new Hand(0, 0, 0);
		static Hand parse(String hand) {
			final String[] cubes = hand.split(", ");
			int r = 0, g = 0, b = 0;
			for (String cube : cubes) {
				final String[] arr = cube.split(" ");
				final int num = Integer.parseInt(arr[0]);
				switch (arr[1]) {
					case "red":
						r += num;
						break;
					case "green":
						g += num;
						break;
					case "blue":
						b += num;
						break;
				}
			}
			return new Hand(r, g, b);
		}
	}
}
