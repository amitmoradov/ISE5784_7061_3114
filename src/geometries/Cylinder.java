package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;
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
    @Override
    public Vector getNormal(Point p) {
        // If the point is on the axis, return the direction of the axis (p0 = p)
        if (p.equals(axis.getHead())) return axis.getDirection();

        // Check whether the point is on one of the bases
        double t = p.subtract(axis.getHead()).dotProduct(axis.getDirection());
        // If the charge is zero
        if (isZero(t) || isZero(t - this.height)) return axis.getDirection();
        return super.getNormal(p);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
