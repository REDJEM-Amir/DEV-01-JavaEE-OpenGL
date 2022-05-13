package main.game.level.tiles.items;

import main.game.level.tiles.Tile;

public abstract class Item extends Tile {
	protected float h;
	
	public boolean removed = false;
	
	public Item(float x, float y) {
		super(x, y);
	}
}