package aoc2017;

import java.util.ArrayList;
import java.util.List;

import aocutil.IO;

public class Day17 {

	public static void main(String[] args) {
		a();
		b();
	}
			
	private static void a() {
		List<Integer> list = new ArrayList<>(List.of(0));
		int ptr = 0;
		int lastVal = 0;
		while (lastVal < 2017) {
			ptr += STEPS;
			ptr = ptr % list.size();
			++lastVal;
			++ptr;
			list.add(ptr, lastVal);
		}
		System.out.println(list.get((ptr+1) % list.size()));
	}
	
	private static void b() {
		//List<Integer> list = new ArrayList<>(List.of(0));
		int second = -1;
		int size = 1;
		int ptr = 0;
		int lastVal = 0;
		while (lastVal < 50000000) {
			ptr += STEPS;
			ptr = ptr % size;
			++lastVal;
			++ptr;
			//list.add(ptr, lastVal);
			if (ptr == 1) second = lastVal;
			++size;
		}
		System.out.println(second);
	}
	
	private static final int STEPS = IO.scanner(2017, 17).nextInt();
}
