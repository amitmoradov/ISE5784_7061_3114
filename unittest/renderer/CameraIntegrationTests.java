package renderer;

import geometries.Intersectable;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {

    private void testRayIntersections(Camera camera, Intersectable geometry, int expectedIntersections,
                                      int actualIntersections) {
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
