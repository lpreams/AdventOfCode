package aoc2015;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aocutil.IO;

public class Day07 {

	public static void main(String[] args) {
		final Map<String, String> wires = IO.lines(2015, 7).map(line -> line.split(" -> ")).collect(Collectors.toMap(a -> a[1], a -> a[0]));
		final Signal a1 = get("a", wires, new HashMap<>());
		System.out.println(a1.val());
		final Signal a2 = get("a", wires, new HashMap<>(Map.of("b", a1)));
		System.out.println(a2.val());
	}

	private static Signal get(String name, Map<String, String> wires, Map<String, Signal> mem) {
		if (mem.containsKey(name)) return mem.get(name);

		if (name.chars().allMatch(c -> '0' <= c && c <= '9')) {
			return put(mem, name, Signal.parse(Integer.parseInt(name)));
		}

		final String[] arr = wires.get(name).split(" ");
		if (arr.length == 1) {
			return put(mem, name, get(arr[0], wires, mem));
		}
		if (arr.length == 2) {
			return put(mem, name, get(arr[1], wires, mem).not());
		}
		if (arr.length == 3) {
			final Signal left = get(arr[0], wires, mem);
			final String op = arr[1];
			final Signal right = get(arr[2], wires, mem);
			return put(mem, name, switch (op) {
				case "AND" -> left.and(right);
				case "OR" -> left.or(right);
				case "LSHIFT" -> left.lshift(right);
				case "RSHIFT" -> left.rshift(right);
				default -> throw new RuntimeException();
			});
		}
		throw new RuntimeException();
	}

	private static Signal put(Map<String, Signal> mem, String name, Signal value) {
		mem.put(name, value);
		return value;
	}

	@SuppressWarnings("squid:S6218")
	private static record Signal(boolean[] value) {

		static Signal parse(int x) {
			boolean[] value = new boolean[16];
			for (int i = 15; i >= 0 && x > 0; --i) {
				value[i] = (x & 1) == 1;
				x >>= 1;
			}
			return new Signal(value);
		}

		Signal and(Signal that) {
			boolean[] asdf = new boolean[16];
			for (int i = 0; i < 16; ++i) asdf[i] = this.value[i] && that.value[i];
			return new Signal(asdf);
		}

		Signal or(Signal that) {
			boolean[] asdf = new boolean[16];
			for (int i = 0; i < 16; ++i) asdf[i] = this.value[i] || that.value[i];
			return new Signal(asdf);
		}

		Signal not() {
			boolean[] asdf = new boolean[16];
			for (int i = 0; i < 16; ++i) asdf[i] = !this.value[i];
			return new Signal(asdf);
		}

		Signal lshift(Signal ns) {
			final int n = ns.val();
			boolean[] asdf = new boolean[16];
			for (int i = 0; i < 16 - n; ++i) {
				asdf[i] = this.value[i + n];
			}
			return new Signal(asdf);
		}

		Signal rshift(Signal ns) {
			final int n = ns.val();
			boolean[] asdf = new boolean[16];
			for (int i = 0; i < 16 - n; ++i) {
				asdf[i + n] = this.value[i];
			}
			return new Signal(asdf);
		}

		public int val() {
			String s = IntStream.range(0, 16).mapToObj(i -> value[i] ? "1" : "0").collect(Collectors.joining());
			return Integer.parseInt(s, 2);
		}
	}

}
