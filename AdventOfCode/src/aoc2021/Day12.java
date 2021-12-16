package aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import aocutil.IO;

public class Day12 {

	public static void main(String[] args) {
		System.out.println(dfs1("start", getMap(), new HashSet<>()));
		System.out.println(dfs2("start", getMap(), new Path()));
	}
	
	private static int dfs2(String visit, HashMap<String, HashSet<String>> map, Path p) {
		if (visit.equals("end")) return 1; // found 1 path
		p.push(visit);
		int paths = 0;
		for (String next : map.get(visit)) {
			if (p.canVisit(next)) {
				paths += dfs2(next, map, p);
			}
		}
		p.pop();
		return paths;
	}
	
	private static int dfs1(String visit, HashMap<String, HashSet<String>> map, HashSet<String> visited) {
		if (visit.equals("end")) return 1; // found 1 path
		if (Character.isLowerCase(visit.charAt(0))) visited.add(visit);
		int paths = 0;
		for (String next : map.get(visit)) {
			if (visited.contains(next)) continue;
			paths += dfs1(next, map, visited);
		}
		visited.remove(visit);
		return paths;
	}
	
	private static class Path {
		private ArrayList<String> order = new ArrayList<>();
		private HashSet<String> setA = new HashSet<>();
		private String b = null;
		
		public boolean canVisit(String node) {
			if (node.equals("start")) return false;
			if (node.equals(b)) return false;
			if (setA.contains(node)) return b == null;
			return true;
		}
		
		public void push(String node) {
			if (!Character.isUpperCase(node.charAt(0))) {
				
				if (node.equals(b)) throw new RuntimeException();
				if (setA.contains(node)) {
					if (b != null) throw new RuntimeException();
					else b = node;
				}
				else setA.add(node);
			}
			order.add(node);
		}
		
		public String pop() {
			String str = order.remove(order.size()-1);
			if (str.equals(b)) b = null;
			else setA.remove(str);
			return str;
		}
	}
	
	private static HashMap<String, HashSet<String>> getMap(){
		HashMap<String, HashSet<String>> map = new HashMap<>();
		for (String[] line : IO.lines(2021, 12).map(l -> l.split("-")).toList()) {
			addPair(line[0], line[1], map);
			addPair(line[1], line[0], map);
		}
		return map;
	}
	
	private static void addPair(String a, String b, HashMap<String, HashSet<String>>  map) {
		map.computeIfAbsent(a, s -> new HashSet<>()).add(b);
	}

}
