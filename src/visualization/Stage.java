package geom.visualization;

import geom.structures.Polygon;
import geom.math.Vector;
import java.util.LinkedList;

import processing.core.PImage;
import processing.core.PShape;
import processing.event.Event;


public class Stage {

	float speed;
	float aceleration;
	LinkedList<Actor> actors;

	class Actor {
		Rol r;
		Polygon skeleton;
		PShape shape;
		PImage face;
		boolean inStage;
		boolean rTl;
		int birth;
	} 

	public Stage () {
		actors =  new LinkedList<>();
	}

	public static Actor createBullet() {
		Polygon p =  new Polygon();
		return new Actor()
	}

}

enum Rol {
    CAT_SHIP,
    CAT_BULLET, 
    ICECREAM_ASTEROID
};  


