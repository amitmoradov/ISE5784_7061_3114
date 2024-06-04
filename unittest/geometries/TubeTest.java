package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Testing Tube
 * @author Amit Moradov
 *          Yinon Shaul
 */
class TubeTest {

    private final double DELTA = 0.000001;
    /** Test method for {@link geometries.Tube#getNormal(Point)}. */
    @Test
    void getNormal() {
        // By the formula normalize(p - center O)
        // t = v * (p - p0)
        // O = p0 + t * v

        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing correct of calculate Normal with a simple tube
        Tube tube = new Tube(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)), 1);
        assertEquals(new Vector(0,1,0), tube.getNormal(new Point(1,1,1)),"ERROR:" +
                " getNormal() does not return the correct normal vector");

        // ================= Boundary Values Tests ==================

        // TC02: Testing when p - p0 is orthogonal to direction of the ray in the tube
        Tube tube2 = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1);
        assertEquals(new Vector(1,0,0), tube2.getNormal(new Point(1,0,0)),"ERROR:" +
                " getNormal() does not return the correct normal vector");

        // TC03: Testing if the length of norma of tube is 1.
        assertEquals(1, tube.getNormal(new Point(1,1,1)).length(),DELTA,
                "ERROR: getNormal().length() of Tube does not return the correct");
    }
}