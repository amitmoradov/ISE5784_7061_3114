package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest{

    private final double DELTA = 0.000001;

    @Test
    void constructorTest(){
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0),"Can not init to Zero Vector");
    }
    @Test
    void add() {
        Vector v1 = new Vector(1,2,3);
        Vector v1_opozit = new Vector(-1,-2,-3);
        Vector v2 = new Vector(4,5,6);

        // ============ Equivalence Partitions Tests ==============

        Vector result = v1.add(v2);
        assertEquals(new Vector(5,7,9),result,"Vector addition failed");

        //TC01: Connection of a vector with itself
        //v1.add(v1) == v1.scale(2)
        assertEquals(new Vector(2,4,6),v1.add(v1),"Vector addition failed");
        //TC02: Addition of opposite vectors of equal length
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1_opozit), "Adding a vector to itself should throw an IllegalArgumentException of zero vector");
    }

    @Test
    void scale() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============

        //Checking if the scale is work on v1 * 2
        assertEquals(new Vector(2,4,6),v1.scale(2),"Vector scale failed");
        assertEquals(new Vector(-2,-4,-6),v1.scale(-2),"Vector scale failed");


        // =============== Boundary Values Tests =====================

        //TC3: Chacking if give zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "Scaling a vector to itself should throw an IllegalArgumentException of zero vector");
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(4,5,6);
        Vector v3 = new Vector(0,0,-1);
        //Two vectors are perpendicular to each other
        Vector verticalXtoY = new Vector(0,0,1);
        Vector verticalYtoX = new Vector(0,1,0);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Checking if dot product work on two vectors
        assertEquals(32,v1.dotProduct(v2),DELTA,"Vector dotProduct failed");
       //TC02: Checking if two vectors create angle more 90% and this equal to negative number.
        assertEquals(-1,DELTA ,v3.dotProduct(verticalXtoY), "dot Product a vector is not negative");

        // =============== Boundary Values Tests =====================

        //TC03: Checking if two perpendicular vectors (90%) are equal to zero
        assertEquals(0,DELTA ,verticalXtoY.dotProduct(verticalYtoX), "dot Product a vector is zero");

        //TC04: Scalar multiplication when one of the vectors is of unit size (like: 0,0,1)
        assertEquals(3,DELTA,v1.dotProduct(verticalXtoY), "dot Product a vector is need to be value of z of vector v1");


    }

    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        Vector v1_pozit = new Vector(-1,0,0);
        Vector v3 = new Vector(2,0,0);


        // ============ Equivalence Partitions Tests ==============

        //TC01: Chaking cross product with two vectors
        assertEquals(new Vector(0, 0, 1), v1.crossProduct(v2), "Cross product calculation failed");


        // =============== Boundary Values Tests =====================

        //TC02: Vector multiplication - so the zero vector is the multiplication of a vector with itself
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");
        //TC03: The vectors are opposite and of equal length, the vector product between them is zero
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1_pozit), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");
        //TC04: Vector product of vectors in the same direction is Zero
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");

    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Checking if length squared is correctly
        assertEquals(14,v1.lengthSquared(),DELTA,"Vector lengthSquared failed");
    }

    @Test
    void length() {
        Vector v1 = new Vector(1, 0, 0); // Edge case: zero vector
        Vector v2 = new Vector(3, 4, 0); // Edge case: right triangle
        Vector v3 = new Vector(1, 1, 1); // Regular case

        // Act
        double result1 = v1.length();
        double result2 = v2.length();
        double result3 = v3.length();

        // ============ Equivalence Partitions Tests ==============

        //TC01: Checking if the length is correct
        assertEquals(1, result1,DELTA, "Zero vector length calculation failed");
        assertEquals(5, result2,DELTA, "Right triangle vector length calculation failed");
        assertEquals(Math.sqrt(3), result3,DELTA, "Regular vector length calculation failed");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);


        // Act
        Vector result = v.normalize();
        double resultLength = result.length();

        // Checking if the normalization is work
        assertEquals(1,DELTA, resultLength, "Vector normalization failed");
    }


    @Test
    void testEquals() {

        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3);

        boolean is_equals = v1.equals(v2);
        assertTrue(is_equals,"Vector equals failed");
    }
}