package primitives;

/**
 * * Class Point is the basic class representing a point in space of Euclidean geometry in Cartesian
 * * 3-Dimensional coordinate system.
 * @author Amit and Yinon
 */
public class Point {
    /**
     * Coordinates of the point
     */
    protected Double3 xyz;
    /**
     * A static field representing the beginning of the axes
     */
    public static final Point ZERO = new Point(0,0,0);

    /**
     * Parameter constructor for creating a new point based on 3 new values of Coordinates
     * @param x first number value of point
     * @param y second number value of point
     * @param z third number value of point
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Parameter constructor for creating a new point based on Double3 existing object
     * @param xyz is a triad of coordinates exists
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracting a vector (represented by another point) from this point.
     * @param point The other point representing the vector to subtract
     * @return The resulting vector from this point to the other point
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Adding a vector (represented by another point) to this point.
     * @param vector The vector to add, represented by another point
     * @return The resulting point after adding the vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Calculates the squared distance between two points.
     * @param other The other point to calculate the distance
     * @return The squared distance between this point and the other point
     */
    public double distanceSquared(Point other) {
        double dx = xyz.d1 - other.xyz.d1;
        double dy = xyz.d2 - other.xyz.d2;
        double dz = xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between two points.
     * @param point The other point to calculate the distance
     * @return The distance between this point and the other point
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other) && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "" + xyz.toString(); }
}


