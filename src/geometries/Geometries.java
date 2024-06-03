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
public class Geometries implements Intersectable {

    // List of Intersectable objects stored in a linked list
    private List<Intersectable> lstGeo = new LinkedList<Intersectable>();

    // Default constructor that initializes the list of geometries
    public Geometries() {}

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

    /**
     * Finds the intersection points between a ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points, or null if no intersections were found.
     */
    public List<Point> findIntersections(Ray ray) {
        if (this.lstGeo.isEmpty()) { // Check if the list is empty
            return null; // If empty, return null
        }
        List<Point> intersections = null; // Initialize the intersections list as null
         for (Intersectable geometry : lstGeo) { // Loop through all objects in the list
            List<Point> geometryIntersections = geometry.findIntersections(ray); // Find intersection points with
            // the current object
            if (geometryIntersections != null) { // Check if intersection points were found
                intersections = new LinkedList<Point>(); // Create a new list to store intersection points
                intersections.addAll(geometryIntersections); // Add the found intersection points to the general list
            }
        }

        if (intersections.isEmpty()) { // Check if no intersection points were found
            return null; // If none were found, return null
        }
        return intersections; // Return the list of found intersection points
    }
}
