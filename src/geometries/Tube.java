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
     * Constructs a new tube with the specified axis.
     *
     * @param axis The axis of the tube.
     */
    public Tube(Ray axis) {
        super(0); // Required by the superclass
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
