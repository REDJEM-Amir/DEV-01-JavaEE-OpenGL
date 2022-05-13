package main.game.entities.particles;

import java.util.Random;

import main.game.Game;
import main.game.level.Level;
import main.rendering.Renderer;
import main.rendering.Texture;

public class Particle {
	
	public boolean removed = false;
	
	float x, y, z;
	float dx, dy, dz;
	float speed = 0.01f;
	public Level level;
	
	Random rand = new Random();
	int time;
	
	public Particle(float x, float y, float z, float force, int time) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.time = time;
		
		dx = (float) rand.nextGaussian();
		dy = (float) rand.nextGaussian();
		dz = (float) rand.nextGaussian();
		
		speed = force;
		
		level = Game.getGame().getLevel();
	}
	
	int timer = 0;
	public void update() {
		if (time != 0) {
			timer++;
		}
		
		if (timer + rand.nextInt(60) > time) {
			removed = true;
		}
		
		x += dx * speed;
		y += dy * speed;
		z += dz * speed;
		
		if (y <= -1 + 0.05f) {
			y = -1 + 0.05f;
			speed *= 0.9f;
		}
		
		if (level.isWall(x, z, 0.05f)) {
			speed = 0;
		}

		dy -= 0.1f;			
		
	}
	
	public void render() {
		if (z < Game.getGame().getPlayer().y + 3) {
			float px = Game.getGame().getPlayer().x;
			float py = Game.getGame().getPlayer().y;
			float xx = x - (px);
			float yy = z - (py);
			float dist = (float) Math.sqrt(xx * xx + yy * yy);
			if (dist < 10) {
				Texture.unbind();
				Renderer.quadData(x, y, z, 0.05f);							
			}
		}
	}
}
