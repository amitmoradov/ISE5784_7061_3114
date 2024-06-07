package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    // Private constructor
    /*
    private Camera(Point location, Vector vTo, Vector vUp, double viewPlaneWidth, double viewPlaneHeight, double distance) {
        this.p0 = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
        this.width = viewPlaneWidth;
        this.distance = distance;
        this.height = viewPlaneHeight;

    }
    */
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
        private Builder setLocation(Point location){
            if (location == null)
            {
                throw new IllegalArgumentException("Camera location cannot be null");
            }
            this.camera.p0 = location;
            return this;
        }

        private Builder setDirection(Vector direction){}


    }


    /**
     * Returns a new object of A Builder class
     */
    public Builder getBuilder()
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
    public Ray construcRay(int nx, int ny, int j, int i){
         return null;
    }
}

