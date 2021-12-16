package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import aocutil.IO;

public class Day03 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		int oxy = oxygen();
		int co2 = co2();
		System.out.println(oxy + " * " + co2 + " = " + oxygen()*co2());
	}
	
	private static int co2() {
		ArrayList<int[]> data = new ArrayList<>(getReport());
		for (int bit = 0; bit < data.get(0).length; ++bit) {
			int zeros = 0, ones = 0;
			for (int row = 0; row < data.size(); ++row) {
				if (data.get(row)[bit] == 0) ++zeros;
				else ++ones;
			}
			int criteria = zeros > ones ? 1 : 0;
			final int Bit = bit;
			data.removeIf(row -> row[Bit] != criteria);
			if (data.size() < 2) break;
		}
		
		return Integer.parseInt(Arrays.stream(data.get(0)).mapToObj(Integer::toString).collect(Collectors.joining()), 2);
	}
	
	private static int oxygen() {
		ArrayList<int[]> data = new ArrayList<>(getReport());
		for (int bit = 0; bit < data.get(0).length; ++bit) {
			int zeros = 0, ones = 0;
			for (int row = 0; row < data.size(); ++row) {
				if (data.get(row)[bit] == 0) ++zeros;
				else ++ones;
			}
			int criteria = zeros > ones ? 0 : 1;
			final int Bit = bit;
			data.removeIf(row -> row[Bit] != criteria);
			if (data.size() < 2) break;
		}
		
		return Integer.parseInt(Arrays.stream(data.get(0)).mapToObj(Integer::toString).collect(Collectors.joining()), 2);
	}
	
	private static void a() {
		List<int[]> data = getReport();
		StringBuilder gam = new StringBuilder();
		StringBuilder eps = new StringBuilder();
		
		for (int bit = 0; bit < data.get(0).length; ++bit) {
			int zeros = 0, ones = 0;
			for (int row = 0; row < data.size(); ++row) {
				if (data.get(row)[bit] == 0) ++zeros;
				else ++ones;
			}
			gam.append(zeros > ones ? '0' : '1');
			eps.append(zeros < ones ? '0' : '1');
			
		}
		
		int gamma = Integer.parseInt(gam.toString(), 2);
		int epsilon = Integer.parseInt(eps.toString(), 2);
		
		System.out.println(gamma * epsilon);
	}
	
	private static List<int[]> getReport() {
		return IO.lines(2021, 3).map(line -> line.chars().map(c -> c == '0' ? 0 : 1).toArray()).toList();
	}

}
