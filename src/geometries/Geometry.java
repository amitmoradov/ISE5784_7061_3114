package geometries;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * The Geometry interface represents a geometric shape in 3D space.
 */
public abstract class Geometry extends Intersectable {

    /**
     * Field representing the emission color of the geometry.
     */
    protected Color emission = Color.BLACK;

    /**
     * Field representing the material of the geometry.
     */
    private Material material = new Material();

    /**
     * Method that returns the emission color of the geometry.
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Method to update the emission color of the geometry.
     *
     * @param emission the new emission color to set
     * @return the updated Geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Method that returns the material of the geometry.
     *
     * @return the material of the geometry
     */
//    public Material getMaterial() {
//        return material;
//    }

    /**
     * Method to update the material of the geometry.
     *
     * @param material the new material to set
     * @return the updated Geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Method that returns the normal vector to the surface body at a given point.
     * This method must be implemented by subclasses.
     *
     * @param point the point on the body's surface
     * @return a vector
     */
    public abstract Vector getNormal(Point point);

}
