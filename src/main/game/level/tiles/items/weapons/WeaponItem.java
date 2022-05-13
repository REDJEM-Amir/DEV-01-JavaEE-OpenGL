package main.game.level.tiles.items.weapons;

import main.game.Game;
import main.game.entities.Player;
import main.game.entities.weapons.Weapon;
import main.game.level.tiles.items.Item;

public abstract class WeaponItem extends Item {
	protected Weapon weapon;
	
	public WeaponItem(float x, float y) {
		super(x, y);
	}
	
	int time = (int) (Math.random() * 360);
	public void update() {
		if (removed) return;
		time++;
		
		h = (float) Math.sin(time * 0.1f) * 0.1f;
		
		Player player = Game.getGame().getPlayer();
		
		if (player.x > x - 0.5f && player.y > y - 0.5f && player.x < x + 1.5f && player.y < y + 1.5f) {
			player.add(weapon);
			removed = true;
		}
	}

}
