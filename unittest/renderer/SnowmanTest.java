package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.PointLight;
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
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.geometries.add(

                new Plane(new Point(0,0, -50), new Vector(0, 0, 1)).setEmission(new Color(200,125,50))
                        .setMaterial(new Material().setKd(0.5).setShininess(100)),

                // Snowman spheres
                new Sphere(new Point(0, 100, 5), 50).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                new Sphere(new Point(0, 100, 70), 35).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),

                new Sphere(new Point(0, 100, 110), 25).setEmission(new Color(180, 180, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                // Eyes
                new Sphere(new Point(8, 80, 120), 3.7).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                new Sphere(new Point(-8, 80, 120), 3.7).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                // Left mirror
                new Polygon(
                        new Point(-100, 50, -100),
                        new Point(-100, 50, 200),
                        new Point(-100, 150, 200),
                        new Point(-100, 150, -100)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000)),

                // Right mirror
                new Polygon(
                        new Point(100, 50, -100),
                        new Point(100, 50, 200),
                        new Point(100, 150, 200),
                        new Point(100, 150, -100)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000)),

                // Back-left mirror
                new Polygon(
                        new Point(-100, 50, 200),
                        new Point(-50, 50, 300),
                        new Point(-50, 150, 300),
                        new Point(-100, 150, 200)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000)),

                // Back-right mirror
                new Polygon(
                        new Point(100, 50, 200),
                        new Point(50, 50, 300),
                        new Point(50, 150, 300),
                        new Point(100, 150, 200)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000)),

                // Back-middle-left mirror
                new Polygon(
                        new Point(-50, 50, 300),
                        new Point(0, 50, 350),
                        new Point(0, 150, 350),
                        new Point(-50, 150, 300)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000)),

                // Back-middle-right mirror
                new Polygon(
                        new Point(50, 50, 300),
                        new Point(0, 50, 350),
                        new Point(0, 150, 350),
                        new Point(50, 150, 300)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.1)
                                .setKs(1.0)
                                .setKR(1.0)
                                .setShininess(1000))
        );

        // Add light sources
        scene.lights.add(new PointLight(new Color(1000, 600, 600), new Point(-500, 200, 700))
                .setKl(0.0004).setKq(0.0000006));

        scene.lights.add(new SpotLight(new Color(1000, 600, 600), new Point(0, -200, 100),
                new Vector(1, -1, 0)).setKl(0.0004).setKq(0.0000006));

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));


        cameraBuilder.setVpSize(300, 300)
                .setImageWriter(new ImageWriter("allItemsP", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}
