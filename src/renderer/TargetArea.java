package renderer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a target area in three-dimensional space.
 * The target area is used to define a specific area in the scene.
 */
public class TargetArea {

    private Point p0; // The target area's location
    private Vector vTo; // The vector pointing towards the view plane
    private Vector vUp; // The vector pointing upwards
    private Vector vRight; // The vector pointing to the right
    private Point pCenter; // The center point of the view plane


    double width = 0.0; // The width of the view plane
    double height = 0.0; // The height of the view plane
    double distance = 0.0; // The distance between the target area and the view plane

    int density = 9; // The density of the grid

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

    double getDensity() {
        return density;
    }

    /**
     * A builder class for constructing a TargetArea object.
     */
    public TargetArea() {
    }

    /**
     * Builder class for TargetArea, implementing the Builder Pattern.
     */
    public static class Builder {
        /**
         * Represents a builder for constructing TargetArea objects.
         * This builder class allows for the creation of TargetArea objects with a fluent interface.
         */
        private final TargetArea targetArea = new TargetArea();

        /**
         * Private constructor for Builder.
         */
        private Builder() {
        }

        /**
         * Constructor for TargetArea class
         *
         * @param ray  the main ray to the target area
         * @param size the size of the target area
         */
        public Builder(Ray ray, double size) {
            setDirection(ray);
            targetArea.height = targetArea.width = size;
            targetArea.distance = 100;
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
            this.targetArea.p0 = location;
            return this;
        }

        /**
         * Set the TargetArea direction according to the ray
         *
         * @param ray the TargetArea direction
         * @return the TargetArea builder
         */
        public Builder setDirection(Ray ray) {
            targetArea.p0 = ray.getHead();
            targetArea.vTo = ray.getDirection();
            targetArea.vUp = targetArea.vTo.makePerpendicularVector();
            targetArea.vRight = targetArea.vUp.crossProduct(targetArea.vTo);
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
            // Validate the direction and up vector
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Target area direction cannot be null");
            }

            // Check if the vectors are orthogonal
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("The vectors vTo and vUp are not orthogonal");
            }

