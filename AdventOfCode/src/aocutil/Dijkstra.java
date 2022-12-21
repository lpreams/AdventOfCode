package aocutil;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Dijkstra<T> {

	/**
	 * @return a Set containing all nodes in the graph
	 */
	public abstract Set<T> getAllNodes();

	/**
	 * Get all neighbors of the current node
	 * 
	 * NOTE: you must either implement this method, or override
	 * getUnvisitedNeighbors, you do not need to do both
	 * 
	 * (if getUnvisitedNeighbors is implemented, getNeighbors can simply throw an
	 * UnsupportedOperationException)
	 * 
	 * @param current The node in question
	 * @return All neighbors of current, and the distances to them from current
	 */
	public abstract Map<T, Integer> getNeighbors(T current);

	/**
	 * Get unvisited neighbors of the current node
	 * 
	 * NOTE: you must either implement this method, or override
	 * getUnvisitedNeighbors, you do not need to do both
	 * 
	 * @param current   The node in question
	 * @param unvisited Set of nodes not yet visited
	 * @return Unvisited neighbors of current, and the weights to them from current
	 */
	public Map<T, Integer> getUnvisitedNeighbors(T current, HashSet<T> unvisited) {
		return getNeighbors(current)
				.entrySet()
				.stream()
				.filter(e -> unvisited.contains(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Run Dijkstra's algorithm with the given start and end nodes
	 * @param start The node you are starting from
	 * @param end The node you wish to end on
	 * @return The total weight of the shortest path from start to end, or null if no such path exists
	 * @throws NoPathExistsException 
	 */
	public int dijkstra(T start, T end) throws NoPathExistsException {
		final DijkstraPredicate<T> p = (unvisited, dists) -> !unvisited.contains(end);
		final Map<T, Integer> dists = dijkstraImpl(start, p);
		final Integer res = dists.get(end);
		if (res == null) throw new NoPathExistsException();
		return res;
	}
	
	/**
	 * Run Dijkstra's algorithm with the given start node
	 * @param start The node you are starting from
	 * @return A map, in which each value is the length of the shortest path from the start node to the associated key
	 */
	public Map<T, Integer> dijkstraFull(T start) {
		final DijkstraPredicate<T> p = (unvisited, dists) -> unvisited.stream().filter(n -> dists.containsKey(n)).findAny().isEmpty();
		final Map<T, Integer> dists = dijkstraImpl(start, p);		
		return Collections.unmodifiableMap(dists);
	}
	
	/**
	 * Implementation of Dijkstra's algorithm
	 * @param start The node you are starting from
	 * @param p Predicate for when to end iteration (either when end node is visited, or when all remaining unvisited nodes are unreachable)
	 * @return
	 */
	private HashMap<T, Integer> dijkstraImpl(T start, DijkstraPredicate<T> p) {
		final HashSet<T> unvisited = new HashSet<>(getAllNodes());
		final HashMap<T, Integer> dists = new HashMap<>();
		dists.put(start, 0);
		T current = start;
		while (true) {
			final T c = current;
			getUnvisitedNeighbors(current, unvisited).entrySet().forEach(e -> {
				if (dists.get(c) != null) 
					dists.merge(e.getKey(), e.getValue() + dists.get(c), (old, nue) -> old < nue ? old : nue);
			});
			unvisited.remove(current);
			if (p.get(unvisited, dists)) break;
			current = unvisited.stream().reduce((a,b) -> {
				final Integer da = dists.get(a);
				if (da == null) return b;
				final Integer db = dists.get(b);
				if (db == null) return a;
				return da < db ? a : b;
			}).get();
		}
		return dists;
	}
	
	private interface DijkstraPredicate<E> {
		public boolean get(Set<E> unvisited, Map<E, Integer> dists);
	}
	
	public static class NoPathExistsException extends Exception{
		private static final long serialVersionUID = 4217719756086802677L;
	}

}
