package geom.visualization;

import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.Event;
import gifAnimation.*;
/**
 * Juego de naves espaciales contra rocas.
 */
public class SpaceShipGameApp extends PApplet {

  Gif starfield;
  Stage.Actor cat;
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
  final int WIDTH = 1024;
  final int HEIGHT = 768;
  final int ACCELERATION = 15;

  Stage st =  new Stage(WIDTH, HEIGHT, SPEED, ACCELERATION);

  /**
   * Se ejecuta al incio de la app.
   * Sirve para establecer configuraciones de la ventana de la app,
   * como el tamaño.
   * 
   */
  @Override
  public void settings() {
    size(1024, 768);
  }

  /**
   * Se ejecuta después de establecer las configuraciones de la app.
   * Sirve para incializar variables globales de la app.
   */
  @Override
  public void setup() {
    String path = SpaceShipGameApp.class.getResource("").getPath() + "data/";
    starfield = new Gif(this, path +  "starfield.gif");
    starfield.play();

    /* creamos la nave */
    Gif myCat = new Gif(this, path +  "nyan.gif");
    myCat.play();
    cat = Stage.createShip((PImage)myCat);
    cat.w = 150;
    cat.h = 100;
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
    hint(DISABLE_DEPTH_MASK);
    image(starfield, 0, 0, width, height);
    image(cat.face, cat.x, cat.y, cat.w, cat.h);


    if (keyPressed) {
      if (keyCode == UP)
        cat.y = cat.y > 0 ? cat.y - Y_SENS : 0;
      else if (keyCode == DOWN)
        cat.y = cat.y <= height-cat.h ? cat.y + Y_SENS : height;
      if (keyCode == LEFT)
        cat.x = cat.x > 0 ? cat.x - X_SENS : 0;
      else if (keyCode == RIGH)
        cat.x = cat.x <= height ? cat.x + X_SENS : height;
      else if (keyCode == 1) 
        STATUS = 1;
      else if (keyCode == 0) {
        st.addActor(Stage.createBullet(frameCount, cat));
      }
    }

    if (frameCount % 2 == 0) {
      if (st.actors.size() > 0)
        try {
          for (Stage.Actor a : st.actors.values()) {
            a.updateAge(frameCount);
            if (!a.inStage)
              st.removeActor(a);
          }
        } catch (Exception e) {}
    }


  }


}