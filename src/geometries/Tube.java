package geometries;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a tube in three-dimensional space.
 * Extends the RadialGeometry class.
 */
public class Tube extends RadialGeometry {

    protected final Ray axis; // The axis of the tube

    /**
     * Constructs a new Tube object with the specified axis and radius.
     *
     * @param axis the axis of the tube, a Ray object representing the central axis
     * @param radius the radius of the tube
     */
    public Tube(Ray axis, double radius) {
        super(radius); // Required by the superclass
        this.axis = axis;
    }


    /**
     * Returns the normal vector to the tube at a given point.
     * Since a tube is infinitely thin, it has no normal at a specific point.
     *
     * @param p The point to calculate the normal at.
     */
    public Vector getNormal(Point p) {
        return null;
    }
}
