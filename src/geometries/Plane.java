package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A Plane represents a flat, two-dimensional surface extending infinitely in all directions.
 */
public class Plane implements Geometry {

    private final Point q; // A point on the plane
    private final Vector normal; // The normal vector to the plane

    /**
     * Constructs a plane from three points on it.
     *
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
    public Plane(Point p1, Point p2, Point p3) {
        // By the formula normalize((p2 - p1) x (p3 - p1))
        this.normal = (p2.subtract(p1).crossProduct(p3.subtract(p1))).normalize();
        this.q = p1;
    }

    /**
     * Constructs a plane from a point on it and its normal vector.
     *
     * @param p1 The point on the plane.
     * @param n  The normal vector to the plane.
     */
    public Plane(Point p1, Vector n) {
        this.q = p1;
        this.normal = n.normalize(); // Normalizing the normal vector to ensure it represents a unit vector.
    }

    /**
     * Retrieves the normal vector to the plane at a specified point.
     *
     * @param p The point on the plane (unused in this implementation).
     * @return The normal vector to the plane.
     */
    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * Retrieves the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
