package main.game.entities.mobs;

import java.util.Random;

import main.game.Game;
import main.game.entities.Entity;
import main.game.entities.Player;

public class EnemyDoubleHead extends Enemy {

	Random rand = new Random();
	boolean canAttack = true;
	
	int startCounter = 0; 
	
	public EnemyDoubleHead(float x, float y) {
		super(x, y);
		tex = 5 * 8;
		life = 10 * Game.difficulty;
	}
	int time = 0;
	int counter = 0;
	
	float xa, ya, xd, yd;
	float smoothing = 0.8f;
	float speed = 0.08f;
	public void update() {
		time++;
		counter++;
		startCounter++;
		
		Entity e = getNearBy(5);
		
		if (e instanceof Player && startCounter > 120) {
			xd = e.x - x;
			yd = e.y - y;
			float dist = (float)Math.sqrt(xd * xd + yd * yd);
			xd /= dist;
			yd /= dist;
		}else {
			if (time % (rand.nextInt(200) + 1) == 0) {
				xd = (float) rand.nextInt(3) - 1;
				yd = (float) rand.nextInt(3) - 1;
				if (rand.nextInt(4) == 0) {
					xd = 0;
					yd = 0;
				}
			}
		}
		
		xa += fx;
		ya += fy;
		
		fx *= 0.01f;
		fy *= 0.01f;

		xa += xd * speed * (1 - smoothing);
		ya += yd * speed * (1 - smoothing);
		
		move(xa, ya);
		
		xa *= smoothing;
		ya *= smoothing;
		
		if (xd != 0 || yd != 0) {
			if (time % 10 == 0) {
				if (xa >= 0) {
					tex++;
					if (tex > 5 * 8 + 3) tex = 5 * 8;
				}else {
					if (tex < 5 * 8 + 4) tex = 5 * 8 + 4;
					tex++;
					if (tex > 5 * 8 + 7) tex = 5 * 8 + 4;
				}
			}
		}else {
			
		}
		if (canAttack) {
			Entity e2 = getNearBy(1);
			if (e2 instanceof Player) {
				e2.giveDammage(2, this);
				canAttack = false;
			}
		}else {
			counter++;
			if (counter >= 250) {
				canAttack = true;
				counter = 0;
			}
		}
	}
	
	public void renderGUI() {
		
	}
}
