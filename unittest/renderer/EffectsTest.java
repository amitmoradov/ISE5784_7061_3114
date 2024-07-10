package renderer;

import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

public class EffectsTest {

        /** A scene with ten different bodies that combines the material of the bodies, shining light, shadow, mirroring,
         *  and reflection */
        @Test
        public void allItems() {
            Scene scene = new Scene("Test scene");
            Camera.Builder cameraBuilder = Camera.getBuilder()
                    .setLocation(new Point(0, -300, 0))
                    .setVpDistance(200)
                    .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                    .setRayTracer(new SimpleRayTracer(scene));


            scene.geometries.add(
                    new Plane(new Point(0,0, -50), new Vector(0, 0, 1)).setEmission(new Color(200,125,50))
                            .setMaterial(new Material().setKd(0.5).setShininess(100)),
                    new Sphere(new Point(0, 100, 0), 50d).setEmission(new Color(YELLOW))
                            .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                    // Sphere with transparency
                    new Sphere(new Point(0, 100, 5), 50).setEmission(new Color(70,0,10))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                    new Sphere(new Point(120,-80,0),30).setEmission(new Color(BLACK))
                            .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(5)),

                    new Sphere(new Point(120, -80, 5), 30).setEmission(new Color(YELLOW))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                    new Sphere(new Point(-100,-100,0),25).setEmission(new Color(0,0,139))
                            .setMaterial(new Material().setKd(0.2).setKs(0.2).setKT(0.8).setShininess(100)),

                    new Sphere(new Point(-100, -100, 5), 25).setEmission(new Color(255,165,0))
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))

            );
            // Add a light sources
            scene.lights.add(new SpotLight(new Color(1000,600,600), new Point(-500, 250, 500),
                    new Vector(1, -1, 0)).setKl(0.0004).setKq(0.0000006));
//
            scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));

            cameraBuilder.setVpSize(300, 300)
                    .setImageWriter(new ImageWriter("allItemsPic", 600, 600))
                    .build()
                    .renderImage()
                    .writeToImage();
        }
}

