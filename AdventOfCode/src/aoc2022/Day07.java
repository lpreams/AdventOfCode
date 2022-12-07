package aoc2022;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import aocutil.IO;

public class Day07 {

	public static void main(String[] args) {
		final DirObj root = populate();
		System.out.println(a(root));
		System.out.println(b(root));
	}
	
	private static int b(DirObj root) {
		final int target = 30000000 - (70000000 - root.size());
		int found = Integer.MAX_VALUE;
		ArrayList<DirObj> toCheck = new ArrayList<>(List.of(root));
		while (toCheck.size() > 0) {			
			ArrayList<DirObj> nextCheck = new ArrayList<>();
			for (DirObj dir : toCheck) {
				final int dirSize = dir.size();
				if (target <= dirSize && dirSize < found) {
					found = dirSize;
				}
				for (FSObj o : dir.files) if (o instanceof DirObj) nextCheck.add((DirObj)o);
			}
			toCheck.clear();
			toCheck.addAll(nextCheck);
		}
		return found;
	}
	
	private static int a(DirObj root) {
		int sum = 0;
		int thisSize = root.size();
		if (thisSize <= 100000) sum += thisSize;
		for (FSObj o : root.files) {
			if (!(o instanceof DirObj)) continue;
			DirObj dir = (DirObj)o;
			sum += a(dir);
		}
		return sum;
	}
	
	private static DirObj populate() {
		final LinkedList<DirObj> stack = new LinkedList<>();
		for (String line : IO.linesIter(2022, 7)) {
			if (line.equals("$ cd /")) {
				stack.push(new DirObj("/"));
				continue;
			}
			final String[] arr = line.split(" ");
			switch(arr[0]) {
				case "$":
					switch(arr[1]) {
						case "cd":
							final String dirName = arr[2];
							if (dirName.equals("..")) {
								stack.pop();
								break;
							}
							final DirObj dir = new DirObj(dirName);
							stack.peek().files.add(dir);
							stack.push(dir);
							break;
						case "ls": // ignore, subsequent lines will contain files or subdirs
							break;
						default:
							throw new RuntimeException("Unrecognized command: " + arr[1]);
					}
					break;
				case "dir": // ignore, we'll get it on the cd
					break;
				default:
					stack.peek().files.add(new FileObj(arr[1], Integer.parseInt(arr[0])));
					break;
			}
		}		
		return stack.peekLast();
	}

	private static abstract class FSObj {
		abstract String name();
		abstract int size();
	}
	
	private static class DirObj extends FSObj {

		private final String name;
		private final ArrayList<FSObj> files = new ArrayList<>();
		
		public DirObj(String name) {
			this.name = name;
		}
		
		public String name() {
			return name;
		}

		public int size() {
			return files.stream().mapToInt(o -> o.size()).sum();
		}
	}

	private static class FileObj extends FSObj {

		private final String name;
		private final int size;
		
		public FileObj(String name, int size) {
			this.name = name;
			this.size = size;
		}

		public String name() {
			return name;
		}

		public int size() {
			return size;
		}
	}
}
