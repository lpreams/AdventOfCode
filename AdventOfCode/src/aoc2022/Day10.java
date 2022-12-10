package aoc2022;

import java.io.StringWriter;

import aocutil.IO;

public class Day10 {

	public static void main(String[] args) {
		Day10 f = new Day10();
		for (String[] line : IO.linesIterSplit(2022, 10, " ")) {
			f.cycle();
			if (line[0].equals("addx")) {
				f.cycle();
				f.add(line[1]);
			}
		}
		System.out.println(f.sum);
		System.out.println(f.crt);
	}
	
	private int screenPtr = 0;
	private int sprite = 1;
	private int hPtr;
	private int cycle = 0;
	private int sum = 0;
	private StringWriter crt = new StringWriter();

	private void cycle() {
		++cycle;
		if ((cycle - 20) % 40 == 0) sum += cycle * sprite;
		hPtr = screenPtr % 40;
		if (hPtr == sprite - 1 || hPtr == sprite || hPtr == sprite + 1) crt.write("#");
		else crt.write(" ");
		++screenPtr;
		if (screenPtr % 40 == 0) crt.write(System.lineSeparator());
	}

	private void add(String add) {
		sprite += Integer.parseInt(add);
	}
}
