package geom.visualization;

import geom.structures.Polygon;
import geom.math.Vector;
import java.util.LinkedList;
import java.util.Arrays;

import processing.core.PImage;
import processing.core.PShape;
import processing.event.Event;


public class Stage {
	int width;
	int height;
	int speed;
	int aceleration;
	int score;
	int level;
	LinkedList<Actor> actors;

	static class Actor {
		Rol r;
		Polygon skeleton;
		PShape shape;
		PImage face;
		boolean inStage;
		boolean rTl;
		int birth;


	} 

	public Stage (int w, int h, int s, int a) {
		width = w;
		height = h;
		speed = s;
		aceleration = a;
		actors =  new LinkedList<>();
		score = 0;
		level = 0;
	}

	public static Actor createBullet() {
		Polygon p =  new Polygon();
		return new Actor();
	}

	public static Actor createRock() {
		Polygon p =  new Polygon();
		return new Actor();
	}

}

enum Rol {
    CAT_SHIP,
    CAT_BULLET, 
    ICECREAM_ROCK
};  



