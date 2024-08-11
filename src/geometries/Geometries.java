package geometries;

import primitives.Point;
import primitives.Ray;

import java.io.Serializable;
import java.util.*;

/**
 * The Geometries class represents a collection of Intersectable objects.
 * It is used to store multiple objects and find intersection points with them.
 * author Amit Moradov , Yinon Shaul
 */
public class Geometries extends Intersectable {

    // List of Intersectable objects stored in a linked list
    private final List<Intersectable> lstGeo = new LinkedList<Intersectable>();

    // Default constructor that initializes the list of geometries
    public Geometries() {
    }

    // Constructor that takes a collection of Intersectable objects and adds them to the list
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Constructor that initializes the Geometries collection with a list of Intersectable objects.
     *
     * @param geometries A list of Intersectable objects to be added to the collection.
     */
    public Geometries(List<Intersectable> geometries) {
        add(geometries);
    }

    /**
     * Adds a list of Intersectable objects to the Geometries collection.
     *
     * @param geometries A list of Intersectable objects to be added.
     */
    public void add(List<Intersectable> geometries) {
        this.lstGeo.addAll(geometries);
    }

    // Method to add objects to the list, accepts an array of Intersectable objects
    public void add(Intersectable... geometries) {
        if (geometries != null) { // Check if the array is not null
            this.lstGeo.addAll(List.of(geometries)); // Add all objects to the list
        }
    }

/*
    @Override
    public List<Point> findIntersections(Ray ray) {
        return List.of();
    }
*/
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (this.lstGeo.isEmpty()) { // Check if the list is empty
            return null; // If empty, return null
        }

        List<GeoPoint> intersections = null; // Create a new list to store intersection points
        for (Intersectable geometry : lstGeo) { // Loop through all objects in the list
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersections(ray)
                    ; // Find intersection points with
            // The current object
            if (geometryIntersections != null) { // Check if intersection points were found
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geometryIntersections); // Add the found intersection points to the general list
            }
        }

        return intersections; // Return the list of found intersection points
    }

    /**
     * Creates a bounding volume hierarchy (BVH) for the geometries in the collection.
     * This method optimizes the intersection tests by organizing the geometries into a hierarchical structure.
     */
    public void makeBVH() {
        // Create a new list to store the optimized geometries
        List<Intersectable> intersectables = BoundingBox.buildBVH(lstGeo);
        // Clear the current list and add the optimized geometries
        lstGeo.clear();
        // Add the optimized geometries to the list
        lstGeo.addAll(intersectables);
    }

}
