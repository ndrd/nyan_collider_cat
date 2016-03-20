package geom.visualization;

import geom.structures.Polygon;
import geom.math.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PApplet;
import processing.event.Event;

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

	PImage bg;

	static {
		path = SpaceShipGameApp.class.getResource("").getPath() + "data/";
		textures =  new HashMap<>();
	}

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

		void updateAge(int frame) {
			// calcula la velocidad de acuerdo al escenario 
			int delta = frame - birth;
			// y = 300; calcular de donde viene y
			x += (rTl) ? -1 * speed : 1 * speed;
			inStage = rTl ? x > 0 : x < width;
		}
	} 

	public Stage (int w, int h, int s, int a) {
		width = w;
		height = h;
		speed = s;
		aceleration = a;
		actors =  new HashMap<>();
		score = 0;
		level = 0;
		status = Status.PLAYING;
	}

	public void setBackground(String path) {
		background = path;
	}

	public void addActor(Actor a) {
		actors.put(a.hashCode(), a);
	}

	public void removeActor(Actor a) {
		actors.remove(a.hashCode());
	}

	public void addTexture(Rol r, PImage face) {
		textures.put(r, face);
	}

	public static Actor createBullet(int birth, Actor sender) {
		System.out.println(birth);
		Vector x1 =  new Vector(sender.x-80, sender.y);
		Vector x2 =  new Vector(sender.x, sender.y);
		Vector x3 =  new Vector(sender.x, sender.y-48);
		Vector x4 =  new Vector(sender.x-80, sender.y-48);
		LinkedList<Vector> vectors = new LinkedList<>();
		vectors.addLast(x1);
		vectors.addLast(x2);
		vectors.addLast(x3);
		vectors.addLast(x4);
		Polygon p =  new Polygon(vectors);
		Actor b = new Actor(Rol.CAT_BULLET, p, true, false, birth);
		b.x = sender.x;
		b.y = sender.y-40;
		b.w = 80;
		b.h = 48;
		b.face = Stage.textures.get(Rol.CAT_BULLET);
		return b;
	}

	public static Actor createShip(PImage image) {
		Polygon p =  new Polygon();
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



