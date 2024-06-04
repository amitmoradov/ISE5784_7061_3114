package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 1, 1), new Point(2, 2, 2)
                ,new Point(3, 3, 3)), "Creating a plane with all points on the same" +
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

    //------------------------------Stage 3------------------------------
    @Test
    void findIntersections() {
        Point point200 = new Point(2,0,0);
        Point point020 = new Point(0,2,0);
        Point point002 = new Point(0,0,2);
        Plane plane = new Plane(point200, point020, point002);

        // ============ Equivalence Partitions Tests ==============

        // **** Group: Ray starts outside the plane, not parallel to the plane

        //TC01: ray starts outside the plane, not parallel to the plane (point 1)
        final var resulte1 = plane.findIntersections(new Ray(new Point(0,0,3)
                , new Vector(0,0,-4)));
        assertEquals(List.of(new Point(0,0,2)),resulte1, " ray starts outside the plane, not parallel to the plane " +
                "and the point is (0,0,2)");
        assertEquals(1,resulte1.size(),"ray starts outside the plane, not parallel to the plane (point 1)" );

        // TCO2:ray starts outside the plane, not parallel to the plane (point 0)
        final var resulte2 = plane.findIntersections(new Ray(new Point(1,1,1)
                , new Vector(-6,-6,14)));
        assertNull(resulte2," ray outside plane and not parallel to a plane (0 points)");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's parallel to plane

        //TC03: ray outside plane and parallel to a plane (0 points)
        final var resulte3 = plane.findIntersections(new Ray(new Point(2,2,0)
                , new Vector(2,-2,0)));
        assertNull(resulte3," ray outside plane and parallel to a plane (0 points)");

        //TC04: ray inside the plane parallel to the plane (0 points)
        final var resulte4 = plane.findIntersections(new Ray(new Point(0,0,2)
                , new Vector(2,-2,0)));
        assertNull(resulte4," ray inside plane and parallel to a plane (0 points)");


        // **** Group: Ray's ortogonal to plane

        //TC05:ray start inside the plane and orthogonal to the plane
        final var resulte5 = plane.findIntersections(new Ray(new Point(0,0,2)
                , new Vector(1,1,1)));
        assertNull(resulte5," ray start inside the plane and orthogonal to the plane (0 points)");

        //TC06: ray before  the plane and orthogonal to the plane (1 point)
        final var resulte6 = plane.findIntersections(new Ray(new Point(-1,0,2)
                , new Vector(1,1,1)));
        assertEquals(List.of(new Point(-0.6666666666666665,0.3333333333333334,2.3333333333333335))
                ,resulte6, " ray starts outside the plane, not parallel to the plane " +
                "and the point is (-0.67,0.33,2.33)");
        assertEquals(1,resulte6.size()," ray before the plane and orthogonal to" +
                " the plane (1 point)" );

        //TC07: ray after  the plane and orthogonal to the plane (0 point)
        final var resulte7 = plane.findIntersections(new Ray(new Point(2,0,2)
                , new Vector(1,1,1)));
        assertNull(resulte7,"ray after the plane and orthogonal to the plane (0 point)");

        //**** Group: ray start inside plane and not parallel and not orthogonal to the plane

        //TC08: ray not parallel (מקביל) and not orthogonal to the plane but starts in one of points that
        // present the plane (0 point)
        final var resulte8 = plane.findIntersections(new Ray(new Point(0,0,2)
                , new Vector(-5,-5,15)));
        assertNull(resulte8,"ray not parallel(מקביל) and not orthogonal to the plane but" +
                " starts inside the plane (0 point)");

        //TC09:  ray not parallel(מקביל) and not orthogonal to the plane but starts inside the plane (0 point)
        final var resulte9 = plane.findIntersections(new Ray(new Point(-2,3,1)
                , new Vector(-3,-8,14)));
        assertNull(resulte9,"ray not parallel(מקביל) and not orthogonal to the plane but starts" +
                " inside the plane (0 point)");
    }

}