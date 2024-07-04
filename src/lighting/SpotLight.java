package lighting;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A class representing a spotlight.
 * Inherits from PointLight and implements LightSource.
 */
public class SpotLight extends PointLight implements LightSource {

    /**
     * the direction vector.
     */
    private Vector direction;

    /**
     * the angle of the beam.
     * 1 - wide beam
     */
    private int narrowBeam = 1;


    @Override
    public PointLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }


    @Override
    public PointLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }


    @Override
    public PointLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);

    }



    /**
     * Constructs a spotlight with the specified intensity, position, and direction.
     *
     * @param intensity The color intensity of the spotlight.
     * @param position  The position of the spotlight in 3D space.
     * @param direction The direction vector indicating the direction in which the
     *                  spotlight is pointing. This vector will be normalized
     *                  internally.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p); // Returns the direction from the point to the light source
    }

    /**
     * Returns The narrow beam of the spotlight.
     *
     * @return The narrow beam of the spotlight.
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * Get the intensity of the light at a given point.
     *
     * @param point the point at which to calculate the intensity
     * @return the intensity of the light at point
     */
    @Override
    public Color getIntensity(Point point) {
        // Calculate the dot product between the light direction and the direction from the point to the light source
        double cos = alignZero(direction.dotProduct(getL(point)));

        // Check if the narrow beam effect is applied (narrowBeam is not equal to 1)
        return narrowBeam != 1
                // If narrow beam is applied, scale the intensity by the dot product raised to the power of narrowBeam
                ? super.getIntensity(point).scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam))
                // If narrow beam is not applied, scale the intensity by the dot product
                : super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));
    }

}
