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
            Vector v1 =
        }
}
