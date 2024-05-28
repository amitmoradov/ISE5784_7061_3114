package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Testing Cylinder
 * @author Amit Moradov
 *          Yinon Shaul
 */
class CylinderTest {
    /** Test method for {@link geometries.Cylinder#getNormal(Point)}. */
    @Test
    void getNormal() {
        // Create a cylinder for testing
        Cylinder cylinder = new Cylinder(1, new Ray(new Point(1, 0, 0),
                new Vector(0, 0, 1)), 1);

        // ============ Equivalence Partitions Tests =============================================================

        // TC01: Point on the lateral surface of the cylinder
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(1, 1, 0.5)),
                "ERROR: getNormal() does not return the correct normal vector for the lateral surface");

        // TC02: Point on the bottom base of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1.5, 0, 0)),
                "ERROR: getNormal() does not return the correct normal vector for the bottom base");

        // TC03: Point on the top base of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1.5, 0, 1)),
                "ERROR: getNormal() does not return the correct normal vector for the top base");

        // ================= Boundary Values Tests ================================================================

        // TC04: Point at the center of the bottom base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 0)),
                "ERROR: getNormal() does not return the correct normal vector for the center of" +
                        " the bottom base");

        // TC05: Point at the center of the top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 1)),
                "ERROR: getNormal() does not return the correct normal vector for the center of the top base");
    }
}
