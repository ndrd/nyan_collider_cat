package geom.math;

import java.util.Random;

/**
 * Representa un vector en dos dimensiones, contiene operaciones básicas
 * de vectores.
 *
 */
public class Vector {

  // Coordenadas
  public double x, y;

  /**
   * Construye el vector en el origen.
   *
   */
  public Vector() {
    this.x = this.y = 0;
  }

  /**
   * Construye un vector dadas sus coordenadas
   *
   * @param x La coordenada x del vector.
   * @param y La coordenada y del vector.
   */
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Suma este vector con otro.
   *
   * @param v El otro vector.
   */
  public void add(Vector v) {
    this.x = this.x + v.x;
    this.y = this.y + v.y;
  }

  /**
   * Calcula la suma de dos vectores y lo regresa
   * como un vector nuevo.
   *
   * @param u El primer vector.
   * @param v El segundo vector.
   * @return Vector El vector suma (u + v).
   */
  public static Vector add(Vector u, Vector v) {
    return new Vector(u.x + v.x, u.y + v.y);
  }

  /**
   * Resta este vector con otro.
   *
   * @param v El otro vector.
   */
  public void sub(Vector v) {
    this.x = this.x - v.x;
    this.y = this.y - v.y;
  }

  /**
   * Calcula la resta de dos vectores y lo regresa
   * como un vector nuevo.
   *
   * @param u El primer vector.
   * @param v El segundo vector.
   * @return Vector El vector resta (u - v).
   */
  public static Vector sub(Vector u, Vector v) {
    return new Vector(u.x - v.x, u.y - v.y);
  }

  /**
   * Multiplicación escalar.
   */
  public void mult(double a) {
    this.x *= a;
    this.y *= a;
  }

  /**
   * Multiplicación escalar.
   */
  public static Vector mult(Vector u, double a) {
    return new Vector(u.x * a, u.y * a);
  }

  /**
   * Regresa la magnitud del vector, es decir, su longitud.
   *
   * @return double La magnitud del vector
   */
  public double mag() {
    return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0));
  }

  /**
   * Normaliza la norma del vector para que mida 1
   * (hace al vector unitario).
   */
  public void normalize() {
    double mag = mag();
    if(mag != 0.0f) {
      double lenInv = 1.0f / mag;

      this.x *= lenInv;
      this.y *= lenInv;
    }
  }

  /**
   * Calcula el producto punto del vector dado
   * con este vector.
   *
   * @param v El vector a operar
   * @return double El resultado del producto punto
   */
  public double dot(Vector v) {
    return (this.x * v.x) + (this.y * v.y);
  }

  /**
   * Calcula el signo del área del paralelogramo de los
   * vectores a-b, a-c.
   * Sirve para determinar si c está del lado izquierdo
   * o derecho del segmento dirigido de a a b.
   * 
   * @param a Primer vector
   * @param b Segundo vector
   * @param c Tercer vector
   * @return double signo del determinante
   */
  public static double areaSign(Vector a, Vector b, Vector c) {
    // Transladamos los puntos para que d quede en el origen
    double ax = a.x - c.x, ay = a.y - c.y;
    double bx = b.x - c.x, by = b.y - c.y;

    // Calculamos el area
    double area = (ax * by) - (ay * bx);
    if (area < 0.0) {
      return -1.0;
    }
    else if (area > 0.0) {
      return 1.0;
    }
    else {
      return 0.0;
    }
  }

  /**
   * Evalua si tres puntos en el plano son colineales
   * 
   * @param a Primer punto 
   * @param b Segundo punto
   * @param c Tercer punto
   * @return boolean si son colineales
   */
  public static boolean areCollinear(Vector a, Vector b, Vector c) {
    double areaSign = Vector.areaSign(a, b, c);
    return areaSign == 0.0;
  }

  /**
   * Genera n puntos aleatorios en un rectangulo.
   * 
   * @param  n     numero de puntos
   * @param  start coordenada inicial
   * @param  end   coordenada final
   * @return       un arreglo con n puntos generados
   */
  public static Vector[] randomPoints (int n, double start, double end) {

    Random random = new Random();
    double range = end - start;

    Vector[] points = new Vector[n];
    int idx = 0;
    int mt = 0;
    while(idx < n) {

      // Genera un nuevo vector
      double x = Math.round((random.nextDouble() * range) + start);
      double y = Math.round((random.nextDouble() * range) + start);
      Vector p = new Vector(x, y);

      if(idx < 2) {

        // Lo guardamos
        points[idx] = p;
        idx++;
      }
      else {

       // Checamos que no haya tres colineales
        boolean areCollinear = false;
        for (int i = 0; i < idx; i++) {
          for (int j = 0; j < idx; j++) {
            if(i != j) {
              boolean test = Vector.areCollinear(points[i], points[j], p);
              areCollinear = areCollinear || test;
            }
          }
        }

        // No hay colineales
        if(!areCollinear || mt > 50) {
          // Lo guardamos
          points[idx] = p;
          idx++;
        } else {
          mt++;
        }
      }
    }
    return points;
  }
  @Override
  public boolean equals(Object obj) {
    Vector vec = (Vector) obj;
    return this.x == vec.x && this.y == vec.y;
  }

  @Override
  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }
}