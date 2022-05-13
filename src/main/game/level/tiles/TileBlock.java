package main.game.level.tiles;

public abstract class TileBlock extends Tile{
	
	public int wallTex = 0;
	
	public TileBlock(float x, float y) {
		super(x, y);
		block = true;
	}
}
