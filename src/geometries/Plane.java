package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.*;


import java.util.List;

/**
 * A Plane represents a flat, two-dimensional surface extending infinitely in all directions.
 */
public class Plane extends Geometry {

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Calculate the denominator of the division for finding the parameter t
        double denominator = this.normal.dotProduct(ray.getDirection());
        // If the denominator is close to zero, the ray is parallel to the plane
        if (Util.isZero(denominator))
            return null; // Ray is parallel to the plane

        // Calculate the numerator of the division for finding the parameter t
        Vector p0MinusQ0;
        try {
            p0MinusQ0 = q.subtract(ray.getHead());
        } catch (IllegalArgumentException ignore) {
            return null;
        }

        double numerator = this.normal.dotProduct(p0MinusQ0);
        // Calculate the parameter t
        double t = Util.alignZero(numerator / denominator);

        // If t is negative, the intersection point is behind the ray's start point
        if (t < 0)
            return null;

        // Calculate the intersection point
        Point intersectionPoint = ray.getPoint(t);

        // Return a list with a single GeoPoint containing this plane and the
        // intersection point
        return List.of(new Intersectable.GeoPoint(this, intersectionPoint));
    }

}
