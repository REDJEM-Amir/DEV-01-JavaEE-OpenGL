package main.game.entities.particles;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import main.game.entities.Entity;
import main.rendering.Gui;
import main.rendering.Renderer;
import main.rendering.Texture;

public class ParticleSystem extends Entity {
	
	List<Particle> particles = new ArrayList<Particle>();
	Vector4f color;
	
	public ParticleSystem(float x, float y, float z, int num, Vector4f color) {
		super(x, z);
		
		this.color = color;
		for (int i = 0; i < num; i++) {
			particles.add(new Particle(x, y, z, 0.01f, 0));
		}
	}
	
	public ParticleSystem(float x, float y, float z, int num, Vector4f color, float force, int time) {
		super(x, z);
		
		this.color = color;
		for (int i = 0; i < num; i++) {
			particles.add(new Particle(x, y, z, force, time));
		}
	}

	public void update() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if (p.removed) particles.remove(p);
			p.update();
		}
		
		if (particles.isEmpty()) removed = true;
	}
	
	public void render() {
		Texture.unbind();
		Gui.color(color.x, color.y, color.z, color.w);
		Renderer.begin();
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.render();
		}	
		Renderer.end();
	}
	
	public void renderGUI() {

	}
}