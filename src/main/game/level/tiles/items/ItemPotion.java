package main.game.level.tiles.items;

import org.lwjgl.opengl.Display;

import main.Main;
import main.audio.Audio;
import main.game.Game;
import main.game.entities.Player;
import main.rendering.Gui;
import main.rendering.Renderer;

public class ItemPotion extends Item {
	
	boolean used = false;
	boolean tryToUse = false;
	boolean canUse = false;
	
	public ItemPotion(float x, float y) {
		super(x, y);
		tex = 0;
	}
	
	public void update() {
		if (!removed) {
			Player p = Game.getGame().getPlayer();
			
			if (p.x > x - 0.5f && p.y > y - 0.5f && p.x < x + 1.5f && p.y < y + 1.5f) {
				p.addPotion(this);
				Main.getMain().playSound(Audio.KEY_PICKUP);
				removed = true;
			}
		}
	}
	
	public void use(Player p) {
		if (!used) {
			tryToUse = true;
			if (canUse) {
				p.addLife(5);
				canUse = false;
				used = true;				
			}
		}
	}
	
	public void render() {
		Renderer.renderItem(x, h, y+1, 8 * 2 + 1);
	}
	
	public void renderGUI() {
		if (tryToUse) {
			if (Game.getGame().getPlayer().life >= 10) {
				Gui.drawString("La vie pleine !", Display.getWidth() / 2, Display.getHeight() / 2, 48, true);
			}else {
				canUse = true;
			}
		}
	}

}