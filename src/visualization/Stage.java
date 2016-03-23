 package geom.visualization;

import geom.structures.Polygon;
import geom.math.Vector;
import geom.algorithms.*;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PApplet;
import processing.event.Event;

 
/**
 * Representa el escenario del videojuego y crea el mapeo entre poligonos y
 * objetos que se dibujan dentro del canvas.
 */
public class Stage {
	static int width;
	static int height;
	static int speed;
	int aceleration;
	int score;
	int level;
	String background;
	HashMap<Integer, Actor> actors;
	static HashMap<Rol, PImage> textures;
	Status status;
	private static String path;
	int rocks;
	int bullet;
	int density;
	static int nactors;

	static Random rnd;

	PImage bg;

	static {
		path = SpaceShipGameApp.class.getResource("").getPath() + "data/";
		textures =  new HashMap<>();
		rnd =  new Random();
		nactors = 0;
	}
	/**
	 * Representa un agente dentro del videojuego
	 * cada actor tiene asociada una imagen, un poligono y un tamaño
	 * así podemos generar el mapeo entre los poligonos y sus colisiones
	 * y lo que se muestra dentro de la pantalla
	 */
	static class Actor {
		Rol rol;
		Polygon skeleton;
		PImage face;
		boolean inStage;
		boolean rTl;
		int birth;
		//centroides
		int x;
		int y;
		int w;
		int h;
		int id;

		Actor(Rol r, Polygon p, boolean inStg, boolean rtl, int b) {
			rol = r;
			skeleton = p;
			face = face;
			inStage = inStg;
			rTl = rtl;
			birth = b;
			if (r == Rol.ICECREAM_ROCK) {
				x = 120;
				y = 300; //nyan
			}
			y = 300;
		}

		/**
		* Calcula la nueva posición del objeto de acuerdo a su tipo y al frame actual 
		*/
		void updateAge(int frame) {
			int speedI = (int)((rol == Rol.ICECREAM_ROCK) ? speed /4 : speed);
			int deltaX = (rTl) ? -1 * speedI : 1 * speedI;
			int deltaY = 0;
			x += deltaX;
			inStage = rTl ? x > 0 : x < width;
			skeleton.traslate(deltaX, deltaY);
		}
	} 

	public Stage (int w, int h, int s, int a, int d) {
		width = w;
		height = h;
		speed = s;
		aceleration = a;
		actors =  new HashMap<>();
		score = 0;
		level = 0;
		status = Status.PLAYING;
		density = d;
		rocks = 1;
		bullet = 0;
	}

	/**
	* Agrega un nuevo actor al escenario 
	*/
	public void addActor(Actor a) {
		actors.put(a.hashCode(), a);
	}

	/**
	* Elimina un actor del diccionario de actores (para mantener O(n))
	*/
	public void removeActor(Actor a) {
		if (a.rol == Rol.ICECREAM_ROCK)
			rocks--;
		else if (a.rol == Rol.CAT_BULLET)
			bullet = 0;
		actors.remove(a.hashCode());
	}

	public void addTexture(Rol r, PImage face) {
		textures.put(r, face);
	}

	/**
	* Genera las rocas dentro del escenario, de acuerdo a las reglas
	* indicadas en el juegp 
	*/
	public void generateRocks(int age) {
		float e = actors.values().size();
		e = e > 0 ? e : 1;
		if (rocks/e < density) {
			addActor(Stage.createSpaceRock(age));
			rocks++;
		}
	}

	/**
	* Crea una bala dentro del escenario, se vincula a la posición
	* de la nave que la esta lanzando
	*/
	public static Actor createBullet(int birth, Actor sender) {
		Vector x1 =  new Vector(sender.x-150, sender.y);
		Vector x2 =  new Vector(sender.x-150, sender.y+100);
		Vector x3 =  new Vector(sender.x, sender.y+100);
		Vector x4 =  new Vector(sender.x, sender.y);
		LinkedList<Vector> vectors = new LinkedList<>();
		vectors.addLast(x1);
		vectors.addLast(x2);
		vectors.addLast(x3);
		vectors.addLast(x4);
		Polygon p =  new Polygon(vectors);
		Actor b = new Actor(Rol.CAT_BULLET, p, true, false, birth);
		b.x = sender.x;
		b.y = sender.y+10;
		b.w = 80;
		b.h = 48;
		b.face = Stage.textures.get(Rol.CAT_BULLET);
		b.id = Stage.nactors++;
		return b;
	}

	/**
	* Crea las rocas del juego usando un generador de rocas aleatorio basado
	* en el algoritmo SteadyGrowth
	*/
	public static Actor createSpaceRock(int birth) {
		Polygon p = null;
		while (p == null)
			p = SteadyGrowth.generateRandomPolygon(5 + Stage.rnd.nextInt(50));
		Vector xx =  p.hull.a1;
		Vector centroid  = p.getCentroid();
		p.traslate(width-xx.x, -Stage.rnd.nextInt(height/2));

		Actor b = new Actor(Rol.ICECREAM_ROCK, p, true, true, birth);
		b.x = (int) centroid.x;
		b.y = (int) centroid.y;
		b.face = Stage.textures.get(Rol.ICECREAM_ROCK);
		b.id = Stage.nactors++;
		return b;
	}

	/**
	* Crea la nave del juego
	*/
	public static Actor createShip(PImage image) {
		Vector x1 =  new Vector(0, Stage.height / 2);
		Vector x2 =  new Vector(0, Stage.height / 2 + 100);
		Vector x3 =  new Vector(150, Stage.height / 2 + 100);
		Vector x4 =  new Vector(150, Stage.height / 2);
		LinkedList<Vector> vectors = new LinkedList<>();
		vectors.addLast(x1);
		vectors.addLast(x2);
		vectors.addLast(x3);
		vectors.addLast(x4);
		Polygon p =  new Polygon(vectors);
		Actor cat = new Actor(Rol.CAT_SHIP, p, true, false, 0);
		cat.face = image;
		cat.x = 0;
		cat.y = Stage.height / 2;
		return cat;
	}
}

enum Rol {
    CAT_SHIP,
    CAT_BULLET, 
    ICECREAM_ROCK
};  

enum Status {
	PAUSE,
	GAME_OVER,
	PLAYING,
	FINISH
}



