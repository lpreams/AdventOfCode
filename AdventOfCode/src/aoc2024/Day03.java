package aoc2024;

import aocutil.IO;

public class Day03 {

	public static void main(String[] args) {
		run(IO.string(2024, 3), false);
		run(IO.string(2024, 3), true);
	}
	
	private static void run(String input, boolean handleDoDont) {
		long sum = 0;
		boolean enabled = true;
		final StateMachine sm = new StateMachine();
		for (int i=0; i<input.length(); ++i) {
			final char c = input.charAt(i);
			if (handleDoDont) {
				if (c == 'd') {
					System.err.println(input.substring(i, i+4));
					System.err.println(input.substring(i, i+7));
					if (input.substring(i, i+4).equals("do()")) {
						sm.reset();
						enabled = true;
						i += 3;
						continue;
					} else if (input.substring(i, i+7).equals("don't()")) {
						sm.reset();
						enabled = false;
						i += 6;
						continue;
					}
				}
			}
			
			switch (sm.state) {
				case Begin:
					if (c == 'm') sm.state = State.M;
					break;
				case M:
					if (c == 'u') 
						sm.state = State.U;
					else
						sm.reset();
					break;
				case U:
					if (c == 'l') 
						sm.state = State.L; 
					else
						sm.reset();
					break;
				case L:
					if (c == '(') 
						sm.state = State.Open;
					else
						sm.reset();
					break;
				case Open:
					if ('0' <= c && c <= '9') {
						sm.state = State.First;
						sm.left += c;
					} else {
						sm.reset();
					}
					break;
				case First:
					if ('0' <= c && c <= '9') {
						if (sm.left.length() >= 3) {
							sm.reset();
							break;
						}
						sm.left += c;
					} else if (c == ',') {
						sm.state = State.Comma;
					} else {
						sm.reset();
					}
					break;
				case Comma:
					if ('0' <= c && c <= '9') {
						sm.state = State.Second;
						sm.right += c;
					} else {
						sm.reset();
					}
					break;
				case Second:
					if ('0' <= c && c <= '9') {
						if (sm.right.length() >= 3) {
							sm.reset();
							break;
						}
						sm.right += c;
					} else if (c == ')') {
						if (enabled) {
							sum += Integer.parseInt(sm.left) * Integer.parseInt(sm.right);
						}
						
						sm.reset();
					} else {
						sm.reset();
					}
					break;
			}
			
		}
		System.out.println(sum);
	}
	
	private static enum State {
		Begin, // look for m
		M, // look for u
		U, // look for l
		L, // look for (
		Open, // look for number
		First, // look for number, or comma
		Comma, // look for number
		Second // look for number, or )
	}

	private static class StateMachine {
		public String left = "";
		public String right = "";
		public State state = State.Begin;
		public void reset() {
			left = "";
			right = "";
			state = State.Begin;
		}
	}

}
