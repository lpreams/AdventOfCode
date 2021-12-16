package aoc2017;

import java.util.HashMap;
import java.util.stream.Stream;

import aocutil.IO;

public class Day13 {

	public static void main(String[] args) {
		HashMap<Integer, Integer> data = getData();
		System.out.println(severity(data, 0, false)); // part 1
		System.out.println(Stream.iterate(0, i->i+1).filter(wait -> severity(data,wait,true)==0).findFirst().get()); // part 2
	}
	
	private static int severity(HashMap<Integer, Integer> data, int wait, boolean onlyCaught) {
		
		int sev = 0;
		
		for (var e : data.entrySet()) {
			int xPos = e.getKey();
			int range = e.getValue();
			
			int cycleLength = (range-1)*2;
			int moves = xPos+wait;
			
			boolean caught = moves % cycleLength == 0;
			
			if (caught) {
				if (onlyCaught) return 1;
				sev += xPos * range;
			}
		}
		
		return onlyCaught ? 0 : sev;
	}
		
	private static HashMap<Integer, Integer> getData() {
		HashMap<Integer, Integer> ranges = new HashMap<>();
		for (String[] arr : IO.lines(2017, 13).map(s -> s.split(": ")).toList()) {
			ranges.put(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		}
		return ranges;
	}
}
