package main.game.entities;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import main.Main;
import main.audio.Audio;
import main.game.Game;
import main.game.entities.particles.ParticleSystem;
import main.rendering.Texture;

public class ItemGrenade extends Entity {
	

	public boolean throwP = false;
	
	public float x, y = -1f, z;
	public float xd, yd, zd;

	public ItemGrenade(float x, float y, float force, float xd, float zd) {
		super(x, y);
		this.x = x - 0.5f;
		this.y = -0.7f;
		this.z = y;
		this.xd = xd;
		this.zd = zd;
		this.xd *= force;
		this.zd *= force;
		
		yd = 0.000001f;
	}
	
	int time = 0;
	public void update() {
		time++;
		x += xd;
		y += yd * 0.1f;
		z += zd;
		
		if (y <= -1.2) y = -1.2f;
		
		
		if (Game.getGame().getLevel().isWall(x, z, 0.1f)) {
			xd = 0;
			zd = 0;
		}
		
		xd *= 0.9f;
		zd *= 0.9f;
		yd -= 0.1f;
		
		if (time > 120) {
			ParticleSystem p = new ParticleSystem(x, y, z, 200, new Vector4f(1, 0.8f, 0.5f, 1), 0.07f, 120);
			Game.getGame().getLevel().add(p);
			List<Entity> near = getNearByEntities(2, 0, 0);
			for (int i = 0; i < near.size(); i++) {
				Entity e = near.get(i);
				if (e instanceof Player) continue;
				e.giveDammage(20, this);
			}
			Main.getMain().playSound(Audio.EXPLOSION);
			removed = true;
		}
		
	}
	
	public void render() {
		Texture.POTATO.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0); glVertex3f(x, y, z);
			glTexCoord2f(1, 0); glVertex3f(x + 0.5f, y, z);
			glTexCoord2f(1, 1); glVertex3f(x + 0.5f, y + 0.5f, z);
			glTexCoord2f(0, 1); glVertex3f(x, y + 0.5f, z);
		glEnd();
		Texture.unbind();
	}
	
	public void renderGUI() {
		
	}
	
	public void throwPotato(Player p, float force) {
		if (throwP) return;
		
		throwP = true;

	}
}
