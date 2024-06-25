package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    /**
     * scene name
     */
    public String name;

    /**
     * background color, initialized to black
     */
    public Color background = Color.BLACK;

    /**
     * scene's ambient light, initiated to NONE (black)
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * scene geometries, initialized to none geometries
     */
    public Geometries geometries = new Geometries();

    /** The lights in the scene */
    public List<LightSource> lights = new LinkedList<>();
    /**
     * constructor for scene's name
     *
     * @param name the name
     */
    public Scene(String name) {
        this.name = name;
    }


    /**
     * setter for scene's background color
     *
     * @param color the color
     * @return this
     */
    public Scene setBackground(Color color){
        this.background = color;
        return this;
    }

    /**
     * setter for scene's ambient lighting
     *
     * @param ambientLight the light
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight){
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter for scene's geometries set
     *
     * @param geometries the geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries){
        this.geometries = geometries;
        return this;
    }

    /**
     * Updates the lights in the scene.
     *
     * @param lights The new list of lights.
     * @return This Scene object.
     */
    public Scene setLights(List<LightSource> lights){
        this.lights = lights;
        return this;
    }


}
