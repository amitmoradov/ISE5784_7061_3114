package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Testing Triangles
 * @author Amit Moradov
 *          Yinon Shaul
 */
class TriangleTest {
    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Triangle#getNormal(Point)}. */
    @Test
    void getNormal() {
        // By the formula normalize((p2 - p1) x (p3 - p1))

        // ============ Equivalence Partitions Tests ==============

        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 2, 0);
        Point p3 = new Point(0, 0, 1);

        // TC01: Testing getNormal with a simple triangle
        Triangle triangle = new Triangle(p1, p2, p3);

        assertEquals(new Vector(2.0 / 3, 1.0 / 3, 2.0 / 3), triangle.getNormal(new Point(1, 0, 0)),
                "ERROR:" + " getNormal() does not return the correct" + " normal vector");

        //Checking if the length of normal of triangle is 1.
        assertEquals(1, triangle.getNormal(new Point(1, 0, 0)).length(),DELTA,
                "ERROR:" + " getNormal().length() does not return the correct");

        // TC02: Testing if the normal of plane or the scale of him by 1 calculated correctly to real
        // By the formula normalize((p2 - p1) x (p3 - p1))
        Vector expectedNormal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
        assertTrue(expectedNormal.equals(triangle.getNormal(p1)) ||
                expectedNormal.equals(triangle.getNormal(p1).scale(-1)) ,
                "ERROR: getNormal() does not return the correct normal vector");
    }

    //----------------------Stag 3 ------------------------------------------------------------------------------//
    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 4), new Point(4, 0, 0)
                , new Point(0, -4, 0));

        // =================== Equivalence Partitions Tests ==================================

        // TC01 : The point of intersection with the contained plane is inside the triangle
        final var result = triangle.findIntersections(new Ray(new Point(1, -1, 3)
                , new Vector(0, 0, -1)));
        assertEquals(1, result.size(), "ERROR: The ray intersects the triangle");
        final var exp = List.of(new Point(1, -1, 0));
        assertEquals(exp, result, "ERROR: The ray intersects the triangle");

        // TC02 : The point of intersection with the contained plane is outside the triangle,
        // in front of one of the sides
        final var result2 = triangle.findIntersections(new Ray(new Point(1, -1, 3)
                , new Vector(0, 0, 1)));
        assertNull(result2, "ERROR: The ray does not intersect the triangle");





    }
}