package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		long[] counts = new long[9];
		initAges().forEach(i -> ++counts[i]);
		for (int n=0; n<256; ++n) { // only go to 80 to solve pt1
			long newFish = counts[0];
			for (int i=1; i<counts.length; ++i) {
				counts[i-1] = counts[i];
			}
			counts[8] = newFish;
			counts[6] += newFish;
		}
		
		System.out.println(Arrays.stream(counts).sum());
	}
	
	private static void a() {
		ArrayList<Integer> list = initAges().mapToObj(x->x).collect(Collectors.toCollection(ArrayList::new));
		for (int n=0; n<80; ++n) { // don't bother trying 256, beware the oom killer
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
		return Arrays.stream(IO.lines(2021, 6).findFirst().get().split(",")).mapToInt(Integer::parseInt);
	}
}
