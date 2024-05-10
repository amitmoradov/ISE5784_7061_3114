package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in three-dimensional space.
 * Extends the Tube class.
 */
public class Cylinder extends Tube {

    private final double height; // The height of the cylinder

    /**
     * Constructs a new cylinder with the specified height, central axis, and radius.
     *
     * @param height the height of the cylinder
     * @param ray the central axis of the cylinder, represented by a Ray object
     * @param radius the radius of the cylinder
     */
    public Cylinder(double height, Ray ray, double radius) {
        super(ray, radius);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at a given point.
     * Since the cylinder is an infinite object, it does not have a meaningful normal vector at any specific point.
     * Therefore, this method always returns null.
     *
     * @param p The point to calculate the normal at.
     * @return null since the cylinder does not have a meaningful normal vector at any specific point.
     */
    public Vector getNormal(Point p) {
        return null;
    }
}
