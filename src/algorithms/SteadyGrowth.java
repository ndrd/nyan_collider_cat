package geom.algorithms;

import geom.structures.Polygon;
import geom.math.Vector;
import java.util.LinkedList;
import java.util.Random;

/**
 * El algoritmo Steady Growth genera sirve para generar polígonos simples
 * aleatorios.
 *
 * Su complejidad es O(n^2).
 * 
 */
public class SteadyGrowth {


  /**
   * Genera un polígono simple aleatorio de n vértices.
   * 
   * @param  n el número de vértices
   * @return el polígono generado
   */
  public static Polygon generateRandomPolygon(int n) {
    LinkedList<Vector> v = new LinkedList<>();
    Random rnd = new Random();
    int x = 1000 + rnd.nextInt(30);
    int y = rnd.nextInt(768);
    int radius = 50;
    float angle = 360 / n;
    for (float i = 0; i < 360; i += angle) {
      double sx = x + Math.cos(i) * radius;
      double sy = y + Math.sin(i) * radius;
      v.addLast(new Vector(sx, sy));
    }
    return new Polygon(v);
  }
}