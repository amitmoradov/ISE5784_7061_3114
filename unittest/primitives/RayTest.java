package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for {@link primitives.Ray#equals(Object)}.
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing equals with two same rays
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        Ray r2 = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        assertTrue(r1.equals(r2), "ERROR: equals does not work correctly");

        // TC02: Testing equals with two different rays
        Ray r3 = new Ray(new Point(1, 2, 4), new Vector(1, 1, 1));
        assertFalse(r1.equals(r3), "ERROR: equals does not work correctly");
    }

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void getPoint() {
        // ========================= Equivalence Partitions Tests ===================================

        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1, 0, 0));

        // TC01 : Negative distance from the head of the ray
        assertEquals(new Point(-1, 2, 3), ray.getPoint(-2),
                "ERROR: getPoint() does not work correctly with negative distance");

        // TC02 : Positive distance from the head of the ray
        assertEquals(new Point(4, 2, 3), ray.getPoint(3),
                "ERROR: getPoint() does not work correctly positive distance");

        // =========================Boundary Values Tests======================================================

        // TC03 : distance from the head of the ray is zero
        assertEquals(new Point(1, 2, 3), ray.getPoint(0),
                "ERROR: getPoint() does not work correctly with zero distance");
    }

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        // ========================= Equivalence Partitions Tests ===================================
        //TC01: A point in the middle of the list is the one closest to the beginning of the foundation
        Point p100 = new Point(1,0,0);
        Point p300 = new Point(3,0,0);
        Point p500 = new Point(5,0,0);
        //insert to new list that contain points
        List<Point> lst = List.of(p100,p300,p500);
        Ray ray = new Ray(new Point(3.1, 0, 0), new Vector(1, 0, 0));
        assertEquals(p300,ray.findClosestPoint(lst),"ERROR: findClosestPoint does not work correctly");

        // =========================Boundary Values Tests======================================================

        //TC02: An empty list (the method should return a null value)
        lst = null;
        assertNull(ray.findClosestPoint(lst),"ERROR: findClosestPoint need to be null");

        //TC03: The first point is closest to the beginning of the beam
        Point p400 = new Point(4,0,0);
        lst = List.of(p300,p400,p500);
        assertEquals(p300,ray.findClosestPoint(lst),"closest first point is in the list" );

        //TC04: The last point is closest to the beginning of the horn
        assertEquals(p500,new Ray(new Point(5.5,0,0),new Vector(1,0,0)).findClosestPoint(lst),"closest point is in the laster of the list" );

    }
}