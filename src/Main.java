package geom;

import processing.core.PApplet;
import geom.visualization.SpaceShipGameApp;

/**
 *  Clase principal.
 *  
 */
public class Main {

  /**
   * Punto de entrada al programa.
   * 
   * @param args Argumentos de consola
   */
  public static void main(String[] args) {

    // Crea una instancia de la app y la inicia
    SpaceShipGameApp app = new SpaceShipGameApp();

    String[] sketchArgs = { "" };
    PApplet.runSketch(sketchArgs, app);
  }
}
