package geom.algorithms;

import geom.structures.Polygon;
import geom.math.Vector;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Arrays;

/**
 * El algoritmo Steady Growth genera sirve para generar polígonos simples
 * aleatorios.
 *
 * Su complejidad es O(n^2).
 * 
 */
public class SteadyGrowth {
  static Random rnd;

  static {
    rnd = new Random();
  }

  /**
   * Genera un polígono simple aleatorio de n vértices.
   * 
   * @param  n el número de vértices
   * @return el polígono generado
   */
  public static Polygon generateRandomPolygon(int n) {
    /* Genera un conjunto s de n puntos  distribuidos uniformemente */
    Vector [] nn = (Vector.randomPoints(n, 700+SteadyGrowth.rnd.nextInt(100), 780+ SteadyGrowth.rnd.nextInt(150)));

    LinkedList<Vector> v =  new LinkedList<>();
    for (Vector o : nn) {
        v.add(o);
    }
    return new Polygon(v).getConvexHull();   
  }

}