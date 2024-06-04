package geometries;

import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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


    @Override
    public Vector getNormal(Point p) {
        // By the formula normalize(p - center O)
        // t = v * (p - p0)
        // O = p0 + t * v

        double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));
        Point O = axis.getPoint(t);
        return p.subtract(O).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
