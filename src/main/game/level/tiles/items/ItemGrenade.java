package main.game.level.tiles.items;

import main.Main;
import main.audio.Audio;
import main.game.Game;
import main.game.entities.Player;
import main.rendering.Renderer;

public class ItemGrenade extends Item {
	
	boolean used = false;
	boolean tryToUse = false;
	boolean canUse = false;
	
	public ItemGrenade(float x, float y) {
		super(x, y);
		tex = 0;
	}
	
	public void update() {
		if (!removed) {
			Player p = Game.getGame().getPlayer();
			
			if (p.x > x - 0.5f && p.y > y - 0.5f && p.x < x + 1.5f && p.y < y + 1.5f) {
				p.addPotato(this);
				Main.getMain().playSound(Audio.POTATO_PICKUP);
				removed = true;
			}
		}
	}
	
	public void render() {
		Renderer.renderItem(x, h, y+1, 8 * 2);
	}
	
	public void renderGUI() {
		
	}
}
