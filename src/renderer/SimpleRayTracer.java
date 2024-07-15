package renderer;

import primitives.Ray;
import scene.Scene;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Vector;
import static primitives.Util.alignZero;
import static java.lang.Math.*;
import java.util.List;

/**
 * A simple ray tracer implementation.
 * <p>
 * This class extends the {@link RayTracerBase} abstract base class.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Maximum recursion level for calculating colors considering transparency or reflection.
     * This constant defines the depth limit to prevent excessive recursion. Adjust this value based on scene complexity and performance considerations.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum threshold value for the accumulated coefficient of transparency or reflection.
     * If the accumulated coefficient falls below this threshold, recursion for transparency or reflection terminates to avoid negligible contributions to the final color.
     * Adjust this value based on scene specifics and desired precision.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * The initial coefficient for transparency or reflection calculations.
     * This constant is used as the starting value for the accumulated coefficient in recursive color calculations.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructs a new SimpleRayTracer with the specified scene.
     *
     * @param scene The scene to be traced.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray in the scene and returns the color of the closest intersection point.
     *
     * @param ray The ray to trace.
     * @return The color of the closest intersection point, or the background color if no intersections are found.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at a given geometric point considering ambient light, emission from the geometry, and local lighting effects.
     * This method uses recursive ray tracing to handle transparency and reflection up to a specified recursion level.
     *
     * @param gp  The geometric point at which to calculate the color.
     * @param ray The ray that intersected with the geometry at the geometric point.
     * @return The calculated color at the geometric point, taking into account ambient light, emission, local lighting effects (diffuse and specular reflections), and recursive effects of transparency or reflection.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given geometric intersection point considering local lighting effects and potentially global effects such as transparency or reflection.
     *
     * @param gp     The geometric point at which to calculate the color.
     * @param ray    The ray that intersected with the geometry at the intersection point.
     * @param level  The current recursion level for handling transparency or reflection effects.
     * @param k      The accumulated coefficient (e.g., transparency or reflection coefficient) up to the current recursion level.
     * @return The calculated color at the intersection point, considering local lighting effects and global effects up to the specified recursion level.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Constructs a reflected ray based on the intersection point and incoming ray.
     * The reflection ray moves in the direction opposite to the normal vector at the intersection point.
     *
     * @param gp  The geometric point of intersection.
     * @param v   The direction vector of the incoming ray.
     * @param n   The normal vector at the intersection point.
     * @return The reflected ray originating from the intersection point.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        double nv = n.dotProduct(v);
        if (nv == 0) return null;

        Vector vec = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, vec, n);
    }

    /**
     * Constructs a refracted ray based on the intersection point and incoming ray.
     * The refraction ray is determined by Snell's law, considering the refractive indices of the materials involved.
     *
     * @param gp  The geometric point of intersection.
     * @param v   The direction vector of the incoming ray.
     * @param n   The normal vector at the intersection point.
     * @return The refracted ray originating from the intersection point.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

//    /**
//     * Calculates the combined global effects (such as reflection and refraction) at a given geometric point using recursive ray tracing.
//     *
//     * @param gp     The geometric point at which to calculate global effects.
//     * @param ray    The view direction vector.
//     * @param level  The current recursion level for handling transparency or reflection effects.
//     * @param k      The accumulated coefficient (e.g., reflection coefficient kR or transparency coefficient kT).
//     * @return The calculated color representing combined global effects at the geometric point.
//     */
//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//        Material material = gp.geometry.getMaterial();
//        Vector v = ray.getDirection();
//        Vector n = gp.geometry.getNormal(gp.point);
//        return calcGlobalEffect(constructRefractedRay(gp, v, n), material.kT, level, k)
//                .add(calcGlobalEffect(constructReflectedRay(gp, v, n), material.kR, level, k));
//    }

    /**
     * Calculates the combined global effects (such as reflection and refraction) at a given geometric point using recursive ray tracing.
     *
     * @param gp     The geometric point at which to calculate global effects.
     * @param ray    The view direction vector.
     * @param level  The current recursion level for handling transparency or reflection effects.
     * @param k      The accumulated coefficient (e.g., reflection coefficient kR or transparency coefficient kT).
     * @return The calculated color representing combined global effects at the geometric point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);

        List<Ray> refractedRays = constructRefractedRays(gp, v, n, material.kB);
        List<Ray> reflectedRays = constructReflectedRays(gp, v, n, material.kG);

        Color refractedColor = calcAverageColor(refractedRays, level, k, material.kT);
        Color reflectedColor = calcAverageColor(reflectedRays, level, k, material.kR);

        return refractedColor.add(reflectedColor);
    }


    /**
     * Calculates the global effect (reflection or refraction) for a given ray and coefficient.
     *
     * @param ray    The ray to trace for the global effect.
     * @param kx     The coefficient for the specific effect being calculated (kR for reflection, kT for refraction).
     * @param level  The current recursion level for handling transparency or reflection effects.
     * @param k      The accumulated coefficient (e.g., reflection coefficient kR or transparency coefficient kT).
     * @return The calculated color representing the global effect for the given ray and coefficient.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK; // Return no contribution if the combined coefficient is too small

        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background // If no intersection found, return background color
                // Recursively calculate color with scaled coefficient
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Finds the closest intersection point of a ray with the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest intersection point (GeoPoint) of the ray with the geometries, or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Calculates the local effects (diffuse and specular reflections) of light on a given geometry point.
     * This method considers the contribution of each light source in the scene.
     *
     * @param gp  The geometric point on which to calculate the local effects.
     * @param ray The ray used to intersect with the geometry.
     * @param k   The accumulated coefficient up to the current recursion level.
     * @return The color resulting from local lighting effects, or the emission color if there is no interaction.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the transparency coefficient for a given geometry point, light source, light direction vector, and normal vector.
     *
     * @param gp  The geometric point at which to calculate transparency.
     * @param ls  The light source affecting the geometric point.
     * @param l   The direction vector from the light source to the geometric point.
     * @param n   The normal vector at the geometric point.
     * @return The transparency coefficient for the given geometry point and light source.
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
        Vector lDir = l.scale(-1);
        Ray lR = new Ray(gp.point, lDir, n);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lR);
        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        double distanceToLight = ls.getDistance(gp.point);
        for (GeoPoint intersectionPoint : intersections) {
            if (alignZero(intersectionPoint.point.distance(gp.point) - distanceToLight) <= 0) {
                ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
                if (ktr.equals(Double3.ZERO))
                    break;
            }
        }
        return ktr;
    }

    /**
     * Calculates the diffuse reflection component based on the material properties and the cosine of the angle between the normal vector and the light direction vector.
     *
     * @param material The material of the geometry.
     * @param nl       The dot product of the normal vector and the light direction vector.
     * @return The diffuse reflection color component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * Calculates the specular reflection component based on the material properties, the normal vector, light direction vector, view direction vector, and the cosine of the angle between the view direction and the reflection direction.
     *
     * @param material The material of the geometry.
     * @param n        The normal vector at the geometric point.
     * @param l        The direction vector from the point to the light source.
     * @param nl       The dot product of the normal vector and the light direction vector.
     * @param v        The view direction vector.
     * @return The specular reflection color component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector reflectVector = l.subtract(n.scale(nl * 2));
        double minusVR = -alignZero(v.dotProduct(reflectVector));
        return minusVR <= 0 ? Double3.ZERO : material.kS.scale(pow(minusVR, material.shininess));
    }

    private Color calcAverageColor(List<Ray> rays, int level, Double3 k, Double3 kx) {
        Color color = Color.BLACK;
        if (rays.isEmpty()) return color;
        for (Ray rT : rays)
            color = color.add(calcGlobalEffect(rT, kx, level, k));
        return color.reduce(rays.size());
    }

    private List<Ray> constructRefractedRays(GeoPoint gp, Vector v, Vector n, double
            kB) {
        Ray rfRay = constructRefractedRay(gp, v, n);
        double res = rfRay.getDirection().dotProduct(n);
        return kB == 0 ? List.of(rfRay)
                : TargetArea.getBuilder(rfRay, kB).build().constructRayGrid().stream()
                .filter(r -> r.getDirection().dotProduct(n) * res > 0).toList();
    }

    private List<Ray> constructReflectedRays(GeoPoint gp, Vector v, Vector n, double
            kG) {
        Ray rfRay = constructReflectedRay(gp, v, n);
        double res = rfRay.getDirection().dotProduct(n);
        return kG == 0 ? List.of(rfRay)
                : TargetArea.getBuilder(rfRay, kG).build().constructRayGrid().stream()
                .filter(r -> r.getDirection().dotProduct(n) * res > 0).toList();
    }

}