package aoc2021;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import aocutil.IO;

public class Day10 {

	public static void main(String[] args) {
		System.out.println(lines().mapToInt(Day10::corruption).sum());
		
		List<Long> list = lines().filter(line -> corruption(line) == 0).mapToLong(Day10::completion).sorted().boxed().toList();
		System.out.println(list.get(list.size()/2));
		
	}
	
	private static final Map<Character, Integer> corruptScores = Map.of(
			')', 3,
			']', 57,
			'}', 1197,
			'>', 25137
			);

	private static final Map<Character, Integer> completeScores = Map.of(
			'(', 1,
			'[', 2,
			'{', 3,
			'<', 4
			);

	private static long completion(String line) {
		LinkedList<Character> stack = new LinkedList<>();
		for (char c : line.toCharArray()) {
			switch (c) {
			case ']':
			case '}':
			case '>':
				if (stack.pop().charValue() + 2 != c) throw new IllegalArgumentException("Corrupted line: " + line);
				break;
			case ')':
				if (stack.pop().charValue() + 1 != c) throw new IllegalArgumentException("Corrupted line: " + line);
				break;
			default:
				stack.push(c);
				break;
			}
		}
		
		long score = 0;
		
		while (stack.size() > 0) {
			char c = stack.pop();
			score *= 5;
			score += completeScores.get(c);
		}
		
		return score;
	}
	
	private static int corruption(String line) {
		LinkedList<Character> stack = new LinkedList<>();
		for (char c : line.toCharArray()) {
			switch (c) {
			case ']':
			case '}':
			case '>':
				if (stack.pop().charValue() + 2 != c) return corruptScores.get(c);
				break;
			case ')':
				if (stack.pop().charValue() + 1 != c) return corruptScores.get(c);
				break;
			default:
				stack.push(c);
				break;
			}
		}
		return 0;
	}
	
	private static Stream<String> lines() {
		return IO.lines(2021, 10);
		
//		final String small = """
//[({(<(())[]>[[{[]{<()<>>
//[(()[<>])]({[<{<<[]>>(
//{([(<{}[<>[]}>{[]{[(<()>
//(((({<>}<{<{<>}{[]{[]{}
//[[<[([]))<([[{}[[()]]]
//[{[{({}]{}}([{[{{{}}([]
//{<[[]]>}<{[{[{[]{()[[[]
//[<(<(<(<{}))><([]([]()
//<{([([[(<>()){}]>(<<{{
//<{([{{}}[<[[[<>{}]]]>[]]
//""";
//		return Arrays.stream(small.split(System.lineSeparator()));
	}

}
