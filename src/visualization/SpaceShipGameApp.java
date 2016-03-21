package geom.visualization;

import geom.math.Vector;
import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.Event;
import processing.opengl.*;

import gifAnimation.*;
/**
 * Juego de naves espaciales contra rocas.
 */
public class SpaceShipGameApp extends PApplet {

  Gif starfield;
  PImage gameOver;
  Stage.Actor cat;
  int xCat = 0;
  int yCat = 0;
  final int X_SENS = 15;
  final int Y_SENS = 15;
  final int UP = 38;
  final int DOWN = 40;
  final int LEFT = 37;
  final int RIGH = 39;
  int STATUS = 0;
  final int SPEED = 20;
  final int WIDTH = 1024;
  final int HEIGHT = 768;
  final int ACCELERATION = 15;
  final int DENSITY = 2; //15%

  Stage st =  new Stage(WIDTH, HEIGHT, SPEED, ACCELERATION, DENSITY);

  /**
   * Se ejecuta al incio de la app.
   * Sirve para establecer configuraciones de la ventana de la app,
   * como el tamaño.
   * 
   */
  @Override
  public void settings() {
    size(1024, 768, OPENGL);
  }

  /**
   * Se ejecuta después de establecer las configuraciones de la app.
   * Sirve para incializar variables globales de la app.
   */
  @Override
  public void setup() {
    String path = SpaceShipGameApp.class.getResource("").getPath() + "data/";
    gameOver =  loadImage(path + "gameOver.png");
    starfield = new Gif(this, path +  "starfield.gif");
    starfield.play();
    Gif myCat = new Gif(this, path +  "cat.gif");
    PImage bullet = loadImage(path + "bullet.png");
    PImage rock = loadImage(path + "helado.png");
    myCat.play();
    cat = Stage.createShip((PImage)myCat);
    Stage.textures.put(Rol.CAT_BULLET, bullet);
    Stage.textures.put(Rol.ICECREAM_ROCK, rock);
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

    fill(255);
    textSize(40); 
    text("SCORE", width/2-200, 40);
    text(st.score, width/2, 40);

    if (st.status == Status.GAME_OVER) {
      image(gameOver, 0, 0, width, height);
      return;
    }

    image(starfield, 0, 0, width, height);
    image(cat.face, cat.x, cat.y, cat.w, cat.h);


    if (keyPressed && st.status == Status.PLAYING) {
      if (keyCode == UP) {
        cat.y = cat.y > 0 ? cat.y - Y_SENS : 0;
        cat.skeleton.traslate(0, -Y_SENS);
      } 
      else if (keyCode == DOWN) {
        cat.y = cat.y <= height-cat.h ? cat.y + Y_SENS : height;
        cat.skeleton.traslate(0,Y_SENS);
      }
      if (keyCode == LEFT) {
        cat.x = cat.x > 0 ? cat.x - X_SENS : 0;
        cat.skeleton.traslate(-X_SENS, 0);
      }
      else if (keyCode == RIGH) {
        cat.x = cat.x <= height ? cat.x + X_SENS : height;
        cat.skeleton.traslate(X_SENS, 0);
      }
      else if (keyCode == 1) 
        STATUS = 1;
      else if (key == ' ') 
        st.addActor(Stage.createBullet(frameCount, cat));
      else if (key == 'p' || key == 'P') {
        st.status = Status.PAUSE;
        Gif g = (Gif)(cat.face);
        g.pause();
        g = (Gif) starfield;
        g.pause();
      }
    } else if (keyPressed) {
      if (st.status == Status.PAUSE && (key == 'p' || key == 'P')) {
        st.status = Status.PLAYING;
        Gif g = (Gif)(cat.face);
        g.play();
        g = (Gif) starfield;
        g.play();

      }
    } 

    if (st.status == Status.PLAYING) {
      st.score++;

      if (frameCount % 23 == 0)
        st.generateRocks(frameCount);
    }

    try {
      for (Stage.Actor a : st.actors.values()) {
        if (st.status == Status.PLAYING)
          a.updateAge(frameCount);
        if (!a.inStage) {
          st.removeActor(a);
        } else {
          if (a.rol != Rol.ICECREAM_ROCK)
            image(a.face, a.x, a.y, a.w, a.h);
          else {
            drawRock(a);
            if (a.skeleton.hull.a1.x <= cat.skeleton.hull.a3.x) {
              if (cat.skeleton.intersects(a.skeleton)) {
                st.status = Status.GAME_OVER;
              }
            }
          }
        }
      }
    } catch (Exception e) {}
  }

  public void drawRock(Stage.Actor a) {
    int radius = 50;
    float angle = TWO_PI / 3;
    beginShape();
    texture(a.face);
    for (Vector v : a.skeleton.points)
      vertex((int)v.x, (int)v.y, 20,10);
    endShape(CLOSE);

  }
}