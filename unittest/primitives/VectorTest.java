package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest{

    @Test
    void add() {
        Vector v1 = new Vector(1,2,3);
        Vector v1_opozit = new Vector(-1,-2,-3);
        Vector v2 = new Vector(4,5,6);

        Vector result = v1.add(v2);
        assertEquals(new Vector(5,7,9),result,"Vector addition failed");

        //Connection of a vector with itself
        //v1.add(v1) == v1.scale(2)
        assertEquals(new Vector(2,4,6),v1.add(v1),"Vector addition failed");
        //Addition of opposite vectors of equal length
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1_opozit), "Adding a vector to itself should throw an IllegalArgumentException of zero vector");
    }

    @Test
    void scale() {
        Vector v1 = new Vector(1,2,3);

        Vector result = v1.scale(2);
        assertEquals(new Vector(2,4,6),result,"Vector scale failed");

        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "Scaling a vector to itself should throw an IllegalArgumentException of zero vector");
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(4,5,6);

        //Two vectors are perpendicular to each other
        Vector verticalXtoY = new Vector(0,0,1);
        Vector verticalYtoX = new Vector(0,1,0);

        double result = v1.dotProduct(v2);
        assertEquals(32,result,"Vector dotProduct failed");
        //Checking if two perpendicular vectors are equal to zero
        assertEquals(0 ,verticalXtoY.dotProduct(verticalYtoX), "dot Product a vector is zero");
        //Scalar multiplication when one of the vectors is of unit size
        assertEquals(3,v1.dotProduct(verticalXtoY), "dot Product a vector is need to be value of z of vector v1");


    }

    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 0, 0);
        Vector v2 = new Vector(0, 1, 0);
        Vector v1_pozit = new Vector(-1,0,0);
        Vector v3 = new Vector(2,0,0);

        // Act
        Vector result = v1.crossProduct(v2);

        // Assert
        assertEquals(new Vector(0, 0, 1), result, "Cross product calculation failed");

        //Vector multiplication - so the zero vector is the multiplication of a vector with itself
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");
        //The vectors are opposite and of equal length, the vector product between them is zero
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1_pozit), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");
        //Vector product of vectors in the same direction is Zero
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), "cross Product on vectors is Zero vector should throw an IllegalArgumentException of zero vector");

    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1,2,3);
        double result = v1.lengthSquared();

        assertEquals(14,result,"Vector lengthSquared failed");
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

        // Assert
        assertEquals(1, result1, "Zero vector length calculation failed");
        assertEquals(5, result2, "Right triangle vector length calculation failed");
        assertEquals(Math.sqrt(3), result3, "Regular vector length calculation failed");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);


        // Act
        Vector result = v.normalize();
        double resultLength = result.length();

        // Assert
        assertEquals(1, resultLength, "Vector normalization failed");
    }


    @Test
    void testEquals() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3);

        boolean is_equals = v1.equals(v2);
        assertTrue(is_equals,"Vector equals failed");
    }
}