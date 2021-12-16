package aoc2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import aocutil.IO;

public class Day12 {

	public static void main(String[] args) {

		HashSet<Integer> allProgs = new HashSet<>();
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>(); // prog, group

		for (String[] arr : IO.lines(2017, 12).map(s -> s.split(" <-> ")).toList()) {
			int progID = Integer.parseInt(arr[0]);
			int[] connIDs = Arrays.stream(arr[1].split(", ")).mapToInt(Integer::parseInt).toArray();

			HashSet<Integer> set = map.computeIfAbsent(progID, id -> new HashSet<Integer>());
			for (int id : connIDs) {
				set.add(id);
				allProgs.add(id);
			}
			allProgs.add(progID);
		}

		// part 1
		HashSet<Integer> zeroVisited = new HashSet<>();
		search(0, map, zeroVisited);
		System.out.println(zeroVisited.size());

		// part 2

		int groups = 0;
		while (allProgs.size() > 0) {
			HashSet<Integer> visited = new HashSet<>();
			int start = allProgs.stream().findAny().get();
			search(start, map, visited);
			allProgs.removeAll(visited);
			++groups;
		}
		System.out.println(groups);

	}

	private static void search(int start, HashMap<Integer, HashSet<Integer>> map, HashSet<Integer> visited) {
		visited.add(start);
		List<Integer> toVisit = map.get(start).stream().toList();
		for (int id : toVisit) {
			if (visited.contains(id)) continue;
			search(id, map, visited);
		}
	}

}
