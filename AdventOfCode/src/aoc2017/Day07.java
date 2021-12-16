package aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import aocutil.IO;

public class Day07 {

	public static void main(String[] args) {
		Prog root = getRoot();
		System.out.println("ROOT: " + root.name + ": " + root.totalWeight());

		for (Prog child : root.children.get(6).children.get(2).children) {
			System.out.println(child.name + ": \t" + child.totalWeight() + " " + child.weight);
		}
		
	}
	
	private static Prog getRoot() {
		HashMap<String, Prog> map = new HashMap<>(); 
		for (String lineString : IO.lines(2017, 7).toList()) {
			
			Scanner line = new Scanner(lineString);
			String name = line.next();
			
			Prog parent = map.computeIfAbsent(name, Prog::new);
			String weight = line.next(); // weight
			
			parent.weight = Integer.parseInt(weight.substring(1, weight.length()-1));
//			System.out.println(parent.name + " (" + parent.weight + ")");
			
			if (!lineString.contains("->")) continue;

			line.next(); // ->
			while (line.hasNext()) {
				String childName = filter(line.next());
				Prog child = map.computeIfAbsent(childName, Prog::new);
				
				child.parent = parent;
				parent.children.add(child);
				
			}
			line.close();
		}
		
		Prog prog = map.values().stream().findAny().get();
		while (prog.parent != null) prog = prog.parent; 
		return prog;
	}
	
	private static class Prog {
		public final String name;
		public Prog parent;
		public ArrayList<Prog> children = new ArrayList<>();
		public int weight;
		private int totalWeight = -1;
		public Prog(String name) {
			this.name = name;
		}
		
		public int totalWeight() {
			if (totalWeight == -1) totalWeight = children.stream().mapToInt(Prog::totalWeight).sum() + weight;
			return totalWeight;
			
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof Prog)) return false;
			Prog other = (Prog) obj;
			return Objects.equals(name, other.name);
		}
		
		
	}
	
	private static String filter(String in) {
		return in.replaceAll("[^a-zA-Z0-9]","");
	}

}
