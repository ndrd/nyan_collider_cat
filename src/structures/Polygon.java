package geom.structures;

import java.util.LinkedList;
import java.util.PriorityQueue;
import geom.math.Vector;
import java.util.Comparator;

/**
 * Representa un polígono simple en dos dimensiones, como una sucesión
 * de puntos (vectores de dos dimensiones).
 *
 */
public class Polygon {

  /**
  * El rectángulo que contiene al polígono
  */
  class Bounds {
    Vector a1;
    Vector a2;
    Vector a3;
    Vector a4;

    Bounds(Vector a1, Vector a2, Vector a3, Vector a4) {
      this.a1 = a1;
      this.a2 = a2;
      this.a3 = a3;
      this.a4 = a4;
    }
  }

  LinkedList<Vector> points;
  PriorityQueue<Vector> orderedPoints;
  Bounds hull;
  /**
   * Construye un polígono sin puntos.
   *
   */
  public Polygon() {
    this(new LinkedList<Vector>());
  }

  /**
   * Construye el polígono dada una lista de puntos.
   *
   * @param points Los puntos que representarán al polígono.
   */
  public Polygon(LinkedList<Vector> pts) {
      orderedPoints = new PriorityQueue<Vector>(3, new Comparator<Vector>(){
        public int compare(Vector a, Vector b) {
          return (int)((a.x - b.x) != 0 ? a.x - b.x : a.y - b.y);
        }
      });
      for (Vector v : pts) {
        orderedPoints.offer(v);
      }
      points = pts;
      hull = getBounds();
  }

  private Bounds getBounds() {
    double minX = Integer.MAX_VALUE;
    double minY = Integer.MAX_VALUE;
    double maxX = Integer.MIN_VALUE;
    double maxY = Integer.MIN_VALUE;

    for (Vector o : points) {
      if (o.x < minX)
        minX = o.x;
      if (o.x > maxX)
        maxX = o.x;
      if (o.y < minY)
        minY = o.y;
      if (o.y > maxY)
        maxY = o.y;
    }
    return new Bounds(
      new Vector(minX, maxY),
      new Vector(minX, minY),
      new Vector(maxX, minY),
      new Vector(maxX, maxY)
    );

  }

  /**
   * Agrega un nuevo punto al final de la sucesión del polígono.
   *
   * @param point El nuevo punto en el polígono.
   */
  public void add(Vector point) {
    points.addLast(point);
    if (point.x < hull.a1.x || point.y < hull.a2.y || point.x > hull.a3.x || point.y > hull.a1.y)
      hull = getBounds();
  }

  /**
   * Devuelve una lista con los vértices del polígono en el orden
   * por convención (contrario a las manecillas del reloj).
   *
   * @return LinkedList<Vector> La lista de vértices
   */
  public LinkedList<Vector> getVertexes() {
    return points;
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
   * Verifica si un polígono se intersecta con él comparando los
   * vértices extremos
   *
   * @param Polygon El polígono a verificar.
   * @return boolean Si el polígono se intersecta o no.
   */
  public boolean intersects(Polygon b) {
    Bounds h1 = b.hull;
    return !(hull.a3.x < h1.a1.x || h1.a4.x < hull.a1.x || hull.a3.y < h1.a4.y || h1.a3.y < hull.a4.y);
  }

  public void traslate(double dx, double dy) {
    for (Vector v : points) {
      v.x += dx;
      v.y += dy;
    }
  }

  public Vector getCentroid() {
    return new Vector((hull.a3.x + hull.a1.x) * 0.5 ,(hull.a1.y + hull.a2.y) * 0.5);
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
    return points.size();
  }
}
