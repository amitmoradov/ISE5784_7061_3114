package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 * Extends the RadialGeometry class.
 */
public class Sphere extends RadialGeometry {

    private final Point center; // The center point of the sphere

    /**
     * Constructs a new sphere with the specified center point.
     *
     * @param center The center point of the sphere.
     */
    public Sphere(Point center,double radius) {
        super(radius); // Required by the superclass
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
       return center.subtract(p).normalize();
    }
}
