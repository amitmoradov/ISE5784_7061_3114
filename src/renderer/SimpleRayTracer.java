package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;

/**
 * This class represents a simple ray tracer
 *
 * @author Amit
 * @author Yinon
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * constructor for scene
     *
     * @param scene the scene
     */
    public SimpleRayTracer(Scene scene){
        super(scene);
    }


    /**
     * function to calculate the color of a ray through pixel in the scene
     *
     * @param ray the ray
     * @return the color
     */
    public Color traceRay(Ray ray) {
        // Find intersections between the ray and the scene items
        List<Point> intersections = this.scene.geometries.findIntersections(ray);

        // If no intersections were found, return the background color of the scene ,
        // Else return the color of the closest intersection point
        return intersections == null ? this.scene.background : calcColor(ray.findClosestPoint(intersections));
    }


    /**
     * function to calculate the color of point in the scene
     *
     * @param point the point
     * @return the color
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
