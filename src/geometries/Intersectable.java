package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents an object that can be intersected by a ray.
 * author Amit Moradov , Yinon Shaul
 */
public interface Intersectable {
    /**
     * Finds the intersection points between a ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points, or null if no intersections were found.
     */
    public List<Point> findIntersections(Ray ray);
}
