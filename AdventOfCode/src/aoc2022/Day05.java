package aoc2022;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day05 {

	public static void main(String[] args) {
		ArrayList<LinkedList<Character>> stacksA = new ArrayList<>();
		ArrayList<LinkedList<Character>> stacksB = new ArrayList<>();
		stacksA.add(null);
		stacksB.add(null);
		for (int i = 1; i <= 9; ++i) stacksA.add(new LinkedList<>());
		Iterator<String> iter = IO.linesIter(2022, 5).iterator();
		parseStacks(iter, stacksA);
		for (int i = 1; i <= 9; ++i) stacksB.add(stacksA.get(i).stream().collect(Collectors.toCollection(LinkedList::new)));

		while (iter.hasNext()) {
			String[] arr = iter.next().split(" ");
			int crateCount = Integer.parseInt(arr[1]);
			int fromStack = Integer.parseInt(arr[3]);
			int toStack = Integer.parseInt(arr[5]);

			LinkedList<Character> fromA = stacksA.get(fromStack);
			LinkedList<Character> toA = stacksA.get(toStack);
			LinkedList<Character> fromB = stacksB.get(fromStack);
			LinkedList<Character> toB = stacksB.get(toStack);
			LinkedList<Character> temp = new LinkedList<>();

			for (int i = 0; i < crateCount; ++i) toA.addFirst(fromA.removeFirst());
			for (int i = 0; i < crateCount; ++i) temp.addFirst(fromB.removeFirst());
			for (int i = 0; i < crateCount; ++i) toB.addFirst(temp.removeFirst());
		}
		System.out.println(stacksA.stream().filter(Objects::nonNull).map(s -> s.peekFirst().toString()).collect(Collectors.joining()));
		System.out.println(stacksB.stream().filter(Objects::nonNull).map(s -> s.peekFirst().toString()).collect(Collectors.joining()));
	}

	private static Iterator<String> parseStacks(Iterator<String> iter, ArrayList<LinkedList<Character>> stacks) {
		while (iter.hasNext()) {
			String line = iter.next();

			if (line.contains("1   2   3   4   5   6   7   8   9")) {
				iter.next(); // empty liner
				return iter;
			}

			for (int i = 0; i * 4 < line.length(); ++i) {
				char c = line.charAt(i * 4 + 1);
				if (c == ' ') continue;
				stacks.get(i + 1).addLast(c);
			}

		}
		throw new RuntimeException();
	}

}
