package geometries;

/**
 * An abstract class representing radial geometries, such as spheres and cylinders, which have a radius.
 */
public abstract class RadialGeometry extends Geometry {

    protected final double radius; // The radius of the radial geometry

    /**
     * Constructs a new RadialGeometry with the specified radius.
     *
     * @param radius The radius of the geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
