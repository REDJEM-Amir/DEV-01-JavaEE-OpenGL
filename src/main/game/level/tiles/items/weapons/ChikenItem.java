package main.game.level.tiles.items.weapons;

import main.game.entities.weapons.WeaponThree;
import main.rendering.Renderer;

public class ChikenItem extends WeaponItem {

	public ChikenItem(float x, float y) {
		super(x, y);
		tex = 1;
		weapon = new WeaponThree();
	}

	public void render() {
		Renderer.renderItem(x, h, y+1, 2);
	}
	
	public void renderGUI() {
		
	}
}
