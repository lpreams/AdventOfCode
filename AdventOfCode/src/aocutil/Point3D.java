package aocutil;

import java.util.Objects;

public class Point3D {
	public final int x, y, z;

	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Point3D)) return false;
		Point3D other = (Point3D) obj;
		return x == other.x && y == other.y && z == other.z;
	}

	public String toString() {
		return String.format("[%d, %d, %d]", x, y, z);
	}
	
	public Point3D up() {
		return new Point3D(x, y+1, z);
	}
	
	public Point3D down() {
		return new Point3D(x, y-1, z);
	}
	
	public Point3D left() {
		return new Point3D(x-1, y, z);
	}
	
	public Point3D right() {
		return new Point3D(x+1, y, z);
	}
	
	public Point3D forward() {
		return new Point3D(x, y, z+1);
	}
	
	public Point3D backward() {
		return new Point3D(x, y, z-1);
	}
}
















