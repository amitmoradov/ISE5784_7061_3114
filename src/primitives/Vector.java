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
        if(xyz.equals(ZERO.xyz)) {
            throw new IllegalArgumentException("The Vector is zero");
        }
    }

    /**
     * Parameter constructor for creating a new Vector based on Double3 existing object
     * @param xyz is a triad of coordinates exists
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(ZERO.xyz)) {
            throw new IllegalArgumentException("The Vector is zero");
        }
    }
    /**
     * Adds the specified vector to this vector.
     *
     * @param v The vector to be added.
     * @return A new vector which is the result of adding the specified vector to this vector.
     */
    public Vector add(Vector v) {
       return new Vector(super.xyz.add(v.xyz));
    }
    /**
     * Scales this vector by the specified factor.
     *
     * @param i The scaling factor.
     * @return A new vector which is the result of scaling this vector by the specified factor.
     */
    public Vector scale(double i) {
        return new Vector(super.xyz.scale(i));
    }

    /**
     * Computes the dot product of this vector with the specified vector.
     *
     * @param vector The vector to compute the dot product with.
     * @return The dot product of this vector with the specified vector.
     */
    public double dotProduct(Vector vector) {
       return super.xyz.d1 * vector.xyz.d1 + super.xyz.d2 * vector.xyz.d2 + super.xyz.d3 * vector.xyz.d3;
    }

    /**
     * Computes the cross product of this vector with the specified vector.
     *
     * @param vector The vector to compute the cross product with.
     * @return A new vector which is the cross product of this vector with the specified vector.
     */
    public Vector crossProduct(Vector vector) {
        double x = super.xyz.d2 * vector.xyz.d3 - super.xyz.d3 * vector.xyz.d2;
        double y = super.xyz.d3 * vector.xyz.d1 - super.xyz.d1 * vector.xyz.d3;
        double z = super.xyz.d1 * vector.xyz.d2 - super.xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Computes the squared length of this vector.
     *
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Computes the length of this vector.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes this vector.
     *
     * @return A new vector which is the normalized version of this vector.
     */
    public Vector normalize() {
        double normal_x = super.xyz.d1 / this.length();
        double normal_y = super.xyz.d2 / this.length();
        double normal_z = super.xyz.d3 / this.length();
        return new Vector(normal_x, normal_y, normal_z);
    }

    /**
     * Checks if this vector is equal to the specified object.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector other) && super.xyz.equals(other.xyz);
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return A string representation of this vector.
     */
    @Override
    public String toString() {
        return "Vector(" + super.xyz.toString() + ")";
    }
}
