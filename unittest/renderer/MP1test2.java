package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import renderer.*;
import lighting.*;
import geometries.*;

public class MP1test2 {
    Scene scene = new Scene("Test scene");

    /**
     * Mega geometry combination test including all features
     */
    @Test
    public void megaTest() {
        Camera.Builder cameraBuilder = Camera.getBuilder().setLocation(new Point(0, 0, 1000)).setVpSize(200, 200).setVpDistance(1000)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setRayTracer(new SimpleRayTracer(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add(
                new Sphere(new Point(-80, 10, -50), 50)
                        .setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKR(0.3)// .setKt(0.5 5)
                                .setkG(10)),
                new Sphere(new Point(60, 0, 20), 35d)
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKR(0.4).setKT(0.2))
                        .setEmission(new Color(252, 148, 3)),
                new Sphere(new Point(-15, -40, 10), 25d)
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

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        cameraBuilder //
                .setRayTracer(new SimpleRayTracer(scene)) //
                .setImageWriter(new ImageWriter("MPCombination", 800, 800))
                .build()
                .renderImage()
                .writeToImage();
    }

}
