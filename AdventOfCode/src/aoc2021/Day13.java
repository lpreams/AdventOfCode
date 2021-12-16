package aoc2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutil.IO;
import aocutil.Point;

public class Day13 {

	public static void main(String[] args) {

		HashSet<Point> points = new HashSet<>();
		ArrayList<Fold> folds = new ArrayList<>();

		try (Scanner scan = IO.scanner(2021, 13)) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.contains(",")) {
					String[] arr = line.split(",");
					int x = Integer.parseInt(arr[0]);
					int y = Integer.parseInt(arr[1]);
					points.add(new Point(x, y));
				} else if (line.contains("fold along")) {
					int z = Integer.parseInt(line.split("=")[1]);
					folds.add(new Fold(z, line.contains(" x")));
				}
			}
		}

		System.out.println(folds.get(0).fold(points).size());

		for (Fold fold : folds) points = fold.fold(points);
		printPoints(points);
	}

	private static void printPoints(HashSet<Point> points) {
		int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();

		IntStream.rangeClosed(0, maxY-minY)
			.mapToObj(j -> 
				IntStream.rangeClosed(0, maxX-minX)
					.mapToObj(i -> points.contains(new Point(i, j)) ? "█" : " ")
					.collect(Collectors.joining()))
			.forEach(System.out::println);
	}

	private static class Fold {
		public final boolean isX;
		public final int z;

		public Fold(int z, boolean isX) {
			this.isX = isX;
			this.z = z;
		}

		public Point fold(Point p) {
			if (isX) {
				return p.x <= z ? p : new Point(z + z - p.x, p.y);
			} else {
				return p.y <= z ? p : new Point(p.x, z + z - p.y);
			}
		}

		public HashSet<Point> fold(HashSet<Point> points) {
			return points.stream().map(this::fold).collect(Collectors.toCollection(HashSet::new));
		}
	}

}
