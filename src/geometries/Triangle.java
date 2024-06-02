package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * A class representing a triangle in three-dimensional space.
 * Extends the Polygon class.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a triangle from three points.
     *
     * @param p1 The first point of the triangle.
     * @param p2 The second point of the triangle.
     * @param p3 The third point of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);

        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double vn1 = ray.getDirection().dotProduct(n1);
        double vn2 = ray.getDirection().dotProduct(n2);
        double vn3 = ray.getDirection().dotProduct(n3);

        if (vn1 > 0 && vn2 > 0 && vn3 > 0 || vn1 < 0 && vn2 < 0 && vn3 < 0) {
            return intersections;
        }
        return null;
    }
}
