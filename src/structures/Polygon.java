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

  public LinkedList<Vector> points;
  PriorityQueue<Vector> orderedPoints;
  public Bounds hull;
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
      points = (LinkedList<Vector>)pts.clone();
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
    if (point == null)
      return;

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
    if (isCounterClockwise())
      return points;
    
    Vector tmp;
    int len = points.size();
    for (int i = 1; i < len; ++i) {
        tmp = points.get(i);
        /* intercambiamos los puntos */
        points.set(i, points.get(len-i));
        points.set(len-i, tmp);
    }
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
    if (isCounterClockwise())
      return points;
    
    Vector tmp;
    int len = points.size();
    for (int i = len-1; i > 0; --i) {
        tmp = points.get(i);
        /* intercambiamos los puntos */
        points.set(i, points.get(len-i));
        points.set(len-i, tmp);
    }
    return points;
  }

  /**
  * Podemos determinar si nuestros vértices ya estan en el orden
  * por convención usando el siguiente truco:
  * http://mathworld.wolfram.com/PolygonArea.html
  */
  private boolean isCounterClockwise () {
    double area = 0;
    int size = points.size();
    for (int i = 0; i < size; ++i) {
        area += (points.get(i+1%size).x - points.get(i).x) *
                (points.get(i+1%size).y - points.get(i).y);
    }
    return area < 0;
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
    LinkedList<Vector> p = getVertexes();
    boolean inside = true;
    for (int i = 0; i < p.size(); i += 2) {
      /* todos deben estar a la derecha */
      inside &= Vector.areaSign(point, p.get(i), p.get(i+1%p.size())) > 0;
    }
    return inside;
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
    return !(hull.a3.x < h1.a1.x || h1.a3.x < hull.a1.x || hull.a4.y < h1.a3.y || h1.a4.y < hull.a3.y);
  }

  public void traslate(double dx, double dy) {
    for (Vector v : points) {
      v.x += dx;
      v.y += dy;
    }
    for (Vector v : hull.points) {
      v.x += dx;
      v.y += dy;
    }
  }

  public Vector getCentroid() {
    return new Vector((hull.a3.x + hull.a1.x) * 0.5 ,(hull.a1.y + hull.a2.y) * 0.5);
  } 

  public Polygon getConvexHull() {
    return null;
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
