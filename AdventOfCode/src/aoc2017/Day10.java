package aoc2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day10 {

	public static void main(String[] args) {
		a();
		b();
	}
	
	private static void b() {
		ArrayList<Integer> lengths = IO.string(2017,10).chars().mapToObj(x->x).collect(Collectors.toCollection(ArrayList::new));
		lengths.addAll(List.of(17, 31, 73, 47, 23));
		
		int[] list = IntStream.rangeClosed(0, 255).toArray();
		int pos = 0;
		int skip = 0;

		for (int round = 0; round < 64; ++round) {

			for (int length : lengths) {

				int ai = pos;
				int bi = (pos + length - 1) & 255;

				for (int i = 0; i < length / 2; ++i) {

					int temp = list[ai];
					list[ai] = list[bi];
					list[bi] = temp;

					ai = (ai + 1) & 255;
					bi = Math.floorMod((bi - 1), 256);

				}

				pos = (pos + length + skip) & 255;
				skip = (skip + 1);

			}
		}
		
		ArrayList<Integer> dense = new ArrayList<>();
		for (int i=0; i<list.length-1; i += 16) {
			int sum = 0;
			for (int j=0; j<16; ++j) {
				sum = sum ^ list[i+j];
			}
			dense.add(sum);
		}
				
		for (int length : dense) {
			if (length < 16) System.out.print("0" + Integer.toHexString(length));
			else System.out.print(Integer.toHexString(length));
		}

	}
	
	private static void a() {
		
		int[] lengths = Arrays.stream(IO.string(2017,10).split(",")).mapToInt(Integer::parseInt).toArray();
		
		int[] list = IntStream.rangeClosed(0, 255).toArray();
		int pos = 0;
		int skip = 0;
		
		for (int length : lengths) {
			
			int ai = pos;
			int bi = (pos+length-1) & 255;
			
			for (int i=0; i<length/2; ++i) {
				
				int temp = list[ai];
				list[ai] = list[bi];
				list[bi] = temp;
				
				ai = (ai + 1) & 255;
				bi = Math.floorMod((bi - 1), 256);
				
			}
						
			pos = (pos + length + skip) & 255;
			skip = (skip + 1);
			
		}
		
		System.out.println(list[0]*list[1]);
	}
	
}
