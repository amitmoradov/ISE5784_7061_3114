package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry implements Geometry {

    protected final double radius;

    /**
     * Constructs a new RadialGeometry with the specified radius.
     *
     * @param radius The radius of the geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
