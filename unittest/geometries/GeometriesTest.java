package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    /**
     * @author Amit Moradov
     *          Yinon Shaul
     *
     */
    @Test
    void findIntersections() {
        Geometries geometries= new Geometries();
        Sphere sphere = new Sphere(new Point(1, 0, 0),1);
        Triangle triangle = new Triangle(new Point(4, 0, 0), new Point(0, -4, 0), new Point(0, 0, 4));
        Plane plane = new Plane (new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, 0, 2));
        geometries.add(sphere, triangle, plane);

        // ============ Equivalence Partitions Tests ==============
        //A few (but not all of them) in interaction
        int numOfIntersections = geometries.findIntersections(new Ray(new Point(-4, -3, 2), new Vector(9,5,-1))).size();
        assertEquals(2, numOfIntersections, "Wrong number of intersection points");

    }
}