            this.targetArea.vTo = vTo.normalize();
            this.targetArea.vUp = vUp.normalize();
            this.targetArea.vRight = vTo.crossProduct(vUp).normalize();
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
        public Builder setSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("The width and height of the view plane must be positive");
            }
            this.targetArea.width = width;
            this.targetArea.height = height;
            return this;
        }

        /**
         * Sets the distance between the target area and the view plane.
         *
         * @param distance The distance to set between the target area and the view plane.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided distance is not positive.
         */
        public Builder setDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("The distance between the target area and the view plane" +
                        " must be positive");
            }
            this.targetArea.distance = distance;
            return this;
        }

        /**
         * Set the view plane size
         *
         * @param width  the width of the view plane
         * @param height the height of the view plane
         * @return the TargetArea builder
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0)
                throw new IllegalArgumentException("Width cannot be equal or smaller than 0");
            if (height <= 0)
                throw new IllegalArgumentException("Height cannot be equal or smaller than 0");
            targetArea.width = alignZero(width);
            targetArea.height = alignZero(height);
            return this;
        }

        /**
         * Set the distance from the TargetArea to the view plane
         *
         * @param distance the distance from the TargetArea to the view plane
         * @return the TargetArea builder
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("Distance cannot be equal or smaller than 0");
            targetArea.distance = alignZero(distance);
            return this;
        }

        /**
         * Sets the density of the grid in the target area.
         *
         * @param density The density to set for the target area.
         * @return The current Builder object.
         * @throws IllegalArgumentException if the provided density is not positive.
         */
        public Builder setDensity(int density) {
            if (density <= 0) {
                throw new IllegalArgumentException("The density must be positive");
            }
            this.targetArea.density = density;
            return this;
        }

        /**
         * Sets the vectors and location based on a given ray.
         * @param ray The ray to base the vectors and location on.
         * @return The current Builder object.
         */
        public Builder setByRay(Ray ray) {
            if (ray == null) {
                throw new IllegalArgumentException("Ray cannot be null");
            }
            this.targetArea.p0 = ray.getHead();
            this.targetArea.vTo = ray.getDirection().normalize();

            // Assuming the up vector is along the Y-axis
            Vector up = new Vector(0, 1, 0);

            // If the direction vector is collinear with the Y-axis, use the Z-axis as up vector
            if (isZero(this.targetArea.vTo.dotProduct(up))) {
                up = new Vector(0, 0, 1);
            }

            this.targetArea.vUp = up.normalize();
            this.targetArea.vRight = this.targetArea.vTo.crossProduct(this.targetArea.vUp).normalize();
            return this;
        }

        /**
         * Builds a TargetArea object with the specified parameters.
         *
         * @return A new TargetArea object with the specified parameters.
         * @throws MissingResourceException if any of the required parameters are missing.
         */
        public TargetArea build() {
            // Const string for missing data to be used in the exception
            final String missingData = "Missing rendering data";

            if (targetArea.p0 == null) {
                throw new MissingResourceException(missingData, TargetArea.class.getName(), "location");
            }

            if (targetArea.vTo == null) {
                throw new MissingResourceException(missingData, TargetArea.class.getName(), "direction");
            }

            if (targetArea.vUp == null) {
                throw new MissingResourceException(missingData, TargetArea.class.getName(), "up vector");
            }

            // Validate the values of the fields
            if (alignZero(targetArea.width) <= 0) {
                throw new IllegalStateException("The width must be positive");
            }

            if (targetArea.height <= 0) {
                throw new IllegalStateException("The height must be positive");
            }

            if (targetArea.distance <= 0) {
                throw new IllegalStateException("The distance must be positive");
            }

            if (targetArea.density <= 0) {
                throw new IllegalStateException("The density must be positive");
            }

            // vRight = vTo x vUp
            if (!isZero(targetArea.vTo.dotProduct(targetArea.vUp))) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }

            this.targetArea.vRight = targetArea.vTo.crossProduct(targetArea.vUp).normalize();
            return this.targetArea;
        }
    }

    /**
     * Returns a new Builder object for TargetArea.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Get a TargetArea builder
     *
     * @param ray  the main ray to the target area
     * @param size the size of the target area
     * @return a new target area builder
     */
    public static Builder getBuilder(Ray ray, double size) {
        return new Builder(ray, size);
    }



    /**
     * Constructs a ray through the center of the pixel.
     *
     * @param nx The number of pixels in the x direction
     * @param ny The number of pixels in the y direction
     * @param j  The x index of the pixel
     * @param i  The y index of the pixel
     * @return The constructed ray
     */
    public Ray constructRay(int nx, int ny, int j, int i) {
        // Calculate the center point of the view plane
        Point Pc = p0.add(vTo.scale(distance));


        double Ry = height / ny;
        double Rx = width / nx;

        // Calculate the coordinates of the pixel
        double Yi = -(i - (ny - 1) / 2.0) * Ry;
        double Xj = (j - (nx - 1) / 2.0) * Rx;

        Point Pij = Pc;

        if (Xj != 0)

            Pij = Pij.add(vRight.scale(Xj));

        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));

        // Calculate the direction vector of the ray
        Vector Vij = Pij.subtract(p0).normalize();
        return new Ray(p0, Vij);
    }

    /**
     * Constructs a grid of rays in the target area
     *
     * @return list of rays
     */
    public List<Ray> constructRayGrid() {
        // Create a list to store the rays
        List<Ray> rays = new LinkedList<>();

        // Loop through the pixels in the target area
        for (int i = 0; i < density; ++i)
            for (int j = 0; j < density; j++)

                // Construct a ray for the current pixel and add it to the list
                rays.add(constructRay(density, density, j, i));
        return rays;
    }

}
