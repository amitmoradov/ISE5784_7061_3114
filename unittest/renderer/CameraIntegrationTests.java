package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            // .setRayTracer(new SimpleRayTracer(new Scene("Test"))).setImageWriter(new
            // ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1);
    Camera camera = cameraBuilder.setVpSize(3, 3).build();

    /**
     * Integration test method for constructing rays from camera and checking
     * intersections with sphere.
     */
    @Test
    public void testsphereIntegretions() {
        // Define the camera parameters

        // Sphere tests
        //TC01:
        testRayIntersections(camera, new Sphere(new Point(0, 0, -3), 1), 2);

        //TC02: The camera is located at point (0,0,0.5) and it cuts the ball there are 18 cuts
        cameraBuilder.setLocation(new Point(0, 0, 0.5));
        testRayIntersections(camera, new Sphere(new Point(0, 0, -2.5), 2.5), 18);

        //TC03:The camera is positioned at (0,0,0.5) and it cuts the ball at 10 points
        testRayIntersections(camera, new Sphere(new Point(0, 0, -2), 2), 10);

        //TC04:The camera inside the sphere
        testRayIntersections(camera, new Sphere(new Point(0, 0, -2), 4), 9);

        //TC05:The count is after the camera so there are no cutoff points
        cameraBuilder.setLocation(Point.ZERO);
        testRayIntersections(camera, new Sphere(new Point(0, 0, 1), 0.5), 0);
    }

    /**
     * Integration test method for constructing rays from camera and checking
     * intersections with plane.
     */
    @Test
    public void testsplaneIntegretions() {
        // Plane tests
        //TC01:
        testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, 0, 1)), 9);
        //TC02:
        testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, 1, 1)), 9);
        //TC03:
        testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, -1, 1)), 6);
    }

    /**
     * Integration test method for constructing rays from camera and checking
     * intersections with triangle.
     */
    @Test
    public void teststrianleIntegretions() {
        //Triangle tests
        //TC01:
        testRayIntersections(camera, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 1);
        //TC02:
        testRayIntersections(camera, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 2);

    }

    /**
     * Helper method to test the number of intersections of rays from the camera
     * with a given geometry.
     *
     * @param camera                the camera
     * @param geometry              the geometry to test intersections with
     * @param expectedIntersections the expected number of intersections
     */
    private void testRayIntersections(Camera camera, Intersectable geometry, int expectedIntersections) {
        int count = 0;
        int nX = 3, nY = 3;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    count += intersections.size();
                }
            }

            assertEquals(expectedIntersections, count, "Wrong number of intersections");
        }
    }
}
