package main.game.entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import main.game.Game;
import main.game.entities.weapons.WeaponThree;
import main.game.entities.weapons.WeaponFour;
import main.game.entities.weapons.PlayerWeapon;
import main.game.entities.weapons.WeaponTwo;
import main.game.entities.weapons.WeaponOne;
import main.game.entities.weapons.WeaponFive;
import main.game.entities.weapons.Weapon;
import main.game.level.tiles.items.ItemPotion;
import main.rendering.Gui;
import main.rendering.Texture;

public class PlayerInventory {
	
	public List<PlayerWeapon> playerWeapons;
	public List<Weapon> weapons;
	public List<ItemPotion> potions;
	
	public List<WeaponThree> chikens;
	public List<WeaponFour> pingpongs;
	public List<WeaponTwo> rawSteaks;
	public List<WeaponOne> steaks;
	public List<WeaponFive> bottles;
	
	int slot = 0;
	
	int x, y;
	
	public static boolean showInv = false;
	
	public List<Weapon> craft;
	
	public PlayerInventory() {
		playerWeapons = new ArrayList<PlayerWeapon>();
		weapons = new ArrayList<Weapon>();
		potions = new ArrayList<ItemPotion>();
		
		chikens = new ArrayList<WeaponThree>();
		pingpongs = new ArrayList<WeaponFour>();
		rawSteaks = new ArrayList<WeaponTwo>();
		steaks = new ArrayList<WeaponOne>();
		bottles = new ArrayList<WeaponFive>();
		
		craft = new ArrayList<Weapon>();
	}
	
