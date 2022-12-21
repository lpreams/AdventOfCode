package aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BinaryOperator;

import aocutil.IO;

public class Day11 {

	public static void main(String[] args) {
		System.out.println(new MonkeyList(true).playRounds(20).monkeyBusiness());
		System.out.println(new MonkeyList(false).playRounds(10000).monkeyBusiness());
	}
		
	private static class MonkeyList {
		private final ArrayList<Monkey> list;
		private final long mod;
		private MonkeyList(boolean relief) {
			list = new ArrayList<>();
			try (Scanner scan = IO.scanner(2022, 11)) {
				while (scan.hasNext()) {
					list.add(new Monkey(scan, relief, this));
				}
			}
			mod = list.stream().mapToLong(m -> m.testDiv).reduce(1l, (a,b)->a*b);
		}
		private MonkeyList playRounds(int N) {
			for (int n = 0; n < N; ++n) {
				list.forEach(m -> m.playRound());			
			}
			return this;
		}
		private long monkeyBusiness() {
			return list.stream()
					.sorted((a,b) -> Long.compare(b.numInspected, a.numInspected))
					.limit(2)
					.mapToLong(m -> m.numInspected)
					.reduce(1, (a,b)->a*b);
		}
	}
		
	private static class Monkey {
		final MonkeyList parentList;
		final ArrayList<Long> itemWorries;
		final Long opLeft, opRight; // null means use the existing itemWorry value
		final BinaryOperator<Long> operator;
		final long testDiv;
		final int trueTarget, falseTarget;
		final boolean relief;
		long numInspected = 0;
		
		private void playRound() {
			while (itemWorries.size() > 0) {
				long itemWorry = itemWorries.remove(0);
				++this.numInspected;
				itemWorry = operator.apply(opLeft==null ? itemWorry : opLeft, opRight==null ? itemWorry : opRight);
				if (relief) itemWorry /= 3;
				itemWorry %= parentList.mod;
				parentList.list.get((itemWorry % testDiv == 0) ? trueTarget : falseTarget).itemWorries.add(itemWorry);
			}
		}
		
		private Monkey(Scanner scan, boolean relief, MonkeyList parentList) {
			this.parentList = parentList;
			scan.nextLine();
			itemWorries = new ArrayList<>(Arrays.stream(scan.nextLine().substring("  Starting items: ".length()).split(", ")).map(Long::parseLong).toList());
			String[] line = scan.nextLine().substring("  Operation: new = ".length()).split(" ");
			this.opLeft = line[0].equals("old") ? null : Long.parseLong(line[0]);
			this.opRight = line[2].equals("old") ? null : Long.parseLong(line[2]);
			this.operator = switch (line[1]) {
				case "+" -> (a, b) -> a + b;
				case "*" -> (a, b) -> a * b;
				default -> throw new RuntimeException();
			};
			this.testDiv = Long.parseLong(scan.nextLine().substring("  Test: divisible by ".length()));
			this.trueTarget = Integer.parseInt(scan.nextLine().substring("    If true: throw to monkey ".length()));
			this.falseTarget = Integer.parseInt(scan.nextLine().substring("    If false: throw to monkey ".length()));
			if (scan.hasNext()) scan.nextLine();
			this.relief = relief;
		}
	}
}
