package aoc2015;

import aocutil.IO;

public class Day01 {

	public static void main(String[] args) {
		final String s = IO.string(2015, 1);
		int floor = 0;
		int found = -1;
		for (int i=0; i<s.length(); ++i) {
			switch(s.charAt(i)) {
				case '(':
					++floor;
					break;
				case ')':
					--floor;
					break;
			}
			if (floor < 0 && found < 0) {
				found = i+1;
			}
		}
		System.out.println(floor);
		System.out.println(found);
	}
}
