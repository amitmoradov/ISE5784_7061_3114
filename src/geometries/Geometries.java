package geometries;

import primitives.Point;
import primitives.Ray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> lstGeo = new LinkedList<Intersectable>();

    public Geometries() {}

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries){
        if (geometries != null) {
        this.lstGeo.addAll(List.of(geometries));
        }
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
