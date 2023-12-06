package aoc2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import aocutil.IO;

public class Day05 {

	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		long start = System.nanoTime();
		final List<Long> seeds;
		final List<RangeMap> maps = new ArrayList<>();
		try (Scanner scan = IO.scanner(2023, 5)) {
			scan.next(); // "seeds:"
			seeds = Arrays.stream(scan.nextLine().trim().split(" ")).map(Long::parseLong).toList();
			scan.nextLine();
			while (scan.hasNext()) {
				maps.add(new RangeMap(scan));
			}
		}		
		System.out.println(seeds.stream().mapToLong(val -> map(val, maps)).min().getAsLong());
		
		final ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
		final List<Future<Long>> fut = IntStream.iterate(0, i -> i < seeds.size(), i -> i + 2).mapToObj(i -> ex.submit(() -> {
			return LongStream.range(seeds.get(i), seeds.get(i) + seeds.get(i + 1)).map(val -> map(val, maps)).min().getAsLong();
		})).toList();
		
		final List<Long> res = new ArrayList<>();
		for (Future<Long> fu : fut) {
			res.add(fu.get());
		}
		System.out.println(res.stream().mapToLong(z->z).min().getAsLong());
		ex.shutdown();		
		ex.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		long stop = System.nanoTime();
		System.out.println("Only took " + ((stop-start)/1000000000.0) + " seconds!");
	}
	
	private static long map(long val, List<RangeMap> maps) {
		for (RangeMap map : maps) val = map.map(val);
		return val;
	}

	private static class RangeMap {
		
		private final List<Range> ranges;
		
		public RangeMap(Scanner scan) {
			ranges = new ArrayList<>();
			scan.nextLine(); // title
			while (scan.hasNext()) {
				final String line = scan.nextLine();
				if (line.length() == 0) break;
				ranges.add(Range.parse(line));
			}
			Collections.sort(ranges, Comparator.comparing(Range::source));
		}
		
		public long map(long from) {
			final Range range = search(from);
			if (range == null) return from;
			return from - range.source() + range.dest();
		}
		
		private Range search(long key) {
			return ranges.stream().filter(r -> r.source() <= key && key < r.source()+r.count()).findAny().orElse(null);
		}
	}
	
	private static record Range(long source, long dest, long count) implements Comparable<Integer> {
		public static Range parse(String line) {
			final long s, d, c;
			try (Scanner scan = new Scanner(line)) {
				d = scan.nextLong();
				s = scan.nextLong();
				c = scan.nextLong();
			}
			return new Range(s, d, c);
		}

		@Override
		public int compareTo(Integer o) {
			if (o < source) return -1;
			if (o >= source+count) return 1;
			return 0;
		}
	}
}
