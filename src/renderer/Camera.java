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

    private Point p0; // The camera's location
    private Vector vTo; // The vector pointing towards the view plane
    private Vector vUp; // The vector pointing upwards
    private Vector vRight; // The vector pointing to the right

    double width = 0.0; // The width of the view plane
    double height = 0.0; // The height of the view plane
    double distance = 0.0; // The distance between the camera and the view plane

    private ImageWriter imageWriter; // The image writer used to write the rendered image
    private RayTracerBase rayTracer; // The ray tracer used to render the image

    Point getP0() {
        return p0;
    }

    Vector getVTo() {
        return vTo;
    }

    Vector getVUp() {
        return vUp;
    }

    Vector getVRight() {
        return vRight;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    double getDistance() {
        return distance;
    }

    /**
     * A builder class for constructing a Camera object.
     */
    private Camera(){

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

        /**
         * Sets the location of the camera.
         *
         * @param location The position to set for the camera.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided position is null.
         */
        public Builder setLocation(Point location){
            if (location == null)
            {
                throw new IllegalArgumentException("Camera location cannot be null");
            }

            this.camera.p0 = location;
            return this;
        }

        /**
         * Sets the direction of the camera.
         *
         * @param vTo The direction to set for the camera.
         * @param vUp The up vector to set for the camera.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided direction is null or if the direction and up vector are
         * not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp){
            if (vTo == null || vUp == null)
            {
                throw new IllegalArgumentException("Camera direction cannot be null");
            }

            // Check if the vectors are orthogonal
            if (!isZero(vTo.dotProduct(vUp)))
            {
                throw new IllegalArgumentException("The vectors vTo , and vUp are not orthogonal");
            }

            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width The width of the view plane.
         * @param height The height of the view plane.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided width or height is not positive.
         */

        public Builder setVpSize(double width, double height){
            if (width <= 0 || height <= 0)
            {
                throw new IllegalArgumentException("The width and height of the view plane must be positive");
            }

            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the view plane.
         *
         * @param distance The distance to set between the camera and the view plane.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided distance is not positive.
         */
        public Builder setVpDistance(double distance){
            if (distance <= 0)
            {
                throw new IllegalArgumentException("The distance between the camera and the view" +
                        " plane must be positive");
            }

            this.camera.distance = distance;
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
         * Builds a Camera object with the specified parameters.
         *
         * @return A new Camera object with the specified parameters.
         * @throws MissingResourceException if any of the required parameters are missing.
         */
        public Camera build(){

            // Const string for missing data to be used in the exception
            final String missingData = "Missing rendering data";

            if (camera.p0 == null)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "location");
            }

            if (camera.vTo == null)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "direction");
            }

            if (camera.vUp == null)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "up vector");
            }

            // Vright calculation by cross product of vTo and vUp , therefore we not check if vRight is null

            if (camera.width <= 0.0)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "width");
            }

            if (camera.height <= 0.0)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "height");
            }

            if (camera.distance <= 0.0)
            {
                throw new MissingResourceException(missingData, Camera.class.getName(), "distance");
            }

            // Validate the values of the fields
            if (alignZero(camera.width) <= 0)
                throw new IllegalStateException("The width must be positive");

            if (camera.height <= 0)
                throw new IllegalStateException("The height must be positive");

            if (camera.distance <= 0)
                throw new IllegalStateException("dThe distance must be positive");

            if (camera.imageWriter == null){
                throw new MissingResourceException(missingData, Camera.class.getName(), "image writer");
            }

            if (camera.rayTracer == null){
                throw new MissingResourceException(missingData, Camera.class.getName(), "ray tracer");
            }

            // vRight = vTo x vUp

            // Check if the vector right is zero
            if (!isZero(camera.vTo.dotProduct(camera.vUp)))
                throw new IllegalArgumentException("Direction vectors must be orthogonal");

            this.camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            return (Camera) camera.clone();
        }

    }
//====================================== END OF BUILDER CLASS =========================================================

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    /**
     * Returns a new object of A Builder class
     */
    public static Builder getBuilder()
    {
        return new Builder();
    }


    /**
     * Creating a ray through the center of the pixel
     * @param nx The number of pixels in the x direction
     * @param ny The number of pixels in the y direction
     * @param j  The x index of the pixel
     * @param i  The y index of the pixel
     * @return
     */
    public Ray constructRay(int nx, int ny, int j, int i){
        // pc = p0 + d * vto
        Point Pc = p0.add(vTo.scale(distance));

        double Ry = height / ny;
        double Rx = width / nx;

        double Yi = -(i -((ny - 1) / 2.0)) * Ry;
        double Xj = (j -((nx - 1) / 2.0)) * Rx;

        Point Pij = Pc;

        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));

        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));


        // Return a ray in direction of Pij - p0
        Vector Vij = Pij.subtract(p0).normalize();
        return new Ray(p0, Vij);

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

        imageWriter.writeToImage();
    }

    /**
     * Casts a ray from the camera to the view plane.
     * @param Nx The number of pixels in the x direction
     * @param Ny The number of pixels in the y direction
     * @param column The column index of the pixel
     * @param row The row index of the pixel
     */
    private void castRay(int Nx, int Ny, int column, int row){
        /* Construct a ray from the camera to the view plane */
        Ray ray = constructRay(Nx, Ny, column, row);

        Color color = rayTracer.traceRay(ray);
        // Write the color to the image writer
        imageWriter.writePixel(column, row, color);
    }
}

