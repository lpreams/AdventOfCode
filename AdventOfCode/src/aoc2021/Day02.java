package aoc2021;

import aocutil.IO;

public class Day02 {
	public static void main(String[] args) {
		a();
		b();
	}

	private static void a() {
		int pos = 0, dep = 0;
		for (String line : IO.lines(2021, 2).toList()) {
			if (line == null) break;
			try {
				String[] arr = line.split(" ");
				int amount = Integer.parseInt(arr[1]);
				switch (arr[0]) {
				case "forward":
					pos += amount;
					break;
				case "down":
					dep += amount;
					break;
				case "up":
					dep -= amount;
					break;
				}
			} catch (Exception e) {
				System.err.println("Error on line: " + line);
			}
		}
		System.out.println(pos * dep);

	}

	private static void b() {
		int pos = 0, dep = 0, aim = 0;
		for (String line : IO.lines(2021, 2).toList()) {
			if (line == null) break;
			try {
				String[] arr = line.split(" ");
				int amount = Integer.parseInt(arr[1]);
				switch (arr[0]) {
				case "forward":
					pos += amount;
					dep += aim * amount;
					break;
				case "down":
					aim += amount;
					break;
				case "up":
					aim -= amount;
					break;
				}
			} catch (Exception e) {
				System.err.println("Error on line: " + line);
			}
		}
		System.out.println(pos * dep);
	}

}
