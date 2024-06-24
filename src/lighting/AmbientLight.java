
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * A class representing Ambient Light.
 */
public class AmbientLight extends Light {

    /**
     * Represents an ambient light source with no ambient light effect. The ambient
     * light color is set to black (no contribution to ambient lighting), and the
     * ambient light intensity is set to zero. This constant can be used to
     * represent absence of ambient light in a scene.
     */
    public static final AmbientLight NONE = new AmbientLight(new Color(java.awt.Color.BLACK), Double3.ZERO);

    /**
     * Constructs an ambient light source with the specified intensity and ambient
     * reflection coefficient.
     *
     * @param intensity The color intensity of the ambient light.
     * @param KA        The ambient reflection coefficient (ambient reflectivity)
     *                  represented as a {@code Double3} vector. This coefficient
     *                  scales the intensity of the ambient light.
     */
    public AmbientLight(Color intensity, Double3 KA) {
        super(intensity.scale(KA));
    }

    /**
     * Constructs an ambient light source with the specified intensity and ambient
     * reflection coefficient.
     *
     * @param intensity The color intensity of the ambient light.
     * @param KA        The ambient reflection coefficient (ambient reflectivity)
     *                  represented as a scalar value. This coefficient scales the
     *                  intensity of the ambient light.
     */
    public AmbientLight(Color intensity, double KA) {
        super(intensity.scale(KA));
    }
}
