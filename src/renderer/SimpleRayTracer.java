package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;

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
     * @param ray the ray through pixel
     * @return the color
     */
    public Color traceRay(Ray ray) {
        // Find intersections between the ray and the scene items
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections));
    }


    /**
     * function to calculate the color of a GeoPoint in the scene
     *
     * @param gp the GeoPoint
     * @return the color
     */
    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }
}