	public void renderInv() {
		if (!showInv) return;
		x = 0;
		y = 0;
		Gui.drawString("Inventaire", x + Display.getWidth() / 2, y + Display.getHeight() / 2 - 200, 32, true);
		Gui.color(0f, 0f, 0f, 0.5f);
		Gui.drawQuad(x + Display.getWidth() / 2 - 300, y + Display.getHeight() / 2 - 150, 600, 300);
		
		
		int potionX = x + Display.getWidth() / 2 - 250;
		int potionY = y + Display.getHeight() / 2 - 130;
		
		Gui.drawString("Potions: " + potions.size(), potionX + 30, potionY + 20, 24, false);
		
		if (Gui.button("Utiliser", potionX + 400, potionY + 32, 175)) {
			if (potions.size() > 0) {
				potions.remove(0);
				Game.getGame().getPlayer().addLife(5);
			}			
		}
		
		int craftX = x + Display.getWidth() / 2;
		int craftY = y + Display.getHeight() / 2;
		
		
		Gui.drawString("" + rawSteaks.size(), craftX - 80 * 3 + 24, craftY - 55, 24);
		Gui.drawString("" + steaks.size(), craftX - 80 * 2 + 24, craftY - 55, 24);
		Gui.drawString("" + chikens.size(), craftX - 80 * 1 + 24, craftY - 55, 24);
		Gui.drawString("" + pingpongs.size(), craftX - 80 * 0 + 24, craftY - 55, 24);
		Gui.drawString("" + bottles.size(), craftX + 80 * 1 + 24, craftY - 55, 24);
		Gui.drawString("" + 0, craftX + 80 * 2 + 24, craftY - 55, 24);
		
		if (Gui.button("+", craftX - 80 * 3 + 35, craftY + 55, 70)) {
			if (rawSteaks.size() > 0 && craft.size() < 6) {
				craft.add(rawSteaks.get(0));
				rawSteaks.remove(0);
			}
		}
		if (Gui.button("+", craftX - 80 * 2 + 35, craftY + 55, 70)) {
			if (steaks.size() > 0 && craft.size() < 6) {
				craft.add(steaks.get(0));
				steaks.remove(0);
			}
		}
		if (Gui.button("+", craftX - 80 * 1 + 35, craftY + 55, 70)) {
			if (chikens.size() > 0 && craft.size() < 6) {
				craft.add(chikens.get(0));
				chikens.remove(0);
			}
		}
		if (Gui.button("+", craftX - 80 * 0 + 35, craftY + 55, 70)) {
			if (pingpongs.size() > 0 && craft.size() < 6) {
				craft.add(pingpongs.get(0));
				pingpongs.remove(0);
			}
		}
		if (Gui.button("+", craftX + 80 * 1 + 35, craftY + 55, 70)) {
			if (bottles.size() > 0 && craft.size() < 6) {
				craft.add(bottles.get(0));
				bottles.remove(0);
			}
		}
		if (Gui.button("+", craftX + 80 * 2 + 35, craftY + 55, 70)) {

		}
		
		
		if (Gui.button("Actualiser", craftX + 185, craftY + 93, 225, true)) {
			for (int i = 0; i < craft.size(); i++) {
				add(craft.get(i));
			}
			craft.clear();
		}
		if (Gui.button("Artisanat", craftX + 185, craftY + 130, 225, true)) {
			if (!craft.isEmpty()) {
				PlayerWeapon w = new PlayerWeapon();
				
				for (int i = 0; i < craft.size(); i++) {
					w.add(craft.get(i));
				}
				
				craft.clear();
				w.maxLife = w.life;
				playerWeapons.add(w);
			}
		}
		
		Texture.WEAPONS.bind();
		
		float[][] powerCoords = getCoords(0, 8, 8);
		
		for (int i = 0; i < craft.size(); i++) {
			Weapon w = craft.get(i);
			powerCoords = getCoords(w.tex, 8, 8);
			Gui.drawTexturedQuad(craftX - 64 * 4 - 44 + i * 64, craftY + 80, 64, 64, powerCoords);
		}
		
		powerCoords = getCoords(0, 8, 8);
		Gui.drawTexturedQuad(craftX - 80 * 3, craftY - 32, 64, 64, powerCoords);
		
		powerCoords = getCoords(1, 8, 8);
		Gui.drawTexturedQuad(craftX - 80 * 2, craftY - 32, 64, 64, powerCoords);
		
		powerCoords = getCoords(2, 8, 8);
		Gui.drawTexturedQuad(craftX - 80 * 1, craftY - 32, 64, 64, powerCoords);
		
		powerCoords = getCoords(3, 8, 8);
		Gui.drawTexturedQuad(craftX + 80 * 0, craftY - 32, 64, 64, powerCoords);
		
		powerCoords = getCoords(4, 8, 8);
		Gui.drawTexturedQuad(craftX + 80 * 1, craftY - 32, 64, 64, powerCoords);
		
		powerCoords = getCoords(5, 8, 8);
		Gui.drawTexturedQuad(craftX + 80 * 2, craftY - 32, 64, 64, powerCoords);
		
		Texture.unbind();
		
	}
	
	
	public void renderBottom() {
		Gui.color(0f, 0f, 0f, 0.5f);
		Gui.drawQuad(Display.getWidth() / 2 - 200, Display.getHeight() - 50, 400, 200);
		
		Gui.color(0, 0, 0, 1);
		Gui.drawQuad(Display.getWidth() / 2 - 3 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		Gui.drawQuad(Display.getWidth() / 2 - 2 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		Gui.drawQuad(Display.getWidth() / 2 - 1 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		
		Gui.drawQuad(Display.getWidth() / 2 + 1 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		Gui.drawQuad(Display.getWidth() / 2 + 2 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		Gui.drawQuad(Display.getWidth() / 2 + 3 * 44 - 22 + 2, Display.getHeight() - 44, 40, 40);
		
		Gui.color(1, 1, 1, 1);
		Gui.drawQuad(Display.getWidth() / 2 - 3 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		Gui.drawQuad(Display.getWidth() / 2 - 2 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		Gui.drawQuad(Display.getWidth() / 2 - 1 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		
		Gui.drawQuad(Display.getWidth() / 2 + 1 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		Gui.drawQuad(Display.getWidth() / 2 + 2 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		Gui.drawQuad(Display.getWidth() / 2 + 3 * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
		
		Gui.color(0, 0, 0, 0.5f);
		
			if (slot <= 2) {
				Gui.drawQuad(Display.getWidth() / 2 + (slot - 3) * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);
			}else {
				Gui.drawQuad(Display.getWidth() / 2 + (slot - 2) * 44 + 2 - 22 + 2, Display.getHeight() - 44 + 2, 36, 36);				
			}
		
		Gui.color(1, 1, 1, 1);
		
		for (int i = 0; i < playerWeapons.size(); i++) {
			PlayerWeapon w = playerWeapons.get(i);
			if (i <= 2) {
				w.renderGUI(Display.getWidth() / 2 - 155 + (i) * 44, Display.getHeight() - 50 + 6);
			}else {
				w.renderGUI(Display.getWidth() / 2 - 155 + (i + 1) * 44, Display.getHeight() - 50 + 6);				
			}
		}
		
		if (Gui.button("Inventaire", Display.getWidth() / 2, 20, 225)) {
			showInv = !showInv;
		}
		
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				showInv = !showInv;
			}
		}
	}
	
	public void add(Weapon w) {
		if (w instanceof WeaponThree) {
			chikens.add((WeaponThree) w);
			
		}else if (w instanceof WeaponFour) {
			pingpongs.add((WeaponFour) w);
			
		}else if (w instanceof WeaponTwo) {
			rawSteaks.add((WeaponTwo) w);
			
		}else if (w instanceof WeaponOne) {
			steaks.add((WeaponOne) w);
			
		}else if (w instanceof WeaponFive) {
			bottles.add((WeaponFive) w);
		}else {
			weapons.add(w);
		}
	}
	
	public void add(ItemPotion p) {
		potions.add(p);
	}
	
	public float[][] getCoords(int tex, int w, int h) {
		float[][] r = new float[4][2];
		
		int xo = tex % w;
		int yo = tex / w;
		
		r[0][0] = (0 + xo) / (float)w;		r[0][1] = (0 + yo) / (float)h;
		r[1][0] = (1 + xo) / (float)w;		r[1][1] = (0 + yo) / (float)h;
		r[2][0] = (1 + xo) / (float)w;		r[2][1] = (1 + yo) / (float)h;
		r[3][0] = (0 + xo) / (float)w;		r[3][1] = (1 + yo) / (float)h;
		
		return r;
	}
	
	public void update() {
		
		int d = (int) (Mouse.getDWheel() * 0.0095f);
		slot += (int) (d);
		if (slot > 5) slot = 0;
		if (slot < 0) slot = 5;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			slot = 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			slot = 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			slot = 3;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
			slot = 4;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
			slot = 0;
		}

		if (playerWeapons.size() > slot) {
			if (playerWeapons.get(slot).removed) {
				playerWeapons.remove(playerWeapons.get(slot));
			}
		}
	}
	
	public void render(float x, float y, int dir, boolean attack) {
		if (playerWeapons.size() > slot) {
			playerWeapons.get(slot).render(x, y, dir, attack);
		}
	}
	
	public PlayerWeapon getWeapon() {
		if (playerWeapons.size() > slot) {
			return playerWeapons.get(slot);			
		}
		return null;
	}
}
