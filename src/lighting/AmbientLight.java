package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient light is a type of light that fills in the shadows created by other light sources.
 * It is a constant light that shines on all objects in the scene.
 * @author Amit , Yinon
 */
public class AmbientLight {

    /** The fill light bone field */
    private final Color intensity;

    /** The default fill light BLACK */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    // ***************** Constructors ********************** //

    /**
     * Constructor for AmbientLight
     * @param IA Color of the Fill light by RGB
     * @param KA The Fill light attenuation coefficient
     */
    public AmbientLight(Color IA , Double3 KA) {
        this.intensity = IA.scale(KA);
    }

    /**
     * Constructor for AmbientLight
     * @param IA Color of the Fill light by RGB
     * @param KA The attenuation coefficient
     */
    public AmbientLight(Color IA, double KA) {
        this.intensity = IA.scale(KA);
    }

    // ***************** Operations ******************** //

    /**
     * Getter for the fill light intensity
     * @return the fill light intensity
     */
    public Color getIntensity() {
        return intensity;
    }

}
