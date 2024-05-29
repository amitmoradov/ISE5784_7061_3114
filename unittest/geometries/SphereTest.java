package geometries;

import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import java.util.List;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;



/**
 * Testing Sphere
 * @author Amit Moradov
 *          Yinon Shaul
 */
class SphereTest {
    private final double DELTA = 0.000001;


    /** Test method for {@link geometries.Sphere#getNormal(Point)}. */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Testing getNormal with a simple sphere
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);

        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1, 0, 0)),
                "ERROR: getNormal() does not return the correct" + " normal vector");

        //TC02: Checking if the length of norma of sphere is 1.
        assertEquals(1, sphere.getNormal(new Point(1, 0, 0)).length(),DELTA,
                "ERROR: getNormal().length() does not return the correct");
    }

    //----------------------Stag 3 ------------------------------------------------------------------------------//
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */

    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    private final Vector v010 = new Vector(0, 1, 0);
    @Test
    public void testFindIntersections() {

        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(0.5,0,0),
                new Vector(0.5,1,0)));
        assertEquals(1, result2.size(), "Wrong number of points");
        final var exp2 = List.of(new Point(1,1,0));
        assertEquals(exp2 , result2, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        final var result3 = sphere.findIntersections(new Ray(new Point(0.5,0.25,0.2),
                new Vector(-1,-0.25,-0.2)));
        assertNull(result3, "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result4 = sphere.findIntersections(new Ray(new Point(0, 1, 0),
                new Vector(1, -1, 0)));
        assertEquals(1, result4.size(), "Wrong number of points");
        final var exp3 = List.of(new Point(1, 0, 0));
        assertEquals(exp3, result4, "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        final var result5 = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(1, 1, 0)));
        assertNull(result5, "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        final var result6 = sphere.findIntersections(new Ray(new Point(1,-2,0),v010))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(1,-2,0)))).toList();
        assertEquals(2, result6.size(), "Wrong number of points");
        final var exp4 = List.of(new Point(1,-1,0), new Point(1,1,0));
        assertEquals(exp4, result6, "Ray start before sphere and goes through the center");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result7 = sphere.findIntersections(new Ray(new Point(1, -1, 0), v010));
        assertEquals(1, result7.size(), "Wrong number of points");
        final var exp5 = List.of(new Point(1,1,0));
        assertEquals(exp5, result7, "Ray start at sphere and goes inside");

        // TC15: Ray starts inside (1 point)
        final var result8 = sphere.findIntersections(new Ray(new Point(1, 0.5, 0), v010));
        assertEquals(1, result8.size(), "Wrong number of points");
        final var exp6 = List.of(new Point(1,1,0));
        assertEquals(exp6, result8, "Ray start inside sphere");

        // TC16: Ray starts at the center (1 point)
        final var result9 = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals(1, result9.size(), "Wrong number of points");
        assertEquals(exp6, result9, "Ray starts at the center of sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        final var result10 = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, 1, 0)));
        assertNull(result10, "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        final var result11 = sphere.findIntersections(new Ray(new Point(1, 2, 0),
                new Vector(0, 1, 0)));
        assertNull(result11, "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        final var result12 = sphere.findIntersections(new Ray(new Point(4, 0, 0.5),
                new Vector(-3,0,0.5)));
        assertNull(result12, "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        final var result13 = sphere.findIntersections(new Ray(new Point(1, 0, 1), v110));
        assertNull(result13, "Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        final var result14 = sphere.findIntersections(new Ray(new Point(1, 1, 1), v110));
        assertNull(result14, "Ray starts after the tangent point");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        final var result15 = sphere.findIntersections(new Ray(new Point(0, 2, 0),
                new Vector(0, 0, 1)));
        assertNull(result15, "Ray's line is outside and orthogonal to the line from ray start to sphere's center");
    }
}