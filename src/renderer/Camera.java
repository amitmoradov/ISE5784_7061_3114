package renderer;

import jdk.javadoc.doclet.Taglet;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.xml.stream.Location;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera in three-dimensional space.
 * The camera is used to render images of the scene.
 */

public class Camera implements Cloneable {

    private TargetArea targetArea; // The target area for the camera
    private ImageWriter imageWriter; // The image writer used to write the rendered image
    private RayTracerBase rayTracer; // The ray tracer used to render the image
    private Point pCenter;

//    /**
//     * The width of the view plane.
//     */
//    private double viewPlaneWidth = 0.0;
//
//    /**
//     * The height of the view plane.
//     */
//    private double viewPlaneHeight = 0.0;
//
//    /**
//     * The distance from the camera to the view plane.
//     */
//    private double viewPlaneDistance = 0.0;


    /**
     * A builder class for constructing a Camera object.
     */
    private Camera(){}


    public TargetArea getTargetArea() {
        return targetArea;
    }

    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }


    /**
     * Builder class for Camera, implementing the Builder Pattern.
     */
    public static class Builder {
        /**
         * Represents a builder for constructing Camera objects.
         * This builder class allows for the creation of Camera objects with a fluent interface.
         */
        private final Camera camera;
        private final TargetArea.Builder targetAreaBuilder = TargetArea.getBuilder();

        /**
         * Private constructor for Builder.
         */
        private Builder() {
            camera = new Camera();
        }

        /**
         * Constructs a Builder object with the given Camera object.
         *
         * @param camera to initialize the Builder with
         */
        private Builder(Camera camera) {
            this.camera = camera;
        }

        public Builder setTargetArea(TargetArea targetArea) {
            if (targetArea == null) {
                throw new IllegalArgumentException("Target area cannot be null");
            }
            camera.targetArea = targetArea;
            return this;
        }


        /**
         * Sets the location of the target area.
         *
         * @param location The position to set for the target area.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided position is null.
         */
        public Builder setLocation(Point location) {
            // Validate the location
            if (location == null) {
                throw new IllegalArgumentException("Target area location cannot be null");
            }
            targetAreaBuilder.setLocation(location);
            return this;
        }

        /**
         * Sets the direction of the target area.
         *
         * @param vTo The direction to set for the target area.
         * @param vUp The up vector to set for the target area.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided direction is null or if the direction and up vector are not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Target area direction cannot be null");
            }

            // Check if the vectors are orthogonal
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("The vectors vTo and vUp are not orthogonal");
            }

            targetAreaBuilder.setDirection(vTo,vUp);
            return this;
        }

        /**
         * Set the view plane size
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the camera builder
         */
        public Builder setVpSize(double width, double height) {
            targetAreaBuilder.setVpSize(width, height);
            return this;
        }

        /**
         * Set the distance from the camera to the view plane
         *
         * @param distance the distance from the camera to the view plane
         * @return the camera builder
         */
        public Builder setVpDistance(double distance) {
            targetAreaBuilder.setVpDistance(distance);
            return this;
        }

        /**
         * Set the density of the view plane
         *
         * @param density the density of the view plane
         * @return the camera builder
         */
        public Builder setDensity(int density) {
            targetAreaBuilder.setDensity(density);
            return this;
        }

        /**
         * Sets the image writer to be used by the camera.
         *
         * @param imageWriter The image writer to be used by the camera.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided image writer is null.
         */
        public Builder setImageWriter(ImageWriter imageWriter){
            if (imageWriter == null)
            {
                throw new IllegalArgumentException("Image writer cannot be null");
            }

            this.camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer to be used by the camera.
         *
         * @param rayTracer The ray tracer to be used by the camera.
         * @return The current Builder object.
         */
        public Builder setRayTracer(RayTracerBase rayTracer){
            if (rayTracer == null)
            {
                throw new IllegalArgumentException("Ray tracer cannot be null");
            }

            this.camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Build the camera. In case of missing parameters, an exception will be thrown.
         *
         * @return the camera
         */
        public Camera build() {
            // check composed objects
            if (camera.imageWriter == null)
                throw new MissingResourceException("imageWriter is missing", "Camera", "");
            if (camera.rayTracer == null)
                throw new MissingResourceException("rayTracer is missing", "Camera", "");

            camera.targetArea = targetAreaBuilder.build();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException ignore) {
                return null;
            }
        }
    }
//====================================== END OF BUILDER CLASS =========================================================


    /**
     * Returns a new object of A Builder class
     */
    public static Builder getBuilder()
    {
        return new Builder();
    }


    /**
     * Renders the image by casting rays from the camera to the view plane.
     */
    public Camera renderImage(){
        // Get the number of pixels in the x and y direction
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        // Iterate over the pixels in the image
        for (int i = 0; i < Ny; i++){
            for (int j = 0; j < Nx; j++){
                castRay(Nx, Ny, j, i);
            }
        }

        return this;
    }

    /**
     * Print a grid on the view plane with a given interval and color.
     */
    public Camera printGrid(int interval, Color color){
        // For each pixel in the image writer , if the pixel is on the grid , color it with the given color , otherwise
        // color it with the background color
        for (int i = 0; i < imageWriter.getNy(); i++){
            for (int j = 0; j < imageWriter.getNx(); j++){
                if (i % interval == 0 || j % interval == 0){
                    imageWriter.writePixel(j, i, color);
                }
            }
        }

        return this;
    }

    /**
     * Writes the rendered image to a file by delegation to imageWriter class .
     */
    public void writeToImage(){
        if (imageWriter == null) {
            throw new IllegalStateException("Image writer is not set");
        }

        // Write the image to the file
        imageWriter.writeToImage();
    }

    /**
     * Construct a ray from the camera to a pixel
     *
     * @param nX size of webcam in X
     * @param nY size of webcam in Y
     * @param j  the x index of the pixel
     * @param i  the y index of the pixel
     * @return the ray from the camera to the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return targetArea.constructRay(nX, nY, j, i);
    }


    /**
     * Casts a ray from the camera to the view plane.
     *
     * @param Nx     The number of pixels in the x direction
     * @param Ny     The number of pixels in the y direction
     * @param column The column index of the pixel
     * @param row    The row index of the pixel
     */
     void castRay(int Nx, int Ny, int column, int row) {
        Ray ray = targetArea.constructRay(Nx, Ny, column, row);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(column, row, color);
    }
}

