package main.game.entities.mobs;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import main.game.Game;
import main.game.entities.Entity;
import main.game.entities.Player;
import main.rendering.Renderer;

public class EnemyBoss extends Enemy {

	Random rand = new Random();
	boolean canAttack = true;

	int attacks = 0;
	boolean attacking = false;

	public EnemyBoss(float x, float y) {
		super(x, y);
		tex = 25;
		life = 200 * Game.difficulty;
	}
	int time = 0;
	int counter = 0;

	float xa, ya, xd, yd;
	float smoothing = 0.8f;
	float speed = 0.015f;

	int attackCounter = 0;

	public void update() {
		System.out.println("ATTAQUES: " + attacks + " - ");

		time++;
		counter++;

		Entity e = getNearBy(5);

			if (e instanceof Player && attacks >= 3) {
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

		Entity near = getNearBy(10);
		if (near instanceof Player) {
			if (attacks < 3) {
				attackCounter++;
				if (attackCounter % (15 * 60) == 0) attacking = false;
				if (!attacking) {
					attacking = true;
					for (int i = 0; i < 5; i++) {
						Game.getGame().getLevel().add(new EnemyDoubleHead(x, y));
					}
					attacks++;
				}
			}
		}

		if (canAttack) {
			Entity e2 = getNearBy(1);
			if (e2 instanceof Player) {
				e2.giveDammage(4, this);
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

	public void render() {
		glBegin(GL_QUADS);
		Renderer.bigMobData(x, y, tex);
		glEnd();
	}

	public void renderGUI() {

	}
}
