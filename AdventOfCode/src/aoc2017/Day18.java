package aoc2017;

import java.util.HashMap;
import java.util.List;

import aocutil.IO;

public class Day18 {

	public static void main(String[] args) {
		
		HashMap<String, Long> mem = new HashMap<>();
		List<String> prog = IO.lines(2017, 18).toList();
		
		long lastSound = 0;
		
		for (int ptr = 0; ptr < prog.size(); ++ptr) {
			System.out.println(ptr);
			String[] line = prog.get(ptr).split(" ");
			final String inst = line[0];
			final String reg = line[1];
			long value;
			switch (inst) {
			case "snd":
				try {
					lastSound = Integer.parseInt(reg);
				} catch (Exception e) {
					lastSound = mem.getOrDefault(reg, 0l);
				}
				break;
			case "set":
				try {
					value = Integer.parseInt(line[2]);
				} catch (Exception e) {
					value = mem.getOrDefault(line[2], 0l);
				}
				mem.put(reg, value);
				break;
			case "add":
				try {
					value = Integer.parseInt(line[2]);
				} catch (Exception e) {
					value = mem.getOrDefault(line[2], 0l);
				}
				mem.put(reg, mem.getOrDefault(reg, 0l) + value);
				break;
			case "mul":
				try {
					value = Integer.parseInt(line[2]);
				} catch (Exception e) {
					value = mem.getOrDefault(line[2], 0l);
				}
				mem.put(reg, mem.getOrDefault(reg, 0l) * value);
				break;
			case "mod":
				try {
					value = Integer.parseInt(line[2]);
				} catch (Exception e) {
					value = mem.getOrDefault(line[2], 0l);
				}
				mem.put(reg, mem.getOrDefault(reg, 0l) % value);
				break;
			case "rcv":
				if (mem.getOrDefault(reg, 0l) != 0) {
					mem.put(reg, lastSound);
					System.out.println("rcv: " + lastSound);
					System.exit(0);
				}
				break;
			case "jgz":
				if (mem.getOrDefault(reg, 0l) > 0) {
					try {
						value = Integer.parseInt(line[2]);
					} catch (Exception e) {
						value = mem.getOrDefault(line[2], 0l);
					}
					ptr += value-1;
				}
			}			
		}
		
	}

}
