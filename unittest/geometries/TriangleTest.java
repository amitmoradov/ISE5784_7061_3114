package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;

/**
 * Testing Triangles
 * @author Amit Moradov
 *          Yinon Shaul
 */
class TriangleTest {
    private final double DELTA = 0.000001;


    @Test
    void getNormal() {
        // By the formula normalize((p2 - p1) x (p3 - p1))
        // ============ Equivalence Partitions Tests ==============
        // TC01: Testing getNormal with a simple triangle
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 2, 0),
                new Point(0, 0, 1));

        assertEquals(new Vector(2.0 / 3, 1.0 / 3, 2.0 / 3), triangle.getNormal(new Point(1, 0, 0)),
                "ERROR:" + " getNormal() does not return the correct" + " normal vector");

        //Checking if the length of normal of triangle is 1.
        assertEquals(1,DELTA, triangle.getNormal(new Point(1, 0, 0)).length(),
                "ERROR:" + " getNormal().length() does not return the correct");

    }
}