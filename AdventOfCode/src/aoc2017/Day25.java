package aoc2017;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import aocutil.IO;

public class Day25 {

	public static void main(String[] args) {
		Turing t = new Turing(IO.scanner(2017,25));
		for (int i=0; i<t.checkAfter; ++i) t.step();
		System.out.println(t.checksum());
	}

	private static class Turing {

		private HashMap<String, State> states;
		private HashSet<Integer> tape;
		private String statePointer;
		private int tapePointer;
		public final int checkAfter;
				
		Turing(Scanner scan) {
			states = new HashMap<>();
			tape = new HashSet<>();
			tapePointer = 0;
			
			statePointer = lastWord(scan.nextLine(), 1, 0);
			checkAfter = Integer.parseInt(lastWord(scan.nextLine(), 0, 1));
			while (scan.hasNextLine()) {
				scan.nextLine(); // empty
				String name = lastWord(scan.nextLine(), 1, 0);
				scan.nextLine(); // if current value is 0
				boolean zeroWrite = lastWord(scan.nextLine(), 1, 0).equals("1");
				boolean zeroMove = lastWord(scan.nextLine(), 1, 0).equals("right");
				String zeroState = lastWord(scan.nextLine(), 1, 0);
				scan.nextLine(); // if current value is 1
				boolean oneWrite = lastWord(scan.nextLine(), 1, 0).equals("1");
				boolean oneMove = lastWord(scan.nextLine(), 1, 0).equals("right");
				String oneState = lastWord(scan.nextLine(), 1, 0);
				states.put(name, new State(zeroWrite, oneWrite, zeroMove, oneMove, zeroState, oneState));
			}
			
			scan.close();
		}
		
		public void step() {
			State state = states.get(statePointer);
			if (!tape.contains(tapePointer)) { // current value is 0
				if (state.zeroWrite) tape.add(tapePointer);
				else tape.remove(tapePointer);
				
				if (state.zeroMove) ++tapePointer;
				else --tapePointer;
				
				statePointer = state.zeroState;
			} else { // current value is 1
				if (state.oneWrite) tape.add(tapePointer);
				else tape.remove(tapePointer);
				
				if (state.oneMove) ++tapePointer;
				else --tapePointer;
				
				statePointer = state.oneState;
			}
		}
		
		public int checksum() {
			return tape.size();
		}
		
		private static class State {
			private final boolean zeroWrite, oneWrite, zeroMove, oneMove; // true means right, false means left
			private final String zeroState, oneState;

			private State(boolean zeroWrite, boolean oneWrite, boolean zeroMove, boolean oneMove, String zeroState,
					String oneState) {
				this.zeroWrite = zeroWrite;
				this.oneWrite = oneWrite;
				this.zeroMove = zeroMove;
				this.oneMove = oneMove;
				this.zeroState = zeroState;
				this.oneState = oneState;
			}
		}

	}
	
	private static String lastWord(String line, int removeChars, int offset) {
		String[] arr = line.split(" ");
		String word = arr[arr.length-1-offset];
		word = word.substring(0, word.length()-removeChars);
		return word;
	}

}




