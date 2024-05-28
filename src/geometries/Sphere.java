package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Represents a sphere in three-dimensional space.
 * Extends the RadialGeometry class.
 */
public class Sphere extends RadialGeometry {

    private final Point center; // The center point of the sphere

    /**
     * Constructs a new sphere with the specified center point and radius.
     *
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }


    /**
     * Returns the normal vector to the sphere at a given point.
     * Since a sphere is a perfectly symmetrical object, it has the same normal at every point.
     * Therefore, this method always returns null.
     *
     * @param p The point to calculate the normal at.
     * @return null since a sphere has the same normal at every point.
     */
    public Vector getNormal(Point p) {
       // By the formula normalize(p - center)
       return p.subtract(center).normalize();
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
