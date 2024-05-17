package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

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
}