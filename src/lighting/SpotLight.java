package lighting;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

import static primitives.Util.alignZero;


public class SpotLight extends PointLight implements LightSource{

    /**
     * the direction vector.
     */
    private Vector direction;


    @Override
    public PointLight setKC(double kC) {
        return (SpotLight) super.setKC(kC);
    }


    @Override
    public PointLight setKL(double kL) {
        return (SpotLight) super.setKL(kL);
    }


    @Override
    public PointLight setKQ(double kQ) {
        return (SpotLight)super.setKQ(kQ);

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
    public SpotLight(Color intensity, Point position , Vector direction) {
        super(intensity,position);
        this.direction = direction.normalize();
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p); // Returns the direction from the point to the light source
    }

    @Override
    public Color getIntensity(Point point) {
        return null;
    }

}
