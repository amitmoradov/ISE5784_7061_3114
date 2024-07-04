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

    // The offset from the geometry
    private static final double DELTA = 0.1;

    /**
     * The initial coefficient for transparency or reflection calculations.
     * This constant is used as the starting value for the accumulated coefficient in recursive color calculations.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * The maximum level of recursion for transparency or reflection calculations.
     * This constant is used to limit the number of recursive calls to the calcColor method.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum coefficient for transparency or reflection calculations.
     * This constant is used to determine when to stop the recursive color calculations.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

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
     * Calculates the color at a given geometric point considering ambient light, emission from the geometry, and local lighting effects.
     * This method uses recursive ray tracing to handle transparency and reflection up to a specified recursion level.
     *
     * @param gp  The geometric point at which to calculate the color.
     * @param ray The ray that intersected with the geometry at the geometric point.
     * @return The calculated color at the geometric point, taking into account ambient light, emission, local lighting effects (diffuse and specular reflections), and recursive effects of transparency or reflection.
     */
    private Color calcColor(GeoPoint gp, Ray ray,int level, Double3 k) {
        Color color =  scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
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
            // nl - angle between the normal , and the vector l
            double nl = alignZero(n.dotProduct(l));

            // sign(nl) == sing(nv) check if the camera and the light source are on the same side of the geometry
            if ((nl * nv > 0) && unshaded(gp,l,n,nl,lightSource)) {
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
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource lightSource){
        // lightDirection - from the point to the light source
        Vector lightDirection = l.scale(-1); // from point to light source

        // point with a small offset 0.1 from the geometry
        Vector espVector = n.scale(nl < 0 ? DELTA : -DELTA);
        // point with a small offset 0.1 from the geometry
        Point point = gp.point.add(espVector);

        // create a ray from the point to the light source
        Ray ray = new Ray(point, lightDirection);
        /**
         * find intersections of the ray with geometries in the scene
         * if there are no intersections return true
         * else check if the intersection point is closer to the light source than the point
         * if it is return false
         */
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null){
            for (GeoPoint geoPoint : intersections){
                if (geoPoint.point.distance(ray.getHead()) < lightSource.getDistance(gp.point))
                    return false;
            }
        }
        // if there are no intersections or the intersection point is further from the light source than the point
        return true;
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT,level, k)
                .add(constructRefractedRay((gp, ray), material.kR,level, k);



    }
}
