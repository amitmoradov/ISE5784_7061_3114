package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;


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

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        double nv = normal.dotProduct(v); // Dot product of the plane's normal and the ray's direction
        // If the ray is parallel to the plane, there are no intersections
        if (isZero(nv)) {
            return null;
        }

        Vector qp0 = q.subtract(p0); // Vector from the ray's starting point to a point on the plane
        double t = alignZero(normal.dotProduct(qp0) / nv); // Distance from the ray's starting
        // point to the intersection point

        // If the intersection point is in the positive direction of the ray
        if (t > 0) {
            Point p = ray.getPoint(t); // The intersection point
            return List.of(p);
        }

        // If the intersection point is behind the ray's starting point
        return null;
    }

}
