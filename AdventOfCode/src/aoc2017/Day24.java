package aoc2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import aocutil.IO;

public class Day24 {

	public static void main(String[] args) {
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
		for (String line : IO.linesIter(2017, 24)) {
			String[] arr = line.split("/");
			int a = Integer.parseInt(arr[0]);
			int b = Integer.parseInt(arr[1]);
			HashSet<Integer> set = map.computeIfAbsent(a, z -> new HashSet<>());
			set.add(b);
			set = map.computeIfAbsent(b, z -> new HashSet<>());
			set.add(a);
		}
		findBridges(map, 0, 0, new HashSet<>(), 0);
		System.out.println(maxScore);
		System.out.println(maxLengthScore);
	}
	
	private static int maxScore = -1;
	
	private static int maxLength = -1, maxLengthScore = -1;
	
	private static void findBridges(HashMap<Integer, HashSet<Integer>> map, int lastPins, int currentScore, HashSet<Pair> used, int length) {
		
		if (length > maxLength) {
			maxLength = length;
			maxLengthScore = currentScore;
		} else if (length == maxLength && currentScore > maxLengthScore) {
			maxLengthScore = currentScore;
		}
		
		HashSet<Integer> possible = map.get(lastPins);
		for (int next : possible) {
			Pair pair = new Pair(lastPins, next);
			if (used.contains(pair)) continue;
			used.add(pair);
			int newScore = currentScore+lastPins+next;
			findBridges(map, next, newScore, used, length+1);
			if (newScore > maxScore) maxScore = newScore;
			used.remove(pair);
		}
	}
	
	private static class Pair {
		public final int x, y;

		public Pair(int x, int y) {
			this.x = x < y ? x : y;
			this.y = x + y - this.x;
		}

		public int hashCode() {
			return Objects.hash(x, y);
		}

		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof Pair)) return false;
			Pair other = (Pair) obj;
			return x == other.x && y == other.y;
		}
	}
}
