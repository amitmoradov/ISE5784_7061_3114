package renderer;

import primitives.*;

import scene.Scene;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

/**
 * This class represents a simple ray tracer
 *
 * @author Amit
 * @author Yinon
 */
public class SimpleRayTracer extends RayTracerBase{

    private static final double DELTA = 0.1;

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
        // find intersections of ray with geometries in the scene and return the color of the closest point
        var intersections = scene.geometries.findGeoIntersections(ray);
        // if there are no intersections return the background color
        if (intersections == null) return scene.background;

        // find the closest point to the ray
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }


    /**
     * function to calculate the color of a GeoPoint in the scene
     *
     * @param intersection the GeoPoint
     * @return the color
     */
    private Color calcColor(GeoPoint intersection , Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the local effects (diffuse and specular reflections) of light on a
     * given geometry point. This method considers the contribution of each light
     * source in the scene.
     *
     * @param gp  The geometric point on which to calculate the local effects.
     * @param ray The ray used to intersect with the geometry.
     * @return The color resulting from local lighting effects, or null if there is
     *         no interaction (nv == 0).
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        // vector n - normal to the geometry at the point
        Vector n = gp.geometry.getNormal(gp.point);
        // vector v - direction of the ray , from the camera to the point
        Vector v = ray.getDirection();
        // nv - angle between the normal , and the vector v
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0)
            return null;

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();

        for (LightSource lightSource : scene.lights) {
            // vector l - from the point to the light source
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            // sign(nl) == sing(nv) check if the camera and the light source are on the same side of the geometry
            if ((nl * nv > 0) && unshaded(gp,l,n)) {
                // Take the intensity of the light source at the point
                Color iL = lightSource.getIntensity(gp.point);
                // Add the diffusive and specular reflections to the color
                color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }


    /**
     * Calculates the diffuse reflection component based on the material properties
     * and the cosine of the angle between the normal vector and the light direction
     * vector.
     *
     * @param material The material of the geometry.
     * @param nl       The dot product of the normal vector and the light direction
     *                 vector.
     * @return The diffuse reflection color component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * Calculates the specular reflection component based on the material
     * properties, the normal vector, light direction vector, view direction vector,
     * and the cosine of the angle between the view direction and the reflection
     * direction.
     *
     * @param material The material of the geometry.
     * @param n        The normal vector at the geometric point.
     * @param l        The direction vector from the point to the light source.
     * @param nl       The dot product of the normal vector and the light direction
     *                 vector.
     * @param v        The view direction vector.
     * @return The specular reflection color component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        // vector reflectVector - the reflection vector of the light direction vector
        Vector reflectVector = (l).subtract(n.scale(nl * 2));
        double max0_var = max(0, v.scale(-1).dotProduct(reflectVector));
        return material.kS.scale(pow(max0_var, material.shininess));
    }

    /**
     * check if the point is shaded by another geometry
     * @param gp the point
     * @param l the vector from the point to the light source
     * @param n the normal to the geometry
     * @return
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource){
        Vector lightDirection = l.scale(-1); // from point to light source

        Vector espVector = n.scale(DELTA);
        Point point = gp.point.add(espVector);

        Ray ray = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null;
    }
}
