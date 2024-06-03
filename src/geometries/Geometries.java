package geometries;

import primitives.Point;
import primitives.Ray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    // Method to find intersection points between a ray and the objects in the list
    public List<Point> findIntersections(Ray ray) {
        if (this.lstGeo.isEmpty()) { // Check if the list is empty
            return null; // If empty, return null
        }
        List<Point> intersections = new LinkedList<Point>(); // Create a new list to store intersection points
        for (Intersectable geometry : lstGeo) { // Loop through all objects in the list
            List<Point> geometryIntersections = geometry.findIntersections(ray); // Find intersection points with the current object
            if (geometryIntersections != null) { // Check if intersection points were found
                intersections.addAll(geometryIntersections); // Add the found intersection points to the general list
            }
        }
        if (intersections.isEmpty()) { // Check if no intersection points were found
            return null; // If none were found, return null
        }
        return intersections; // Return the list of found intersection points
    }
}
