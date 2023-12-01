package aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day19 {

	public static void main(String[] args) {
		List<String> input = IO.lines(2017, 19).toList();
		int x = input.get(0).indexOf("|");
		int y = 0;
		Dir dir = Dir.Down;

		List<Character> output = new ArrayList<>();
		int steps = 0;

		while (true) {
			final char c = input.get(y).charAt(x);
			if (c == ' ') break;
			if (c == '+') {
				switch (dir) {
					case Up, Down:
						if (x > 0 && input.get(y).charAt(x - 1) != ' ') dir = Dir.Left;
						else dir = Dir.Right;
						break;
					case Left, Right:
						if (y > 0 && input.get(y - 1).charAt(x) != ' ') dir = Dir.Up;
						else dir = Dir.Down;
						break;
				}
			} else if (c != '|' && c != '-') {
				output.add(c);
			}

			x += dir.x;
			y += dir.y;
			++steps;
		}

		System.out.println(output.stream().map(String::valueOf).collect(Collectors.joining()));
		System.out.println(steps);
	}

	private static enum Dir {
		Left(-1, 0), Right(1, 0), Up(0, -1), Down(0, 1);

		final int x, y;

		Dir(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
