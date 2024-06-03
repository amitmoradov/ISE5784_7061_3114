package primitives;

import org.junit.jupiter.api.Test;

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
}