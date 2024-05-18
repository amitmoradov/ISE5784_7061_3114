package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;

/**
 * Testing Sphere
 * @author Amit Moradov
 *          Yinon Shaul
 */
class SphereTest {

    /** Test method for {@link geometries.Sphere#getNormal(Point)}. */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing getNormal with a simple sphere
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);

        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1, 0, 0)),
                "ERROR: getNormal() does not return the correct" + " normal vector");
    }
}