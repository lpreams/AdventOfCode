package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		a(80);
		b(256);
	}
	
	private static void b(int N) {
		long[] counts = new long[9];
		initAges().forEach(i -> ++counts[i]);
		for (int n=0; n<N; ++n) { // only go to 80 to solve pt1
			long newFish = counts[0];
			for (int i=1; i<counts.length; ++i) {
				counts[i-1] = counts[i];
			}
			counts[8] = newFish;
			counts[6] += newFish;
		}
		
		System.out.println(Arrays.stream(counts).sum());
	}
	
	private static void a(int N) {
		ArrayList<Integer> list = initAges().mapToObj(x->x).collect(Collectors.toCollection(ArrayList::new));
		for (int n=0; n<N; ++n) { // don't bother trying 256, beware the oom killer
			ArrayList<Integer> newFish = new ArrayList<>();
			for (int i=0; i<list.size(); ++i) {
				int f = list.get(i);
				if (f == 0) {
					list.set(i, 6);
					newFish.add(8);
				} else {
					list.set(i, f-1);
				}
			}
			list.addAll(newFish);
//			System.out.printf("%3d %d%n", n, list.size());
		}
		System.out.println(list.size());
	}

	private static IntStream initAges() {
		return Arrays.stream(IO.string(2021, 6).split(",")).mapToInt(Integer::parseInt);
	}
}
