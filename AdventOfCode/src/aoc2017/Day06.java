package aoc2017;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import aocutil.IO;

public class Day06 {

	public static void main(String[] args) {
		List<Integer> banks = getBanks();
		countCycles(banks);
		countCycles(banks);
	}
	
	private static void countCycles(List<Integer> banks) {
		HashSet<String> states = new HashSet<>();
		int cycles = 0;
		states.add(banks.toString());
		while (true) {
			int maxi = 0;
			int max = banks.get(0);
			for (int i=1; i<banks.size(); ++i) {
				if (banks.get(i) > max) {
					max = banks.get(i);
					maxi = i;
				}
			}
			
			banks.set(maxi, 0);
			for (int i = (maxi+1)%banks.size(); max > 0; i = (i+1)%banks.size()) {
				banks.set(i, banks.get(i)+1);
				--max;
			}
			
			++cycles;
			
			if (!states.add(banks.toString())) break;
			
		}
		
		System.out.println(cycles);
	}
	
	private static List<Integer> getBanks() {
		try (Scanner scan = new Scanner(IO.string(2017, 6))) {
			ArrayList<Integer> list = new ArrayList<>();
			while (scan.hasNextInt()) list.add(scan.nextInt());
			return list;
		}
	}
}
