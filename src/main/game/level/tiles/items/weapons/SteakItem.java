package main.game.level.tiles.items.weapons;

import main.game.entities.weapons.WeaponOne;
import main.rendering.Renderer;

public class SteakItem extends WeaponItem {

	public SteakItem(float x, float y) {
		super(x, y);
		tex = 1;
		weapon = new WeaponOne();
	}

	public void render() {
		Renderer.renderItem(x, h, y+1, 1);
	}
	
	public void renderGUI() {
		
	}
}
