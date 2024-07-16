package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import renderer.*;
import lighting.*;
import geometries.*;

import static java.awt.Color.*;

public class MP1test {
    Scene scene = new Scene("Test scene");

    /**
     * Mega geometry combination test not including all features
     */
    @Test
    public void megaTestWithoutImproves() {
        Scene scene = new Scene("Mini project 1 test scene without improvements");
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, -400, 100))
                .setVpDistance(250)

                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));


        scene.geometries.add(

                new Plane(new Point(0, 0, -50), new Vector(0, 0, 1)).setEmission(new Color(200, 125, 50))
                        .setMaterial(new Material().setKd(0.5).setShininess(100)),

                new Sphere(new Point(23, 250, -20), 26).setEmission(new Color((BLACK)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.6).setShininess(100)),

                new Sphere(new Point(202, 270, -10), 26).setEmission(new Color(0, 78, 188))
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setKT(0.4).setShininess(100)),

                new Sphere(new Point(-130, 260, -16), 26).setEmission(new Color((GREEN)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.8).setShininess(100)),

                new Sphere(new Point(-240, 260, -16), 26).setEmission(new Color(165,42,42))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.8).setShininess(100)),


                new Sphere(new Point(290, 255, -10), 26).setEmission(new Color(0, 18, 188))
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setKR(0.8).setShininess(70)),

                new Polygon(
                        new Point(-190, 8, -150),
                        new Point(-190, 8, 150),
                        new Point(-120, 8, 150),
                        new Point(-120, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.06).setShininess(100)),
                new Polygon(
                        new Point(-100, 8, -150),
                        new Point(-100, 8, 150),
                        new Point(-30, 8, 150),
                        new Point(-30, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.1).setShininess(100)),

                new Polygon(
                        new Point(-10, 8, -150),
                        new Point(-10, 8, 150),
                        new Point(60, 8, 150),
                        new Point(60, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.2).setShininess(100)),

                new Polygon(
                        new Point(80, 8, -150),
                        new Point(80, 8, 150),
                        new Point(150, 8, 150),
                        new Point(150, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.2).setShininess(100))
        );
        // Add a light sources
        scene.lights.add(new SpotLight(new Color(1000, 600, 600), new Point(-250, 250, 500),
                new Vector(1, -1, 0)).setKl(0.004).setKq(0.0000006));


        // Add a point light with blue color
        scene.lights.add(new PointLight(new Color(0, 0, 255), new Point(0, 92, 148))
                .setKl(0.0004).setKq(0.0000006));

//
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));

        cameraBuilder.setVpSize(300, 300)
                .setImageWriter(new ImageWriter("glossy and blurry without improves", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

// ---------------------------------------------------------------------------------------------------------------
    /**
     * Mega geometry combination test including all features
     */
    @Test
    public void megaTest() {
        Scene scene = new Scene("Mini project 1 test scene");
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, -400, 100))
                .setVpDistance(250)

                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));


        scene.geometries.add(

                new Plane(new Point(0, 0, -50), new Vector(0, 0, 1)).setEmission(new Color(200, 125, 50))
                        .setMaterial(new Material().setKd(0.5).setShininess(100)),

                new Sphere(new Point(23, 250, -20), 26).setEmission(new Color((BLACK)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.6).setShininess(100)),

                new Sphere(new Point(202, 270, -10), 26).setEmission(new Color(0, 78, 188))
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setKT(0.4).setkG(12).setShininess(100)),

                new Sphere(new Point(-130, 260, -16), 26).setEmission(new Color((GREEN)))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.8).setShininess(100)),

                new Sphere(new Point(-240, 260, -16), 26).setEmission(new Color(165,42,42))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKR(0.8).setShininess(100)),


                new Sphere(new Point(290, 255, -10), 26).setEmission(new Color(0, 18, 188))
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setKR(0.8).setkG(11).setShininess(70)),

                new Polygon(
                        new Point(-190, 8, -150),
                        new Point(-190, 8, 150),
                        new Point(-120, 8, 150),
                        new Point(-120, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.06).setkB(0.03).setShininess(100)),
                new Polygon(
                        new Point(-100, 8, -150),
                        new Point(-100, 8, 150),
                        new Point(-30, 8, 150),
                        new Point(-30, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.1).setkB(2).setShininess(100)),

                new Polygon(
                        new Point(-10, 8, -150),
                        new Point(-10, 8, 150),
                        new Point(60, 8, 150),
                        new Point(60, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.2).setkB(7).setShininess(100)),

                new Polygon(
                        new Point(80, 8, -150),
                        new Point(80, 8, 150),
                        new Point(150, 8, 150),
                        new Point(150, 8, -150)
                ).setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.1).setKs(0.8).setKT(1.0).setKR(0.2).setkB(12).setShininess(100))
        );
        // Add a light sources
        scene.lights.add(new SpotLight(new Color(1000, 600, 600), new Point(-250, 250, 500),
                new Vector(1, -1, 0)).setKl(0.004).setKq(0.0000006));


        // Add a point light with blue color
        scene.lights.add(new PointLight(new Color(0, 0, 255), new Point(0, 92, 148))
                .setKl(0.0004).setKq(0.0000006));

//
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).setBackground(new Color(75, 120, 130));

        cameraBuilder.setVpSize(300, 300)
                .setImageWriter(new ImageWriter("glossy and blurry", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}