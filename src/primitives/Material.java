package primitives;

/**
 * A class representing Material properties.
 */
public class Material {
    /** The ambient coefficient of the material */
    public double kA;

    /** The diffuse coefficient of the material
     * dispersion of light to all directions */

    public Double3 kD = Double3.ZERO;

    /** The specular coefficient of the material
     * mirror reflection */
    public Double3 kS = Double3.ZERO;

    /**
     * The transparency coefficient of the material. Initialized to ZERO3.Double.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material. Initialized to ZERO3.Double.
     */
    public Double3 kR = Double3.ZERO;

    /** The shininess exponent of the material */
    public int shininess = 0;

    /**Glossy*/
    public double kG = 0;

    /** טישטוש*/
    public double kB = 0;


    public Material setkB(double kB) {
        this.kB = kB;
        return this;
    }

    public Material setkG(double Glossy) {
        this.kG = Glossy;
        return this;
    }

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

    /**
     * Setter for the transparency coefficient.
     * double KT
     * @param kT The transparency coefficient to set.
     * @return This Material object.
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for the transparency coefficient.
     * Double3 KT
     * @param kT The transparency coefficient to set.
     * @return This Material object.
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for the reflection coefficient.
     * double KR
     * @param kR The reflection coefficient to set.
     * @return This Material object.
     */
    public Material setKR(double kR) {
        this.kR =new Double3(kR);
        return this;
    }

    /**
     * Setter for the reflection coefficient.
     * Double3 KR
     * @param kR The reflection coefficient to set.
     * @return This Material object.
     */
    public Material setkR(Double3 kR){
        this.kR = kR;
        return this;
    }
}