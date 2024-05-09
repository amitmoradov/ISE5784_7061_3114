package primitives;

/**
 * A ray represents a half-straight line in three-dimensional space, starting from a point (the head) and extending in an infinite direction. */
public class Ray {
    private final Point head; // The starting point of the ray
    private final Vector direction; // The direction of the ray

    /**
     * Constructs a new Ray with the specified starting point (head) and direction.
     * @param head The starting point of the ray.
     * @param direction The direction of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize(); // Normalizing the direction vector to ensure it represents a unit vector.
    }

    /**
     * Checks if this ray is equal to another object.
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
    }

    /**
     * Returns a string representation of this ray.
     * @return A string representation of this ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
