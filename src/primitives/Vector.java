package primitives;

/**
 * * Class Vector is the basic class representing a Vector in space of Euclidean geometry in Cartesian
 * * 3-Dimensional coordinate system.
 * @author Amit and Yinon
 */
public class Vector extends Point {

    /**
     * Parameter constructor for creating a new Vector based on 3 new values of Coordinates
     * @param x first number value of Vector
     * @param y second number value of Vector
     * @param z third number value of Vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(this.xyz.equals(ZERO)) {
            throw new IllegalArgumentException("The Vector is zero");
        }
    }

    /**
     * Parameter constructor for creating a new Vector based on Double3 existing object
     * @param xyz is a triad of coordinates exists
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(this.xyz.equals(ZERO)) {
            throw new IllegalArgumentException("The Vector is zero");
        }
    }
    public Vector add(Vector v) {
       return this.xyz.add(v.xyz);
    }
}
