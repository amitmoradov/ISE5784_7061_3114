package geometries;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   /** The size of the polygon - the amount of the vertices in the polygon */
   private final int           size;

   /**
    * Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {


      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane = new Plane(vertices[0], vertices[1], vertices[2]);

      // calculate bounding box:
      this.box = getBoundingBox(vertices);

      if (size == 3) return; // no need for more tests for a Triangle

      Vector n = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with the normal. If all the rest consequent edges will generate the same sign
      // - the polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }

   }
   /**
    * Calculate the bounding box of the polygon
    * @param vertices the vertices of the polygon
    * @return the bounding box of the polygon
    */
   private static BoundingBox getBoundingBox(Point[] vertices) {
      // Initialize minimum and maximum values for each coordinate
      double minX = Double.POSITIVE_INFINITY;
      double minY = Double.POSITIVE_INFINITY;
      double minZ = Double.POSITIVE_INFINITY;
      double maxX = Double.NEGATIVE_INFINITY;
      double maxY = Double.NEGATIVE_INFINITY;
      double maxZ = Double.NEGATIVE_INFINITY;

      // Iterate through each vertex to find the minimum and maximum coordinates
      for (Point p : vertices) {
         minX = Math.min(minX, p.getX());
         minY = Math.min(minY, p.getY());
         minZ = Math.min(minZ, p.getZ());
         maxX = Math.max(maxX, p.getX());
         maxY = Math.max(maxY, p.getY());
         maxZ = Math.max(maxZ, p.getZ());
      }

      // Create a BoundingBox object using the calculated minimum and maximum points
      return new BoundingBox(
              new Point(minX, minY, minZ),
              new Point(maxX, maxY, maxZ)
      );
   }


   /**
    * @return the normal to the polygon
    * @param point
    *
    */
   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }

   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
      // First, we check if the ray intersects the plane of the polygon
      List<Point> intersections = plane.findIntersections(ray);

      if (intersections != null) {
         Point p0 = ray.getHead();
         Vector v = ray.getDirection();
         // We need all the dot products between ray direction , and the normals are just positive or just negative
         boolean allPositive = true;
         boolean allNegative = true;

         for (int i = 0; i < vertices.size(); i++) {
            // v1 , v2 ... vn
            Vector vector = vertices.get(i).subtract(p0);
            // v2 , v3 ... v1
            Vector nextVector = vertices.get((i + 1) % size).subtract(p0);
            // The normal ni from formula v1 x v2 ... vn x v1
            Vector currentNormal = vector.crossProduct(nextVector).normalize();
            double dotProduct = v.dotProduct(currentNormal);

            // If one or more are 0.0 - no intersection
            if (isZero(dotProduct)) {
               return null;
            }

            else if (dotProduct > 0) {
               allNegative = false;
            }

            else if (dotProduct < 0) {
               allPositive = false;
            }
         }
         // If all the dot products are positive or all are negative, there is an intersection
         if (allPositive || allNegative) {
            return List.of(new GeoPoint(this, intersections.get(0)));
         }
      }
      return null;
   }
}
