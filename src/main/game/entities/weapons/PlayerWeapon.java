package main.game.entities.weapons;

import java.util.ArrayList;
import java.util.List;


import main.rendering.Gui;
import main.rendering.Renderer;
import main.rendering.Texture;
import static org.lwjgl.opengl.GL11.*;

public class PlayerWeapon {
	List<Weapon> weapons = new ArrayList<Weapon>();
	
	public int life;
	public int maxLife;
	
	boolean attacking = false;
	float attackSpeed = 0.2f;
	public boolean attack = false;
	
	public int power = 1;
	public boolean removed = false;
	
	public PlayerWeapon() {
		
	}
	
	public void add(Weapon weapon) {
		if (weapons.size() < 6) {
			power += weapon.power;
			life += weapon.life;
			weapons.add(weapon);
		}
	}
	
	int time = 0;
	boolean hasAttack = false;
	public void update() {
		if (attack) {
			attack = false;
		}
		if (attacking) {
			time++;	
			
			if (time * attackSpeed >= Math.PI * 2) {
				time = 0;
				attacking = false;
			}
			
			if (!hasAttack) {
				if (time * attackSpeed >= Math.PI * 1.5f) {
					attack = true;
					hasAttack = true;
				}
			}
		}else {
			time = 0;
			hasAttack = false;
		}
		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = weapons.get(i);
			w.update();
		}
		
		if (life - 1  <= 0) {
			removed = true;
		}
	}
	
	public void render(float x, float y, int dir, boolean attack) {
		Texture.WEAPONS.bind();
		glPushMatrix();
		
		if (attack) {
			attacking = true;
			attack = false;
		}
		
		if (dir == 0) {
			glTranslatef(x, 0, y);
			glRotatef((float) Math.sin(-time * attackSpeed) * 30, 0, 0, 1);
			glTranslatef(-x, 0, -y);
			
			renderWeapon(x - 0.4f - 0.5f, -0.1f, y - 0.5f, true);
		}else if (dir == 1) {
			glTranslatef(x, 0, y);
			glRotatef((float) Math.sin(time * attackSpeed) * 30, 0, 0, 1);
			glTranslatef(-x, 0, -y);

			renderWeapon(x - 0.4f + 0.4f, -0.1f, y - 0.2f, false);
		}else if (dir == 2) {
			glTranslatef(x, 0, y);
			glRotatef((float) Math.sin(-time * attackSpeed) * 30, 0, 0, 1);
			glTranslatef(-x, 0, -y);

			renderWeapon(x - 0.5f - 0.3f, -0.1f, y - 0.2f, true);
		}else if (dir == 3) {
			glTranslatef(x, 0, y);
			glRotatef((float) Math.sin(time * attackSpeed) * 30, 0, 0, 1);
			glTranslatef(-x, 0, -y);

			
			renderWeapon(x - 0.5f + 0.4f, -0.3f, y - 0.5f, false);
		}
		glPopMatrix();
		Texture.MOBS.bind();
	}
	
	public void renderWeapon(float x, float h, float y, boolean flip) {
		if (weapons.size() > 0)
			Renderer.weaponData(x + 0.5f, h - 0.3f, y, weapons.get(0).tex, flip, 0.4f);
		for (int i = 0; i < weapons.size(); i++) {
			Weapon w = weapons.get(i);
			if (i == 0) {
								
			}else {
				if (flip) {
					Renderer.weaponData(x + 0.8f, h - 0.1f, y + 0.2f, w.tex, flip, 0.2f, i);					
				}else {					
					Renderer.weaponData(x + 0.8f, h - 0.1f, y + 0.2f, w.tex, flip, 0.2f, i);					
				}
			}
		}
	}
	
	public void renderGUI(int x, int y) {
		Texture.WEAPONS.bind();
		for (int i = 0; i < weapons.size(); i++) {
			
			Weapon w = weapons.get(i);
			if (i == 0) {
				w.renderGUI(x, y, 40);				
			}else {				
				w.renderGUI(x + i * 5, y - 1, 16);			
			}

		}
		Texture.unbind();
		
		if (!weapons.isEmpty()) {
			Gui.color(0, 0, 0, 1);
			Gui.drawQuad(x + 3, y + 35, 40, 5);
			
			Gui.color(0, 1, 0, 1);
			Gui.drawQuad(x + 4, y + 35 + 1, ((float)life / (float)maxLife) * 38.0f, 5 - 2);
			Gui.color(1, 1, 1, 1);
		}
	}
}
