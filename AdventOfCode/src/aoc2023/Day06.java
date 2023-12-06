package aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		a();
		b();
	}

	public static void a() {
		List<Integer> times = new ArrayList<>();
		List<Integer> dists = new ArrayList<>();
		try (Scanner scan = IO.scanner(2023, 6)) {
			try (Scanner line = new Scanner(scan.nextLine())) {
				line.next();
				while (line.hasNext()) times.add(line.nextInt());
			}
			try (Scanner a = new Scanner(scan.nextLine())) {
				a.next();
				while (a.hasNext()) dists.add(a.nextInt());
			}
		}
		System.out.println(IntStream.range(0, times.size()).mapToLong(i -> ways(times.get(i), dists.get(i))).reduce(1, (a, b) -> a * b));
	}

	public static void b() {
		long time, dist;
		try (Scanner scan = IO.scanner(2023, 6)) {
			try (Scanner a = new Scanner(scan.nextLine())) {
				a.next();
				String t = "";
				while (a.hasNext()) t += (a.nextInt());
				time = Long.parseLong(t);
			}
			try (Scanner a = new Scanner(scan.nextLine())) {
				a.next();
				String t = "";
				while (a.hasNext()) t += (a.nextInt());
				dist = Long.parseLong(t);
			}
		}
		System.out.println(ways(time, dist));
	}

	private static long ways(long totalTime, long bestDist) {
		return LongStream.range(1, totalTime - 1).filter(i -> simulate(totalTime, i) > bestDist).count();
	}

	private static long simulate(long totalTime, long holdTime) {
		return holdTime >= totalTime || holdTime <= 0 ? 0 : (totalTime - holdTime) * holdTime;
	}

}
