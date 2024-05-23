package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    Point p1 = new Point(1, 0, 0);
    Point p2 = new Point(0, 2, 0);
    Point p3 = new Point(0, 0, 1);

    Plane plane = new Plane(p1,p2,p3);

    /** Test method for {@link geometries.Plane#Plane(Point, Point, Point)}. */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing correct plane
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 2, 0), new Point(0, 0, 1));
        // Verify that the plane object is not null
        assertNotNull(plane, "Failed to create a valid plane with three valid points");

        // =============== Boundary Values Tests ==================

        // TC02: Testing plane creation with first two points being identical
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p1,p3), "Creating a plane with first" +
                " two points being identical should throw an IllegalArgumentException");

        // TC03: Testing plane creation with all points on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2,
                        2), new Point(3, 3, 3)), "Creating a plane with all points on the same" +
                " line should throw an IllegalArgumentException");

    }

    /** Test method for {@link geometries.Plane#getNormal()}. */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing getNormal length is 1
        assertEquals(1, plane.getNormal().length(), DELTA, "ERROR: getNormal() does not return" +
                " a unit vector");

        // TC02: Testing if the normal of plane calculated correctly to real
        // By the formula normalize((p2 - p1) x (p3 - p1))
        Vector expectedNormal = new Vector(2.0 / 3, 1.0 / 3, 2.0 / 3);
        assertEquals(expectedNormal, plane.getNormal(), "ERROR: getNormal() does not return the correct" +
                " normal vector");

        // TC03: Testing getNormal is orthogonal to the plane
        assertTrue(plane.getNormal().dotProduct(p2.subtract(p1))==0,
                "ERROR: getNormal() does not return a vector orthogonal to the plane");

        assertTrue(plane.getNormal().dotProduct(p3.subtract(p1))==0,
                "ERROR: getNormal() does not return a vector orthogonal to the plane");

    }

    /** Test method for {@link geometries.Plane#getNormal()}. */
    @Test
    void testGetNormal() {
        // TC01: Testing getNormal length is 1
        assertEquals(1, plane.getNormal().length(), DELTA, "ERROR: getNormal() does not return a " +
                "unit vector");

        // TC02: Testing if the normal of plane or the scale of him by 1 calculated correctly to real
        // By the formula normalize((p2 - p1) x (p3 - p1))
        Vector expectedNormal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
        assertTrue(expectedNormal.equals(plane.getNormal()) || expectedNormal.equals(plane.getNormal()
                        .scale(-1)) , "ERROR: getNormal() does not return the correct normal vector");

        // TC03: Testing getNormal is orthogonal to the plane
        assertTrue(plane.getNormal().dotProduct(p2.subtract(p1))==0,
                "ERROR: getNormal() does not return a vector orthogonal to the plane");

        assertTrue(plane.getNormal().dotProduct(p3.subtract(p1))==0,
                "ERROR: getNormal() does not return a vector orthogonal to the plane");
    }
}