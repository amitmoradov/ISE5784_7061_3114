package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            // .setRayTracer(new SimpleRayTracer(new Scene("Test"))).setImageWriter(new
            // ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(1);


    private void testsphereIntegretions() {
        // Define the camera parameters
        Camera camera = cameraBuilder.setVpSize(3, 3).build();
        // Sphere tests
        testRayIntersections(camera,new Sphere(new Point(0,0,-3),1),2);

        //TC02: המצלמה ממוקמת בנקודה 0,0,0.5 והיא חותכת את הכדור יש 18 חיתוכים
        cameraBuilder.setLocation(new Point(0,0,0.5));
        testRayIntersections(camera,new Sphere(new Point(0,0,-2.5),2.5),18);

        //TC03: המצלמה ממוקמת ב0,0,0.5 והיא חותכת את כדור ב10 נקודות
        testRayIntersections(camera,new Sphere(new Point(0,0,-2),2),10);

        //TC04:צריך לשנות את נקודת האמצע של הכדור
        testRayIntersections(camera,new Sphere(new Point(0,0,-2),4),9);

        //TC05:הספירה אחרי המצלמה לכן אין נקודות חיתוך
        testRayIntersections(camera,new Sphere(new Point(0,0,1),0.5),0);

        //Triangle tests

    }
    /**
     * Helper method to test the number of intersections of rays from the camera
     * with a given geometry.
     *
     * @param camera                the camera
     * @param geometry              the geometry to test intersections with
     * @param expectedIntersections the expected number of intersections
     * */
    private void testRayIntersections(Camera camera, Intersectable geometry, int expectedIntersections){
        int count = 0;
        int nX = 3, nY = 3;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                count += geometry.findIntersections(ray).size();
            }
        }

        assertEquals(expectedIntersections, count, "Wrong number of intersections");
    }

}
