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
    public void Snow() {
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

                // Cipa
                new Sphere(new Point(0, 100, 126), 15).setEmission(new Color(116,  138,250))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                // Sphere to show left eye
                new Sphere(new Point(10, 80, 120), 3.5).setEmission(new Color((BLACK)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                // Sphere to show left eye
                new Sphere(new Point(-10, 80, 120), 3.5).setEmission(new Color((BLACK)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                // Sphere to show nose
                new Sphere(new Point(0, 80, 108), 6).setEmission(new Color((ORANGE))),

                // Add a small sphere to show the mouth
                new Sphere(new Point(0, 30, -20), 26).setEmission(new Color((BLACK)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.6).setShininess(100)),

                new Sphere(new Point(86, 50, -10), 26).setEmission(new Color(0,78,188))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKT(0.4).setShininess(100)),

                new Sphere(new Point(-82, 40, -16), 26).setEmission(new Color((GREEN)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.8).setShininess(100)),


                // Polygon to create a wall on the right side of the snowman
                new Polygon(
                        new Point(180, 250, -50),
                        new Point(180, 250, 150),
                        new Point(180, 0, 150),
                        new Point(180, 0, -50)
                ).setEmission(new Color(70, 130, 180))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKT(0.7)),

                // Polygon to create a wall on the left side of the snowman
                new Polygon(
                        new Point(-180, 250, -50),
                        new Point(-180, 250, 150),
                        new Point(-180, 0, 150),
                        new Point(-180, 0, -50)
                ).setEmission(new Color(70, 130, 180))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKT(0.7)),

                // Polygon to create a higher wall behind the snowman
                new Polygon(
                        new Point(-180, 300, 150),
                        new Point(180, 300, 150),
                        new Point(180, 0, 150),
                        new Point(-180, 0, 150)
                ).setEmission(new Color(70, 130, 180))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100).setKT(0.7))
        );
        // Add a light sources
        scene.lights.add(new SpotLight(new Color(1000,600,600), new Point(-500, 250, 500),
                new Vector(1, -1, 0)).setKl(0.0004).setKq(0.0000006));

        // Add a direction light
        scene.lights.add(new SpotLight(new Color(YELLOW), new Point(-80, 200, 150),
                new Vector(0, 1, 0)).setKl(0.0004).setKq(0.0000006));

//
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));

        cameraBuilder.setVpSize(300, 300)
                .setImageWriter(new ImageWriter("SnowMenAndAround", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}
