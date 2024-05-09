package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane extends RadialGeometry{

    private final Point q;
    private final Vector normal;

    public Plane(Point p1,Point p2,Point p3) {
        super(0);
        this.normal = null;
        this.q = p1;
    }

    public Plane(Point p1, Vector n) {
        super(0);
        this.q = p1;
        this.normal =  n.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
