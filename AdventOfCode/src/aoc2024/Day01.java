package aoc2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aocutil.IO;

public class Day01 {

	public static void main(String[] args) {
		final List<Integer> l = new ArrayList<>();
		final List<Integer> r = new ArrayList<>();
		for (String[] line : IO.linesIterSplit(2024, 1, "\\s+")) {
			l.add(Integer.parseInt(line[0]));
			r.add(Integer.parseInt(line[1]));
		}
		Collections.sort(l);
		Collections.sort(r);
		int sum = 0;
		for (int i=0; i<l.size(); ++i) {
			sum += Math.abs(l.get(i) - r.get(i));
		}
		System.out.println(sum);
		
		final Map<Integer, Integer> freqs = new HashMap<>();
		int count = 1;
		for (int i=0; i<r.size()-1; ++i) {
			if (!r.get(i).equals(r.get(i+1))) {
				freqs.put(r.get(i), count);
				count = 1;
			} else {
				++count;
			}
		}
		freqs.put(r.get(r.size()-1), count);
		System.out.println(l.stream().mapToInt(v -> v * freqs.getOrDefault(v, 0)).sum());
	}
}
