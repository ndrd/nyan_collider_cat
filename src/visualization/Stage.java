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
	}

	public void setBackground(String path) {
		background = path;
	}

	public void addActor(Actor a) {
		actors.put(a.hashCode(), a);
	}

	public void removeActor(Actor a) {
		if (a.rol == Rol.ICECREAM_ROCK)
			rocks--;
		actors.remove(a.hashCode());
	}

	public void addTexture(Rol r, PImage face) {
		textures.put(r, face);
	}

	public void generateRocks(int age) {
		float e = actors.values().size();
		e = e > 0 ? e : 1;
		if (rocks/e < density) {
			addActor(Stage.createSpaceRock(age));
			rocks++;
		}
	}

	public static Actor createBullet(int birth, Actor sender) {
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
		b.x = sender.x+130;
		b.y = sender.y+10;
		b.w = 80;
		b.h = 48;
		b.face = Stage.textures.get(Rol.CAT_BULLET);
		b.id = Stage.nactors++;
		return b;
	}

	public static Actor createSpaceRock(int birth) {
		Polygon p = SteadyGrowth.generateRandomPolygon(3);
		Actor b = new Actor(Rol.ICECREAM_ROCK, p, true, true, birth);
		Vector centroid = p.getCentroid();
		b.x = (int) centroid.x;
		b.y = (int) centroid.y;
		b.face = Stage.textures.get(Rol.ICECREAM_ROCK);
		b.id = Stage.nactors++;
		return b;
	}

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



