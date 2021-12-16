package aoc2017;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import aocutil.IO;

public class Day02 {

	public static void main(String[] args) {
		a();
		b();
	}

	private static void b() {
		int sum = 0;
		for (Scanner line : IO.lines(2017, 2).map(Scanner::new).toList()) {
			ArrayList<Integer> row = new ArrayList<>();
			while (line.hasNextInt()) {
				int next = line.nextInt();
				row.add(next);
			}
			Collections.sort(row);
			boolean found = false;
			for (int i = 0; i < row.size() - 1; ++i) {
				for (int j = i + 1; j < row.size(); ++j) {
					if (row.get(j) % row.get(i) == 0) {
						found = true;
						int div = row.get(j) / row.get(i);
						sum += div;
						break;
					}
				}
				if (found) break;
			}
			if (!found) throw new RuntimeException("you suck");
		}
		System.out.println(sum);
	}

	private static void a() {
		int sum = 0;
		for (Scanner line : IO.lines(2017, 2).map(Scanner::new).toList()) {
			int min = line.nextInt();
			int max = min;
			while (line.hasNextInt()) {
				int next = line.nextInt();
				if (next < min) min = next;
				if (next > max) max = next;
			}
			sum += max - min;
		}
		System.out.println(sum);
	}

}
