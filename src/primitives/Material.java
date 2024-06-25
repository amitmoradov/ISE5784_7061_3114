package primitives;

/**
 * A class representing Material properties.
 */
public class Material {
    /** The ambient coefficient of the material */
    public double kA;

    /** The diffuse coefficient of the material */
    public Double3 kD = Double3.ZERO;

    /** The specular coefficient of the material */
    public Double3 kS = Double3.ZERO;

    /** The shininess exponent of the material */
    public int shininess = 0;

    /**
     * Setter for the diffuse coefficient.
     *
     * @param kD The diffuse coefficient to set.
     * @return This Material object.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for the diffuse coefficient.
     *
     * @param kD The diffuse coefficient to set.
     * @return This Material object.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for the specular coefficient.
     *
     * @param kS The specular coefficient to set.
     * @return This Material object.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for the specular coefficient.
     *
     * @param kS The specular coefficient to set.
     * @return This Material object.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for the shininess exponent.
     *
     * @param shininess The shininess exponent to set.
     * @return This Material object.
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}