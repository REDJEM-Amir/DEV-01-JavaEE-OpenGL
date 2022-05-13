package main.game.level.tiles;

public abstract class Tile {
	public float x, y;
	public int tex;
	public boolean solid = false;
	public boolean block = false;
	public boolean vegetation = false;
	
	public Tile(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void update();
	public void render() {};
	public abstract void renderGUI();
}
