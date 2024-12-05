package aoc2024;

import java.util.AbstractList;
import java.util.List;

import aocutil.IO;

public class Day02 {

	public static void main(String[] args) {
		final List<List<Integer>> listList = IO.lines(2024, 2).map(line -> List.of(line.split("\\s+")).stream().map(Integer::parseInt).toList()).toList();
		System.out.println(listList.stream().filter(list -> isSafe(list)).count());
		System.out.println(listList.stream().filter(list -> isSafe2(list)).count());
	}

	private static boolean isSafe(List<Integer> list) {
		boolean dir = list.get(0) < list.get(1);
		for (int i = 0; i < list.size() - 1; ++i) {
			final int diff = list.get(i + 1) - list.get(i);
			if ((dir && diff <= 0) || (!dir && diff >= 0)) {
				return false;
			}
			if ((dir && diff > 3) || (!dir && diff < -3)) {
				return false;
			}
		}
		return true;
	}

	private static List<Integer> removeAt(List<Integer> list, int idx) {
		return new AbstractList<Integer>() {
			public int size() {
				return list.size() - 1;
			}
			public Integer get(int index) {
				if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();
				return index < idx ? list.get(index) : list.get(index + 1);
			}
		};
	}

	private static boolean isSafe2(List<Integer> list) {
		if (isSafe(list)) return true;
		for (int i = 0; i < list.size(); ++i) {
			if (isSafe(removeAt(list, i))) return true;
		}
		return false;
	}
}
