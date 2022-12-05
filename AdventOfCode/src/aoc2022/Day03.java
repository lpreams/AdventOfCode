package aoc2022;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day03 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		int sum = 0;
		List<String> sacks = IO.lines(2022, 3).toList();
		for (int i=0; i<sacks.size(); i+=3) {
			HashSet<Integer> a = setify(sacks.get(i+0));
			HashSet<Integer> b = setify(sacks.get(i+1));
			HashSet<Integer> c = setify(sacks.get(i+2));
			a.retainAll(b);
			a.retainAll(c);
			sum += a.stream().mapToInt(x -> x).sum();
		}
		System.out.println(sum);
	}
	
	private static void a() {
		System.out.println(IO.lines(2022, 3).mapToInt(sack -> {
			HashSet<Integer> left = setify(sack.substring(0, sack.length()/2));
			HashSet<Integer> right = setify(sack.substring(sack.length()/2, sack.length()));
			left.retainAll(right);	
			return left.stream().mapToInt(x -> x).sum();
		}).sum());
	}
	
	private static HashSet<Integer> setify(String str) {
		return str.chars()
			.map(c -> ('a' <= c && c <= 'z') ? (c - 'a' + 1) : (('A' <= c && c <= 'Z') ? (c - 'A' + 27) : 0))
			.boxed()
			.collect(Collectors.toCollection(HashSet::new));
	}

}
