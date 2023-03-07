package aoc2022;

import java.util.HashSet;
import java.util.Set;

import aocutil.IO;
import aocutil.Point3D;

public class Day18 {

	public static void main(String[] args) {
		final Set<Point3D> lava = lava();
		
		// part 1
		System.out.println(lava.stream().mapToInt(point -> (int)adjacent(point).stream().filter(p -> !lava.contains(p)).count()).sum());

		// part 2
		final Point3D[] bounds = bounds(lava);
		final Set<Point3D> air = new HashSet<>();
		visit(bounds[0], lava, air, bounds[0], bounds[1]);
		System.out.println(lava.stream().mapToInt(l -> (int)adjacent(l).stream().filter(adj -> air.contains(adj)).count()).sum());
	}
	
	private static void visit(Point3D point, Set<Point3D> lava, Set<Point3D> visited, Point3D min, Point3D max) {
		visited.add(point);
		for (Point3D adj : adjacent(point)) {
			if (adj.x < min.x || adj.y < min.y || adj.z < min.z || adj.x > max.x || adj.y > max.y || adj.z > max.z) continue;
			if (visited.contains(adj)) continue;
			if (lava.contains(adj)) continue;
			visit(adj, lava, visited, min, max);
		}
	}
	
	private static Point3D[] bounds(Set<Point3D> points) {
		final int minX = points.stream().mapToInt(p -> p.x).min().getAsInt() - 1;
		final int minY = points.stream().mapToInt(p -> p.y).min().getAsInt() - 1;
		final int minZ = points.stream().mapToInt(p -> p.z).min().getAsInt() - 1;
		final int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt() + 1;
		final int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt() + 1;
		final int maxZ = points.stream().mapToInt(p -> p.z).max().getAsInt() + 1;
		return new Point3D[] {new Point3D(minX, minY, minZ), new Point3D(maxX, maxY, maxZ)};
	}
	
	private static Set<Point3D> adjacent(Point3D point) {
		return Set.of(point.up(), point.down(), point.left(), point.right(), point.forward(), point.backward());
	}
	
	private static Set<Point3D> lava() {
		return new HashSet<>(IO.lines(2022, 18)
				.map(line -> line.split(","))
				.map(arr -> new Point3D(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
				.toList());
	}
}
