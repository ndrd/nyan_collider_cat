  package geom.structures;
  import geom.math.Vector;
  import java.util.LinkedList;

  /**
  * El rectángulo que contiene al polígono
  */
  public class Bounds {
    public Vector a1;
    public Vector a2;
    public Vector a3;
    public Vector a4;
    public LinkedList<Vector> points; 

    Bounds(Vector a1, Vector a2, Vector a3, Vector a4) {
      this.a1 = a1;
      this.a2 = a2;
      this.a3 = a3;
      this.a4 = a4;
      points = new LinkedList<>();
      points.addLast(a1);
      points.addLast(a2);
      points.addLast(a3);
      points.addLast(a4);

    }
  }
