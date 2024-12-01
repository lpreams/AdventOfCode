package aoc2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day09 {

	public static void main(String[] args) {
		final Map<String, Map<String, Integer>> dists = new HashMap<>();
		for (String[] line : IO.linesIterSplit(2015, 9, " ")) {
			final String a = line[0], b = line[2];
			final int dist = Integer.parseInt(line[4]);
			dists.computeIfAbsent(a, z->new HashMap<>()).put(b, dist);
			dists.computeIfAbsent(b, z->new HashMap<>()).put(a, dist);
		}
		
		int minDist = Integer.MAX_VALUE;
		int maxDist = Integer.MIN_VALUE;
		for (List<String> order : permute(dists.keySet().stream().sorted().toList())) {
			final int dist = IntStream.range(0, order.size()-1).map(i -> dists.get(order.get(i)).get(order.get(i+1))).sum();
			if (dist < minDist) minDist = dist;
			if (dist > maxDist) maxDist = dist;
		}
		System.out.println(minDist);
		System.out.println(maxDist);
	}
	
	private static <T> Iterable<List<T>> permute(List<T> inputList) {
		final List<T> list = Collections.unmodifiableList(new ArrayList<>(inputList));
		return () -> new Iterator<List<T>>() {
			int[] order = IntStream.range(0, list.size()).toArray();
			boolean hasNext = !list.isEmpty();

			public boolean hasNext() {
				return hasNext;
			}

			public List<T> next() {
				final List<T> ret = Arrays.stream(order).mapToObj(list::get).toList();
				if (!nextPermutation(order)) hasNext = false;
				return ret;
			}
			
			private static boolean nextPermutation(int[] P) {
				int x, y;
				boolean foundX = false;
				for (x = P.length - 2; x >= 0; --x) {
					if (P[x] < P[x + 1]) {
						foundX = true;
						break;
					}
				}
				if (!foundX) return false;
				for (y = P.length - 1; y >= 0; --y) {
					if (P[x] < P[y]) {
						break;
					}
				}
				swap(P, x, y);
				final int remaining = (P.length - x - 1)/2;
				for (int o = 0; o < remaining; ++o) {
					swap(P, x + 1 + o, P.length - o - 1);
				}
				return true;
			}

			private static void swap(int[] arr, int i, int j) {
				final int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		};
	}
}
