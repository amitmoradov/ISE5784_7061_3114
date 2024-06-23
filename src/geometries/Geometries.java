package geometries;

import primitives.Point;
import primitives.Ray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (this.lstGeo.isEmpty()) { // Check if the list is empty
            return null; // If empty, return null
        }
        List<GeoPoint> intersections = null; // Create a new list to store intersection points
        for (Intersectable geometry : lstGeo) { // Loop through all objects in the list
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersectionsHelper(ray); // Find intersection points with
            // the current object
            if (geometryIntersections != null) { // Check if intersection points were found
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geometryIntersections); // Add the found intersection points to the general list
            }
        }

        return intersections; // Return the list of found intersection points
    }

}
