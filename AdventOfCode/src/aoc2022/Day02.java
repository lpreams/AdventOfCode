package aoc2022;

import aocutil.IO;

public class Day02 {

	private static final char ROCK = 'A';
	private static final char PAPER = 'B';
	private static final char SCISSORS = 'C';

	private static final char LOSE = 'X';
	private static final char TIE = 'Y';
	private static final char WIN = 'Z';

	public static void main(String[] args) {
		System.out.println(a());
		System.out.println(b());
	}

	private static int a() {
		return IO.lines(2022, 2).mapToInt(line -> {
			final char op = line.charAt(0);
			final char me = (char) (line.charAt(2) - 'X' + 'A');
			return shapeScore(me) + winScore(me, op);
		}).sum();
	}

	private static int b() {
		return IO.lines(2022, 2).mapToInt(line -> {
			final char op = line.charAt(0);
			final char me = switch (line.charAt(2)) {
				case TIE -> op;
				case WIN -> switch (op) {
					case ROCK -> PAPER;
					case PAPER -> SCISSORS;
					case SCISSORS -> ROCK;
					default -> throw new IllegalArgumentException("Unexpected value: " + op);
				};
				case LOSE -> switch (op) {
					case ROCK -> SCISSORS;
					case PAPER -> ROCK;
					case SCISSORS -> PAPER;
					default -> throw new IllegalArgumentException("Unexpected value: " + op);
				};
				default -> throw new RuntimeException();
			};
			return shapeScore(me) + winScore(me, op);
		}).sum();
	}

	private static int winScore(char me, char op) {
		if (op == me) return 3;
		if ((op == ROCK && me == PAPER) || (op == PAPER && me == SCISSORS) || (op == SCISSORS && me == ROCK)) return 6;
		return 0;
	}

	private static int shapeScore(int shape) {
		return switch (shape) {
			case ROCK -> 1;
			case PAPER -> 2;
			case SCISSORS -> 3;
			default -> throw new IllegalArgumentException("Unexpected value: " + shape);
		};
	}

}
