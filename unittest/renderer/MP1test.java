package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import renderer.*;
import lighting.*;
import geometries.*;

public class MP1test {
    Scene scene = new Scene("Test scene");

    /**
     * Mega geometry combination test including all features
     */
    @Test
    public void megaTest() {
        Camera.Builder cameraBuilder = Camera.getBuilder().setLocation(new Point(0,-600,100)).
                setVpSize(200,200).setVpDistance(200)
                .setDirection(new Vector(0, 1, 0),new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15))
                .setBackground(new Color(75, 120, 130));

        scene.geometries.add(
//                new Sphere( new Point(-80, 10, -50),50)
//                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKR(0.5)// .setKt(0.55)
//                                .setkG(10)),
//                new Sphere( new Point(60, 0, 0),35d)
//                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4).setKT(0.2))
//                        .setEmission(new Color(252, 148, 3)),
//                new Sphere(new Point(-15, -40, 10),25d)
//                        .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKR(0.7).setKT(0.2))
//                        .setEmission(new Color(252, 3, 252)),

                new Polygon(new Point(-250, 10, -150),
                        new Point(-250, 10, 200),
                        new Point(-100, 10, 200),
                        new Point(-100, 10, -150))
                        .setEmission(Color.BLACK)
                        .setMaterial(new Material().setKd(0.0).setKs(1.0).setShininess(100).setKR(1.0).setkG(10)),

                new Polygon(new Point(-50, 10, -150),
                        new Point(-50, 10, 200),
                        new Point(100, 10, 200),
                        new Point(100, 10, -150))
                        .setEmission(Color.BLACK)
                        .setMaterial(new Material().setKd(0.0).setKs(1.0).setShininess(60).setKR(1.0).setkG(10).setkB(0.4))
                );




//                new Polygon(new Point(-80, 60, -60), new Point(80, 60, -60), new Point(80, 100, 10),
//                        new Point(-80, 100, 10)).setMaterial(new Material().setKd(0.6).setKR(0.4))
//                        .setEmission(new Color(3, 250, 190)),
//
//                new Triangle(new Point(-10, 20, 100), new Point(100, 10, 100), new Point(50, 100, 100))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKT(0.6).setkB(5))
//                        .setEmission(new Color(250, 70, 0)),
//
//                new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20, 70, 0))
//                        .setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKT(0.6))
//                        .setEmission(new Color(java.awt.Color.RED)));
//
//        scene.geometries.add( //
//                new Triangle(new Point(-160, -160, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//                new Triangle(new Point(-160, -160, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//                new Sphere( new Point(60, 50, -50),30d).setEmission(new Color(java.awt.Color.BLUE)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.6)));


        scene.lights.add(new SpotLight(new Color(1000, 600, 600), new Point(-500, 10, 500),
                new Vector(1, -1, 0)).setKl(0.0004).setKq(0.0000006));

        scene.lights.add(new PointLight(new Color(java.awt.Color.WHITE), new Point(0, -20, 148))
                .setKl(0.0004).setKq(0.0000006));


        cameraBuilder //
                .setRayTracer(new SimpleRayTracer(scene)) //
                .setImageWriter(new ImageWriter("MPCombination", 800, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}