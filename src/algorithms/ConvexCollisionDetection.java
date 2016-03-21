package geom.algorithms;

import geom.structures.Polygon;
import geom.math.Vector;

/**
 * Detecta colisiones de polígonos convexos:
 *
 * - Entre dos polígonos convexos, detecta si hay intersección.
 * - Entre un polígono convexo y un punto, detecta si el punto está dentro
 * del polígono convexo.
 * 
 */
public class ConvexCollisionDetection {

  /**
   * Obtiene la intersección de dos polígonos convexos.
   * Si existe tal intersección la devuelve como un polígono,
   * en otro caso devuelve un nulo.
   *
   * Se puede detectar una colisión si es que la intersección obtenida no
   * es nula.
   * 
   * @param  polygonA primer polígono
   * @param  polygonB segundo polígono
   * @return          el polígono intersección, es nulo si no existe.
   */
  public Polygon detectCollision(Polygon a, Polygon b) {
    if (b.hull.a1.x <= a.hull.a3.x) 
      if (a.intersects(b)) 
        return new Polygon();
    return null;
    
  }

  /**
   * Detecta si un punto está colisionando con un polígono convexo, es decir,
   * si lo contiene.
   * 
   * @param  polygon  el polígono
   * @param  point    el punto
   * @return          si el punto está dentro o no del polígono
   */
  public boolean detectCollision(Polygon polygon, Vector point) {
    return false;
  }
}