package aoc2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import aocutil.IO;

public class Day01 {

	public static void main(String[] args) throws Exception {
		ArrayList<Integer> sums = new ArrayList<>();
		int curSum = 0;
		for (String line : IO.linesIter(2022, 1)) {
			if (line.length() == 0) {
				sums.add(curSum);
				curSum = 0;
			} else {
				curSum += Integer.parseInt(line);
			}
		}
		if (curSum > 0) sums.add(curSum);
		
		Collections.sort(sums, Comparator.reverseOrder());
		
		System.out.println(sums.get(0));
		System.out.println(sums.get(0) + sums.get(1) + sums.get(2));
	}		
}