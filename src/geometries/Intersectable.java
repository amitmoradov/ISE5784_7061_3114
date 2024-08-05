package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents an object that can be intersected by a ray.
 * author Amit Moradov , Yinon Shaul
 */
public abstract class Intersectable {


    /**
     * Bounding Box
     */
    protected BoundingBox box;

    /**
     * Constructs an Intersectable object with the specified bounding box.
     */
    public BoundingBox getBoundingBox() {
        return box;
    }

    /**
     * Public method findIntersections for finding intersection points between the
     * intersectable object and a given ray, by calling the helper method.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points between the object and the ray.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * Inner static class GeoPoint representing a geometric intersection point with
     * associated geometry.
     */
    public static class GeoPoint {
        /**
         * The geometry object of this intersection point.
         */
        public Geometry geometry;

        /**
         * The actual point of intersection.
         */
        public Point point;

        /**
         * Constructor for GeoPoint.
         *
         * @param geometry The geometry object of the intersection.
         * @param point    The actual point of intersection.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof GeoPoint geoPoint))
                return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);

        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
        }

    }

    /**
     * Public method findGeoIntersections for finding GeoPoints of intersections ,
     * between the intersectable object and a given ray , by calling the helper method.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of GeoPoints representing intersection points between the
     *         object and the ray.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Protected method findGeoIntersectionsHelper for finding GeoPoints of
     * intersections between the intersectable object and a given ray. This method
     * should be implemented in subclasses.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of GeoPoints representing intersection points between the
     *         object and the ray.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
