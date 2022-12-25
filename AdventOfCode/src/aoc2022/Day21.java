package aoc2022;

import java.util.HashMap;
import java.util.Scanner;

import aocutil.IO;

public class Day21 {

	public static void main(String[] args) {		
		a();
		b();		
	}
	
	private static void a() {
		System.out.println(getInitialMonkeys(false).get("root").yell());
	}
	
	private static void b() {
		final HashMap<String, Monkey> monkeyList = getInitialMonkeys(true);
		final OpMonkey root = (OpMonkey) monkeyList.get("root");
		final Monkey left = monkeyList.get(root.leftName);
		final Monkey right = monkeyList.get(root.rightName);
		if (left.yell() == null) left.fixNull(right.yell());
		else if (right.yell() == null) right.fixNull(left.yell());
		System.out.println(monkeyList.get("humn").yell());
	}
	
	private static HashMap<String, Monkey> getInitialMonkeys(boolean unknownHuman) {
		HashMap<String, Monkey> monkeys = new HashMap<>();
		try (Scanner scan = IO.scanner(2022, 21)) {
			while (scan.hasNext()) try (Scanner line = new Scanner(scan.nextLine())) {
				final String name = line.next().substring(0, 4);
				final String left = line.next();
				Monkey monkey;
				try {
					monkey = new Monkey((unknownHuman && name.equals("humn")) ? null : Long.parseLong(left));
				} catch (NumberFormatException e) {
					final char op = line.next().charAt(0);
					final String right = line.next();
					monkey = new OpMonkey(op, left, right, monkeys);
				}
				monkeys.put(name, monkey);
			}
		}
		return monkeys;
	}
	
	private static class Monkey {

		private Long number;
		
		public Monkey(Long number) {
			this.number = number;
		}

		public Long yell() {
			return this.number;
		}
		
		public void fixNull(long desiredValue) {
			this.number = desiredValue;
		}		
	}
	
	private static class OpMonkey extends Monkey {
		
		private final char op;
		private final String leftName, rightName;
		private final HashMap<String, Monkey> monkeyList;
		
		private OpMonkey(char op, String left, String right, HashMap<String, Monkey> monkeyList) {
			super(null);
			this.op = op;
			this.leftName = left;
			this.rightName = right;
			this.monkeyList = monkeyList;
		}

		@Override
		public void fixNull(long desiredValue) {
			final Monkey left = monkeyList.get(leftName);
			final Monkey right = monkeyList.get(rightName);
			final Long leftV = left.yell();
			final Long rightV = right.yell();
			if (leftV == null && rightV == null) throw new RuntimeException();
			if (leftV != null && rightV != null && this.yell() != desiredValue) throw new IllegalArgumentException("Can't be fixed");
			final long knownOperand = leftV == null ? rightV : leftV;			
			(leftV == null ? left : right).fixNull(switch (op) {
				case '+' -> desiredValue - knownOperand; 
				case '-' -> leftV != null ? knownOperand - desiredValue : desiredValue + knownOperand; 
				case '*' -> desiredValue / knownOperand; 
				case '/' -> leftV != null ? knownOperand / desiredValue : desiredValue * knownOperand; 
				default -> throw new RuntimeException();
			});
		}
		
		@Override
		public Long yell() {
			Long av = monkeyList.get(this.leftName).yell();
			Long bv = monkeyList.get(this.rightName).yell();
			if (av == null || bv == null) return null;
			return switch (op) {
				case '+' -> av + bv;
				case '-' -> av - bv;
				case '*' -> av * bv;
				case '/' -> av / bv;
				default -> throw new RuntimeException();
			};
		}
	}	
}
