package primitives;

import java.util.List;

import static primitives.Util.isZero;

import geometries.Intersectable.GeoPoint;


/**
 * A ray represents a half-straight line in three-dimensional space, starting from a point (the head) and extending in an infinite direction. */
public class Ray {
    private final Point head; // The starting point of the ray
    private final Vector direction; // The direction of the ray
    /**
     * A constant delta value used for numerical approximations or small adjustments
     */
    private static final double DELTA = 0.1;
    /**
     * Constructs a new Ray with the specified starting point (head) and direction.
     * @param head The starting point of the ray.
     * @param direction The direction of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize(); // Normalizing the direction vector to ensure it represents
        // a unit vector.
    }
    /**
     * ray constructor with offset point
     *
     * @param point     in ray
     * @param direction in ray
     * @param normal    on plane
     */
    public Ray(Point point, Vector direction, Vector normal) {
        this.direction = direction.normalize();
        double nv = normal.dotProduct(this.direction);
        Vector dltVector = normal.scale(nv < 0 ? -DELTA : DELTA);
        head = point.add(dltVector);

    }
    /**
     * Retrieves the direction of the ray.
     * @return The direction of the ray.
     */
    public Vector getDirection(){
        return direction;
    }

     /**
     * Retrieves the starting point of the ray.
     * @return The starting point of the ray.
     */
    public Point getHead(){
        return head;
    }

    /**
     * Checks if this ray is equal to another object.
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    /**
     * Returns a string representation of this ray.
     * @return A string representation of this ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Retrieves a point on the ray at a specified distance from the head.
     * @param t The distance from the head of the ray.
     * @return The point on the ray at the specified distance.
     */
    public Point getPoint(double t) {
        if (isZero(t)){
            return head;
        }
        return head.add(direction.scale(t));
    }

    /**
     * find the closest point to ray's head
     *
     * @return the closest Point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * find the closest GeoPoint to ray's head
     *
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
if (points == null || points.isEmpty()) {
            return null;
        }
        GeoPoint result = null;
        double minDistance = Double.POSITIVE_INFINITY;
        Point head = getHead();
        for (GeoPoint geo : points) {
            double distance = head.distance(geo.point);
            if (distance < minDistance) {
                minDistance = distance;
                result = geo;
            }
        }
        return result;
    }

}
