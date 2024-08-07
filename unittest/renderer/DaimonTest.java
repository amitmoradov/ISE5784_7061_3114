
package renderer;

import geometries.Polygon;
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

public class DaimonTest {

    private Scene scene = new Scene("Final Scene");

    @Test
    void createImage() {
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setLocation(new Point(0, 1000, 500))
                .setDirection(  new Vector(0, -1, 0), new Vector(0, 0, 1))
        .setVpDistance(250)
                .setMultithreading(5)
                .setVpSize(1000, 1000).setVpDistance(1000);
        scene.setBackground(new Color(BLACK));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        Point A = new Point(0, 0, 170);

        //vertices on the under side
        double num = 1.2;
        Point B1 = new Point(100, 0, 300).mult(num);
        Point B2 = new Point(50, 87, 300).mult(num);
        Point B3 = new Point(-50, 87, 300).mult(num);
        Point B4 = new Point(-100, 0, 300).mult(num);
        Point B5 = new Point(-50, -87, 300).mult(num);
        Point B6 = new Point(50, -87, 300).mult(num);
        //mid vertices on the underside
        Point B1B2 = new Point(86, 49, 300).mult(num);
        Point B2B3 = new Point(0, 100, 300).mult(num);
        Point B3B4 = new Point(-86, 49, 300).mult(num);
        Point B4B5 = new Point(-86, -49, 300).mult(num);
        Point B5B6 = new Point(0, -100, 300).mult(num);
        Point B6B1 = new Point(86, -49, 300).mult(num);
        //vertices on the upper side
        Point C1 = new Point(50, 0, 350).mult(num);
        Point C2 = new Point(25, 43, 350).mult(num);
        Point C3 = new Point(-25, 43, 350).mult(num);
        Point C4 = new Point(-50, 0, 350).mult(num);
        Point C5 = new Point(-25, -43, 350).mult(num);
        Point C6 = new Point(25, -43, 350).mult(num);
        //mid vertices on the upper side
        Point C1C2 = new Point(47, 27, 350).mult(num);
        Point C2C3 = new Point(0, 47, 350).mult(num);
        Point C3C4 = new Point(-47, 27, 350).mult(num);
        Point C4C5 = new Point(-47, -27, 350).mult(num);
        Point C5C6 = new Point(0, -47, 350).mult(num);
        Point C6C1 = new Point(47, -27, 350).mult(num);
        //colors of Diamonds
        java.awt.Color colorDiamond1 = red;
        java.awt.Color colorDiamond2 = pink;

        //The red Diamond
        scene.geometries.add(
                //triangles on the bottom side
                new Triangle(A, B1, B1B2).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B2, B1B2).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(A, B2, B2B3).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.2).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B3, B2B3).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(A, B3, B3B4).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.2).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B4, B3B4).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B5, B4B5).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(A, B5, B5B6).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.2).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B6, B5B6).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(A, B6, B6B1).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.2).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(A, B1, B6B1).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.8).setKs(0.8).setKd(0.2).setShininess(1000)),

                //triangles on the top side
                new Triangle(B1, C1, B1B2).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(C1, B1B2, C2).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(B1B2, C2, B2).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(B2, C2, B2B3).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(C2, B2B3, C3).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(B2B3, C3, B3).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(B3, C3, B3B4).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(C3, B3B4, C4).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(B3B4, C4, B4).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(B4, C4, B4B5).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(C4, B4B5, C5).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(B4B5, C5, B5).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(B5, C5, B5B6).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(C5, B5B6, C6).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),
                new Triangle(B5B6, C6, B6).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(1000)),

                new Triangle(B6, C6, B6B1).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(100)),
                new Triangle(C6, B6B1, C1).setEmission(new Color(colorDiamond2)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(100)),
                new Triangle(B6B1, C1, B1).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.6).setKs(0.8).setKd(0.2).setShininess(100)),
                new Polygon(C1, C2, C3, C4, C5).setEmission(new Color(colorDiamond1)).setMaterial(new Material().setKR(0.4).setKT(0.2).setKs(0.8).setKd(0.2).setShininess(100))
        );

        // The Square
        scene.geometries.add(new Polygon(new Point(0, -300, 170), new Point(300, 0, 170), new Point(0, 300, 170), new Point(-300, 0, 170))
                        .setEmission(new Color(79, 118, 131))
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setKR(0.2).setKT(0.4).setShininess(10)),
                new Polygon(new Point(0, -300, 170), new Point(300, 0, 170), new Point(300, 0, 700), new Point(0, -300, 700))
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setKR(0.2).setKT(0.4).setShininess(10)),
                new Polygon(new Point(0, -300, 170), new Point(0, -300, 700), new Point(-300, 0, 700), new Point(-300, 0, 170))
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setKR(0.2).setKT(0.4).setShininess(10)));



        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(
                new SpotLight(new Color(83, 122, 195), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(new PointLight(new Color(83, 122, 195), new Point(-100, -400, 800)).setKq(0.000001));

        scene.geometries.makeBVH();

        cameraBuilder.setImageWriter(new ImageWriter("FinalDiamond - MP2 - without ", 1000, 1000)) //
                .setRayTracer(new SimpleRayTracer(scene))
                .build()//
                .renderImage() //
                .writeToImage();

    }
}
