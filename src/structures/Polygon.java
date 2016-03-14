package geom.structures;

import java.util.LinkedList;
import geom.math.Vector;

/**
 * Representa un polígono simple en dos dimensiones, como una sucesión
 * de puntos (vectores de dos dimensiones).
 *
 */
public class Polygon {

  /**
   * Construye un polígono sin puntos.
   *
   */
  public Polygon() {
  }

  /**
   * Construye el polígono dada una lista de puntos.
   *
   * @param points Los puntos que representarán al polígono.
   */
  public Polygon(LinkedList<Vector> points) {
  }

  /**
   * Agrega un nuevo punto al final de la sucesión del polígono.
   *
   * @param point El nuevo punto en el polígono.
   */
  public void add(Vector point) {
  }

  /**
   * Devuelve una lista con los vértices del polígono en el orden
   * por convención (contrario a las manecillas del reloj).
   *
   * @return LinkedList<Vector> La lista de vértices
   */
  public LinkedList<Vector> getVertexes() {
    return null;
  }

  /**
   * Devuelve una lista con los vértices del polígono en orden
   * a favor de las manecillas del reloj (lo opuesto a la convencion).
   * NOTA: el vertice inicial no cambia
   *
   * @return LinkedList<Vector> La lista de vértices
   */
  public LinkedList<Vector> getClockwiseVertexes() {
    return null;
  }

  /**
   * Verifica si el polígono es convexo o no.
   *
   * @return boolean Si el polígono es convexo.
   */
  public boolean isConvex() {
    return false;
  }

  /**
   * Verifica si un punto está dentro o sobre el polígono.
   * NOTA: Para esta práctica, este método supone que el polígono
   * es convexo.
   *
   * @param point El punto a verificar.
   * @return boolean Si el punto esta contenido o no.
   */
  public boolean containsPoint(Vector point) {
    return false;
  }

  /**
   * Limpia el polígono.
   *
   */
  public void clear() {
  }

  /**
   * Regresa el número de vértices del polígono.
   *
   * @return int El número de vértices
   */
  public int size() {
    return -1;
  }
}
