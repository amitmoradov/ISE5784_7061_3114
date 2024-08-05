package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The BoundingBox class represents an axis-aligned bounding box (AABB) for 3D geometries.
 * It provides methods for intersection tests and constructing bounding volume hierarchies (BVH).
 */
public class BoundingBox {

    /**
     * The minimum point of the bounding box
     */
    public Point min;
    /**
     * The maximum point of the bounding box
     */
    public Point max;

    /**
     * Constructs a BoundingBox with specified minimum and maximum points.
     *
     * @param min the minimum point of the bounding box
     * @param max the maximum point of the bounding box
     */
    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Checks if a ray intersects the bounding box.
     *
     * @param ray the ray to test for intersection
     * @return true if the ray intersects the bounding box, false otherwise
     */
    public boolean hasIntersection(Ray ray) {
        // Extract the coordinates of the bounding box's minimum and maximum points
        double minX = min.getX();
        double minY = min.getY();
        double minZ = min.getZ();
        double maxX = max.getX();
        double maxY = max.getY();
        double maxZ = max.getZ();

        // Extract the ray's origin (head) coordinates and direction vector components
        Point head = ray.getHead();
        double headX = head.getX();
        double headY = head.getY();
        double headZ = head.getZ();
        Vector dir = ray.getDirection();
        double dirX = dir.getX();
        double dirY = dir.getY();
        double dirZ = dir.getZ();

        // Initialize the intersection parameters for each axis
        double tMinX = Double.NEGATIVE_INFINITY;
        double tMaxX = Double.POSITIVE_INFINITY;
        double tMinY = Double.NEGATIVE_INFINITY;
        double tMaxY = Double.POSITIVE_INFINITY;
        double tMinZ = Double.NEGATIVE_INFINITY;
        double tMaxZ = Double.POSITIVE_INFINITY;

        // Calculate intersection with the X planes
        if (dirX != 0) {
            tMinX = (minX - headX) / dirX;
            tMaxX = (maxX - headX) / dirX;
            // Swap if direction is negative
            if (dirX < 0) {
                double temp = tMinX;
                tMinX = tMaxX;
                tMaxX = temp;
            }
        }

        // Calculate intersection with the Y planes
        if (dirY != 0) {
            tMinY = (minY - headY) / dirY;
            tMaxY = (maxY - headY) / dirY;
            // Swap if direction is negative
            if (dirY < 0) {
                double temp = tMinY;
                tMinY = tMaxY;
                tMaxY = temp;
            }
        }

        // Check for overlap in the XY slab
        if (tMinX > tMaxY || tMinY > tMaxX) return false;

        // Update the X intersection parameters based on Y intersection
        if (tMinY > tMinX) tMinX = tMinY;
        if (tMaxY < tMaxX) tMaxX = tMaxY;

        // Calculate intersection with the Z planes
        if (dirZ != 0) {
            tMinZ = (minZ - headZ) / dirZ;
            tMaxZ = (maxZ - headZ) / dirZ;
            // Swap if direction is negative
            if (dirZ < 0) {
                double temp = tMinZ;
                tMinZ = tMaxZ;
                tMaxZ = temp;
            }
        }

        // Check for overlap in the XYZ slab
        return tMinX <= tMaxZ && tMinZ <= tMaxX;
    }

    /**
     * Gets the center point of the bounding box.
     *
     * @return the center point of the bounding box
     */
    public Point getCenter() {
        double centerX = (min.getX() + max.getX()) / 2.0;
        double centerY = (min.getY() + max.getY()) / 2.0;
        double centerZ = (min.getZ() + max.getZ()) / 2.0;
        return new Point(centerX, centerY, centerZ);
    }

    /**
     * Creates a new bounding box that is the union of this bounding box and another.
     *
     * @param box the bounding box to union with
     * @return a new bounding box that encompasses both bounding boxes
     */
    private BoundingBox union(BoundingBox box) {
        return new BoundingBox(
                new Point(Math.min(min.getX(), box.min.getX()), Math.min(min.getY(), box.min.getY()), Math.min(min.getZ(), box.min.getZ())),
                new Point(Math.max(max.getX(), box.max.getX()), Math.max(max.getY(), box.max.getY()), Math.max(max.getZ(), box.max.getZ()))
        );
    }

    /**
     * Builds a bounding volume hierarchy (BVH) from a list of intersectable geometries.
     *
     * @param intersectableList the list of intersectable geometries
     * @return a list of intersectable geometries organized in a BVH
     */
    static public List<Intersectable> buildBVH(List<Intersectable> intersectableList) {
        // If the list has one or zero elements, return it as is
        if (intersectableList.size() <= 1) {
            return intersectableList;
        }

        // Separate geometries without a bounding box
        List<Intersectable> infiniteGeometries = new ArrayList<>();
        intersectableList.removeIf(g -> {
            if (g.getBoundingBox() == null) {
                infiniteGeometries.add(g);
                return true;
            }
            return false;
        });

        // Sort geometries by the X coordinate of their bounding box centers
        intersectableList.sort(Comparator.comparingDouble(g -> g.getBoundingBox().getCenter().getX()));

        // Split the list into two halves and recursively build the BVH
        int mid = intersectableList.size() / 2;
        List<Intersectable> leftGeometries = buildBVH(intersectableList.subList(0, mid));
        List<Intersectable> rightGeometries = buildBVH(intersectableList.subList(mid, intersectableList.size()));

        // Create bounding boxes for the left and right halves
        BoundingBox leftBox = getBoundingBox(leftGeometries);
        BoundingBox rightBox = getBoundingBox(rightGeometries);

        // Combine the left and right geometries into a single Geometries object
        Geometries combined = new Geometries(leftGeometries);
        combined.add(rightGeometries);
        combined.box = leftBox.union(rightBox);

        // Combine the infinite geometries and the BVH into a single list
        List<Intersectable> result = new ArrayList<>(infiniteGeometries);
        result.add(combined);
        return result;
    }

    // Calculates the bounding box for a list of intersect geometries.
    static private BoundingBox getBoundingBox(List<Intersectable> intersectableList) {
        if (intersectableList.isEmpty()) {
            return null;
        }

        // Initialize the bounding box with the first geometry's bounding box
        BoundingBox boundingBox = intersectableList.get(0).getBoundingBox();
        for (Intersectable g : intersectableList) {
            boundingBox = boundingBox.union(g.getBoundingBox());
        }

        return boundingBox;
    }
}