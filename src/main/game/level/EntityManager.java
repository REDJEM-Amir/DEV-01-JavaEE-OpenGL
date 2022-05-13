package main.game.level;

import java.util.ArrayList;
import java.util.List;

import main.game.Game;
import main.game.entities.Entity;
import main.game.entities.PlayerInventory;
import main.game.entities.Player;
import main.game.entities.mobs.EnemyBoss;
import main.game.entities.mobs.MillipedeEnemy;
import main.game.entities.mobs.EnemyWeird;
import main.game.entities.mobs.EnemyWorm;
import main.game.level.tiles.items.ItemKey;
import main.game.level.tiles.items.weapons.ChikenItem;
import main.game.level.tiles.items.weapons.RawSteakItem;
import main.game.level.tiles.items.weapons.SteakItem;
import main.rendering.Texture;

public class EntityManager {
	List<Entity> entities = new ArrayList<Entity>();
	
	public void add(Entity e) {
		entities.add(e);
	}
	
	public void updateEntities(Level level) {
		entities.get(0).update();
		
		if (PlayerInventory.showInv) return;
		for (int i = 0; i < entities.size(); i++) {
			if (i == 0) continue;
			Entity e = entities.get(i);
			
			if (e.dead) {
				e.removed = true;
			}
			
			if (!(e instanceof Player)) {
				if (e.removed) {
					if (e instanceof EnemyWorm) {
						if (Math.random() > 0.5f)
							Game.getGame().getLevel().items.add(new RawSteakItem(e.x - 0.5f, e.y - 0.5f));
					} else if (e instanceof MillipedeEnemy) {
						if (Math.random() > 0.5f)
							Game.getGame().getLevel().items.add(new SteakItem(e.x - 0.5f, e.y - 0.5f));
					}else if (e instanceof EnemyWeird) {
						if (Math.random() > 0.5f)
							Game.getGame().getLevel().items.add(new ChikenItem(e.x - 0.5f, e.y - 0.5f));
					}else if (e instanceof EnemyBoss) {
						Game.getGame().getLevel().items.add(new ItemKey(e.x - 0.5f, e.y - 0.5f, 0xff900000));
					}
					
					entities.remove(e);
					continue;
				}
			}
			
			e.update();
		}
	}
	
	public void renderEntities() {
		Texture.MOBS.bind();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render();
		}
	}
	
	public void renderGUIEntities() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			
			e.renderGUI();
		}
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
}
