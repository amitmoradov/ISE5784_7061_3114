package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * The Geometry interface represents a geometric shape in 3D space.
 */
public interface Geometry extends Intersectable {

    /**
     * Computes the normal vector to this geometry at the specified point.
     *
     * @param p The point on the geometry.
     * @return The normal vector to the geometry at the specified point.
     */
    public Vector getNormal(Point p);

}
