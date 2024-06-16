package renderer;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for renderer.ImageWriter class
 */
class ImageWriterTest {

    /** Test method for {@link ImageWriter#writeToImage()} */
    @Test
    void writeToImage() {
        /**
         * In this test, a grid of 10x16 squares was built on the screen (ViewPlane) with a resolution of
         * 800 x 500 pixels , background color is yellow and the grid lines are red.
         */

        ImageWriter imageWriter = new ImageWriter("Grid 10x16", 800, 500);
        /** The number of columns of pixels */
        int Nx = imageWriter.getNx();
        /** The number of row of pixels */
        int Ny = imageWriter.getNy();

        /** The colors of the background and , grid lines by RGB */
        final Color YELLOW = new Color(255, 255, 0);
        final Color RED = new Color(255,0,0);

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                // 50 because 800/16 = 50 and 500/10 = 50 , so every 50 pixels we draw a grid line
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i,RED);
                } else {
                    // Color the background in yellow
                    imageWriter.writePixel(j, i, YELLOW);
                }
            }
        }

        // Produces an image file in png format
        imageWriter.writeToImage();
    }
}