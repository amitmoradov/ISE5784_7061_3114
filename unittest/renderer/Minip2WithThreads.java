package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.*;

public class Minip2WithThreads {
    Scene scene = new Scene("Test scene");

    /**
     * Mega geometry combination test including all features
     */
    @Test
    public void megaTest1() {
        Camera.Builder cameraBuilder = Camera.getBuilder().
                setLocation(new Point(0, 0, 1000)).setVpSize(200, 200).setVpDistance(1000)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setMultithreading(100)
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(
                new Sphere(new Point(-50, 40, 40), 50)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKR(0.3)// .setKt(0.5 5)
                                .setkG(10)),
                new Sphere(new Point(60, 0, 20), 35d)
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4).setKT(0.2))
                        .setEmission(new Color(252, 148, 3)),
                new Sphere(new Point(-40, -40, 10), 25d)
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0.2))
                        .setEmission(new Color(252, 3, 252)),

                //Background
                new Polygon(new Point(-251, -251, -150), new Point(251, -251, -150), new Point(251, 251, -150),
                        new Point(-251, 251, -150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4)),

                //חייב
                new Triangle(new Point(-10, 20, 100), new Point(100, 10, 100), new Point(50, 100, 100))
                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKT(0.6).setkB(15))
                        .setEmission(new Color(250, 0, 0)));


        scene.geometries.add( //
                new Triangle(new Point(-160, -160, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)), //
                new Triangle(new Point(-160, -160, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));//

        scene.lights.add(new SpotLight(new Color(200, 400, 200), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        cameraBuilder //
                .setRayTracer(new SimpleRayTracer(scene)) //
                .setImageWriter(new ImageWriter("MPCombination", 800, 800))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    public void mega1Test2() {
        Camera.Builder cameraBuilder = Camera.getBuilder().setLocation(new Point(0, 0, 1000)).setVpSize(200, 200).setVpDistance(1000)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(
                new Sphere(new Point(-50, 40, 40), 50)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKR(0.3)// .setKt(0.5 5)
                        ),
                new Sphere(new Point(60, 0, 20), 35d)
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4).setKT(0.2))
                        .setEmission(new Color(252, 148, 3)),
                new Sphere(new Point(-40, -40, 10), 25d)
                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0.2))
                        .setEmission(new Color(252, 3, 252)),

                //Background
                new Polygon(new Point(-251, -251, -150), new Point(251, -251, -150), new Point(251, 251, -150),
                        new Point(-251, 251, -150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4)),

                //חייב
                new Triangle(new Point(-10, 20, 100), new Point(100, 10, 100), new Point(50, 100, 100))
                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKT(0.6))
                        .setEmission(new Color(250, 0, 0)));


        scene.geometries.add( //
                new Triangle(new Point(-160, -160, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)), //
                new Triangle(new Point(-160, -160, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));//

        scene.lights.add(new SpotLight(new Color(200, 400, 200), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        cameraBuilder //
                .setRayTracer(new SimpleRayTracer(scene)) //
                .setImageWriter(new ImageWriter("MP1WithoutKGKB", 800, 800))
                .build()
                .renderImage()
                .writeToImage();
    }


    /**
     * Mega geometry combination test including all features
     */
    @Test
    public void megaTest3() {
        Scene scene = new Scene("Mini project 1 test scene");
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, -400, 100))
                .setVpDistance(250)
                .setMultithreading(5)

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
