package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**
 * This abstract class represents a ray tracer
 *
 * @author Amit
 * @author Yinon
 */
public abstract class RayTracerBase {
    /**
     * the scene
     */
    protected Scene scene;

    /**
     * contructor for scene
     *
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * function to calculate the color of a ray through pixel in the scene
     *
     * @param ray the ray
     * @return the color
     */
    public abstract Color traceRay(Ray ray);
}
