package geom.visualization;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Juego de naves espaciales contra rocas.
 */
public class SpaceShipGameApp extends PApplet {

  PImage starfield;
  PImage cat;


  /**
   * Se ejecuta al incio de la app.
   * Sirve para establecer configuraciones de la ventana de la app,
   * como el tamaño.
   * 
   */
  @Override
  public void settings() {
    size(1024, 768);
    String path = "data/";
    starfield = loadImage(path +  "starfied.jpg");
    cat = loadImage(path +  "cat.gif");
  }

  /**
   * Se ejecuta después de establecer las configuraciones de la app.
   * Sirve para incializar variables globales de la app.
   */
  @Override
  public void setup() {
  }

  /**
   * Se ejecuta de acuerdo al frameRate establecido.
   * Por default el frameRate es 60, es decir, esta función
   * se ejecuta 60 veces por segundo (60fps).
   *
   * En ella deben estar todos las llamadas a funciones de dibujo.
   */
  @Override
  public void draw() {
    background(0);
    hint(DISABLE_DEPTH_MASK);
    image(starfield, 0, 0, width, height);
    image(cat, 0, 0, 150, 100);
    hint(ENABLE_DEPTH_MASK);

  }
}