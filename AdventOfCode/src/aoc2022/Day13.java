package aoc2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import aocutil.IO;

public class Day13 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		final ArrayList<Item> list = Stream.concat(
				IO.lines(2022, 13)
					.filter(line -> line.length()>0)
					.map(line -> Item.parseItem(line, false)), 
				Stream.of(Item.parseItem("[[2]]", true), Item.parseItem("[[6]]", true))
			).sorted().collect(Collectors.toCollection(ArrayList::new));
		System.out.println(IntStream.range(0, list.size()).filter(i -> list.get(i).isDivider()).map(i -> i+1).reduce(1, (a,b)->a*b));
	}
	
	private static void a() {
		int count = 0;
		try (Scanner scan = IO.scanner(2022, 13)) {
			for (int i = 1; scan.hasNext(); ++i) {
				final String topString = scan.nextLine();
				final String botString = scan.nextLine();
				Item top = Item.parseItem(topString, false);
				Item bot = Item.parseItem(botString, false);
				if (scan.hasNext()) scan.nextLine(); // emtpy
				
				if (top.compareTo(bot) <= 0) count += i;
			}
		}
		System.out.println(count);
	}

	private static abstract class Item implements Comparable<Item> {
		
		public abstract boolean isDivider();
		
		public static Item parseItem(String input, boolean isDivider) {
			return parseItemImpl(input.substring(1, input.length()-1), isDivider);
		}
		
		private static Item parseItemImpl(String input, boolean isDivider) {
			if (input.isEmpty()) return new ListItem(List.of(), isDivider);
			final ArrayList<Item> list = new ArrayList<>();
			for (int i = 0; i < input.length(); ++i) {
				switch (input.charAt(i)) {
					case ',':
						break;
					case '[':
						final int closedPos = findMatchingCloseBracket(input, i);
						list.add(parseItemImpl(input.substring(i + 1, closedPos), isDivider));
						i = closedPos;
						break;
					default:
						StringBuilder parse = new StringBuilder();
						char current = input.charAt(i);
						while ('0' <= current && current <= '9') {
							parse.append(current);
							if (++i >= input.length()) break;
							current = input.charAt(i);
							continue;
						}
						list.add(new ValItem(Integer.parseInt(parse.toString()), isDivider));
						break;
				}
			}
			return new ListItem(list, isDivider);
		}
		
		private static int findMatchingCloseBracket(String str, int startIndex) {
			if (str.charAt(startIndex) != '[') throw new RuntimeException();
			int count = 0;
			for (int i = startIndex; i <= str.length(); ++i) {
				count += switch (str.charAt(i)) {
					case '[' -> 1;
					case ']' -> -1;
					default -> 0;
				};
				if (count == 0) return i;
			}
			throw new RuntimeException("No matching bracket");
		}
	}
	
	private static class ListItem extends Item {
		private final ArrayList<Item> list;
		private final boolean isDivider;

		public ListItem(List<Item> list, boolean isDivider) {
			this.list = new ArrayList<>(list);
			this.isDivider = isDivider;
		}

		public int compareTo(Item o) {
			if (o instanceof ListItem that) {

				final int min = Integer.min(this.list.size(), that.list.size());
				for (int i = 0; i < min; ++i) {
					final int comp = this.list.get(i).compareTo(that.list.get(i));
					if (comp != 0) return comp;
				}
				return Integer.compare(this.list.size(), that.list.size());
			} else if (o instanceof ValItem that) return this.compareTo(new ListItem(List.of(that), isDivider));
			else throw new RuntimeException();
		}

		public String toString() {
			return list.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
		}

		public boolean isDivider() {
			return this.isDivider;
		}
	}

	private static class ValItem extends Item {
		private final int value;
		private final boolean isDivider;

		public ValItem(int value, boolean isDivider) {
			this.value = value;
			this.isDivider = isDivider;
		}

		public int compareTo(Item o) {
			if (o instanceof ValItem that) return Integer.compare(this.value, that.value);
			if (o instanceof ListItem that) return new ListItem(List.of(this), isDivider).compareTo(that);
			throw new RuntimeException();
		}

		public String toString() {
			return Integer.toString(value);
		}
		
		public boolean isDivider() {
			return this.isDivider;
		}
	}

}
