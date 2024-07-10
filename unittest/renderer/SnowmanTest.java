package renderer;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

public class SnowmanTest {
    /** A scene with ten different bodies that combines the material of the bodies, shining light, shadow, mirroring,
     *  and reflection */
    @Test
    public void allItems() {
        Scene scene = new Scene("Test scene");
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, -200, 100))
                .setVpDistance(200)

                .setDirection(new Vector(0, 1,0), new Vector( 0, 0,1))
                .setRayTracer(new SimpleRayTracer(scene));


        scene.geometries.add(

                new Plane(new Point(0,0, -50), new Vector(0, 0, 1)).setEmission(new Color(200,125,50))
                        .setMaterial(new Material().setKd(0.5).setShininess(100)),

                new Sphere(new Point(0, 100, 5), 50).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                new Sphere(new Point(0,100,70),35).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),

                new Sphere(new Point(0, 100, 110), 25).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                // Sphere to show left eye
                new Sphere(new Point(10, 80, 120), 3.5).setEmission(new Color((BLACK)))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                // Sphere to show left eye
                new Sphere(new Point(-10, 80, 120), 3.5).setEmission(new Color((BLACK)))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                new Triangle(
                        new Point(2, 80, 120),
                        new Point(8, 80, 124),
                        new Point(5, 60, 120)).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))

        );
         // Add a light sources
        scene.lights.add(new SpotLight(new Color(1000,600,600), new Point(-500, 250, 500),
                        new Vector(1, -1, 0)).setKl(0.0004).setKq(0.0000006));
//
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));

        cameraBuilder.setVpSize(300, 300)
                .setImageWriter(new ImageWriter("allItemsP", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }


}
