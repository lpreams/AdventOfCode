package aoc2017;

import aocutil.IO;

public class Day09 {

	public static void main(String[] args) {
		String data = IO.string(2017, 9); // "{{<!!>},{<!!>},{<!!>},{<!!>}}";
		
		int totalScore = 0;
		int groups = 0;
		int depth = 0;
		int totalGarbage = 0;
		boolean isGarbage = false;
		boolean skipNext = false;
		
		for (char c : data.toCharArray()) {
			
			if (skipNext) {
				skipNext = false;
				continue;
			}
			
			if (isGarbage) {
				if (c == '!') {
					skipNext = true;
					continue;
				}
								
				if (c == '>') {
					isGarbage = false;
					continue;
				}
				
				++totalGarbage;
				
			} else {
				
				if (c == '{') {
					++depth;
					totalScore += depth;
					continue;
				}
				
				if (c == '}') {
					--depth;
					++groups;
					continue;
				}
				
				if (c == '<') {
					isGarbage = true;
					continue;
				}
			}
		}
		
		System.out.println(totalScore + " (" + groups + ")");
		System.out.println(totalGarbage);
		
	}
}
