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
        Geometries geometries = new Geometries();
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);
        Triangle triangle = new Triangle(new Point(4, 0, 0), new Point(0, -4, 0), new Point(0, 0, 4));
        Plane plane = new Plane (new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2));
        geometries.add(sphere, triangle, plane);

        // ============ Equivalence Partitions Tests ==============
        //TC01: A few (but not all of them) in interaction
        int numOfIntersections = geometries.findIntersections(new Ray(new Point(-4, -3, 2), new Vector(9, 5, 2))).size();
        assertEquals(2, numOfIntersections, "Wrong number of intersection points");

        // =============== Boundary Values Tests ===================

        //TC02: Empty collection of objects (0 point)
        assertNull(geometries.findIntersections(new Ray(new Point(-7, -3, 2),new Vector(3,5,50))), "Empty collection of objects - wrong intersection points");

        //TC03: only one intersection in all geometries
        //There is one object with interaction
        int result3  = geometries.findIntersections(new Ray(new Point(-7, -3, 2), new Vector(10, 5, 1))).size();
        assertEquals(1, result3, "Wrong number of intersection points");

        //TC04:The all shapes with interaction (3 points)
        int result4  = geometries.findIntersections(new Ray(new Point(0, 2, 0), new Vector(2, -4, 1))).size();
        assertEquals(3, result3, "Wrong number of intersection points");

        //TC05: ??? I dont Know if that's what they want
        Geometries emptyGeometries = new Geometries();
        assertNull(emptyGeometries.findIntersections(new Ray(new Point(0, 2, 0), new Vector(2, -4, 1))), "Empty collection of objects - wrong intersection points");



    }
}