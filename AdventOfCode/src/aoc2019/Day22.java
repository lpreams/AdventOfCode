package aoc2019;

import java.util.Arrays;

import aocutil.IO;

public class Day22 {

	public static void main(String[] args) {
		int ops = 0;
		
		Deck d = new Deck(10007);
		for (String line : IO.lines(2019, 22).toList()) {
			if (line == null) break;
			if (line.startsWith("cut")) {
				int N = Integer.parseInt(line.split(" ")[1]);
				d.cut(N);
			} else if (line.startsWith("deal with increment")) {
				int N = Integer.parseInt(line.split(" ")[3]);
				d.deal(N);
			} else if (line.startsWith("deal into new stac")) {
				d.newStack();
			}
			++ops;
		}

		for (int i=0; i<d.arr.length; ++i) if (d.arr[i] == 2019) {
			System.out.println(i + " in " + ops + " ops");
			break;
		}
	}
	
	private static class Deck {
		
		private int[] arr, spare;
		
		Deck(int size) {
			if (size < 2) throw new IllegalArgumentException();
			arr = new int[size];
			spare = new int[size];
			for (int i=0; i<size; ++i) arr[i] = i;
		}
		
		void newStack() {
			for (int i=0; i<arr.length/2; ++i) {
				int temp = arr[i];
				arr[i] = arr[arr.length-1-i];
				arr[arr.length-1-i] = temp;
			}
		}
		
		void cut(int N) {
			int[] newArr = spare;
			if (N > 0) {
				copyRange(arr, newArr, 0, arr.length-N, N);
				copyRange(arr, newArr, N, 0, arr.length-N);
			} else if (N < 0) {
				N = -N;
				copyRange(arr, newArr, arr.length-N, 0, N);
				copyRange(arr, newArr, 0, N, arr.length-N);
			}
			spare = arr;
			arr = newArr;
		}
		
		void deal(int N) {
			int[] newArr = spare;
			int oldptr = 0, newptr = 0;
			for (int i=0; i<arr.length; ++i) {
				newArr[newptr] = arr[oldptr];
				++oldptr;
				newptr += N;
				while (newptr >= arr.length) newptr -= arr.length; 
			}
			spare = arr;
			arr = newArr;
		}
		
		public String toString() {
			return Arrays.toString(arr);
		}
	}

	private static void copyRange(int[] in, int[] out, int startIn, int startOut, int num) {
		if (startIn +num > in.length) throw new IllegalArgumentException();
		if (startOut+num > out.length) throw new IllegalArgumentException();
		
		for (int i=0; i<num; ++i) {
			out[i+startOut] = in[i+startIn];
		}
	}
}
