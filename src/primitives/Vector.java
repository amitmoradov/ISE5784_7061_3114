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
       return new Vector(super.xyz.add(v.xyz));

    }

    public Vector scale(double i) {
        return new Vector(super.xyz.scale(i));
    }


    public double dotProduct(Vector vector) {
       return super.xyz.d1 * vector.xyz.d1 + super.xyz.d2 * vector.xyz.d2 + super.xyz.d3;
    }

    public Vector crossProduct(Vector vector) {
        double x = super.xyz.d2 * vector.xyz.d3 - super.xyz.d3 * vector.xyz.d2;
        double y = super.xyz.d3 * vector.xyz.d1 - super.xyz.d1 * vector.xyz.d3;
        double z = super.xyz.d1 * vector.xyz.d2 - super.xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    public double lengthSquared() {
        return this.dotProduct(this);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }
    public Vector normalize() {
        double normal_x = super.xyz.d1 / this.length();
        double normal_y = super.xyz.d2 / this.length();
        double normal_z = super.xyz.d3 / this.length();
        return new Vector(normal_x, normal_y, normal_z);
    }

}
