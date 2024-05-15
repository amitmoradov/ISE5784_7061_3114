package primitives;

import org.junit.jupiter.api.Test;

import static primitives.Util.isZero;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;
import primitives.Point;

/**
 * Testing Point
 * @author Amit Moradov
 *          Yinon Shaul
 */

class PointTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    Point p1 = new Point(1, 2, 4);
    Point p2 = new Point(4, 5, 8);
    Vector v1 = new Vector(3, 3, 4);

    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing subtract with two different points
        Vector result = p1.subtract(p2);
        Vector expected = new Vector(-3, -3, -4);
        assertEquals(expected, result,"ERROR: (point2 - point1) does not work correctly");

        // TC02 : Testing subtract with when result is vector v1
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

        // TC03: Testing subtract with the same point
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "Subtracting a point from itself" +
                " should throw an IllegalArgumentException of zero vector");}

    @Test
    void add() {
        // TC01: Testing correctly add point to vector
        Point result = p1.add(new Vector(p2.xyz));
        Point expected = new Point(5, 7, 12);
        assertEquals(expected,result,"ERROR: (point + vector) = other point does not work correctly");

        // TC02: Testing correctly add point to vector is equal to the other point (p2)
        assertEquals(p2, p1.add(new Vector(3, 3, 4)), "ERROR: (point + vector) = other point does " +
                "not work correctly");

        // TC03: Testing add when result is zero point
        Point result2 = p1.add(new Vector(-1, -2, -4));
        Point expected2 = Point.ZERO;
        assertEquals(expected2, result2, "ERROR: (point + vector) = center of coordinates does not work correctly");

        // TC04: Testing add with the zero vector
        assertThrows(IllegalArgumentException.class, () -> p1.add(new Vector(0, 0, 0)), "Adding the" +
                " zero vector to a point should throw an IllegalArgumentException of the same point");
    }

    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing distanceSquared with two different points
        double result = p1.distanceSquared(p2);
        double expected = 34;
        assertEquals(expected, result, DELTA,"Squared distance between two different points is wrong");

        // TC02: Testing distanceSquared with the same point
        assertTrue(isZero(p1.distanceSquared(p1)), "Squared distance between the same point is wrong");

        // TC03: Testing distanceSquared when the end result is zero
        assertEquals(0, p1.distanceSquared(p2) - 34, DELTA, "Squared distance between the same point is wrong");
    }

    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing distance with two different points
        double result = p1.distance(p2);
        double expected = 5.830951894845301;
        assertEquals(expected, result, DELTA, "Distance between two different points is wrong");

        // TC02: Testing distance with the same point
        double result2 = p1.distance(p1);
        double expected2 = 0;
        assertEquals(expected2, result2, "Distance between the same point is wrong");

        // TC03: Testing distance when the end result is zero
        assertTrue(isZero(p1.distance(p2) - 5.830951894845301), "Distance between the same point is wrong");
    }

    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Testing equals with two different points
        Point p3 = new Point(1, 2, 4);
        assertFalse(p1.equals(p2), "Equals should return false for two different points");

        // TC02: Testing equals with the same point
        assertTrue(p1.equals(p3), "Equals should return true for two equal points");
    }
}