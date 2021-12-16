package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import aocutil.IO;

public class Day04 {

	public static void main(String[] args) {
		List<Integer> numbers;
		List<Board> boards = new ArrayList<>();
		
		try (Scanner scan = new Scanner(IO.string(2021, 4))) {
			numbers = Arrays.stream(scan.nextLine().split(",")).map(Integer::parseInt).distinct().toList();
			while (scan.hasNext()) {
				boards.add(new Board(scan));
			}
		}
		
		Integer first = null, last = null;
		
		for (int num : numbers) {
			for (Board b : boards) {
				if (b.hasWon()) continue;
				if (b.mark(num)) {
					int score = b.score*num;
					if (first == null) first = score;
					last = score;
				}
			}
		}
		System.out.println(first);
		System.out.println(last);
	}

	static class Board {
		HashMap<Integer, Integer> nums = new HashMap<>(); // number, index
		HashSet<Integer> markedInds = new HashSet<>();
		boolean won = false;
		int score = 0;
		
		Board(Scanner in) {
			for (int i = 0; i < 25; ++i) {
				int num = in.nextInt();
				nums.put(num, i);
				score += num;
			}
		}
		
		boolean mark(int num) {
			Integer index = nums.get(num);
			if (index == null) return won;
			if (markedInds.contains(index)) return won;
			
			if (markedInds.add(index)) {
				score -= num;
			}
			
			if (checkBoard(true) || checkBoard(false)) won = true;
			return won;
		}
		
		boolean hasWon() {
			return won;
		}
		
		boolean checkBoard(boolean rowsOrCols) {
			for (int row = 0; row < 5; ++row) {
				boolean allMarked = true;
				for (int col = 0; col < 5; ++col) {
					int index = rowsOrCols ? (row*5 + col) : (col*5 + row);
					if (!markedInds.contains(index)) {
						allMarked = false;
						break;
					}
				}
				if (allMarked == true) return true;
			}
			return false;

		}
		
	}
}
