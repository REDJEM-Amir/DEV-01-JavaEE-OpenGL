package main.game.level.tiles.items.weapons;

import main.game.entities.weapons.WeaponTwo;
import main.rendering.Renderer;

public class RawSteakItem extends WeaponItem {

	public RawSteakItem(float x, float y) {
		super(x, y);
		tex = 1;
		weapon = new WeaponTwo();
	}

	public void render() {
		Renderer.renderItem(x, h, y+1, 0);
	}
	
	public void renderGUI() {
		
	}
}
