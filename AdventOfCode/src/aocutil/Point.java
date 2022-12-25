package aocutil;

import java.util.Objects;

public class Point {
	public final int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int hashCode() {
		return Objects.hash(x, y);
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Point)) return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	
	public Point up() {
		return new Point(x, y+1);
	}
	
	public Point down() {
		return new Point(x, y-1);
	}
	
	public Point left() {
		return new Point(x-1, y);
	}
	
	public Point right() {
		return new Point(x+1, y);
	}
	
	public Point ul() {
		return new Point(x-1, y+1);
	}
	
	public Point ur() {
		return new Point(x+1, y+1);
	}

	public Point dl() {
		return new Point(x-1, y-1);
	}
	
	public Point dr() {
		return new Point(x+1, y-1);
	}
}
















