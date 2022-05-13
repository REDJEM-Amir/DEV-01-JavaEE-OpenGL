package main.game.level.tiles.items.weapons;

import main.game.entities.weapons.WeaponFour;
import main.rendering.Renderer;

public class PingPongItem extends WeaponItem {

	public PingPongItem(float x, float y) {
		super(x, y);
		tex = 1;
		weapon = new WeaponFour();
	}

	public void render() {
		Renderer.renderItem(x, h, y-1, 3);
	}
	
	public void renderGUI() {
		
	}
}
