package main.game.level.tiles.items.weapons;

import main.game.entities.weapons.WeaponFive;
import main.rendering.Renderer;

public class WaterBottleItem extends WeaponItem {

	public WaterBottleItem(float x, float y) {
		super(x, y);
		tex = 1;
		weapon = new WeaponFive();
	}
	
	public void render() {
		Renderer.renderItem(x, h, y+1, 4);
	}
	
	public void renderGUI() {
		
	}
}
