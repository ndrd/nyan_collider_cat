package geom.visualization;

import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.Event;

/**
 * Juego de naves espaciales contra rocas.
 */
public class SpaceShipGameApp extends PApplet {

  PImage starfield;
  PImage cat;
  int xCat = 0;
  int yCat = 0;
  final int X_SENS = 60;
  final int Y_SENS = 60;
  final int UP = 38;
  final int DOWN = 40;
  final int LEFT = 37;
  final int RIGH = 39;
  int STATUS = 0;
  final int SPEED = 30;
  Stage st =  new Stage();



  /**
   * Se ejecuta al incio de la app.
   * Sirve para establecer configuraciones de la ventana de la app,
   * como el tamaño.
   * 
   */
  @Override
  public void settings() {
    size(1024, 768);
        
    String path = SpaceShipGameApp.class.getResource("").getPath() + "data/";
    File file = new File(path);
    System.out.println(file.getAbsolutePath());

    starfield = loadImage(path +  "starfield.jpg");
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

    if (keyPressed) {
      if (keyCode == UP)
        yCat = yCat > 0 ? yCat - Y_SENS : 0;
      else if (keyCode == DOWN)
        yCat = yCat <= height ? yCat + Y_SENS : height;
      if (keyCode == LEFT)
        xCat = xCat > 0 ? xCat - X_SENS : 0;
      else if (keyCode == RIGH)
        xCat = xCat <= height ? xCat + X_SENS : height;
      else if (keyCode == 1) 
        STATUS = 1;
      else if (keyCode == 0)
        System.out.println("fire");
    }

    hint(DISABLE_DEPTH_MASK);
    image(starfield, 0, 0, width, height);
    image(cat, xCat, yCat, 150, 100);

  }


}