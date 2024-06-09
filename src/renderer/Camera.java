package renderer;

import jdk.javadoc.doclet.Taglet;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.xml.stream.Location;
import java.util.MissingResourceException;

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
                new IllegalArgumentException("Camera location cannot be null");
            }

            if (camera.vTo == null)
            {
                new IllegalArgumentException("Camera direction vTo cannot be null");
            }

            if (camera.vUp == null)
            {
                new IllegalArgumentException("Camera direction vUp cannot be null");
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

            // vRight = vTo x vUp
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
}

