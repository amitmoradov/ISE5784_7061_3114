package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

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
        // Call the RadialGeometry constructor with the specified radius
        super(radius);
        this.box = getBoundingBox(center, radius);
        this.center = center;
        
    }


    /**
     * Returns the normal vector to the sphere at a given point.
     * The normal is the normalized vector from the center of the sphere to the point.
     *
     * @param p The point to calculate the normal at.
     *
     */
    public Vector getNormal(Point p) {
       // By the formula normalize(p - center)
       return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        if (p0.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector u = center.subtract(p0); // Vector from the ray's starting point to the sphere's center
        double tm = v.dotProduct(u); // The projection of vector u on the direction vector of the ray
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm)); // The distance from the sphere's
        // center to the ray

        // If the distance from the ray to the sphere center is greater than the radius, there are no intersections
        if (d >= radius) {
            return null;
        }

        double th = Math.sqrt(radius * radius - d * d); // The distance from the points
        // of intersection to the point of perpendicularity
        double t1 = alignZero(tm - th); // The distance from the ray's origin to the first intersection point
        double t2 = alignZero(tm + th); // The distance from the ray's origin to the second intersection point

        // If both intersection points are in front of the ray origin
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);

            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }
        // If only one intersection point is in front of the ray origin
        if (t1 > 0) {
            Point p1 = ray.getPoint(t1); // The first intersection point

            return List.of(new GeoPoint(this, p1));
        }
        if (t2 > 0) {
            Point p2 = ray.getPoint(t2); // The second intersection point

            return List.of(new GeoPoint(this, p2));
        }

        // If no intersection points are in front of the ray origin
        return null;
    }

    /**
     * Calculate the bounding box of the sphere
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     * @return the bounding box of the sphere
     */
    private static BoundingBox getBoundingBox(Point center, double radius) {
        // Initialize minimum and maximum values for each coordinate to positive and negative infinity respectively

        double minX = Double.NEGATIVE_INFINITY;
        double minY = Double.NEGATIVE_INFINITY;
        double minZ = Double.NEGATIVE_INFINITY;
        double maxX = Double.POSITIVE_INFINITY;
        double maxY = Double.POSITIVE_INFINITY;
        double maxZ = Double.POSITIVE_INFINITY;

        // Calculate the minimum and maximum values for each coordinate
        minX = center.getX() - radius;
        minY = center.getY() - radius;
        minZ = center.getZ() - radius;
        maxX = center.getX() + radius;
        maxY = center.getY() + radius;
        maxZ = center.getZ() + radius;

        // Create a BoundingBox object using the calculated minimum and maximum points
        return new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
}
