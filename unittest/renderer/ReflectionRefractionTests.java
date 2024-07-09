/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
           .setDirection(new Vector(0, 0, -1), new Vector(0,1,0))
      .setRayTracer(new SimpleRayTracer(scene));

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKT(0.3)),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500),
                               new Vector(-1, -1, -2))
                          .setKl(0.0004).setKq(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKT(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setKR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150),
              new Vector(-1, -1, -4))
         .setKl(0.00001).setKq(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build()
         .renderImage()
         .writeToImage();
   }

   /** produce an image featuring four different shapes, lighting, shadows, reflections, and transparency */
   @Test
   public void ALLCombinations() {
      scene.setAmbientLight(new AmbientLight(new Color(BLUE), new Double3(0.20)));

      scene.geometries.add(
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkR(new Double3(0.8))),
              new Sphere(new Point(-60, 70, 40), 30d).setEmission(new Color(2, 200, 2))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKT(0.9)),
              new Sphere(new Point(50, -20, -100), 30d).setEmission(new Color(250, 140, 4))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30))
      );

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      // Strengthened Point Lights
      scene.lights.add(new PointLight(new Color(0, 255, 255), new Point(60, 50, 100))
              .setKl(0.00002).setKq(0.000005));

      scene.lights.add(new PointLight(new Color(255, 255, 180), new Point(-6, 24, -10))
              .setKl(0.00002).setKq(0.000005));

      scene.lights.add(new DirectionalLight(new Color(2, 6, 182), new Vector(-3, -3, -3)));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
              .setImageWriter(new ImageWriter("AllItems", 600, 600)).build().renderImage().writeToImage();
   }
}
